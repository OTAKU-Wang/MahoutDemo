
package com.inspur.mahout.MahoutDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.mahout.classifier.naivebayes.BayesUtils;
import org.apache.mahout.classifier.naivebayes.NaiveBayesModel;
import org.apache.mahout.common.Pair;
import org.apache.mahout.common.iterator.sequencefile.SequenceFileIterable;

/**
 * 
 */
public class TopCategoryWords {
	
	public static Map<Integer, String> readInverseDictionnary(Configuration conf, Path dictionnaryPath) {
		Map<Integer, String> inverseDictionnary = new HashMap<Integer, String>();
		for (Pair<Text, IntWritable> pair : new SequenceFileIterable<Text, IntWritable>(dictionnaryPath, true, conf)) {
			inverseDictionnary.put(pair.getSecond().get(), pair.getFirst().toString());
		}
		return inverseDictionnary;
	}
	
	public static Map<Integer, Long> readDocumentFrequency(Configuration conf, Path documentFrequencyPath) {
		Map<Integer, Long> documentFrequency = new HashMap<Integer, Long>();
		for (Pair<IntWritable, LongWritable> pair : new SequenceFileIterable<IntWritable, LongWritable>(documentFrequencyPath, true, conf)) {
			documentFrequency.put(pair.getFirst().get(), pair.getSecond().get());
		}
		return documentFrequency;
	}

	public static Map<Integer, Long> getTopWords(Map<Integer, Long> documentFrequency, int topWordsCount) {
		List<Map.Entry<Integer, Long>> entries = new ArrayList<Map.Entry<Integer, Long>>(documentFrequency.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<Integer, Long>>() {
			public int compare(Entry<Integer, Long> e1, Entry<Integer, Long> e2) {
				return -e1.getValue().compareTo(e2.getValue());
			}
		});
		
		Map<Integer, Long> topWords = new HashMap<Integer, Long>();
		int i = 0;
		for(Map.Entry<Integer, Long> entry: entries) {
			topWords.put(entry.getKey(), entry.getValue());
			i++;
			if (i > topWordsCount) {
				break;
			}
		}
		
		return topWords;
	}
	
	public static class WordWeight implements Comparable<WordWeight> {
		private int wordId;
		private double weight;
		
		public WordWeight(int wordId, double weight) {
			this.wordId = wordId;
			this.weight = weight;
		}
		
		public int getWordId() {
			return wordId;
		}

		public Double getWeight() {
			return weight;
		}

		public int compareTo(WordWeight w) {
			return -getWeight().compareTo(w.getWeight());
		}
	}

	public static void  getTopCategoryWords(String modelPath,String labelIndexPath,String dictionaryPath,String documentFrequencyPath) throws Exception {
	
		Configuration configuration = new Configuration();
		// model is a matrix (wordId, labelId) => probability score
		NaiveBayesModel model = NaiveBayesModel.materialize(new Path(modelPath), configuration);
		// labels is a map label => classId
		Map<Integer, String> labels = BayesUtils.readLabelIndex(configuration, new Path(labelIndexPath));
		Map<Integer, String> inverseDictionary = readInverseDictionnary(configuration, new Path(dictionaryPath));
		Map<Integer, Long> documentFrequency = readDocumentFrequency(configuration, new Path(documentFrequencyPath));

		Map<Integer, Long> topWords = getTopWords(documentFrequency, 1000);
		System.out.println("Top words");
		for(Map.Entry<Integer, Long> entry: topWords.entrySet()) {
			System.out.println(" - " + inverseDictionary.get(entry.getKey())
					+ ": " + entry.getValue());
		}
		
		int labelCount = labels.size();
		int documentCount = documentFrequency.get(-1).intValue();
		
		System.out.println("Number of labels: " + labelCount);
		System.out.println("Number of documents in training set: " + documentCount);
		
		for(int labelId = 0 ; labelId < model.numLabels() ; labelId++) {
			SortedSet<WordWeight> wordWeights = new TreeSet<WordWeight>();
			for(int wordId = 0 ; wordId < model.numFeatures() ; wordId++) {
				WordWeight w = new WordWeight(wordId, model.weight(labelId, wordId));
				wordWeights.add(w);
			}
			System.out.println("Top 10 words for label " + labels.get(labelId));
			int i = 0;
			for(WordWeight w: wordWeights) {
				System.out.println(" - " + inverseDictionary.get(w.getWordId())
						+ ": " + w.getWeight());
				i++;
				if (i >= 10) {
					break;
				}
			}
		}
	}
}
