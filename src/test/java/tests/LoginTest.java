package tests;

import base.BaseTest;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Pages.InventoryPage;
import Pages.LoginPage;
import Utils.DataDriven;

public class LoginTest extends BaseTest {
    private JSONObject testData;

    @BeforeClass
    public void loadTestData() {
        testData = DataDriven.jsonReader("testData/testData.json");
    }

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        JSONObject validUser = (JSONObject) testData.get("validUser");

        InventoryPage inventoryPage = loginPage.login(
                (String) validUser.get("username"),
                (String) validUser.get("password")
        );

        Assert.assertTrue(inventoryPage.getCurrentUrl().contains("/inventory.html"),
                "User was not redirected to inventory page.");
    }

    @Test(priority = 2)
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        JSONObject invalidUser = (JSONObject) testData.get("invalidUser");

        loginPage.login(
                (String) invalidUser.get("username"),
                (String) invalidUser.get("password")
        );

        String errorText = loginPage.getErrorMessageText();
        Assert.assertTrue(errorText.contains("Username and password do not match"),
                "Error message text mismatch!");
    }

    @Test(priority = 3)
    public void testLoginWithoutPassword() {
        LoginPage loginPage = new LoginPage(driver);
        JSONObject userWithoutPassword = (JSONObject) testData.get("userWithoutPassword");

        loginPage.login(
                (String) userWithoutPassword.get("username"),
                (String) userWithoutPassword.get("password")
        );

        String errorText = loginPage.getErrorMessageText();
        Assert.assertTrue(errorText.contains("Password is required"),
                "Error message text mismatch!");
    }
}
