package user.create;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CreateUserStep {

    Response response;

    @Step
    public void sendCreateUserRequest(CreateUserModel createUserModel) {

        Gson gson = new Gson();

        System.out.println(gson.toJson(createUserModel));

        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(createUserModel)
                .when()
                .post("api/auth/register");

        System.out.println(response.body().asString());

    }

    @Step
    public void sendDeleteUserRequest(CreateUserModel createUserModel) {

        Gson gson = new Gson();

        System.out.println(gson.toJson(createUserModel));

        response = given()
                .header("Content-type", "application/json")
                //.auth().oauth2("подставь_сюда_свой_токен")
                .and()
                .body(createUserModel)
                .when()
                .delete("api/auth/register");

        System.out.println(response.body().asString());

        System.out.println(response.statusCode());
    }

}

