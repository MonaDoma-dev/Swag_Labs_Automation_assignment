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

    // Locators
    private By shoppingCartIcon = By.className("shopping_cart_link");
    private By inventoryItems = By.className("inventory_item");
    private By pageTitle = By.className("title");

    // Constructor
    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Actions
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
}
