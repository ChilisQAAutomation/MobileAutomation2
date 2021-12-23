package com.retryconfig.framework;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
	
	private int retryCount = 0;
	private static final int maxRetryCount =1;
	

	public boolean retry(ITestResult result) {
		if(retryCount<maxRetryCount)
		{
			System.out.println("Retrying.....");
			retryCount++;
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}
}
