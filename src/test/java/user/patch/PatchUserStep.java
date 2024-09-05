package user.patch;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import user.CreateUserStep;
import user.LoggedInUser;
import user.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class PatchUserStep {
    Response response;

    @Step
    public void sendGetUserDataRequestWithToken(LoggedInUser loggedInUser) {

        Gson gson = new Gson();

        System.out.println(gson.toJson(loggedInUser));

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUser.getAccessToken())
                .when()
                .get("api/auth/user");

        System.out.println(response.body().asString());
        System.out.println(response.statusCode());
    }

    @Step
    public void sendPatchUserDataRequestWithToken(LoggedInUser loggedInUser, User newUser) {

        Gson gson = new Gson();

        System.out.println(gson.toJson(loggedInUser));
        System.out.println(gson.toJson(newUser));

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUser.getAccessToken())
                .and()
                .body(newUser)
                .when()
                .patch("api/auth/user");

        System.out.println(response.body().asString());
        System.out.println(response.statusCode());
    }


    @Step
    public LoggedInUser getLoggedInUser(User user) {

        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        System.out.println(new Gson().toJson(user));

        return createUserStep.getLoggedInUser();
    }


    @Step
    public void clearUserData() {

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
