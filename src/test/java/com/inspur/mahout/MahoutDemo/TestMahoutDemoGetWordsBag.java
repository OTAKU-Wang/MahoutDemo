package com.inspur.mahout.MahoutDemo;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.tika.exception.TikaException;

public class TestMahoutDemoGetWordsBag {
	public static void main(String[] args) throws IOException, TikaException{
		String path="/home/diblo/Documents/data";
		String StopWords="/home/diblo/Documents/MahoutDemo/StopWords";
		 Map<String,String> contents=WordsBag.getWordsBag(path,StopWords);
		Set<Map.Entry<String, String>> entrySet=contents.entrySet();
		for(Map.Entry<String, String> entry: entrySet){
			File f=new File(entry.getKey());
			String category=f.getParent().substring(f.getParent().lastIndexOf("/"));
			System.out.println(category);
			System.out.println(entry.getKey());
			System.out.println("=============================================================================================================");
			System.out.println(entry.getValue());
		}
	}
}
