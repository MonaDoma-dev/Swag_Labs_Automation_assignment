package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class InventoryPage {
    private WebDriver driver;
    private WebDriverWait wait;


    private By shoppingCartIcon = By.className("shopping_cart_link");
    private By inventoryItems = By.className("inventory_item");
    private By pageTitle = By.className("title");
    private By linkedinIcon = By.linkText("LinkedIn");
    private By facebookIcon = By.linkText("Facebook");
    private By twitterIcon = By.linkText("Twitter");
    private By menuButton = By.id("react-burger-menu-btn");
    private By logoutButton = By.id("logout_sidebar_link");


    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isCartIconDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(shoppingCartIcon)).isDisplayed();
    }

    public int getProductCount() {
        List<WebElement> items = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(inventoryItems));
        return items.size();
    }

    public String getPageTitleText() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).getText();
    }

    public void clickCartIcon() {
        WebElement cart = wait.until(ExpectedConditions.presenceOfElementLocated(shoppingCartIcon));
        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", cart);
    }

    public void addProductToCart(String productName) {
        By addToCartBtn = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button");
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBtn)).click();
    }

    public double getProductPrice(String productName) {
        By priceLocator = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//div[@class='inventory_item_price']");
        String priceText = wait.until(ExpectedConditions.visibilityOfElementLocated(priceLocator)).getText();
        return Double.parseDouble(priceText.replace("$", ""));
    }

    public String getProductButtonText(String productName) {
        By buttonLocator = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='inventory_item']//button");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(buttonLocator)).getText();
    }

    public void clickLinkedIn() {
        wait.until(ExpectedConditions.elementToBeClickable(linkedinIcon)).click();
    }

    public void clickFacebook() {
        wait.until(ExpectedConditions.elementToBeClickable(facebookIcon)).click();
    }

    public void clickTwitter() {
        wait.until(ExpectedConditions.elementToBeClickable(twitterIcon)).click();
    }

    public void logout() {

        wait.until(ExpectedConditions.elementToBeClickable(menuButton)).click();

        WebElement logoutBtn = wait.until(ExpectedConditions.presenceOfElementLocated(logoutButton));

        org.openqa.selenium.JavascriptExecutor js = (org.openqa.selenium.JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", logoutBtn);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")));
    }
}