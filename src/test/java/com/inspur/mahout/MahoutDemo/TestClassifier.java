package com.inspur.mahout.MahoutDemo;

import java.util.Map;
import java.util.Set;

import org.apache.tika.exception.TikaException;

public class TestClassifier {
	public static void main(String[] args) throws Exception, TikaException{
		String path="G:\\课件\\实验";
		String modelPath="F:\\Users\\wangmaoshuai\\Workspaces\\MyEclipse Professional 2014\\MahoutDemo\\model";
		String labelIndexPath = "F:\\Users\\wangmaoshuai\\Workspaces\\MyEclipse Professional 2014\\MahoutDemo\\labelindex";
		String dictionaryPath = "F:\\Users\\wangmaoshuai\\Workspaces\\MyEclipse Professional 2014\\MahoutDemo\\TestData-vectors\\dictionary.file-0";
		String documentFrequencyPath = "F:\\Users\\wangmaoshuai\\Workspaces\\MyEclipse Professional 2014\\MahoutDemo\\TestData-vectors\\df-count\\part-r-00000";
		String StopFile="F:\\Users\\wangmaoshuai\\Workspaces\\MyEclipse Professional 2014\\MahoutDemo\\StopWords";
		Map<String,Map<String,String>> contents=WordsBag.getContent(path);
		Set<Map.Entry<String, Map<String,String>>> e=contents.entrySet();
		for(Map.Entry<String, Map<String,String>> c:e){
			Set<Map.Entry<String, String>> content=c.getValue().entrySet();
			for(Map.Entry<String, String> text:content){
				System.out.println(text.getKey());
				Classifier.GetClassifierResult(text.getValue(),modelPath,labelIndexPath,dictionaryPath,documentFrequencyPath,StopFile);
			}
		}
	}
}
