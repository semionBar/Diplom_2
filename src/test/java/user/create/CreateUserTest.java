package user.create;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class CreateUserTest {

    private CreateUserModel createUserModel;

    private CreateUserStep createUserStep;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";
    }

    @Test
    public void ZeroTest() {

        createUserModel = new CreateUserModel();

        createUserModel.generateNewUser();

        createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(createUserModel);
    }

    @After
    public void clearData() {
        createUserStep.sendDeleteUserRequest(createUserModel);
    }

}
