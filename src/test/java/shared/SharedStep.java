package shared;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.user.LoggedInUserModel;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class SharedStep {

    public static void checkResponseCode(int expectedCode, Response response) {
        response.then().statusCode(expectedCode);
    }

    public static void checkBodyFieldEqualsObject(String field, Object object, Response response) {
        response.then().assertThat().body(field,equalTo(object));
    }

    public static void checkBodyFieldNotNull(String field, Response response) {
        response.then().assertThat().body(field, Matchers.notNullValue());
    }

    @Step("Отправить запрос на удаление пользователя")
    public static void sendDeleteUserRequest(LoggedInUserModel loggedInUserModel, Response response) {

        if (loggedInUserModel != null) {
            if (loggedInUserModel.getAccessToken() != null) {
                response = given()
                        .header("Content-type", "application/json")
                        .header("Authorization", loggedInUserModel.getAccessToken())
                        //.auth().oauth2("подставь_сюда_свой_токен")
                        .when()
                        .delete("api/auth/user");

            }
        }
    }
}
