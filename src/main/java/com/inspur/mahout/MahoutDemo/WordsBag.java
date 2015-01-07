package com.inspur.mahout.MahoutDemo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ansj.domain.Term;
import org.ansj.recognition.NatureRecognition;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.FilterModifWord;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import com.inspur.tika.Tika_Support;

/**
 * wangmaoshai
 *
 */
public class WordsBag 
{
	public static void main(String[] args){
		
	}
public static Set<Map.Entry<String, List<Term>>> getWords(Map<String,Map<String,List<Term>>> contents){
	Set<Map.Entry<String, Map<String,List<Term>>>> entrySet=contents.entrySet();
	Set<Map.Entry<String, List<Term>>> e=null;
	for(Map.Entry<String, Map<String,List<Term>>> entry: entrySet){
		Map<String,List<Term>> en=entry.getValue();
		 e=en.entrySet();
	}
	return e;
	}
	
  public static  Map<String,String> getWordsBag(String path,String StopWordsPath) throws IOException, TikaException{
	  List<File> files=Tika_Support.findFiles(path);
	  Map<String,String> contents=new HashMap<String,String>(); 
	  Tika tika=new Tika();
	  StringBuffer text=new StringBuffer();
	  for(File f:files){		
		String content=tika.parseToString(f).trim();
//		String classification=f.getParent().substring(f.getParent().lastIndexOf("/"));
		addStopWord(StopWordsPath);
		List<Term> words=ToAnalysis.parse(content);
		new NatureRecognition(words).recognition();
		words=FilterModifWord.modifResult(words);
		for(Term t:words){
			text.append(t.getName()+" ");
		}
		contents.put(f.getAbsolutePath(),text.toString());
		text=new StringBuffer("");
	  }
	  return contents;
  }
  public static  Map<String,Map<String,String>> getContent(String path) throws IOException, TikaException{
	  List<File> files=Tika_Support.findFiles(path);
	  Map<String,Map<String,String>> contents=new HashMap<String,Map<String,String>>(); 
	  Tika tika=new Tika();
	  Map<String,String> temp=new HashMap<String,String>();
	  for(File f:files){
		System.out.println(f.getName());
		String content=tika.parseToString(f).trim();
		String classification=f.getParent().substring(f.getParent().lastIndexOf("\\"));
		String filepath=f.getAbsolutePath();
		temp.put(filepath,content );
		contents.put(classification, temp);
	  }
	  return contents;
  }
  public static void addStopWord(String StopWordFile) throws IOException{
		File WordFile=new File(StopWordFile);
		List<String> words=new ArrayList<String>();
		InputStreamReader inputstream=new InputStreamReader(new FileInputStream(WordFile),"UTF-8");
		@SuppressWarnings("resource")
		BufferedReader bufferreader=new BufferedReader(inputstream);
		String readLine=null;
		while((readLine=bufferreader.readLine())!=null){
			words.add(readLine);	
		}
//		FilterModifWord.insertStopNatures("nw") ;
		FilterModifWord.insertStopWords(words);
	}
}
