package base;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.FileReader;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {
    protected WebDriver driver;
    protected String username;
    protected String password;
    protected List<String> cartProducts = new ArrayList<>();
    public WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        loadTestData();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.saucedemo.com/");
    }

    private void loadTestData() {
        try {
            JSONParser parser = new JSONParser();
            FileReader reader = new FileReader("testData/testData.json");
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONObject validUser = (JSONObject) jsonObject.get("validUser");
            if (validUser != null) {
                username = (String) validUser.get("username");
                password = (String) validUser.get("password");
            }

            JSONArray productsArray = (JSONArray) jsonObject.get("cartProducts");
            if (productsArray != null) {
                cartProducts.clear();
                for (Object prod : productsArray) {
                    cartProducts.add((String) prod);
                }
            }
        } catch (Exception e) {
            System.out.println("Error reading JSON file: " + e.getMessage());
        }
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}