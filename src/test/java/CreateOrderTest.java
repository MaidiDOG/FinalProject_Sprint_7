
import type.Order;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static config.Config.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String[] color;

    public CreateOrderTest(String[] color) {
        this.color = color;
    }
    @Parameterized.Parameters
    public static Object[][] getSumData() {
        return new Object[][]{
                {new String[] {"Black"}},
                {new String[] {"Grey"}},
                {new String[] {""}},
                {new String[] {"Black", "Grey"}}
        };
    }
    @Test
    @DisplayName("Успешное создание заказа")
    public void createOrder(){
        Order order = new Order("Рандомыч", "Неизвестов", "ул. Пушкина, д. Колотушкна", "4", "+7 800 555 35 35", 5, "2023-04-08", "Важный комментарий", color);
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post("/api/v1/orders");
        response.then().statusCode(201)
                .and()
                .assertThat().body("track", is(notNullValue()));
    }
    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }
}

