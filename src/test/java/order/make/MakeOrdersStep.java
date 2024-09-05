package order.make;

import model.ingredient.IngredientIdListRequestModel;
import model.ingredient.IngredientListResponseModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.ingredient.IngredientModel;
import model.order.OrderResponseModel;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class MakeOrdersStep {

    Response response;

    @Step
    public void sendGetIngredientsListRequest() {

        response = given()
                .header("Content-type", "application/json")
                .get("api/ingredients");

    }

    @Step
    public void sendMakeOrderRequest(IngredientIdListRequestModel ingredientIdListRequestModel) {

        response = given()
                .header("Content-type", "application/json")
                .and()
                .body(ingredientIdListRequestModel)
                .post("api/orders");

    }

    @Step
    public OrderResponseModel getOrderResponseModel() {
        OrderResponseModel orderResponseModel = new OrderResponseModel();

        orderResponseModel = response.body().as(OrderResponseModel.class);

        return orderResponseModel;
    }

    @Step
    public IngredientListResponseModel getIngredientList() {

        IngredientListResponseModel ingredientListResponseModel = new IngredientListResponseModel();

        ingredientListResponseModel = response.body().as(IngredientListResponseModel.class);

        return ingredientListResponseModel;
    }

    @Step
    public IngredientIdListRequestModel getIngredientIdListRequestModel() {
        IngredientIdListRequestModel ingredientIdListRequestModel = new IngredientIdListRequestModel();

        IngredientListResponseModel ingredientListResponseModel = response.body().as(IngredientListResponseModel.class);

        ingredientIdListRequestModel.setIngredients(List.of(ingredientListResponseModel.getRandomBunId(), ingredientListResponseModel.getRandomMainId(), ingredientListResponseModel.getRandomSauceId()));

        return ingredientIdListRequestModel;
    }
}
