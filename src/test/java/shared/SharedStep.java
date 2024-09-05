package shared;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.user.LoggedInUserModel;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class SharedStep {

    @Step
    public static void checkResponseCode(int expectedCode, Response response) {
        response.then().statusCode(expectedCode);
    }

    @Step("Проверить поле из body равно ожидаемому значению")
    public static void checkBodyFieldEqualsObject(String field, Object object, Response response) {
        response.then().assertThat().body(field,equalTo(object));
    }

    @Step("Проверить, что поле из body присутствует в ответе")
    public static void checkBodyFieldNotNull(String field, Response response) {
        response.then().assertThat().body(field, Matchers.notNullValue());
    }

    @Step
    public static void sendDeleteUserRequest(LoggedInUserModel loggedInUserModel, Response response) {

        if (loggedInUserModel != null) {
            if (loggedInUserModel.getAccessToken() != null) {
                response = given()
                        .header("Content-type", "application/json")
                        .header("Authorization", loggedInUserModel.getAccessToken())
                        //.auth().oauth2("подставь_сюда_свой_токен")
                        .when()
                        .delete("api/auth/user");

                System.out.println(response.body().asString());

            }
        }
    }
}
