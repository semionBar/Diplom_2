package user.create;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.user.LoggedInUserModel;
import model.user.User;
import shared.SharedStep;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateUserStep {

    Response response;


    @Step("Отправить запрос на создание нового пользователя")
    public void sendCreateUserRequest(User user) {

        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(user)
                .when()
                .post("api/auth/register");


    }

    @Step("Получить модель залогиненного пользователя")
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

