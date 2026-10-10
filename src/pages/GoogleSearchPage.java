package pages;

import common.CommonMethods;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Objects;
import java.util.Random;

public class GoogleSearchPage {

    private final WebDriver driver;
    private final CommonMethods commonMethods;
    private final By acceptCookies = By.id("L2AGLb");
    private final By searchBox = By.name("q");
    private final By searchResults = By.cssSelector("div.g");

    public GoogleSearchPage(WebDriver driver) {
        this.driver = driver;
        this.commonMethods = new CommonMethods(driver);
    }

    public void navigateToGoogle() {
        driver.get("https://www.google.com/");
    }

    public void searchFor(String query) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            WebElement acceptCookiesBtn = wait.until(ExpectedConditions.elementToBeClickable(acceptCookies));
            if (acceptCookiesBtn.isDisplayed()) {
                acceptCookiesBtn.click();
                commonMethods.sleepRandom(500, 1000);
            }
        } catch (Exception e) {
            // Cookie button not present, continue
        }

        WebElement searchBar = wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        searchBar.clear();
        commonMethods.sleepRandom(500, 1000);

        Random random = new Random();
        for (char c : query.toCharArray()) {
            searchBar.sendKeys(String.valueOf(c));
            try {
                Thread.sleep(random.nextInt(100) + 50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        searchBar.submit();
    }

    public boolean isSearchResultPresent(String expectedText) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(searchResults));
            return Objects.requireNonNull(driver.getPageSource()).contains(expectedText);
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
