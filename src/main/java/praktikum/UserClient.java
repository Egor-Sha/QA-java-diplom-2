package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient {

    private static final String USER_PATH = "api/auth";

    @Step
    @DisplayName("Create User")
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then();
    }

    @Step
    @DisplayName("User login")
    public ValidatableResponse loginToEnter(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_PATH + "/login")
                .then();
    }

    @Step
    @DisplayName("Get user data by token")
    public ValidatableResponse dataReceive(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .when()
                .get(USER_PATH + "/user")
                .then();
    }

    @Step
    @DisplayName("Update user data")
    public ValidatableResponse dataUpdateAuth(UserCredentialsAll credentials, String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .and()
                .body(credentials)
                .when()
                .patch(USER_PATH + "/user")
                .then();
    }

}
