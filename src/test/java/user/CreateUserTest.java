package user;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateUserTest {

    private User user;

    private LoggedInUser loggedInUser;

    private CreateUserStep createUserStep;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        user = new User();

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

        loggedInUser = createUserStep.getLoggedInUser();

    }

    @Test
    public void createExistingUserTest() {

        createUserStep.sendCreateUserRequest(user);

        loggedInUser = createUserStep.getLoggedInUser();

        createUserStep.sendCreateUserRequest(user);

        createUserStep.checkResponseCode(SC_FORBIDDEN);

        createUserStep.checkBodyFieldEqualsObject( "success", false);

        createUserStep.checkBodyFieldNotNull("message");

    }

    @Test
    public void createUserNoPasswordFieldTest() {

        user.setPassword(null);

        createUserStep.sendCreateUserRequest(user);

        createUserStep.checkResponseCode(SC_FORBIDDEN);

        createUserStep.checkBodyFieldEqualsObject( "success", false);

        createUserStep.checkBodyFieldNotNull("message");

        loggedInUser = createUserStep.getLoggedInUser();

    }

    @After
    public void clearData() {
        createUserStep.sendDeleteUserRequest(loggedInUser);
    }

}
