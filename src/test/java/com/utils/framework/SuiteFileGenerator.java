package com.utils.framework;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class SuiteFileGenerator {
	
	public static void createSuiteXMLFile(String suiteName, String TestName,String classPath,String xmlFilePath)
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootDoc = setRootNodeAsSuite(suiteName, doc);
			Element listeners = doc.createElement("listeners");
			Element listenerChild1 = doc.createElement("listener");
			listenerChild1.setAttribute("class-name", "com.listener.framework.Listeners");
			Element listenerChild2 = doc.createElement("listener");
			listenerChild2.setAttribute("class-name", "com.retryconfig.framework.Transformer");
			listeners.appendChild(listenerChild1);
			listeners.appendChild(listenerChild2);
			rootDoc.appendChild(listeners);
			Element testNode = setTestNode(TestName, doc, rootDoc);
			setTestCases(doc, testNode, classPath,
					suiteName);
			writeXMLFile(doc, xmlFilePath);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
	}
	public static Element setRootNodeAsSuite(String suiteName, Document doc) {
		Element rootElement = doc.createElement("suite");
		rootElement.setAttribute("name", suiteName);
		doc.appendChild(rootElement);
		return rootElement;
	}

	public static Element setTestNode(String testName, Document doc, Element rootDoc) {
		Element tests = doc.createElement("test");
		tests.setAttribute("name", testName);
		rootDoc.appendChild(tests);
		return tests;

	}

	public static void writeXMLFile(Document doc, String xmlFilePath) {
		try {
			// for output to file, console
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			// for pretty print
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "testng-1.0.dtd");
			DOMSource source = new DOMSource(doc);

			// write to console or file
			StreamResult console = new StreamResult(System.out);
			StreamResult file = new StreamResult(new File(xmlFilePath).getAbsolutePath());

			// write data
			transformer.transform(source, console);
			transformer.transform(source, file);
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public static void insertDocType(Document doc, String xmlFilePath) throws Exception {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer1 = transformerFactory.newTransformer();

		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(xmlFilePath).getAbsolutePath());

	}

	public static void setTestCases(Document doc, Element testNode, String classPath, String suiteName)
			throws Exception {
		String projectPath = System.getProperty("user.dir");
		ExcelUtils excel = new ExcelUtils(projectPath + "/RunManager.xlsx");
		Element classes = doc.createElement("classes");
		testNode.appendChild(classes);
		Element classChild = doc.createElement("class");
		classChild.setAttribute("name", classPath);
		classes.appendChild(classChild);
		Element methods = doc.createElement("methods");
		classChild.appendChild(methods);
		for (int i = 0; i < excel.getNoOfRows(suiteName); i++) {
			if (excel.getCellData(suiteName, "Execute", i + 1).equals("No")) {
				Element methodChild = doc.createElement("exclude");
				methodChild.setAttribute("name", excel.getCellData(suiteName, "Test Case ID", i + 1));
				methods.appendChild(methodChild);
			}
			if (excel.getCellData(suiteName, "Execute", i + 1).equals("Yes")) {
				Element methodChild = doc.createElement("include");
				methodChild.setAttribute("name", excel.getCellData(suiteName, "Test Case ID", i + 1));
				methods.appendChild(methodChild);

			}

		}
	}
}
