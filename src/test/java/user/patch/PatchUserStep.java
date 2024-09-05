package user.patch;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import user.create.CreateUserStep;
import model.user.LoggedInUserModel;
import model.user.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PatchUserStep {
    Response response;

    @Step
    public void sendGetUserDataRequestWithToken(LoggedInUserModel loggedInUserModel) {

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUserModel.getAccessToken())
                .when()
                .get("api/auth/user");

    }

    @Step
    public void sendGetUserDataRequestWitOutToken() {

        response = given()
                .header("Content-type", "application/json")
                //.header("Authorization", loggedInUser.getAccessToken())
                .when()
                .get("api/auth/user");

    }

    @Step
    public void sendPatchUserDataRequestWithOutToken() {

        response = given()
                .header("Content-type", "application/json")
                //.header("Authorization", loggedInUser.getAccessToken())
                .when()
                .get("api/auth/user");

        System.out.println(response.statusCode());
        System.out.println(response.body().asString());

    }

    @Step
    public void sendPatchUserDataRequestWithToken(LoggedInUserModel loggedInUserModel, User newUser) {

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUserModel.getAccessToken())
                .and()
                .body(newUser)
                .when()
                .patch("api/auth/user");



    }


    @Step
    public LoggedInUserModel getLoggedInUser(User user) {

        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        return createUserStep.getLoggedInUser();
    }


    @Step
    public void clearUserData(LoggedInUserModel loggedInUserModel) {
        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendDeleteUserRequest(loggedInUserModel);
    }

    @Step
    public void checkResponseCode(int expectedCode) {
        response.then().statusCode(expectedCode);
    }

    @Step("Проверить поле из body равно ожидаемому значению")
    public void checkBodyFieldEqualsObject(String field, Object object) {
        response.then().assertThat().body(field,equalTo(object));
    }

    @Step("Проверить, что поле из body присутствует в ответе")
    public void checkBodyFieldNotNull(String field) {
        response.then().assertThat().body(field, Matchers.notNullValue());
    }

}
