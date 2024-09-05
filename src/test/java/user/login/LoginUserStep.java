package user.login;

import groovy.lang.Newify;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.user.LoggedInUserModel;
import model.user.User;
import shared.SharedStep;
import user.create.CreateUserStep;

import static io.restassured.RestAssured.given;

public class LoginUserStep {

    Response response;

    @Step("Отправить запрос на логин")
    public void sendLoginUserRequest(User user) {


        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(user)
                .when()
                .post("api/auth/login");


    }

    @Step("Получить модель залогиненного пользователя")
    public LoggedInUserModel getLoggedInUser(User user) {

        sendLoginUserRequest(user);

        LoggedInUserModel loggedInUserModel;

        loggedInUserModel = response.body().as(LoggedInUserModel.class);

        return loggedInUserModel;
    }

    @Step("Создать нового юзера и зарегистрироваться")
    public User createNewUserAndRegister() {
        User user = createNewUser();

        registerUser(user);

        return user;
    }

    @Step("Создать нового пользователя")
    public User createNewUser() {

        return new User(0);
    }

    @Step("Зарегистрировать нового пользователя")
    public void registerUser(User user) {

        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);
    }

    @Step("Очистить данные пользователя")
    public void clearUserData(LoggedInUserModel loggedInUserModel) {
        SharedStep.sendDeleteUserRequest(loggedInUserModel, response);
    }

    @Step("Проверить, что код ответа равен ожидаемому")
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
