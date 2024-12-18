package Pages;

import ActionClass.ActionUtiles;
import BaseClass.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class JFK_to_TimeSqure extends BaseClass {

    //Initializing as Constructor
    public JFK_to_TimeSqure() {
        PageFactory.initElements(driver, this);

    }


    //PickUp Location locator
    @FindBy(id = "pickup-location")
    WebElement Pickuplocation;


    //DropOff Location locator
    @FindBy(id = "dropoff-location")
    WebElement Dropofflocation;

    //DatePicker locator
    @FindBy(id = "pickUpDate")
    WebElement datepicker;

    //TimePicker locator
    @FindBy(id = "pick-upTime")
    WebElement timepicker;

    // Search btn locator
    @FindBy(xpath = "//*[@type='submit']")
    WebElement searchbtn;

    // Validate the Search result page
    @FindBy(xpath = "//p[contains(text(), 'Pickup Location')]")
    WebElement searchresultpage;


// Perform Actions


    //Validate the URL of Page

    public String gettitleofpage() {

        return driver.getTitle();
    }

    //Select value from dropdown list

    public WebElement selectDropdownValue(String valueToSelect) {
        // Wait for the dropdown to become visible
        WebElement dropdownElement = ActionUtiles.waitForElementToBeVisible(Pickuplocation);

        // Get all the dropdown items
        List<WebElement> items = dropdownElement.findElements(By.xpath("//li[@role='option']"));

        // Loop through the items and select the desired one
        for (WebElement item : items) {
            if (item.getText().contains(valueToSelect)) { // Match the desired value
                item.click();
                break;
            }
        }
        return dropdownElement;
    }


    public void entervalueinpickuplocation(String pickup) {

        ActionUtiles.waitForElementToBeVisible(Pickuplocation);
        ActionUtiles.type(Pickuplocation, pickup);
    }

    public void entervaluefordropofflocation(String dropoff) {
        ActionUtiles.type(Dropofflocation, dropoff);
    }

    // Method to click on the date picker and select a date

    public void setDate(String date) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Set the value of the date picker
        js.executeScript("arguments[0].setAttribute('value', arguments[1]);", datepicker, date);

        // Trigger the 'change' or 'input' event if required
        js.executeScript("var event = new Event('input', { bubbles: true }); arguments[0].dispatchEvent(event);", datepicker);
    }

    public void setTime(String hour, String minute, String period) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            // Step 1: Open the time picker by clicking on the input field
            timepicker.click();

            // Wait for the clock widget to appear (if applicable)
            ActionUtiles.waitForElementToBeVisible(timepicker);

            // Step 2: Set the time value directly in the input field using JavaScriptExecutor
            String timeValue = hour + ":" + minute + " " + period;
            js.executeScript("arguments[0].setAttribute('value', arguments[1]);", timepicker, timeValue);

            // Step 3: Trigger the 'change' event to ensure the value is saved in the input field
            js.executeScript("arguments[0].dispatchEvent(new Event('change', { bubbles: true }));", timepicker);

        } catch (Exception e) {
            System.out.println("Failed to set the time dynamically: " + e.getMessage());
        }
    }


    public void clickonsubmit() {

        //Click on Search btn

        ActionUtiles.JSClick(searchbtn);
    }


    // Method to get the title of the search results page
    public String getSearchResultPageTitle() {
        return driver.getTitle();
    }

    // Method to wait for the search results page to load by waiting for the visibility of an element
    public void waitForSearchResultPageToLoad() {
        // Wait for the search results header or any specific element to become visible
        ActionUtiles.waitForElementToBeVisible(searchresultpage);
    }


    // Add method to return and check the response time
    public WebElement getSearchResultsHeader() {
        return searchresultpage;

    }

}
