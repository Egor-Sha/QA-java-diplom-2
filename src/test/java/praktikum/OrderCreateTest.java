package praktikum;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class OrderCreateTest {

    boolean isAuthorized;
    String ingredientHash1;
    String ingredientHash2;
    int statusCodeExpected;
    boolean isOrderCreatedExpected;

    public OrderCreateTest(boolean isAuthorized, String ingredientHash1, String ingredientHash2, int statusCodeExpected, boolean isOrderCreatedExpected) {
        this.isAuthorized = isAuthorized;
        this.ingredientHash1 = ingredientHash1;
        this.ingredientHash2 = ingredientHash2;
        this.statusCodeExpected = statusCodeExpected;
        this.isOrderCreatedExpected = isOrderCreatedExpected;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{

                {true, "61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6e", 200, true},
                {false, "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa6f", 200, true},
                {true, null, null, 400 , false},
                {true, "", "", 500 , false}
        };
    }

    @Test
    public void orderCreateWithDifAuthAndHashParametersTest() {        //создать уникального пользователя;

        User user = User.getRandom();
        UserClient userClient = new UserClient();
        userClient.create(user);

        String[] ingredientsHash = {ingredientHash1,ingredientHash2};
        IngredientsCredentials ingredients = new IngredientsCredentials(ingredientsHash);

        String accessToken = userClient.loginToEnter(UserCredentials.from(user)).extract().path("accessToken");

        if (isAuthorized) {
            accessToken = StringUtils.remove(accessToken, "Bearer ");
        } else {
            accessToken = "";
        }

        ValidatableResponse response = OrdersClient.create(ingredients, accessToken);

        int statusCode = response.extract().statusCode();
        assertThat("Bad Status Code", statusCode, equalTo(statusCodeExpected));
        System.out.println(statusCode);

        if (statusCode == 200 || statusCode == 400) {
            boolean isOrderCreatedActual = response.extract().path("success");
            assertEquals("Order wasn't created", isOrderCreatedActual, isOrderCreatedExpected);
            System.out.println(isOrderCreatedActual);
        }
    }
}
