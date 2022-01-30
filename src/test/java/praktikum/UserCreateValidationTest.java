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

    public UserCreateValidationTest(User user, int expectedStatus) {
        this.user = user;
        this.expectedStatus = expectedStatus;
    }
    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {User.getWithEmailPassOnly(), 403},
                {User.getWithEmailNameOnly(), 403},
                {User.getWithPassNameOnly(), 403}
        };
    }
    @Test
    public void userCannotBeCreatedWithoutFieldTest() {

        ValidatableResponse response = new UserClient().create(user);

        int statusCode = response.extract().statusCode();
        assertThat("Courier created by fault", statusCode, equalTo(expectedStatus));
    }
}
