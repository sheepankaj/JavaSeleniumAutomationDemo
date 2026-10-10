package common;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

/**
 * Common utility methods reusable across all page objects and tests.
 */
public class CommonMethods {

    private final WebDriver driver;

    public CommonMethods(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Waits until an element is visible and displays the expected text.
     * Checks visible text, placeholder, and value attributes.
     *
     * @param locator         The By locator for the element
     * @param expectedText    The text to wait for
     * @param timeoutSeconds  Maximum wait time in seconds
     */
    public void waitForTextDisplayed(By locator, String expectedText, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(driver -> {
            WebElement element = driver.findElement(locator);
            if (!element.isDisplayed()) {
                return false;
            }
            String text = element.getText();
            if (text.contains(expectedText)) {
                return true;
            }
            String placeholder = element.getAttribute("placeholder");
            if (placeholder != null && placeholder.contains(expectedText)) {
                return true;
            }
            String value = element.getAttribute("value");
            return value != null && value.contains(expectedText);
        });
    }

    /**
     * Sleeps for a random duration between minMs and maxMs to mimic human behavior.
     *
     * @param minMs  Minimum delay in milliseconds
     * @param maxMs  Maximum delay in milliseconds
     */
    public void sleepRandom(long minMs, long maxMs) {
        Random random = new Random();
        long delay = minMs + random.nextInt((int) (maxMs - minMs));
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
