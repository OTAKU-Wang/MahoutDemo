package com.inspur.mahout.MahoutDemo;

public class TestTopCategoryWords {
	public static void main(String[] args) throws Exception{
//		String modelPath="/home/diblo/Documents/MahoutDemo/model";
//		String labelIndexPath = "/home/diblo/Documents/MahoutDemo/labelindex";
//		String dictionaryPath = "/home/diblo/Documents/MahoutDemo/TestData-vectors/dictionary.file-0";
//		String documentFrequencyPath = "/home/diblo/Documents/MahoutDemo/TestData-vectors/df-count/part-r-00000";
		String modelPath="H:\\linux work\\MahoutDemo.tar\\MahoutDemo\\MahoutDemo\\model";
		String labelIndexPath = "H:\\linux work\\MahoutDemo.tar\\MahoutDemo\\MahoutDemo\\labelindex";
		String dictionaryPath = "H:\\linux work\\MahoutDemo.tar\\MahoutDemo\\MahoutDemo\\TestData-vectors\\dictionary.file-0";
		String documentFrequencyPath = "H:\\linux work\\MahoutDemo.tar\\MahoutDemo\\MahoutDemo\\TestData-vectors\\df-count\\part-r-00000";
		TopCategoryWords.getTopCategoryWords(modelPath,labelIndexPath,dictionaryPath,documentFrequencyPath);
	}
}
