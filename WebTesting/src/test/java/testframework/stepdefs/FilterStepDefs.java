package testframework.stepdefs;

import io.cucumber.java.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import testframework.lib.pages.HomePage;
import testframework.lib.pages.ProductsPage;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class FilterStepDefs extends StepDefsSuper{
    HomePage homePage;
    WebDriverWait webDriverWait;

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
        webDriverWait = new WebDriverWait(webDriver,Duration.ofSeconds(10));
    }

    @BeforeStep
    public void closePopups(){
        //consent to cookies
        List<WebElement> consentButton = new ArrayList<>();
        if (!(consentButton = webDriver.findElements(By.className("fc-button-label"))).isEmpty()){
            consentButton.get(0).click();
        }

        //close ads
        if (webDriver.getCurrentUrl().contains("google"))
            webDriver.findElement(By.id("dismiss-button")).click();

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

    @After
    public void afterEach() {
        webDriver.quit();
    }

    @AfterAll
    public static void afterAll() {
        service.stop();
    }

    @Given("I am on the home page")
    public void iAmOnTheHomePage(){
        webDriver.get("https://automationexercise.com/");
        homePage = new HomePage(webDriver);
    }

    @When("I click the WOMEN option in the categories menu")
    public void iClickTheWOMENOptionInTheCategoriesMenu() throws InterruptedException {
        var categoryMenuWomen = webDriver.findElement(By.linkText("WOMEN"));
        categoryMenuWomen.click();
    }

    @And("I click the DRESS option in the dropdown menu that opens")
    public void iClickTheDRESSOptionInTheDropdownMenuThatOpens() {
        WebDriverWait webDriverWait = new WebDriverWait(webDriver,Duration.ofSeconds(10));
        webDriverWait.until(driver -> !webDriver.findElements(By.linkText("DRESS")).isEmpty());
        var categoryMenuWomenDress = webDriver.findElement(By.linkText("DRESS"));
        categoryMenuWomenDress.click();
    }

    @Then("I should be taken to the page for Women's dresses")
    public void iShouldBeTakenToThePageForWomenSDresses() {
        webDriverWait.until(driver -> driver.getCurrentUrl().contains("products"));
        Assertions.assertEquals("Automation Exercise - Dress Products",webDriver.getTitle());
    }

    @And("the heading {string} should be displayed at the top of the page")
    public void theHeadingShouldBeDisplayedAtTheTopOfThePage(String expectedHeading) {
        var heading = webDriver.findElement(By.cssSelector(".title"));
        Assertions.assertEquals(expectedHeading,heading.getText());
    }

    @And("each product shown should be in the category Women > Dress")
    public void eachProductShownShouldBeInTheCategoryWomenDress() {
        var products = webDriver.findElements(By.linkText("View Product"));
        for (int i = 0; i < products.size(); i++){
            products = webDriver.findElements(By.linkText("View Product"));
            String productUrl = products.get(i).getAttribute("href");
            webDriver.navigate().to(productUrl);
            var category = webDriver.findElement(By.className("product-information"));
            Assertions.assertTrue(category.getText().contains("Category: Women > Dress"));
            webDriver.navigate().back();
        }
    }

    @Given("I am on the products page")
    public void iAmOnTheProductsPage() {
        webDriver.get("https://automationexercise.com/products");
        ProductsPage productsPage = new ProductsPage(webDriver);
    }

    @When("I click the POLO option from the BRANDS menu")
    public void iClickThePOLOOptionFromTheBRANDSMenu() {
        var brandsMenuPolo = webDriver.findElement(By.partialLinkText("POLO"));
        brandsMenuPolo.click();
    }

    @Then("I should be taken to the page for Polo products")
    public void iShouldBeTakenToThePageForPoloProducts() {
        Assertions.assertEquals("Automation Exercise - Polo Products",webDriver.getTitle());
    }

    @And("each product shown should have the brand Polo")
    public void eachProductShownShouldHaveTheBrandPolo() {
        var products = webDriver.findElements(By.linkText("View Product"));
        for (int i = 0; i < products.size(); i++){
            products = webDriver.findElements(By.linkText("View Product"));
            String productUrl = products.get(i).getAttribute("href");
            webDriver.navigate().to(productUrl);
            var category = webDriver.findElement(By.className("product-information"));
            Assertions.assertTrue(category.getText().contains("Brand: Polo"));
            webDriver.navigate().back();
        }
    }

    @And("the number of products shown should equal the number displayed next to POLO in the BRANDS menu")
    public void theNumberOfProductsShownShouldEqualTheNumberDisplayedNextToPOLOInTheBRANDSMenu() {
        var brandsMenu = webDriver.findElement(By.className("brands-name"));
        int numPoloProucts = Integer.parseInt(String.valueOf(brandsMenu.findElement(By.className("pull-right")).getText().charAt(1)));
        var products = webDriver.findElements(By.linkText("View Product"));
        Assertions.assertEquals(numPoloProucts,products.size());
    }
}
