package tests;

import base.BaseTest;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import Pages.InventoryPage;
import Pages.loginPage;
import Utils.DataDriven;

public class InventoryTest extends BaseTest {
    private JSONObject testData;

    @BeforeClass
    public void loadTestData() {
        testData = DataDriven.jsonReader(System.getProperty("user.dir") + "/testData/testData.json");
    }

    @Test
    public void testInventoryPageElements() {
        loginPage loginPage = new loginPage(driver);
        JSONObject validUser = (JSONObject) testData.get("validUser");

        // Step 1: Login successfully
        InventoryPage inventoryPage = loginPage.login(
                (String) validUser.get("username"),
                (String) validUser.get("password")
        );

        // Step 2: Verify Page Title/Header
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Page title mismatch!");

        // Step 3: Verify Cart Icon is displayed
        Assert.assertTrue(inventoryPage.isCartIconDisplayed(), "Cart icon is not displayed!");

        // Step 4: Verify 6 products displayed
        Assert.assertEquals(inventoryPage.getProductCount(), 6, "Products count is not 6!");
    }
}
