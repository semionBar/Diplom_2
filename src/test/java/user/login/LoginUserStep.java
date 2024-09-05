package user.login;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import user.CreateUserStep;
import user.LoggedInUser;
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


        System.out.println(response.statusCode());
        System.out.println(response.body().asString());


    }

    @Step
    public LoggedInUser getLoggedInUser(User user) {

        sendLoginUserRequest(user);

        LoggedInUser loggedInUser;

        loggedInUser = response.body().as(LoggedInUser.class);

        return loggedInUser;
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
    public void deleteUserData(LoggedInUser loggedInUser) {
        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendDeleteUserRequest(loggedInUser);
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
