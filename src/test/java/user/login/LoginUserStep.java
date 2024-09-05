package user.login;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import shared.SharedStep;
import user.create.CreateUserStep;
import model.user.LoggedInUserModel;
import model.user.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserStep {

    Response response;

    @Step
    public void sendLoginUserRequest(User user) {


        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(user)
                .when()
                .post("api/auth/login");


    }

    @Step
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

    @Step
    public User createNewUser() {
        User user = new User();

        user.generateNewUser();

        return user;
    }

    @Step
    public void registerUser(User user) {

        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);
    }

    @Step
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
