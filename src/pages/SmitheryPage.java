package pages;

import common.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SmitheryPage {

    private final WebDriver driver;
    private final CommonMethods commonMethods;
    private final By searchBox = By.id("hero-server-search");
    private final By searchResults = By.cssSelector("a[href*='/servers/']");

    public SmitheryPage(WebDriver driver) {
        this.driver = driver;
        this.commonMethods = new CommonMethods(driver);
    }

    public void navigateToSmithery() {
        driver.get("https://smithery.ai/");
    }

    public void searchForMcpServer(String query) {
        commonMethods.waitForTextDisplayed(searchBox, "Search for MCP servers...", 15);

        WebElement searchBar = driver.findElement(searchBox);
        searchBar.clear();

        for (char c : query.toCharArray()) {
            searchBar.sendKeys(String.valueOf(c));
            commonMethods.sleepRandom(50, 150);
        }

        searchBar.sendKeys(Keys.ENTER);
    }

    public boolean isSearchResultPresent(String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(searchResults));
            return driver.getPageSource().contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
