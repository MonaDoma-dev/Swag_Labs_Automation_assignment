package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By cartItemNames = By.className("inventory_item_name");
    private By checkoutButton = By.id("checkout");
    private By errorMessage = By.xpath("//h3[@data-test='error']");
    private By itemTotalLabel = By.className("summary_subtotal_label");
    private By continueShoppingBtn = By.id("continue-shopping");
    private By firstNameInput = By.id("first-name");
    private By lastNameInput = By.id("last-name");
    private By postalCodeInput = By.id("postal-code");
    private By continueButton = By.id("continue");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public List<String> getCartItemNames() {
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(cartItemNames));
        List<String> names = new ArrayList<>();
        for (WebElement el : elements) {
            names.add(el.getText());
        }
        return names;
    }

    public int getCartItemCount() {
        return driver.findElements(cartItemNames).size();
    }

    public void removeItemByName(String productName) {
        By removeBtn = By.xpath("//div[text()='" + productName + "']/ancestor::div[@class='cart_item']//button");
        wait.until(ExpectedConditions.elementToBeClickable(removeBtn)).click();
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingBtn)).click();
    }

    public void fillCheckoutInformation(String firstName, String lastName, String postalCode) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput)).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(postalCodeInput).sendKeys(postalCode);
        driver.findElement(continueButton).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotalLabel));
    }

    public double getItemTotal() {
        String totalText = wait.until(ExpectedConditions.visibilityOfElementLocated(itemTotalLabel)).getText();
        return Double.parseDouble(totalText.replace("Item total: $", ""));
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}