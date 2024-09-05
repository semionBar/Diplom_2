package order.make;

import com.google.gson.Gson;
import model.ingredient.IngredientIdListRequestModel;
import model.ingredient.IngredientListResponseModel;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

public class MakeOrdersTest {

    MakeOrdersStep makeOrdersStep;

    IngredientListResponseModel ingredientListResponseModel;

    IngredientIdListRequestModel ingredientIdListRequestModel;
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        makeOrdersStep = new MakeOrdersStep();

        makeOrdersStep.sendGetIngredientsListRequest();

        ingredientListResponseModel = new IngredientListResponseModel();

        ingredientListResponseModel = makeOrdersStep.getIngredientList();

    }

    @Test
    public void setMakeOrderTest() {

        ingredientIdListRequestModel = makeOrdersStep.getIngredientIdListRequestModel();



        System.out.println(new Gson().toJson("Ответ:"));
        System.out.println(new Gson().toJson(ingredientListResponseModel));

        System.out.println(new Gson().toJson("Тело запроса"));
        System.out.println(new Gson().toJson(ingredientIdListRequestModel));

        System.out.println(ingredientListResponseModel.getRandomBunId());
        System.out.println(ingredientListResponseModel.getRandomMainId());
        System.out.println(ingredientListResponseModel.getRandomSauceId());


    }
}
