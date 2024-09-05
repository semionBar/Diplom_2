package order.get;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import model.user.LoggedInUserModel;
import model.user.User;
import order.make.MakeOrdersStep;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;

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
        getUserOrdersStep.checkBodyFieldEqualsObject("success", true);
        getUserOrdersStep.checkResponseCode(SC_OK);
    }

    @Test
    public void getOrdersOfUserReturnsActualNumberOfOrders() {

        loggedInUserModel = getUserOrdersStep.getLoggedInUserModel(new User(0));

        getUserOrdersStep.makeOrders(amountOfOrders, loggedInUserModel);

        getUserOrdersStep.sendGetUserOrdersWithToken(loggedInUserModel);

        getUserOrdersStep.checkOrderListContainsExactAmount(amountOfOrders);
        getUserOrdersStep.checkBodyFieldEqualsObject("success", true);
        getUserOrdersStep.checkResponseCode(SC_OK);

    }

    @Test
    public void getOrdersWithoutToken() {
        getUserOrdersStep.sendGetUserOrdersWithoutToken();

        getUserOrdersStep.checkBodyFieldNotNull("message");
        getUserOrdersStep.checkBodyFieldEqualsObject("success", false);
        getUserOrdersStep.checkResponseCode(SC_UNAUTHORIZED);

    }


    @After
    public void clearUserData() {
        getUserOrdersStep.clearUserData(loggedInUserModel);
    }
}
