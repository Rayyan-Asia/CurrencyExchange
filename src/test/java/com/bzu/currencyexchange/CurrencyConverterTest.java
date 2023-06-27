package com.bzu.currencyexchange;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.Select;

import static junit.framework.TestCase.*;

public class CurrencyConverterTest {
    static WebDriver driver = new ChromeDriver();

    @BeforeEach
    public void setUp() {
        // Navigate to the currency converter page
        driver.get("http://localhost:8080");
        // Set the path to the Chrome WebDriver (replace with yours)
        System.setProperty("webdriver.chrome.driver", "/Users/kareemhalayka/Downloads/chromedriver");

    }

    @AfterEach
    public void tearDown() {
        // Close the browser
        driver.quit();
    }
    @Test
    public void currencyConverterTest() {
        // Navigate to the currency converter page
        driver.get("http://localhost:8080");

        // Find the amount input element and enter a value
        WebElement amountInput = driver.findElement(By.id("amount"));
        amountInput.sendKeys("100");

        // Find the fromCurrency select element and select a value
        Select fromCurrencySelect = new Select(driver.findElement(By.id("fromCurrency")));
        fromCurrencySelect.selectByValue("USD");

        // Find the toCurrency select element and select a value
        Select toCurrencySelect = new Select(driver.findElement(By.id("toCurrency")));
        toCurrencySelect.selectByValue("EUR");

        // Find the submit button and click it
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        submitButton.click();

        // Wait for the conversion result to be displayed
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Find the conversion rate and converted amount elements and print their values
        WebElement rate = driver.findElement(By.id("rate"));
        WebElement convertedAmount = driver.findElement(By.id("convertedAmount"));

        assertTrue(amountInput.getAttribute("validationMessage").isEmpty());
        assertEquals("1.176", rate.getAttribute("value"));
        assertEquals("117.647", convertedAmount.getAttribute("value"));
    }

    @Test
    public void currencyConverterNullParameterTest() {
        // Navigate to the currency converter page
        driver.get("http://localhost:8080");

        // Leave the amount input element blank (null parameter)

        // Find the fromCurrency select element and select a value
        Select fromCurrencySelect = new Select(driver.findElement(By.id("fromCurrency")));
        fromCurrencySelect.selectByValue("USD");

        // Find the toCurrency select element and select a value
        Select toCurrencySelect = new Select(driver.findElement(By.id("toCurrency")));
        toCurrencySelect.selectByValue("EUR");

        // Find the submit button and click it
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        submitButton.click();

        // Check if the amount input element is invalid due to being required but empty
        WebElement amountInput = driver.findElement(By.id("amount"));
        assertFalse(amountInput.getAttribute("validationMessage").isEmpty());
    }

    @Test
    public void currencyConverterInvalidAmountTest() {
        // Navigate to the currency converter page
        driver.get("http://localhost:8080");

        // Find the amount input element and enter an invalid value (less than 1)
        WebElement amountInput = driver.findElement(By.id("amount"));
        amountInput.sendKeys("0");

        // Find the fromCurrency select element and select a value
        Select fromCurrencySelect = new Select(driver.findElement(By.id("fromCurrency")));
        fromCurrencySelect.selectByValue("USD");

        // Find the toCurrency select element and select a value
        Select toCurrencySelect = new Select(driver.findElement(By.id("toCurrency")));
        toCurrencySelect.selectByValue("EUR");

        // Find the submit button and click it
        WebElement submitButton = driver.findElement(By.xpath("//input[@type='submit']"));
        submitButton.click();

        // Wait for the API call to complete
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if the rate and convertedAmount input elements are empty (because an invalid amount was entered)
        WebElement rateInput = driver.findElement(By.id("rate"));
        assertTrue(rateInput.getAttribute("value").isEmpty());

        WebElement convertedAmountInput = driver.findElement(By.id("convertedAmount"));
        assertTrue(convertedAmountInput.getAttribute("value").isEmpty());
    }


}
