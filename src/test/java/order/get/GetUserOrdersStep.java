package order.get;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.ingredient.IngredientIdListRequestModel;
import model.order.OrderListResponseModel;
import model.user.LoggedInUserModel;
import model.user.User;
import order.make.MakeOrdersStep;
import org.junit.Assert;
import shared.SharedStep;
import user.create.CreateUserStep;

import static io.restassured.RestAssured.given;

public class GetUserOrdersStep {

    Response response;

    private final String getOrdersPath = "/api/orders";
    @Step("Отправить запрос на получение заказов пользователя без токена")
    public void sendGetUserOrdersWithoutToken() {

        response = given()
                .header("Content-type", "application/json")
                .get(getOrdersPath);

    }

    @Step("Отправить запрос на получение заказов пользователя с токеном")
    public void sendGetUserOrdersWithToken(LoggedInUserModel loggedInUserModel) {

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUserModel.getAccessToken())
                .get(getOrdersPath);

    }

    @Step("Получить модель ответа залогиненого пользователя")
    public LoggedInUserModel getLoggedInUserModel(User user) {

        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        LoggedInUserModel loggedInUserModel;

        loggedInUserModel = createUserStep.getLoggedInUser();

        return loggedInUserModel;
    }

    @Step("Очистить данные пользователя")
    public void clearUserData(LoggedInUserModel loggedInUserModel) {
        SharedStep.sendDeleteUserRequest(loggedInUserModel, response);
    }


    @Step("Проверить, что количество заказов равно ожидаеиому количеству")
    public void checkOrderListContainsExactAmount(int amountOfOrders) {
        OrderListResponseModel orderListResponseModel;

        orderListResponseModel = response.as(OrderListResponseModel.class);

        Assert.assertEquals(orderListResponseModel.getOrders().size(), amountOfOrders);

    }

    @Step("Оформить заказ")
    public void makeOrders(int amountOfOrders, LoggedInUserModel loggedInUserModel) {
        MakeOrdersStep makeOrdersStep = new MakeOrdersStep();

        IngredientIdListRequestModel ingredientIdListRequestModel;

        for (int i = 0; i < amountOfOrders; i++) {

            makeOrdersStep.sendGetIngredientsListRequest();

            ingredientIdListRequestModel = makeOrdersStep.getIngredientIdListRequestModel();

            makeOrdersStep.sendMakeOrderRequestWithToken(ingredientIdListRequestModel, loggedInUserModel);
        }
    }

    @Step("Проверить, что код овета ")
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
