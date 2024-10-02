package com.napshop;

import com.napshop.stubs.InventoryClientStub;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.testcontainers.containers.MySQLContainer;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

	@ServiceConnection
	static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");
	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	static {
		mySQLContainer.start();
	}

	@Test
	void shouldSubmitOrder() {
        String submitOrderJson = """
            {
                 "skuCode" : "samsung",
                 "price" : "40000",
                 "quantity" : "1",
                 "orderDate" : "22-09-2024"
            }
            """;

		InventoryClientStub.stubInventoryCall("samsung", 1);

        // Extract the response as a JSON object
        var responseJson = RestAssured.given()
                .contentType("application/json")
                .body(submitOrderJson)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .jsonPath();

        // Validate only the fields you are concerned with
        assertThat(responseJson.getString("skuCode"), Matchers.is("samsung"));
        assertThat(responseJson.getInt("price"), Matchers.is(40000));
        assertThat(responseJson.getInt("quantity"), Matchers.is(1));
        assertThat(responseJson.getString("orderDate"), Matchers.is("2024-09-22"));
	}
}
