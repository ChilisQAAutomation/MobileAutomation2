package com.testrunner.framework;


import java.util.ArrayList;
import java.util.List; 
import org.testng.TestNG;

import com.configuration.framework.Base;
import com.utils.framework.SuiteFileGenerator;
 
public class TestRunner extends Base {
 
public static void main(String[] args) {
	
 
// Create object of TestNG Class
TestNG runner=new TestNG();
//SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.AndroidBrowser.getSuiteName(),SuiteFilePath.AndroidBrowser.getTestName(),SuiteFilePath.AndroidBrowser.getClassPath(),PROJECT_PATH+SuiteFilePath.AndroidBrowser.getXmlFilePath());
//SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.AndroidApp.getSuiteName(),SuiteFilePath.AndroidApp.getTestName(),SuiteFilePath.AndroidApp.getClassPath(),PROJECT_PATH+SuiteFilePath.AndroidApp.getXmlFilePath());
//SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.IOSBrowser.getSuiteName(),SuiteFilePath.IOSBrowser.getTestName(),SuiteFilePath.IOSBrowser.getClassPath(),PROJECT_PATH+SuiteFilePath.IOSBrowser.getXmlFilePath());
//SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.IOSApp.getSuiteName(),SuiteFilePath.IOSApp.getTestName(),SuiteFilePath.IOSApp.getClassPath(),PROJECT_PATH+SuiteFilePath.IOSApp.getXmlFilePath());
SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.UberEats.getSuiteName(),SuiteFilePath.UberEats.getTestName(),SuiteFilePath.UberEats.getClassPath(),PROJECT_PATH+SuiteFilePath.UberEats.getXmlFilePath());
SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.DoorDashChilis.getSuiteName(),SuiteFilePath.DoorDashChilis.getTestName(),SuiteFilePath.DoorDashChilis.getClassPath(),PROJECT_PATH+SuiteFilePath.DoorDashChilis.getXmlFilePath());
SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.DoorDashIJW.getSuiteName(),SuiteFilePath.DoorDashIJW.getTestName(),SuiteFilePath.DoorDashIJW.getClassPath(),PROJECT_PATH+SuiteFilePath.DoorDashIJW.getXmlFilePath());
//SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.AndroidBrowser_QA3.getSuiteName(),SuiteFilePath.AndroidBrowser_QA3.getTestName(),SuiteFilePath.AndroidBrowser_QA3.getClassPath(),PROJECT_PATH+SuiteFilePath.AndroidBrowser_QA3.getXmlFilePath());
//SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.AndroidApp_QA3.getSuiteName(),SuiteFilePath.AndroidApp_QA3.getTestName(),SuiteFilePath.AndroidApp_QA3.getClassPath(),PROJECT_PATH+SuiteFilePath.AndroidApp_QA3.getXmlFilePath());
//SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.IOSBrowser_QA3.getSuiteName(),SuiteFilePath.IOSBrowser_QA3.getTestName(),SuiteFilePath.IOSBrowser_QA3.getClassPath(),PROJECT_PATH+SuiteFilePath.IOSBrowser_QA3.getXmlFilePath());
//SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.IOSApp_QA3.getSuiteName(),SuiteFilePath.IOSApp_QA3.getTestName(),SuiteFilePath.IOSApp_QA3.getClassPath(),PROJECT_PATH+SuiteFilePath.IOSApp_QA3.getXmlFilePath());
// Create a list of String 
List<String> suitefiles=new ArrayList<String>();
 
// Add xml file which you have to execute
//suitefiles.add(PROJECT_PATH+SuiteFilePath.AndroidBrowser_QA3.getXmlFilePath());
//suitefiles.add(PROJECT_PATH+SuiteFilePath.AndroidApp_QA3.getXmlFilePath());
//suitefiles.add(PROJECT_PATH+SuiteFilePath.IOSBrowser_QA3.getXmlFilePath());
//suitefiles.add(PROJECT_PATH+SuiteFilePath.IOSApp_QA3.getXmlFilePath());
suitefiles.add(PROJECT_PATH+SuiteFilePath.UberEats.getXmlFilePath());
suitefiles.add(PROJECT_PATH+SuiteFilePath.DoorDashChilis.getXmlFilePath());
suitefiles.add(PROJECT_PATH+SuiteFilePath.DoorDashIJW.getXmlFilePath());






 
// now set xml file for execution
runner.setTestSuites(suitefiles);
 
// finally execute the runner using run method
runner.run();
}

 
}
