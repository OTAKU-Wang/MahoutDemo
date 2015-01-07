package com.inspur.mahout.MahoutDemo;

import java.util.Map;

import org.apache.tika.exception.TikaException;

public class TestWordsBagToSeq {
	public static void main(String[] args) throws Exception, TikaException{
		String path="G:/SogouC.reduced.20061127/SogouC.reduced/Reduced";
		String OutPutDir="G:/OutPutDir";
		String StopWords="H:/linux work/MahoutDemo.tar/MahoutDemo/MahoutDemo/StopWords";
		Map<String,String> FileContent=WordsBag.getWordsBag(path, StopWords);
		WordsBagToSeq.wordsToseq(FileContent, OutPutDir);
	}
}
