package com.rldevel;

import java.io.File;
import java.io.IOException;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

/***
 * 
 * @author rlomez
 *
 * This class was made for the use of pattern navigation view
 * the idea it is to content all the functionalities and work as a bridge between Nav. View
 * and the mapper.xml definition.
 * 
 *  05/12/2016
 * 
 */
public class NavigatorFunct{

	private String packageName;
	private String className;
	private File mappingFile = new File(PatternConsole.currentDirectory+"/patternfolder/Mapper.xml");
	
	public NavigatorFunct(String packageName, String className) {
		this.packageName = packageName;
		this.className = className;
	}
	
	public void addPackageToMapper() throws Exception{
		try{
			if (!this.mappingFile.exists())
				this.mappingFile.createNewFile(); //Generar template para tener ya el xml bien formado con el nodo packages
			
			XPathFactory factory = XPathFactory.newInstance();
			XPath xpath = factory.newXPath();
			
			Node packagesNode = (Node) xpath.evaluate ("/Packages", 
					new InputSource(mappingFile.getAbsolutePath()), XPathConstants.NODE);
			
			Node packageNode = (Node) xpath.evaluate ("/Packages/Package[@name='"+packageName+"']", 
					new InputSource(mappingFile.getAbsolutePath()), XPathConstants.NODE);
			
			if (packageNode == null){
				Element element = packagesNode.getOwnerDocument().createElement("Package");
				element.setAttribute("name", packageName);
				packagesNode.appendChild(element);
			}else{
				throw new Exception("Package "+packageName+" already exists, can not add");
			}
			
		}catch(IOException | XPathExpressionException ex){
			
		}
	}
	
	public void addClassToMapper() throws Exception{
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		
		try{
			Node packageNode = (Node) xpath.evaluate ("/Packages/Package[@name='"+packageName+"']", 
					new InputSource(mappingFile.getAbsolutePath()), XPathConstants.NODE);
			
			Node classNode = (Node) xpath.evaluate ("/Packages/Package[@name='"+packageName+"']/Class", 
					new InputSource(mappingFile.getAbsolutePath()), XPathConstants.NODE);
			
			if (classNode == null){
				Element element = packageNode.getOwnerDocument().createElement("Package");
				element.setAttribute("name", className);
				packageNode.appendChild(element);
			}else{
				throw new Exception("Class "+className+" already exists, can not add");
			}
		}catch(XPathExpressionException ex){
			ex.printStackTrace();
		}
	}
	
}