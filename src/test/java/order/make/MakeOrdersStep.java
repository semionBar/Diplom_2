package order.make;

import model.ingredient.IngredientListResponseModel;
import io.qameta.allure.Step;
import io.restassured.response.Response;

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
    public IngredientListResponseModel getIngredientList() {

        IngredientListResponseModel ingredientListResponseModel = new IngredientListResponseModel();

        ingredientListResponseModel = response.body().as(IngredientListResponseModel.class);

        return ingredientListResponseModel;
    }
}
