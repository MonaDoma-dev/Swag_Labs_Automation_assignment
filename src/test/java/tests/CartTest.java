package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import Pages.CartPage;
import Pages.InventoryPage;
import Pages.LoginPage;

import java.util.List;

public class CartTest extends BaseTest {

    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void setUpCartTest() {
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);

        loginPage.login(username, password);
    }

    @Test(priority = 1)
    public void verifySocialLinks() {
        // 1. LinkedIn Check
        String linkedinHref = driver.findElement(org.openqa.selenium.By.linkText("LinkedIn")).getAttribute("href");
        Assert.assertTrue(linkedinHref.contains("linkedin.com"), "LinkedIn link is incorrect!");

        // 2. Facebook Check
        String facebookHref = driver.findElement(org.openqa.selenium.By.linkText("Facebook")).getAttribute("href");
        Assert.assertTrue(facebookHref.contains("facebook.com"), "Facebook link is incorrect!");

        // 3. Twitter/X Check
        String twitterHref = driver.findElement(org.openqa.selenium.By.linkText("Twitter")).getAttribute("href");
        Assert.assertTrue(twitterHref.contains("twitter.com") || twitterHref.contains("x.com"), "Twitter link is incorrect!");
    }


    @Test(priority = 2)
    public void verifyCartIsEmpty() {
        inventoryPage.clickCartIcon();
        Assert.assertEquals(cartPage.getCartItemCount(), 0, "Cart is not empty!");
    }

    @Test(priority = 3)
    public void addThreeSpecificProductsDataDriven() {
        for (String product : cartProducts) {
            inventoryPage.addProductToCart(product);
        }

        inventoryPage.clickCartIcon();
        List<String> addedProducts = cartPage.getCartItemNames();

        Assert.assertEquals(addedProducts, cartProducts, "Cart items or order do not match JSON data!");
    }

    @Test(priority = 4)
    public void removeOneProduct() {
        for (String product : cartProducts) {
            inventoryPage.addProductToCart(product);
        }

        inventoryPage.clickCartIcon();
        cartPage.removeItemByName("Sauce Labs Bolt T-Shirt");
        cartPage.clickContinueShopping();

        Assert.assertEquals(inventoryPage.getProductButtonText("Sauce Labs Bolt T-Shirt"), "Add to cart");
        Assert.assertEquals(inventoryPage.getProductButtonText("Sauce Labs Backpack"), "Remove");
        Assert.assertEquals(inventoryPage.getProductButtonText("Sauce Labs Onesie"), "Remove");
    }

    @Test(priority = 5)
    public void verifyCartTotalPrice() {
        double expectedSum = 0.0;

        for (String product : cartProducts) {
            expectedSum += inventoryPage.getProductPrice(product);
            inventoryPage.addProductToCart(product);
        }

        inventoryPage.clickCartIcon();
        cartPage.clickCheckout();

        cartPage.fillCheckoutInformation("Mona", "Doma", "12345");

        double actualItemTotal = cartPage.getItemTotal();
        Assert.assertEquals(actualItemTotal, expectedSum, "Calculated price sum does not match Item Total at checkout!");
    }

    @Test(priority = 6)
    public void checkoutWithEmptyCart() {
        inventoryPage.clickCartIcon();
        cartPage.clickCheckout();

        boolean isErrorDisplayed = cartPage.isErrorMessageDisplayed();
        boolean isNotOnCheckoutStepOne = !driver.getCurrentUrl().contains("checkout-step-one");

        Assert.assertTrue(isErrorDisplayed || isNotOnCheckoutStepOne,
                "Site allowed proceeding to the checkout form normally with an empty cart!");
    }

    // Scenario 7: Cart State After Logout/Login
    @Test(priority = 7)
    public void cartStateAfterLogoutLogin() {

        inventoryPage.addProductToCart(cartProducts.get(0));
        inventoryPage.addProductToCart(cartProducts.get(1));
        inventoryPage.logout();

        loginPage = new LoginPage(driver);
        inventoryPage = loginPage.login(username, password);

        inventoryPage.clickCartIcon();
        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart items were lost after logout/login!");
    }
}

//    private void switchToNewWindowAndVerify(String originalWindow, String expectedUrlPart) {
//        org.openqa.selenium.support.ui.WebDriverWait wait = new org.openqa.selenium.support.ui.WebDriverWait(driver, java.time.Duration.ofSeconds(10));
//
//        wait.until(driver -> driver.getWindowHandles().size() > 1);
//
//        for (String windowHandle : driver.getWindowHandles()) {
//            if (!windowHandle.equals(originalWindow)) {
//                driver.switchTo().window(windowHandle);
//                break;
//            }
//        }
//
//        wait.until(org.openqa.selenium.support.ui.ExpectedConditions.urlContains(expectedUrlPart));
//        Assert.assertTrue(driver.getCurrentUrl().contains(expectedUrlPart), "Verification failed for: " + expectedUrlPart);
//
//        driver.close();
//        driver.switchTo().window(originalWindow);
//    }
//        }
