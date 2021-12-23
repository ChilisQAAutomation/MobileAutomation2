package com.utils.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.experitest.appium.SeeTestClient;

public class ReusableMethods {
	
	public static String captureSeeTestScreenShot(SeeTestClient seeTestClient) 
	{
	      String basePath = System.getProperty("user.dir") + "\\screenshots";
	      
	      String capturePath = "";
	      String newScreenshotFilePath = "";
	      Random rand = new Random();
	      String imageName="";
	      File newFile = null;
	      File file = null;
	            try {
	                  (new File(basePath)).mkdirs();
	                  String capture = seeTestClient.capture();
	                  System.out.println("Capture: "+capture);
	                  seeTestClient.downloadFile(capture, basePath);
	                  int lastindex=capture.lastIndexOf("/");
	                  capturePath = basePath + "\\" + capture.substring(lastindex+1);
	                  file = new File(capturePath);
	                  imageName=String.valueOf(rand.nextInt(100000))+"_"+System.currentTimeMillis();
	                  newScreenshotFilePath = basePath + "/" + imageName + ".png";
	                  newFile = new File(newScreenshotFilePath);
	                  if (file.renameTo(newFile)) {
	                        System.out.println("Success");
	                  } else {
	                        System.out.println("Failed");
	                  }
	                  //Thread.sleep(1000);
	                  seeTestClient.sleep(1000);
	            } catch (Exception e) {
	                  e.printStackTrace();
	            }
	            String encodedBase64 = null;
	            FileInputStream fileInputStreamReader = null;
	            try {
	            	fileInputStreamReader = new FileInputStream(newFile);
	            	byte[] bytes = new byte[(int)newFile.length()];
	            	fileInputStreamReader.read(bytes);
	            	encodedBase64 = new String(Base64.encodeBase64(bytes));
	            }
	            catch(FileNotFoundException f)
	            {f.printStackTrace();
	            }
	            catch(IOException e)
	            {
	            	e.printStackTrace();	
	            }
//	            return "screenshots/" + imageName + ".png";
//	            return newScreenshotFilePath;
	            return "data:image/png;base64, "+encodedBase64;
	            

	}
	
	public static String captureWebScreenshot(RemoteWebDriver driver)
	{
		File srcFile =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        try {
        	fileInputStreamReader = new FileInputStream(srcFile);
        	byte[] bytes = new byte[(int)srcFile.length()];
        	fileInputStreamReader.read(bytes);
        	encodedBase64 = new String(Base64.encodeBase64(bytes));
        }
        catch(FileNotFoundException f)
        {f.printStackTrace();
        }
        catch(IOException e)
        {
        	e.printStackTrace();	
        }
//        return "screenshots/" + imageName + ".png";
//        return newScreenshotFilePath;
        return "data:image/png;base64, "+encodedBase64;
		
		
	}
	
}
	
	


