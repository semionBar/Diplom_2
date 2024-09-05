package order.get;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import model.user.LoggedInUserModel;
import model.user.User;
import order.make.MakeOrdersStep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GetUserOrdersTest {


    LoggedInUserModel loggedInUserModel;

    MakeOrdersStep makeOrdersStep;

    GetUserOrdersStep getUserOrdersStep;

    public final int amountOfOrders = 3;

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://stellarburgers.nomoreparties.site";

        getUserOrdersStep = new GetUserOrdersStep();

        makeOrdersStep = new MakeOrdersStep();
    }

    @Test
    public void getOrderOfNewUserReturnsZeroTest() {

        loggedInUserModel = getUserOrdersStep.getLoggedInUserModel(new User(0));

        getUserOrdersStep.sendGetUserOrdersWithToken(loggedInUserModel);

        getUserOrdersStep.checkOrderListContainsExactAmount(0);
    }

    @Test
    public void getOrdersOfUserReturnsActualNumberOfOrders() {

        loggedInUserModel = getUserOrdersStep.getLoggedInUserModel(new User(0));

        getUserOrdersStep.makeOrders(amountOfOrders, loggedInUserModel);

        getUserOrdersStep.sendGetUserOrdersWithToken(loggedInUserModel);

        getUserOrdersStep.checkOrderListContainsExactAmount(amountOfOrders);
    }

    @After
    public void clearUserData() {
        getUserOrdersStep.clearUserData(loggedInUserModel);
    }
}
