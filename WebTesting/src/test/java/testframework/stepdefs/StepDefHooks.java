package testframework.stepdefs;

import io.cucumber.java.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class StepDefHooks extends StepDefsSuper{


    @BeforeAll
    public static void beforeAll() throws IOException {
        service = new ChromeDriverService.Builder()
                .usingDriverExecutable(new File(DRIVER_LOCATION))
                .usingAnyFreePort()
                .build();
        service.start();
    }

    @Before
    public void setup() {
        webDriver = new RemoteWebDriver(service.getUrl(), getChromeOptions());
    }

    @After
    public void afterEach() {
        webDriver.quit();
    }

    @AfterAll
    public static void afterAll() {
        service.stop();
    }


    @BeforeStep
    public void closePopups(){

        //close ads
        if (webDriver.getCurrentUrl().contains("google")) {
            List<WebElement> iframes = webDriver.findElements(By.tagName("iframe"));

            try {
                for (WebElement iframe : iframes){
                    try {
                        webDriver.switchTo().frame(iframe);
                        List<WebElement> nestedIframes = webDriver.findElements(By.tagName("iframe"));

                        if (!nestedIframes.isEmpty()){
                            webDriver.switchTo().frame(nestedIframes.get(0));
                            try {
                                WebElement closeButton = webDriver.findElement(By.id("dismiss-button"));
                                closeButton.click();
                                break;
                            } catch (Exception e){
                                System.out.println("Close button not found");
                            }
                            webDriver.switchTo().parentFrame();
                        }
                        webDriver.switchTo().defaultContent();
                    } catch (Exception e) {
                        System.out.println("error switching to Iframe: " + e.getMessage());
                        webDriver.switchTo().defaultContent();
                    }
                }
            } catch (Exception e) {
                System.out.println("ad not found or other error: " + e.getMessage());
            }
            webDriver.switchTo().defaultContent();
        }
        //An attempt to close popup ads
//        if (webDriver.getCurrentUrl().contains("google")) {
//            try {
//                webDriver.switchTo().frame(2);
//                webDriverWait.until(driver -> !driver.findElements(By.id("dismiss-button")).isEmpty());
//                webDriver.findElement(By.id("dismiss-button")).click();
//            } catch (Exception e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

}

