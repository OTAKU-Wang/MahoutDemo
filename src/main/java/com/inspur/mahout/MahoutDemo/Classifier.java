package com.inspur.mahout.MahoutDemo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ansj.domain.Term;
import org.ansj.recognition.NatureRecognition;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.util.FilterModifWord;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.classifier.naivebayes.StandardNaiveBayesClassifier;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;
import org.apache.mahout.math.RandomAccessSparseVector;
import org.apache.mahout.math.Vector;
import org.apache.mahout.math.Vector.Element;
import org.apache.mahout.vectorizer.TFIDF;

import com.google.common.collect.ConcurrentHashMultiset;
import com.google.common.collect.Multiset;

/**
 * 
 */
public class Classifier {
	
	public static Map<String, Integer> readDictionnary(Configuration conf, Path dictionnaryPath) {
		Map<String, Integer> dictionnary = new HashMap<String, Integer>();
		for (Pair<Text, IntWritable> pair : new SequenceFileIterable<Text, IntWritable>(dictionnaryPath, true, conf)) {
			dictionnary.put(pair.getFirst().toString(), pair.getSecond().get());
		}
		return dictionnary;
	}

	public static Map<Integer, Long> readDocumentFrequency(Configuration conf, Path documentFrequencyPath) {
		Map<Integer, Long> documentFrequency = new HashMap<Integer, Long>();
		for (Pair<IntWritable, LongWritable> pair : new SequenceFileIterable<IntWritable, LongWritable>(documentFrequencyPath, true, conf)) {
			documentFrequency.put(pair.getFirst().get(), pair.getSecond().get());
		}
		return documentFrequency;
	}

	public static void GetClassifierResult(String content,String modelPath,String labelIndexPath,String dictionaryPath,String documentFrequencyPath,String StopFile) throws IOException{
		Configuration configuration=new Configuration();
		NaiveBayesModel model=NaiveBayesModel.materialize(new Path(modelPath), configuration);
		StandardNaiveBayesClassifier classifier =new StandardNaiveBayesClassifier(model);
		Map<Integer, String> labels = BayesUtils.readLabelIndex(configuration, new Path(labelIndexPath));
		Map<String, Integer> dictionary = readDictionnary(configuration, new Path(dictionaryPath));
		Map<Integer, Long> documentFrequency = readDocumentFrequency(configuration, new Path(documentFrequencyPath));
		int labelCount = labels.size();
		int documentCount = documentFrequency.get(-1).intValue();
		System.out.println("Number of labels: " + labelCount);
		System.out.println("Number of documents in training set: " + documentCount);
		WordsBag.addStopWord(StopFile);
		List<Term> temp=NlpAnalysis.parse(content);
		new NatureRecognition(temp).recognition();
		temp=FilterModifWord.modifResult(temp);
		Multiset<String> words = ConcurrentHashMultiset.create();
		int wordCount=0;
		for(Term t:temp){
			Integer wordId=dictionary.get(t.getName());
			if(wordId!=null){
			words.add(t.getName());
			wordCount++;
			}
		}
		Vector vector = new RandomAccessSparseVector(10000);
		TFIDF tfidf = new TFIDF();
		for(Multiset.Entry<String> entry:words.entrySet()){
			String word = entry.getElement();
			int count = entry.getCount();
			Integer wordId = dictionary.get(word);
			Long freq = documentFrequency.get(wordId);
			double tfIdfValue = tfidf.calculate(count, freq.intValue(), wordCount, documentCount);
			vector.setQuick(wordId, tfIdfValue);
		}
		Vector resultVector = classifier.classifyFull(vector);
		double bestScore = -Double.MAX_VALUE;
		int bestCategoryId = -1;
		for(Element element: resultVector.all()) {
			int categoryId = element.index();
			double score = element.get();
			if (score > bestScore) {
				bestScore = score;
				bestCategoryId = categoryId;
			}
			System.out.print("  " + labels.get(categoryId) + ": " + score);
		}
		System.out.println(" => " + labels.get(bestCategoryId));
	}
	
}
