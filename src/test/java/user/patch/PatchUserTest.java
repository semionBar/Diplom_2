package user.patch;

import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import user.LoggedInUser;
import user.User;

public class PatchUserTest {

    User user;

    LoggedInUser loggedInUser;

    PatchUserStep patchUserStep;

    @Before
    public void setUp() {

        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        user = new User();

        user.generateNewUser();

        patchUserStep = new PatchUserStep();

        loggedInUser = patchUserStep.getLoggedInUser(user);


    }

    @Test
    public void nullTest() {

        User newUser = new User();

        newUser.generateNewUser();

        patchUserStep.sendPatchUserDataRequestWithToken(loggedInUser, newUser);

        patchUserStep.sendGetUserDataRequestWithToken(loggedInUser);


    }
}
