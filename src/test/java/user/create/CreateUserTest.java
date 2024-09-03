package user.create;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_FORBIDDEN;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateUserTest {

    private User user;

    private CreateUserStep createUserStep;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void zeroTest() {

        user = new User();

        user.generateNewUser();

        createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        createUserStep.checkResponseCode(SC_OK);

        createUserStep.checkBodyFieldEqualsObject( "success", true);

        createUserStep.checkBodyFieldNotNull("accessToken");

        createUserStep.checkBodyFieldNotNull("refreshToken");

    }

    @Test
    public void createExistingUserTest() {
        user = new User();

        user.generateNewUser();

        createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        createUserStep.sendCreateUserRequest(user);

        createUserStep.checkResponseCode(SC_FORBIDDEN);

        createUserStep.checkBodyFieldEqualsObject( "success", false);

        createUserStep.checkBodyFieldNotNull("message");
    }

    @Test
    public void createUserNoPasswordFieldTest() {
        user = new User();

        user.generateNewUser();

        user.setPassword(null);

        createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        createUserStep.checkResponseCode(SC_FORBIDDEN);

        createUserStep.checkBodyFieldEqualsObject( "success", false);

        createUserStep.checkBodyFieldNotNull("message");
    }

    @After
    public void clearData() {
        createUserStep.sendDeleteUserRequest(user);
    }

}
