package user.create;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserStep {

    Response response;

    @Step
    public void sendCreateUserRequest(User user) {

        Gson gson = new Gson();

        System.out.println(gson.toJson(user));

        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(user)
                .when()
                .post("api/auth/register");

        System.out.println(response.body().asString());
        System.out.println(response.statusCode());

    }

    @Step
    public void sendDeleteUserRequest(User user) {

        Gson gson = new Gson();

        System.out.println(gson.toJson(user));

        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(user)
                .when()
                .delete("api/auth/register");

        System.out.println(response.body().asString());

        System.out.println(response.statusCode());
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

