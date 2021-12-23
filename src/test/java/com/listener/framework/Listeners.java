package com.listener.framework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Properties;
import static java.nio.file.StandardCopyOption.*;
import org.apache.commons.mail.EmailException;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.configuration.framework.Base;
import com.experitest.appium.SeeTestClient;
import com.pageaction.framework.FunctionalComponents;
import com.report.framework.ReportConfiguration;
import com.testscripts.framework.TestChilisAndroidBrowser;
import com.utils.framework.ReusableMethods;


public class Listeners extends TestListenerAdapter implements ITestListener {
	ExtentTest test;
	ExtentReports extent;  
	public int testPassed = 0;
	public int testFailed = 0;
	public int testSkipped = 0;
	public int testExecuted = 0;
	public static Properties prop = Base.returnProperty(Base.PROJECT_PATH+"/src/test/java/com/properties/framework/data.properties");
	public RemoteWebDriver driver;
	public SeeTestClient client;
	
	
	public static void main(String[] args) {

//		if (prop.getProperty("sendEmail").equals("true")) {
//			MailReport.getReportPath(new File(System.getProperty("user.dir") + "/reports"));
//			Runtime current = Runtime.getRuntime();
//			current.addShutdownHook(new MailReport());
//		}
		
		

	}
	public void setClient(RemoteWebDriver driver)
	{
		this.driver = driver;
		client  = new SeeTestClient(driver);
		
	}

	public void onTestStart(ITestResult result) {
	   // TODO Auto-generated method stub
		test = extent.createTest(result.getMethod().getMethodName());
		FunctionalComponents.getExtentTest(test);
		testExecuted++;
	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub
		test.log(Status.PASS,
				MarkupHelper.createLabel(result.getName() + " - Test Case PASSED", ExtentColor.GREEN));
		testPassed++;
	}
	 

	public void onTestFailure(ITestResult result) {
		 Properties platformFlag = Base.returnProperty(Base.PROJECT_PATH+"/src/test/java/com/properties/framework/platform.properties"); 
		try { 
			driver = (RemoteWebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
			if(platformFlag.getProperty("WEB").equals("true"))
			{test.info("Failure Screenshot: ",MediaEntityBuilder.createScreenCaptureFromBase64String(ReusableMethods.captureWebScreenshot(driver)).build());
			}
			else {
			client = new SeeTestClient(driver);
			test.info("Failure Screenshot: ",MediaEntityBuilder.createScreenCaptureFromBase64String(ReusableMethods.captureSeeTestScreenShot(client)).build());
			}
			} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		test.log(Status.FAIL,
				MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));
//		try {
//			test.addScreenCaptureFromPath(ReusableMethods.captureScreenShot(client));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		testFailed++;

	}

	public void onTestSkipped(ITestResult result) {
		if (result.getThrowable().getMessage() == null)
		{	extent.removeTest(test);
		    testExecuted--;
		}
		else {
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
			testSkipped++;
		}
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
	}

	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStart(ITestContext context) {
	    extent 	= ReportConfiguration.reportObjectSelector();
	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub

//		try {
//			GenerateTestSummary.createTestExecutionTable(testExecuted, testPassed, testFailed, testSkipped);
//			if (prop.getProperty("sendEmail").equals("true"))
//				test.log(Status.INFO,
//						MarkupHelper.createLabel("Email sent to recipients", ExtentColor.LIME));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Iterator<ITestResult> skippedTestCases = context.getSkippedTests().getAllResults().iterator();
		while (skippedTestCases.hasNext()) {
			ITestResult skippedTestCase = skippedTestCases.next();
			ITestNGMethod method = skippedTestCase.getMethod();
			if (context.getSkippedTests().getResults(method).size() > 0) {
				System.out.println("Removing:" + skippedTestCase.getTestClass().toString());
				skippedTestCases.remove();

			}
		}
		extent.flush();	
		main(null);

	}

}