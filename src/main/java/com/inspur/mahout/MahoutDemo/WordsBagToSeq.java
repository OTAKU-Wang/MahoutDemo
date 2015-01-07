package com.inspur.mahout.MahoutDemo;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer;
import org.apache.hadoop.io.Text;

public class WordsBagToSeq {
	public static void wordsToseq(Map<String,String> FileContent,String OutPutDir) throws Exception{
		Configuration configration=new Configuration();
		FileSystem fs=FileSystem.get(configration);
		Writer writer=new SequenceFile.Writer(fs, configration, new Path(OutPutDir + "/chunk-0"), Text.class, Text.class);
		int count=0;
		Text Key=new Text();
		Text Value=new Text();
		Set<Map.Entry<String,String>> WordsBagEntry=FileContent.entrySet();
		for(Map.Entry<String, String> e:WordsBagEntry){
			String FilePath=e.getKey();
			File f=new File(FilePath);
			String category=f.getParent().substring(f.getParent().lastIndexOf("\\"));
			String Content=e.getValue();
			Key.set(category+"\\");
			Value.set(Content);
			writer.append(Key, Value);
			count++;
		}
		writer.close();
		System.out.println("Wrote"+count+"entries.");
	}
}
