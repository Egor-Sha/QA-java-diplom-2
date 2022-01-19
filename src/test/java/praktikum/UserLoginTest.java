package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserLoginTest {

    private User user;
    private UserClient userClient;
    private int userId;

    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    //@After
    //public void tearDown() {
    //    courierClient.delete(courierId);    }

    @Test
    public void correctLoginTest() {        //логин под существующим пользователем
        userClient.create(user);

        boolean isSuccess = userClient.loginToEnter(UserCredentials.from(user)).extract().path("success");
        assertEquals("Problem with user's login", true, isSuccess);
    }

    @Test
    public void incorrectLoginTest() {        //логин с неверным логином и паролем
        ValidatableResponse response = userClient.loginToEnter(UserCredentials.from(user));

        boolean isFalse = response.extract().path("success");
        assertEquals("Courier ID was not created", false, isFalse);

        String errorMessage = response.extract().path("message");
        assertThat("The same courier was not created", errorMessage, equalTo("email or password are incorrect"));
    }


}
