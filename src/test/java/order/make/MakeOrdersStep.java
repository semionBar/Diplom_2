package order.make;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.ingredient.IngredientIdListRequestModel;
import model.ingredient.IngredientListResponseModel;
import model.user.LoggedInUserModel;
import model.user.User;
import shared.SharedStep;
import user.create.CreateUserStep;

import java.util.List;

import static io.restassured.RestAssured.given;

public class MakeOrdersStep {

    Response response;

    private final String getIngredientsPath = "api/ingredients";
    private final String getOrdersPath = "api/orders";



    @Step("Отправить запрос на получение списка всех доступных ингредиентов")
    public void sendGetIngredientsListRequest() {

        response = given()
                .header("Content-type", "application/json")
                .get(getIngredientsPath);

    }

    @Step("Отправить запрос на создание заказа без токена")
    public void sendMakeOrderRequestWithOutToken(IngredientIdListRequestModel ingredientIdListRequestModel) {

        response = given()
                .header("Content-type", "application/json")
                .and()
                .body(ingredientIdListRequestModel)
                .post(getOrdersPath);


    }

    @Step("Отправить запрос на создание заказа с токеном")
    public void sendMakeOrderRequestWithToken(IngredientIdListRequestModel ingredientIdListRequestModel, LoggedInUserModel loggedInUserModel) {

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUserModel.getAccessToken())
                .and()
                .body(ingredientIdListRequestModel)
                .post(getOrdersPath);


    }

    @Step("Получить модель ответа залогиненного пользователя")
    public LoggedInUserModel getLoggedInUserModel(User user) {
        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        return createUserStep.getLoggedInUser();
    }


    @Step("Получить модель ответа всех доступных ингредиентов")
    public IngredientListResponseModel getIngredientList() {

        IngredientListResponseModel ingredientListResponseModel;

        ingredientListResponseModel = response.body().as(IngredientListResponseModel.class);

        return ingredientListResponseModel;
    }

    @Step("Получить модель запроса заказов пользователя")
    public IngredientIdListRequestModel getIngredientIdListRequestModel() {
        IngredientIdListRequestModel ingredientIdListRequestModel = new IngredientIdListRequestModel();

        IngredientListResponseModel ingredientListResponseModel = response.body().as(IngredientListResponseModel.class);

        ingredientIdListRequestModel.setIngredients(List.of(ingredientListResponseModel.getRandomBunId(), ingredientListResponseModel.getRandomMainId(), ingredientListResponseModel.getRandomSauceId()));

        return ingredientIdListRequestModel;
    }

    @Step("Очистить данные пользователя")
    public void clearUserData(LoggedInUserModel loggedInUserModel) {
        SharedStep.sendDeleteUserRequest(loggedInUserModel);
    }

    @Step("Проверить, что фактический код ответа соответствует ожидаемому")
    public void checkResponseCode(int expectedCode) {
        SharedStep.checkResponseCode(expectedCode, response);
    }

    @Step("Проверить, что поле из body равно ожидаемому значению")
    public void checkBodyFieldEqualsObject(String field, Object object) {
        SharedStep.checkBodyFieldEqualsObject(field, object, response);
    }

    @Step("Проверить, что поле из body присутствует в ответе")
    public void checkBodyFieldNotNull(String field) {
        SharedStep.checkBodyFieldNotNull(field, response);
    }
}
