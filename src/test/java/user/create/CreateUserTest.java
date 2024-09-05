package user.create;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.user.LoggedInUserModel;
import model.user.User;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateUserTest {

    private User user;

    private LoggedInUserModel loggedInUserModel;

    private CreateUserStep createUserStep;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        user = new User(0);

        user.generateNewUser();

        createUserStep = new CreateUserStep();
    }

    @Test
    public void createUserTest() {


        createUserStep.sendCreateUserRequest(user);

        createUserStep.checkResponseCode(SC_OK);

        createUserStep.checkBodyFieldEqualsObject( "success", true);

        createUserStep.checkBodyFieldNotNull("accessToken");

        createUserStep.checkBodyFieldNotNull("refreshToken");

        loggedInUserModel = createUserStep.getLoggedInUser();

    }

    @Test
    public void createExistingUserReturns403() {

        createUserStep.sendCreateUserRequest(user);

        loggedInUserModel = createUserStep.getLoggedInUser();

        createUserStep.sendCreateUserRequest(user);

        createUserStep.checkResponseCode(SC_FORBIDDEN);

        createUserStep.checkBodyFieldEqualsObject( "success", false);

        createUserStep.checkBodyFieldNotNull("message");

    }

    @Test
    public void createUserNoPasswordFieldReturns403() {

        user.setPassword(null);

        createUserStep.sendCreateUserRequest(user);

        createUserStep.checkResponseCode(SC_FORBIDDEN);

        createUserStep.checkBodyFieldEqualsObject( "success", false);

        createUserStep.checkBodyFieldNotNull("message");

        loggedInUserModel = createUserStep.getLoggedInUser();

    }

    @After
    public void clearData() {
        createUserStep.clearUserData(loggedInUserModel);
    }

}
