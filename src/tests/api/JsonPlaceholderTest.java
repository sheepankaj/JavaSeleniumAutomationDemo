package tests.api;

import api.base.ApiBaseTest;
import api.models.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

public class JsonPlaceholderTest extends ApiBaseTest {

    @SuppressWarnings("all")
    @Test
    public void testGetUserById() {
        String endpoint = "/users";
        int targetId = 2;

        String expectedName = "Ervin Howell";
        String expectedUsername = "Antonette";
        String expectedEmail = "Shanna@melissa.tv";

        System.out.println("========================================");
        System.out.println("API Test: GET " + endpoint);
        System.out.println("Looking for user with id: " + targetId);
        System.out.println();
        System.out.println("Expected values:");
        System.out.println("  name:     " + expectedName);
        System.out.println("  username: " + expectedUsername);
        System.out.println("  email:    " + expectedEmail);

        User[] users = RestAssured.given()
                .contentType(ContentType.JSON)
                .when()
                .get(endpoint)
                .then()
                .statusCode(200)
                .extract()
                .as(User[].class);

        User targetUser = null;
        for (User user : users) {
            if (user.getId() == targetId) {
                targetUser = user;
                break;
            }
        }

        Assert.assertNotNull(targetUser, "User with id " + targetId + " should exist in the response");

        String actualName = targetUser.getName();
        String actualUsername = targetUser.getUsername();
        String actualEmail = targetUser.getEmail();

        System.out.println();
        System.out.println("Actual response values:");
        System.out.println("  name:     " + actualName);
        System.out.println("  username: " + actualUsername);
        System.out.println("  email:    " + actualEmail);
        System.out.println("========================================");

        Assert.assertEquals(actualName, expectedName,
                "Name mismatch! Expected: " + expectedName + " but got: " + actualName);
        Assert.assertEquals(actualUsername, expectedUsername,
                "Username mismatch! Expected: " + expectedUsername + " but got: " + actualUsername);
        Assert.assertEquals(actualEmail, expectedEmail,
                "Email mismatch! Expected: " + expectedEmail + " but got: " + actualEmail);
    }
}
