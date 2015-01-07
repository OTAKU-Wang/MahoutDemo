package com.inspur.mahout.MahoutDemo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class FilesPath {
	private String modelPath="";
	private String labelIndexPath="";
	private String dictionaryPath="";
	private String documentFrequencyPath="";
	private String StopFile="";
	public  FilesPath() throws IOException{
		Properties prop=new Properties();
		InputStream in=getClass().getResourceAsStream("FilesPath.properties");
		prop.load(in);
		Set<?> keyValue=prop.keySet();
		for(Iterator<?> it=keyValue.iterator();it.hasNext();){
			String key=(String)it.next();
			if(key.equals("modelPath")) modelPath=prop.get(key).toString();
			if(key.equals("labelIndexPath")) labelIndexPath=prop.get(key).toString();
			if(key.equals("dictionaryPath")) dictionaryPath=prop.get(key).toString();
			if(key.equals("documentFrequencyPath")) documentFrequencyPath=prop.get(key).toString();
			if(key.equals("StopFile")) StopFile=prop.get(key).toString();
		}
	}
	public String getModelPath(){
		return modelPath;
	}
	public String getLabelIndexPath(){
		return labelIndexPath;
	}
	public String getDictionaryPath(){
		return dictionaryPath;
	}
	public String getDocumentFrequencyPath(){
		return documentFrequencyPath;
	}
	public String getStopFile(){
		return StopFile;
	}
}
