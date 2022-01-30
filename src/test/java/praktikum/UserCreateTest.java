package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserCreateTest {

    private User user;
    private UserClient userClient;
    String accessToken;

    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    @Test
    public void userCanBeCreatedTest() {
        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        assertThat("Bad Status Code", statusCode, equalTo(200));
    }

    @Test
    public void theSameUserCannotBeCreatedTest() {

        userClient.create(user);

        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        assertThat("Wrong code, expected 403", statusCode, equalTo(403));

        String errorMessage = response.extract().path("message");
        assertThat("The same courier was not created", errorMessage, equalTo("User already exists"));
    }

}
