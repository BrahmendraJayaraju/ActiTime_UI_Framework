package com.orangeHRM.generic;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public abstract class BaseTest implements AutoConstant {

    public WebDriver driver;

    @BeforeMethod
    public void precondition() {
        try {
            ChromeOptions options = new ChromeOptions();

            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            driver = new ChromeDriver(options); 

            driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

        } catch (Exception e) {
            System.out.println("Error in setup: " + e.getMessage());
        }
    }

    @AfterMethod
    public void postcondition(ITestResult res) {
        String name = res.getMethod().getMethodName();
        int val = res.getStatus();

        if (val == ITestResult.FAILURE) {
            try {
                GenericUtils.takescreenshot(driver, name);
            } catch (IOException e) {
                System.out.println("Screenshot failed: " + e.getMessage());
            }
        }

        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("Driver quit failed: " + e.getMessage());
        }
    }
}