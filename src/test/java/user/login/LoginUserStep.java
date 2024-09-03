package user.login;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import user.create.CreateUserStep;
import user.create.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class LoginUserStep {

    Response response;

    @Step
    public void sendLoginUserRequest(User createUserModel) {

        Gson gson = new Gson();

        System.out.println(gson.toJson(createUserModel));

        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(createUserModel)
                .when()
                .post("api/auth/login");

        System.out.println(response.body().asString());
        System.out.println(response.statusCode());

    }

    @Step("Создать нового юзера")
    public User createNewUser() {
        User user = new User();

        user.generateNewUser();

        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        return user;
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
