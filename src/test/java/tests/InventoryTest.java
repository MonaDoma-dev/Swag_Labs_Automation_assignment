package tests;

import base.BaseTest;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Pages.InventoryPage;
import Pages.LoginPage;
import Utils.DataDriven;

public class InventoryTest extends BaseTest {
    private JSONObject testData;

    @BeforeClass
    public void loadTestData() {
        testData = DataDriven.jsonReader(System.getProperty("user.dir") + "/testData/testData.json");
    }

    @Test
    public void testInventoryPageElements() {
        LoginPage loginPage = new LoginPage(driver);
        JSONObject validUser = (JSONObject) testData.get("validUser");

        InventoryPage inventoryPage = loginPage.login(
                (String) validUser.get("username"),
                (String) validUser.get("password")
        );

        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Page title mismatch!");
        Assert.assertTrue(inventoryPage.isCartIconDisplayed(), "Cart icon is not displayed!");
        Assert.assertEquals(inventoryPage.getProductCount(), 6, "Products count is not 6!");
    }
}