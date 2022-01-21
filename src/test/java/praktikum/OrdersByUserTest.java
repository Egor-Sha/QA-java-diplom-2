package praktikum;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class OrdersByUserTest {

    private User user;
    private UserClient userClient;

    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    //@After
    //public void tearDown() {
    //    courierClient.delete(courierId);    }

    @Test
    public void getOrdersByUserWithAuth() {
        OrdersClient listOrders = new OrdersClient();
        userClient.create(user);

        String accessToken = userClient.loginToEnter(UserCredentials.from(user)).extract().path("accessToken");
        accessToken = StringUtils.remove(accessToken, "Bearer ");

        ValidatableResponse response = listOrders.list(accessToken);

        int statusCode = response.extract().statusCode();
        assertEquals("You don't have the list of the orders", 200, statusCode);

        ArrayList ordersList = response.extract().path("orders");
        assertThat("No orders list in the response", ordersList, is(not(nullValue())));

    }

    @Test
    public void getOrdersByUserWithoutAuth() {
        OrdersClient listOrders = new OrdersClient();
        userClient.create(user);

        userClient.loginToEnter(UserCredentials.from(user));
        String accessToken = "";

        ValidatableResponse response = listOrders.list(accessToken);

        int statusCode = response.extract().statusCode();
        assertEquals("Auth checking doesn't work", 401, statusCode);

    }

}
