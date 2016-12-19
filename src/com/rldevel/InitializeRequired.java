package com.rldevel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class InitializeRequired {

	public static String currentDirectory = getCurrentPath();
	public static final String sys_sep = System.getProperty("file.separator");
	public static final InitializeRequired req = new InitializeRequired();
	
	public InitializeRequired(){}
	
	public static void initilize() throws IOException{
		// Patternfolder directory creation
		if (!(new File(currentDirectory+sys_sep+"patternfolder").exists()))
			new File(currentDirectory+sys_sep+"patternfolder").mkdir();
		
		// Mapper.xml file creation
		File mapper = new File(currentDirectory+sys_sep+"patternfolder"+sys_sep+"Mapper.xml");
		if (!mapper.exists()){
			mapper.createNewFile();
			req.readMapperTemplate(mapper);
		}
		
	}
	
	private void readMapperTemplate(File mapper) throws IOException{
		BufferedReader reader = 
				new BufferedReader(new InputStreamReader(
						this.getClass().getResourceAsStream("/templates/MapperTemplate.txt")));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(mapper));
		String line="";
		while((line = reader.readLine())!= null){
			writer.write(line);
			writer.newLine();
		}
		reader.close();
		writer.close();
	}
	
	private static String getCurrentPath(){
		String currentPath = "";
		if (System.getProperty("os.name").compareToIgnoreCase("linux")==0){
			currentPath = System.getenv("PWD");
		}else if(System.getProperty("os.name").toLowerCase().contains("windows")){
			currentPath = System.getProperty("user.dir");
		}
		return currentPath;
	}

}
