package user.patch;

import io.restassured.RestAssured;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import model.user.LoggedInUserModel;
import model.user.User;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

public class PatchUserTest {

    User user;

    LoggedInUserModel loggedInUserModel;

    PatchUserStep patchUserStep;

    @Before
    public void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        patchUserStep = new PatchUserStep();

        loggedInUserModel = patchUserStep.getLoggedInUser(new User(0));
    }

    @Test
    public void patchUserDataTest() {

        patchUserStep.sendPatchUserDataRequestWithToken(loggedInUserModel, new User(0));

        patchUserStep.sendGetUserDataRequestWithToken(loggedInUserModel);

        patchUserStep.checkResponseCode(SC_OK);
        patchUserStep.checkBodyFieldNotNull("user");
        patchUserStep.checkBodyFieldEqualsObject("success", true);

    }

    @Test
    public void getUserDataTest() {
        patchUserStep.sendGetUserDataRequestWithToken(loggedInUserModel);

        patchUserStep.checkResponseCode(SC_OK);
        patchUserStep.checkBodyFieldNotNull("user");
        patchUserStep.checkBodyFieldEqualsObject("success", true);
    }

    @Test
    public void patchUserDataWithOutTokenReturns401() {
        patchUserStep.sendPatchUserDataRequestWithOutToken();

        patchUserStep.checkResponseCode(SC_UNAUTHORIZED);
        patchUserStep.checkBodyFieldEqualsObject("success", false);
        patchUserStep.checkBodyFieldNotNull("message");

    }



    @After
    public void clearUserData() {
        patchUserStep.clearUserData(loggedInUserModel);
    }
}
