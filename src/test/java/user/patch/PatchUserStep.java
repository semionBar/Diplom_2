package user.patch;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.user.LoggedInUserModel;
import model.user.User;
import shared.SharedStep;
import user.create.CreateUserStep;

import static io.restassured.RestAssured.given;

public class PatchUserStep {
    Response response;

    private final String userDataPath = "api/auth/user";
    @Step("Отправить запрос на получение данных пользователя с токеном")
    public void sendGetUserDataRequestWithToken(LoggedInUserModel loggedInUserModel) {

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUserModel.getAccessToken())
                .when()
                .get(userDataPath);

    }

    @Step("Отправить запрос на обновление данных пользователя без токена")
    public void sendPatchUserDataRequestWithOutToken() {

        response = given()
                .header("Content-type", "application/json")
                .when()
                .get(userDataPath);


    }

    @Step("Отправить запрос на обновление данных пользователя с токеном")
    public void sendPatchUserDataRequestWithToken(LoggedInUserModel loggedInUserModel, User newUser) {

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUserModel.getAccessToken())
                .and()
                .body(newUser)
                .when()
                .patch(userDataPath);



    }


    @Step("Получить модель залогиненного пользователя")
    public LoggedInUserModel getLoggedInUser(User user) {

        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        return createUserStep.getLoggedInUser();
    }


    @Step("Очистить данные пользователя")
    public void clearUserData(LoggedInUserModel loggedInUserModel) {
        SharedStep.sendDeleteUserRequest(loggedInUserModel, response);
    }

    @Step("Проверить код ответа")
    public void checkResponseCode(int expectedCode) {
        SharedStep.checkResponseCode(expectedCode, response);
    }

    @Step("Проверить поле из body равно ожидаемому значению")
    public void checkBodyFieldEqualsObject(String field, Object object) {
        SharedStep.checkBodyFieldEqualsObject(field, object, response);
    }

    @Step("Проверить, что поле из body присутствует в ответе")
    public void checkBodyFieldNotNull(String field) {
        SharedStep.checkBodyFieldNotNull(field, response);
    }

}
