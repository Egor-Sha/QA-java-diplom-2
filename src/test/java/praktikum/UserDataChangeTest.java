/*package praktikum;

import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class UserDataChangeTest {       //ЭТО НУЖНО УДАЛИТЬ

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
    public void incorrectUpdateDataTest() {        //Изменение данных пользователя без авторизации
        userClient.create(user);                    //Желательно сделать изменения на другие данные
        ValidatableResponse response = userClient.dataUpdate(UserCredentials.from(user));
        boolean isFalse = response.extract().path("success");
        assertEquals("Auth checking doesn't work", false, isFalse);

        int statusCode = response.extract().statusCode();
        assertEquals("Auth checking doesn't work", 401, statusCode);

        String errorMessage = response.extract().path("message");
        assertThat("Incorrect message after auth checking or auth checking doesn't work", errorMessage, equalTo("You should be authorised"));
    }

    @Test
    public void correctUpdateDataTest() {        //Изменение данных пользователя c авторизацией
        userClient.create(user);

        String accessToken = userClient.loginToEnter(UserCredentials.from(user)).extract().path("accessToken");
        accessToken = StringUtils.remove(accessToken, "Bearer ");

        ValidatableResponse response = userClient.dataUpdateAuth(new UserCredentialsAll("NEW"+user.email, user.password, user.name),accessToken);

        int statusCode = response.extract().statusCode();
        assertEquals("Auth checking doesn't work", 200, statusCode);

        boolean isTrue = response.extract().path("success");
        assertEquals("Auth checking doesn't work", true, isTrue);

    }

}
*/