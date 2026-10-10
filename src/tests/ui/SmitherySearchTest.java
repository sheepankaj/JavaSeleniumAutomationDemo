package tests.ui;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.SmitheryPage;

public class SmitherySearchTest extends BaseTest {

    @Test
    public void testSmitherySearch() {
        SmitheryPage smitheryPage = new SmitheryPage(driver);
        String huggingFaceText = "Hugging Face";

        smitheryPage.navigateToSmithery();
        smitheryPage.searchForMcpServer(huggingFaceText);

        Assert.assertTrue(smitheryPage.isSearchResultPresent(huggingFaceText),
                "Search results for 'Hugging Face' should be present on the page");

        String currentUrl = smitheryPage.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("smithery"),
                "URL should be on the Smithery domain");
    }
}
