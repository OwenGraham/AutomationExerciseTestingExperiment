package testframework.lib.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.WebElement;

public class ProductsPage extends BasePage {
    private By firstProduct = By.linkText("View Product");

    public ProductsPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected boolean isCorrectPage() {
        return webDriver.getTitle().contains("Products");
    }

    public void addOneItemToCart() {

        WebElement element1 = webDriver.findElement(By.cssSelector(".col-sm-4:nth-child(3) .product-overlay .btn"));
        WebElement element2 = webDriver.findElement(By.className("add-to-cart"));

        Actions builder = new Actions(webDriver);


        //for (int i=1;i<3;i++){
        builder.scrollByAmount(0, 500).moveToElement(element2).build().perform();

        wait.until(ExpectedConditions.elementToBeClickable(element1));
        //  }
        builder.release();
        element1.click();
        // webDriver.findElement(By.cssSelector(".btn-success")).click();
    }

    public String getHeading() {
        WebElement headingElement = webDriver.findElement(By.cssSelector(".title"));
        return headingElement.getText();
    }

    public void search(String searchTerm){
        WebElement searchField = webDriver.findElement(By.name("search"));
        searchField.sendKeys(searchTerm);
        webDriver.findElement(By.id("submit_search")).click();
    }

    public WebDriver clickProduct(){
        webDriver.findElement(firstProduct).click();
        return webDriver;
    }
}
