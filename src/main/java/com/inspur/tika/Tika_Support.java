package com.inspur.tika;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tika_Support {
	public static List<File> findFiles(String path) {
		List<File> HTML = new ArrayList<File>();
		File files = new File(path);
		File[] file = files.listFiles();
		for (int i = 0; i < file.length; i++) {
			File isfile = file[i];
			if (isfile.isDirectory()) {
				HTML.addAll(findFiles(isfile.getAbsolutePath()));
			} else if (isfile.isFile()) {
				if(isfile.getName().endsWith(".html")||isfile.getName().endsWith(".txt")||isfile.getName().endsWith(".pdf")||isfile.getName().endsWith(".doc")||isfile.getName().endsWith(".docx")){
					HTML.add(isfile);
				}				
			}
		}
		return HTML;
	}
}
