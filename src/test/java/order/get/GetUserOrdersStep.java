package order.get;

import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.ingredient.IngredientIdListRequestModel;
import model.order.Order;
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
    @Step
    public void sendGetUserOrdersWithoutToken() {

        response = given()
                .header("Content-type", "application/json")
                .get("/api/orders");

        System.out.println(response.body().asString());
        System.out.println(response.statusCode());
    }

    @Step
    public void sendGetUserOrdersWithToken(LoggedInUserModel loggedInUserModel) {

        response = given()
                .header("Content-type", "application/json")
                .header("Authorization", loggedInUserModel.getAccessToken())
                .get("/api/orders");

        System.out.println(response.body().asString());
    }

    @Step
    public LoggedInUserModel getLoggedInUserModel(User user) {

        CreateUserStep createUserStep = new CreateUserStep();

        createUserStep.sendCreateUserRequest(user);

        LoggedInUserModel loggedInUserModel;

        loggedInUserModel = createUserStep.getLoggedInUser();

        return loggedInUserModel;
    }

    @Step
    public void clearUserData(LoggedInUserModel loggedInUserModel) {
        SharedStep.sendDeleteUserRequest(loggedInUserModel, response);
    }


    @Step
    public void checkOrderListContainsExactAmount(int amountOfOrders) {
        OrderListResponseModel orderListResponseModel;

        orderListResponseModel = response.as(OrderListResponseModel.class);

        Assert.assertEquals(orderListResponseModel.getOrders().size(), amountOfOrders);

        System.out.println(new Gson().toJson(orderListResponseModel));
    }

    @Step
    public void makeOrders(int amountOfOrders, LoggedInUserModel loggedInUserModel) {
        MakeOrdersStep makeOrdersStep = new MakeOrdersStep();

        IngredientIdListRequestModel ingredientIdListRequestModel;

        for (int i = 0; i < amountOfOrders; i++) {

            makeOrdersStep.sendGetIngredientsListRequest();

            ingredientIdListRequestModel = makeOrdersStep.getIngredientIdListRequestModel();

            makeOrdersStep.sendMakeOrderRequestWithToken(ingredientIdListRequestModel, loggedInUserModel);
        }
    }

    @Step
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
