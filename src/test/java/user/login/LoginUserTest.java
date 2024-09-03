package user.login;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.create.User;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class LoginUserTest {

    User user;

    LoginUserStep loginUserStep;

    @Before
    public void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

    }

    @Test
    public void zeroTest() {

        loginUserStep = new LoginUserStep();
        user = loginUserStep.createNewUser();

        loginUserStep.sendLoginUserRequest(user);

        loginUserStep.checkResponseCode(SC_OK);

        loginUserStep.checkBodyFieldNotNull("accessToken");
        loginUserStep.checkBodyFieldNotNull("refreshToken");
        loginUserStep.checkBodyFieldEqualsObject("success", true);

    }

    @Test
    public void badLoginOrPasswordTest() {

        loginUserStep = new LoginUserStep();

        user = new User();
        user.generateNewUser();

        loginUserStep.sendLoginUserRequest(user);

        loginUserStep.checkResponseCode(SC_UNAUTHORIZED);

        loginUserStep.checkBodyFieldNotNull("message");
        loginUserStep.checkBodyFieldEqualsObject("success", false);

    }

    @After
    public void clearUserData() {

    }


}
