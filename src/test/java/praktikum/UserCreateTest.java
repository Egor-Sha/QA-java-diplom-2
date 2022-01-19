package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserCreateTest {


    private User user;
    private UserClient userClient;
    private int userId;

    @Before
    public void setUp() {
        user = User.getRandom();
        userClient = new UserClient();
    }

    /*@After
    public void tearDown() {
        userClient.delete(userId);
    }*/

    @Test
    public void userCanBeCreatedTest() {        //создать уникального пользователя;
        ValidatableResponse response = userClient.create(user);

        System.out.println(user.email);
        System.out.println(user.password);
        System.out.println(user.name);

        boolean isUserCreated = response.extract().path("success");
        System.out.println(isUserCreated);

        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);

        assertTrue("User was not created", isUserCreated);
        assertThat("Bad Status Code", statusCode, equalTo(200));
    }

    @Test
    public void theSameUserCannotBeCreatedTest() {        //создать пользователя, который уже зарегистрирован;
        userClient.create(user);
        System.out.println(user.email);
        System.out.println(user.password);
        System.out.println(user.name);
        ValidatableResponse response = userClient.create(user);

        int statusCode = response.extract().statusCode();
        assertThat("Wrong code, expected 403", statusCode, equalTo(403));
        System.out.println(statusCode);

        String errorMessage = response.extract().path("message");
        assertThat("The same courier was not created", errorMessage, equalTo("User already exists"));
    }



}
