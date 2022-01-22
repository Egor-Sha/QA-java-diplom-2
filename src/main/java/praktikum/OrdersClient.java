package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrdersClient  extends RestAssuredClient {

    private static final String ORDERS_PATH = "api/orders";

    @Step
    public static ValidatableResponse ordersList(String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .get(ORDERS_PATH)
                .then();
    }

    @Step
    public static String ingredientsList() {       //все ингредиенты
        return given()
                .spec(getBaseSpec())
                .get("api/ingredients")
                .asString();
    }

    @Step
    public static ValidatableResponse create(IngredientsCredentials ingredients, String token) {
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token)
                .and()
                .body(ingredients)
                .when()
                .post(ORDERS_PATH)
                .then();
    }


}


