package BaseClass;

import ActionClass.ActionUtiles;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static java.lang.System.getProperty;


    public class BaseClass {

        public static Properties prop;
        public static WebDriver driver;


        // File reading method of Config.properties file in Base Class
        @BeforeTest
        public void loadConfig() {
            try {
                prop = new Properties();
                System.out.println("super constructor invoked");
                FileInputStream ip = new FileInputStream( getProperty("user.dir") + "\\Configurations\\Config.properties");
                prop.load(ip);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        // Getting and launching method of browser from Config.properties file

        public static void launchApp() {

            WebDriverManager.chromedriver().setup();
            String browserName = prop.getProperty("browser");

            if (browserName == null) {
                // Handle the case where the browser property is null
                System.out.println("Browser property is not defined in the configuration file.");
                return; // Exit the method
            }

            if (browserName.contains("Chrome")) {
                driver = new ChromeDriver();
            } else if (browserName.contains("FireFox")) {
                driver = new FirefoxDriver();
            } else if (browserName.contains("IE")) {
                driver = new InternetExplorerDriver();
            } else {
                // Handle the case where the browser property is invalid
                System.out.println("Invalid browser specified in the configuration file.");
                return; // Exit the method
            }


            // Handling Browser method

            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();


            // Setting the wait in Base class to use at global level

            // Check if implicitWait and pageLoadTimeOut properties are null or not
            String implicitWaitStr = prop.getProperty("implicitWait");
            String pageLoadTimeOutStr = prop.getProperty("pageLoadTimeOut");

            int implicitWait = implicitWaitStr != null ? Integer.parseInt(implicitWaitStr) : 20;
            int pageLoadTimeOut = pageLoadTimeOutStr != null ? Integer.parseInt(pageLoadTimeOutStr) : 40;

            driver.manage().timeouts().implicitlyWait(implicitWait, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(pageLoadTimeOut, TimeUnit.SECONDS);

            ActionUtiles.implicitlyWait(20);
            ActionUtiles.pageLoadTimeOut(40);

            // Launch URL and this URL is getting from Config.properties file
            String url = prop.getProperty("url");
            if (url == null) {
                // Handle the case where the URL property is null
                System.out.println("URL property is not defined in the configuration file.");
                return; // Exit the method
            }
            driver.get(url);
        }

    }
