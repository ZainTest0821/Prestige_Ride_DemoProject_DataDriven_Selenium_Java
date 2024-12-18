package TestClasses;

import ActionClass.ActionUtiles;
import BaseClass.BaseClass;
import DataProviderUtility.DataProviderClass;
import Pages.JFK_to_Manhattan;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class JFK_to_Manhattan_test extends BaseClass {

    JFK_to_Manhattan Location_for_J_to_M;

    // Then we have to initialize the super method of baseclass so that every property of BaseClass can be access easily
    public JFK_to_Manhattan_test () {
        super();
    }

    @BeforeClass
    public void DFWtoRichardson() {

        //Launch URL first in before method
        launchApp();

        //Initialize the page for using

        Location_for_J_to_M = new JFK_to_Manhattan();
    }

    @AfterClass

    public void teardown() {
        if (driver != null)
        {driver.quit();

        }
    }

    @Test(priority = 1)

    public void validatethepage() {

        //Validate the Page title first
        String title = Location_for_J_to_M.driver.getTitle();
        Assert.assertEquals(title, "Prestige Ride | First-Class Black Car & Chauffeur Services");
    }

    @DataProvider(name = "locationData")
    public Object[][] getData() throws IOException {
        // Provide the path to the Excel file and the sheet name
        return DataProviderClass.getExcelData("C:\\Users\\Fast Computers\\IdeaProjects\\PrestigeRide_Demo_Java_Selenium\\Test_Data\\Test_Data_for Location.xlsx", "Sheet3");
    }

    @Test(priority = 2,dataProvider = "locationData")
    public void firstlocationtest(String pickup, String dropoff) throws InterruptedException {

        //Enter Details for first location - JFK to Manhattan

        Location_for_J_to_M.entervalueinpickuplocation(pickup);
        ActionUtiles.waitForElementToBeVisible(Location_for_J_to_M.selectDropdownValue("John F."));

        //Select DropoffLocation
        Location_for_J_to_M.entervaluefordropofflocation(dropoff);
        ActionUtiles.waitForElementToBeVisible(Location_for_J_to_M.selectDropdownValue("Manhattan"));
        Location_for_J_to_M.setDate("25/12/2025");

        //Wait for manually set the time
        Thread.sleep(80000);
        Location_for_J_to_M.setTime("10","00","PM");
    }

    @Test(priority = 3)

    public void clickonsubmitbtn(){

        long startTime = System.currentTimeMillis(); // Start the timer before clicking the search button

        //Click on submit button
        Location_for_J_to_M.clickonsubmit();

        // Wait for an element that confirms the page has loaded
        ActionUtiles.waitForElementToBeVisible(Location_for_J_to_M.getSearchResultsHeader());

        long endTime = System.currentTimeMillis(); // End the timer when the element is visible

        long responseTime = endTime - startTime; // Calculate the response time
        System.out.println("Response time for navigating to the search result screen: " + responseTime + " ms");

    }


    @Test(priority = 4)
    public void validatethesearchresultpage() {
        // Wait for the search result page to load
        Location_for_J_to_M.waitForSearchResultPageToLoad();

        // Validate the page title after waiting for the page to load
        String pageTitle = Location_for_J_to_M.getSearchResultPageTitle();
        Assert.assertEquals(pageTitle, "Customize Your Vehicle with Prestige Ride");
    }

}




