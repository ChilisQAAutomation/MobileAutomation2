package com.testrunner.framework;

public enum SuiteFilePath  {
	
	AndroidBrowser("AndroidBrowser","Test Chilis In Android Browser","com.testscripts.framework.TestChilisAndroidBrowser","/src/test/java/com/testrunner/framework/AndroidBrowserSuite.xml"),
	AndroidApp("AndroidApp","Test Chilis In Android App","com.testscripts.framework.TestChilisAndroidApp","/src/test/java/com/testrunner/framework/AndroidAppSuite.xml"),
	IOSBrowser("IOSBrowser","Test Chilis In IOS Browser","com.testscripts.framework.TestChilisIOSBrowser","/src/test/java/com/testrunner/framework/IOSBrowserSuite.xml"),
	IOSApp("IOSApp","Test Chilis In IOS App","com.testscripts.framework.TestChilisIOSApp","/src/test/java/com/testrunner/framework/IOSAppSuite.xml"),
	UberEats("UberEats","Test Uber Eats in Web browser","com.testscripts.framework.TestUberEats","/src/test/java/com/testrunner/framework/UberEatsSuite.xml"),
	DoorDashChilis("DoorDashChilis","Test Door Dash Chilis in Web browser","com.testscripts.framework.TestDoorDashChilis","/src/test/java/com/testrunner/framework/DoorDashChilisSuite.xml"),
	DoorDashIJW("DoorDashIJW","Test Door Dash IJW in Web browser","com.testscripts.framework.TestIJW","/src/test/java/com/testrunner/framework/DoorDashIJW.xml"),
	AndroidBrowser_QA3("AndroidBrowser","Test Chilis In Android Browser in QA3","com.testscripts.framework.TestChilisAndroidBrowser_QA3","/src/test/java/com/testrunner/framework/AndroidBrowserSuiteQA3.xml"),
	AndroidApp_QA3("AndroidApp","Test Chilis In Android App in QA3","com.testscripts.framework.TestChilisAndroidApp_QA3","/src/test/java/com/testrunner/framework/AndroidAppSuiteQA3.xml"),
	IOSBrowser_QA3("IOSBrowser","Test Chilis In IOS Browser in QA3","com.testscripts.framework.TestChilisIOSBrowser_QA3","/src/test/java/com/testrunner/framework/IOSBrowserSuiteQA3.xml"),
	IOSApp_QA3("IOSApp","Test Chilis In IOS App in QA3","com.testscripts.framework.TestChilisIOSApp_QA3","/src/test/java/com/testrunner/framework/IOSAppSuiteQA3.xml"),;
	private String suiteName;
	private String testName;
	private String classPath;
	private String xmlFilePath;
	SuiteFilePath(String suiteName, String testName, String classPath, String xmlFilePath)
	{
		this.suiteName = suiteName;
		this.testName = testName;
		this.classPath = classPath;
		this.xmlFilePath = xmlFilePath;
	}
	
	public String getSuiteName()
	{
		return suiteName;
	}

	public String getTestName()
	{
		return testName;
	}
	public String getClassPath()
	{
		return classPath;
	}
	public String getXmlFilePath()
	{
		return xmlFilePath;
	}
}
