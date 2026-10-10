package api.base;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;

public class ApiBaseTest {

    @BeforeSuite
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }
}
