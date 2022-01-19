package praktikum;

import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class UserCreateValidationTest {

    private final User user;
    private final int expectedStatus;
    private final String expectedErrorMessage;

    public UserCreateValidationTest(User user, int expectedStatus, String expectedErrorMessage) {
        this.user = user;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{

                {User.getWithEmailPassOnly(), 403, "Email, password and name are required fields"},
                {User.getWithEmailNameOnly(), 403, "Email, password and name are required fields"},
                {User.getWithPassNameOnly(), 403, "Email, password and name are required fields"}
        };
    }
    @Test
    public void userCannotBeCreatedWithoutFieldTest() {        //создать пользователя и не заполнить одно из обязательных полей, проверяю без

        ValidatableResponse response = new UserClient().create(user);

        System.out.println(user.email);
        System.out.println(user.password);
        System.out.println(user.name);

        boolean isResponse = response.extract().path("success");
        System.out.println(isResponse);

        int statusCode = response.extract().statusCode();
        System.out.println(statusCode);

        String errorMessage = response.extract().path("message");
        assertThat("The courier was created by error", errorMessage, equalTo(expectedErrorMessage));
        assertThat("Courier created by fault", statusCode, equalTo(expectedStatus));

    }
}
