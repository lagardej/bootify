package me.grumpy.bootify.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import me.grumpy.bootify.config.BaseIT;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class CustomerResourceTest extends BaseIT {

    @Test
    @Sql("/data/customerData.sql")
    void getAllCustomers_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/customers")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("content.get(0).id", Matchers.equalTo(1000));
    }

    @Test
    @Sql("/data/customerData.sql")
    void getAllCustomers_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/customers?filter=1001")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("content.get(0).id", Matchers.equalTo(1001));
    }

    @Test
    @Sql("/data/customerData.sql")
    void getCustomer_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/customers/1000")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("firstName", Matchers.equalTo("Nulla facilisis."));
    }

    @Test
    void getCustomer_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/customers/1666")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createCustomer_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/customerDTORequest.json"))
                .when()
                    .post("/api/customers")
                .then()
                    .statusCode(HttpStatus.CREATED.value())
                    .header("Location", Matchers.is(Matchers.not(Matchers.emptyString())));
        assertEquals(1, customerRepository.count());
    }

    @Test
    @Sql("/data/customerData.sql")
    void updateCustomer_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/customerDTORequest.json"))
                .when()
                    .put("/api/customers/1000")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value())
                    .header("Location", Matchers.is(Matchers.not(Matchers.emptyString())));
        assertEquals("No sea takimata.", customerRepository.findById(((long)1000)).orElseThrow().getFirstName());
        assertEquals(2, customerRepository.count());
    }

    @Test
    @Sql("/data/customerData.sql")
    void deleteCustomer_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/customers/1000")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, customerRepository.count());
    }

}
