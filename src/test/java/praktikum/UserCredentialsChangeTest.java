package praktikum;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class UserCredentialsChangeTest {

    boolean isAuthorized;
    String updEmail; String updPassword; String updName;
    private final int expectedStatus;

    public UserCredentialsChangeTest(boolean isAuthorized, String updEmail, String updPassword, String updName, int expectedStatus) {

    this.isAuthorized = isAuthorized;
    this.updEmail = updEmail; this.updPassword = updPassword; this.updName = updName;
    this.expectedStatus = expectedStatus;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{

                {true, "NEW","","", 200},
                {true, "","NEW","", 200},
                {true, "","","NEW", 200},
                {false, "NEW","","", 401},
                {false, "","NEW","", 401},
                {false, "","","NEW", 401}
        };
    }
    @Test
    public void userDataChangeTest() {        //Изменение данных пользователя
        User user = User.getRandom();
        UserClient userClient = new UserClient();
        userClient.create(user);

        String accessToken = userClient.loginToEnter(UserCredentials.from(user)).extract().path("accessToken");

        if (isAuthorized) {
            accessToken = StringUtils.remove(accessToken, "Bearer ");
        } else {
            accessToken = "";
        }

        ValidatableResponse response = userClient.dataUpdateAuth(new UserCredentialsAll(updEmail+user.email, updPassword+user.password, updName+user.name),accessToken);

        int statusCode = response.extract().statusCode();
        assertEquals("Auth checking doesn't work", expectedStatus, statusCode);

    }
}
