package com.inspur.tika;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class Tika_Test { 
public static void main(String[] args){
	List<File> files=Tika_Support.findFiles("G:\\课件\\课件");
	for(File f:files){
		@SuppressWarnings("unused")
		String test=fileToTxt(f);
	}
}
	public static String fileToTxt(File f) {
		Parser parser = new AutoDetectParser();//自动检测文档类型，自动创建相应的解析器
		InputStream is = null;
		try {
			Metadata metadata = new Metadata();
//			metadata.set(Metadata.AUTHOR, "空号");//重新设置文档的媒体内容
//			metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
			
			//将content-encoding和content-type进行强制设置后，可解析text,并且对解析doc无影响。
			metadata.set(Metadata.CONTENT_ENCODING,"ANSI");
			metadata.set(Metadata.CONTENT_TYPE,"text/plain;charset='ANSI'");
			
			is = new FileInputStream(f);
//		    InputStream iStream = new BufferedInputStream(new FileInputStream(new File(PATH)));
//			OutputStream oStream = new BufferedOutputStream(new FileOutputStream(new File("F:/LC/apache-tomcat-7.0.41/binfiles/测试文本文档解析/测试文档2.txt"))); 
//			ContentHandler handler = new BodyContentHandler(oStream);//
			
			BodyContentHandler handler = new BodyContentHandler(10*1024*1024);//
			ParseContext context = new ParseContext();
			context.set(Parser.class,parser);
			parser.parse(is,handler,metadata,context);
			System.out.println("the size of metadata is:"+metadata.size());
			System.out.println("====================================================");
			for(String name:metadata.names()) {
				System.out.println(name+"===="+metadata.get(name));
			}
			System.out.println("====================================================");
			return handler.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is!=null) is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}	
}
