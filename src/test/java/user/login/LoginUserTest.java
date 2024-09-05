package user.login;

import io.restassured.RestAssured;
import model.user.LoggedInUserModel;
import model.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class LoginUserTest {

    User user;

    LoginUserStep loginUserStep;

    LoggedInUserModel loggedInUserModel;

    @Before
    public void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

    }

    @Test
    public void loginUserTest() {

        loginUserStep = new LoginUserStep();

        user = loginUserStep.createNewUserAndRegister();

        loginUserStep.sendLoginUserRequest(user);

        loggedInUserModel = loginUserStep.getLoggedInUser(user);

        loginUserStep.checkResponseCode(SC_OK);

        loginUserStep.checkBodyFieldNotNull("accessToken");
        loginUserStep.checkBodyFieldNotNull("refreshToken");
        loginUserStep.checkBodyFieldEqualsObject("success", true);

    }

    @Test
    public void wrongPasswordReturns401() {

        loginUserStep = new LoginUserStep();

        user = loginUserStep.createNewUserAndRegister();

        loggedInUserModel = loginUserStep.getLoggedInUser(user);

        user.setPassword("1234567890");

        loginUserStep.sendLoginUserRequest(user);

        loginUserStep.checkResponseCode(SC_UNAUTHORIZED);

        loginUserStep.checkBodyFieldNotNull("message");
        loginUserStep.checkBodyFieldEqualsObject("success", false);

    }



    @After
    public void clearUserData() {
        loginUserStep.clearUserData(loggedInUserModel);
    }


}
