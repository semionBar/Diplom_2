package user.login;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import user.create.CreateUserStep;
import user.LoggedInUserModel;
import user.User;

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
    public void deleteUserData(LoggedInUserModel loggedInUserModel) {
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
