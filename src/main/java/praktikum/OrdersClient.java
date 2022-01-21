package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient  extends RestAssuredClient {

    private static final String ORDERS_PATH = "api/orders";

    @Step
    public static ValidatableResponse list(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .get(ORDERS_PATH)
                .then();
    }
}


