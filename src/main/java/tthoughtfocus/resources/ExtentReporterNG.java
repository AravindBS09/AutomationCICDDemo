package tthoughtfocus.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;


public class ExtentReporterNG
{
	public static ExtentReports getReportObject()
	{
		//BaseTest baseTest = new BaseTest();
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		//ExtentReports , ExtentSparkReporter
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "Aravind");		
		return extent;
	}
}
