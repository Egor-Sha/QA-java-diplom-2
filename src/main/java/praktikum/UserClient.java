package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class UserClient extends RestAssuredClient {

    private static final String USER_PATH = "api/auth";

    @Step       //названия для степов чтобы норм отображалось в аллюре
    public ValidatableResponse create(User user) {
        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(USER_PATH + "/register")
                .then();
    }

    @Step
    public ValidatableResponse loginToEnter(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(USER_PATH + "/login")
                .then();
    }

    @Step
    public ValidatableResponse dataUpdate(UserCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .patch(USER_PATH + "/user")
                .then();
    }

    @Step
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




    /*
    @Step
    public ValidatableResponse delete(int userId) {
        return given()
                .spec(getBaseSpec())
                .when()
                .delete(USER_PATH + userId)
                .then();
    }*/



}
