package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GoogleSearchPage;

public class GoogleSearchTest extends BaseTest {

    @Test
    public void testGoogleSearch() {
        GoogleSearchPage googleSearchPage = new GoogleSearchPage(driver);

        googleSearchPage.navigateToGoogle();
        googleSearchPage.searchFor("Ireland");

        Assert.assertTrue(googleSearchPage.isSearchResultPresent("Ireland"),
                "Search results for 'Ireland' should be present on the page");

        String currentUrl = googleSearchPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("Ireland"),
                "URL should contain the search query 'Ireland'");
    }
}
