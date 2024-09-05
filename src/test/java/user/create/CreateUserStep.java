package user.create;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import model.user.LoggedInUserModel;
import model.user.User;
import shared.SharedStep;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.equalTo;

public class CreateUserStep {

    Response response;


    @Step
    public void sendCreateUserRequest(User user) {

        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(user)
                .when()
                .post("api/auth/register");


    }

    @Step
    public LoggedInUserModel getLoggedInUser() {
        if (response.statusCode() == SC_OK) {


            LoggedInUserModel loggedInUserModel = new LoggedInUserModel();
            loggedInUserModel = response.body().as(LoggedInUserModel.class);

            return loggedInUserModel;
        }
        else {

            return new LoggedInUserModel();
        }
    }

    @Step
    public void sendDeleteUserRequest(LoggedInUserModel loggedInUserModel) {

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

    public void clearUserData(LoggedInUserModel loggedInUserModel) {
        SharedStep.sendDeleteUserRequest(loggedInUserModel, response);
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

