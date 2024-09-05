package order.make;

import model.ingredient.IngredientIdListRequestModel;
import model.ingredient.IngredientListResponseModel;
import io.restassured.RestAssured;
import model.user.LoggedInUserModel;
import model.user.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    @Test public void makeOrderWithTokenTest() {

        User user = new User();

        user.generateNewUser();

        loggedInUserModel = makeOrdersStep.getLoggedInUserModel(user);

        ingredientIdListRequestModel = makeOrdersStep.getIngredientIdListRequestModel();

        makeOrdersStep.sendMakeOrderRequestWithToken(ingredientIdListRequestModel, loggedInUserModel);
    }

    @After
    public void clearUserData() {
        makeOrdersStep.clearUserData(loggedInUserModel);
    }
}
