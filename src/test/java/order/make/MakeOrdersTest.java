package order.make;

import com.google.gson.Gson;
import io.restassured.RestAssured;
import model.ingredient.IngredientIdListRequestModel;
import model.ingredient.IngredientListResponseModel;
import model.user.LoggedInUserModel;
import model.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

public class MakeOrdersTest {

    MakeOrdersStep makeOrdersStep;

    IngredientListResponseModel ingredientListResponseModel;

    IngredientIdListRequestModel ingredientIdListRequestModel;

    LoggedInUserModel loggedInUserModel;
    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        makeOrdersStep = new MakeOrdersStep();

        makeOrdersStep.sendGetIngredientsListRequest();

        ingredientListResponseModel = new IngredientListResponseModel();

        ingredientListResponseModel = makeOrdersStep.getIngredientList();

    }

    @Test
    public void makeOrderWithoutTokenTest() {

        ingredientIdListRequestModel = makeOrdersStep.getIngredientIdListRequestModel();

        makeOrdersStep.sendMakeOrderRequestWithOutToken(ingredientIdListRequestModel);
    }

    @Test
    public void makeOrderWithTokenTest() {
        loggedInUserModel = makeOrdersStep.getLoggedInUserModel(new User(0));

        ingredientIdListRequestModel = makeOrdersStep.getIngredientIdListRequestModel();

        makeOrdersStep.sendMakeOrderRequestWithToken(ingredientIdListRequestModel, loggedInUserModel);
    }

    @Test
    public void makeOrderWithEmptyIngredientListReturns400() {

        ingredientIdListRequestModel = new IngredientIdListRequestModel();

        List<String> hashList = new ArrayList<>();

        ingredientIdListRequestModel.setIngredients(hashList);

        makeOrdersStep.sendMakeOrderRequestWithOutToken(ingredientIdListRequestModel);

        makeOrdersStep.checkResponseCode(SC_BAD_REQUEST);
        makeOrdersStep.checkBodyFieldNotNull("message");
        makeOrdersStep.checkBodyFieldEqualsObject("success", false);
    }

    @Test
    public void makeOrderWithWrongIngredientReturns500() {
        ingredientIdListRequestModel = new IngredientIdListRequestModel();

        ingredientIdListRequestModel.setIngredients(List.of("1234567890"));

        makeOrdersStep.sendMakeOrderRequestWithOutToken(ingredientIdListRequestModel);

        makeOrdersStep.checkResponseCode(SC_INTERNAL_SERVER_ERROR);
    }

    @After
    public void clearUserData() {
        makeOrdersStep.clearUserData(loggedInUserModel);
    }
}
