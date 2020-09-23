package com.gps.greplog;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class Test4 {
	public static void main(String[] args) throws FileNotFoundException, IOException {
	    
		Importer importer = new Importer();
        List<String> logLines = importer.readData();
        StringBuffer sb = new StringBuffer();
        
        List<List<Object>> list = new ArrayList();
        list.add(Arrays.asList((Object)"일시", "API", "KEY", "오류내용", "에러 로그"));
        for (String line : logLines) {
			if("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$".equals(line)) {
				sb.deleteCharAt(sb.lastIndexOf("\n"));
				list.add(Arrays.asList(
					(Object)sb.substring(0, 19), 
					"API", 
					"KEY", 
					"오류내용", 
					sb.toString()
				));
				sb = new StringBuffer();
			}else {
				sb.append(line + "\n");
			}
		}
        
        IOUtils.copy(ExcelGenerator.excelByteStream(list), new FileOutputStream("test.xlsx"));
	}
}
