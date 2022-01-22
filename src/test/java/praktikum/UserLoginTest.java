package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserLoginTest {

    private User user;
    private UserClient userClient;
    private String accessToken;

    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

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
        assertEquals("Comparing with database doesn't work", false, isFalse);

        String errorMessage = response.extract().path("message");
        assertThat("Comparing with database doesn't work", errorMessage, equalTo("email or password are incorrect"));
    }
}
