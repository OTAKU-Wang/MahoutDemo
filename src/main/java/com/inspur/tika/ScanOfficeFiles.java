package com.inspur.tika;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.util.List;

public class ScanOfficeFiles {

	public static List<File> scan(File file)
	   {
	      try
	      {
	         return getFileListing(file);
	      }
	      catch (Exception e)
	      {
	         System.err.println(e.getMessage());
	      }

	      return null;
	   }
	
		public static List<File> scan(String patch){
			File[] floders=getFloders(patch);
			List<File> result=new ArrayList<File>();
			for(int i=0;i<floders.length;i++){
				if(floders[i].isFile()){ if(floders[i].getName().endsWith(".ppt")||floders[i].getName().endsWith(".pptx")||floders[i].getName().endsWith(".doc")||floders[i].getName().endsWith(".docx")||floders[i].getName().endsWith(".xls")||floders[i].getName().endsWith(".pdf")||floders[i].getName().endsWith(".txt")||floders[i].getName().endsWith(".xlsx")){
					result.add(floders[i]);
				}
				}
				try{
					result.addAll(getFileListing(floders[i]));
				}
				 catch (Exception e)
			      {
			         
			      }
			}
			return result;
			
		}
	 private static List<File> getFileListing(File aStartingDir) throws Exception 
	   {
	      List<File> result = getFileListingNoSort(aStartingDir);
	      Collections.sort(result);
	      return result;
	   }

	
/**ɨ���á�java��β���ļ�*/	
 private static List<File> getFileListingNoSort(File aStartingDir) throws Exception{
	 List<File> result=new ArrayList<File>();
	 File[] filesAndDirs = aStartingDir.listFiles();
	 List<File> fileDirs=Arrays.asList(filesAndDirs);
	 for(File file:fileDirs){
		 if(file.isFile()){
			 if(file.getName().endsWith(".ppt")||file.getName().endsWith(".pptx")||file.getName().endsWith(".doc")||file.getName().endsWith(".docx")||file.getName().endsWith(".xls")||file.getName().endsWith(".pdf")||file.getName().endsWith(".xlsx")||file.getName().endsWith(".txt")){
				 result.add(file);
			 }
		 }
		 else if(file.isDirectory()){
			 List<File> deeperList=getFileListingNoSort(file);
			 result.addAll(deeperList);
		 }
	 }
	 return result;
 }
 
 private static File[] getFloders(String path){
	 File file=new File(path);
	 File[] folders=file.listFiles();
	 return folders;
 }
}
