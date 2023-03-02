package API_Tests;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.hamcrest.Matchers.equalTo;
import static API_Tests.Enums.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class ApiTest {
    @Test
    public void getEverything() {
        System.out.println("***************POSITIVE*********************");
        System.out.println("***GET_BOOK_LIST***");
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .when()
                .get(BASEURL.getURL())
                .then().log().all()
                .extract().response();
        assertEquals(200, response.statusCode());
    }

    @Test
    void getById() {
        System.out.println("***************POSITIVE*********************");
        System.out.println("***GET_ONE_BOOK***");
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .when()
                .get(BASEURL.getURL() + BOOKID.getURL())
                .then().log().all()
                .extract().response();
        assertEquals(200, response.statusCode());
    }

    @Test
    void getByWrongId() {
        System.out.println("*****************NEGATIVE***********************");
        System.out.println("***GET_ONE_BOOK_WITH_WRONG_ID***");
        Response response = given().log().all()
                .header("Content-type", "application/json")
                .when()
                .get(BASEURL.getURL() + BOOKWRONGID.getURL())
                .then().log().all()
                .extract().response();
        assertEquals(404, response.statusCode());
    }

    @Test
    void postByName() {
        System.out.println("***************POSITIVE*********************");
        System.out.println("***ADD_NEW_BOOK_BY_NAME***");
        String book = "{\n" +
                " \"name\": \"TestName\",\n" +
                " \"year\": 1000\n" +
                "}";

        given().baseUri(BASEURL.baseURL)
                .contentType(ContentType.JSON)
                .body(book)
                .log().all()
                .when()
                .post()
                .then().assertThat()
                .statusCode(201)
                .body("book.author", Matchers.notNullValue())
                .body("book.id", Matchers.notNullValue())
                .body("book.isElectronicBook", equalTo(false))
                .body("book.name", equalTo("TestName"))
                .body("book.year", equalTo(1000))
                .log().all();

    }

    @Test
    void postEverything() {
        System.out.println("***************POSITIVE*********************");
        System.out.println("***ADD_NEW_BOOK_WITH_FULL_INFO***");
        File jData = new File("src/test/resources/book.json");
        given().baseUri(BASEURL.baseURL)
                .contentType(ContentType.JSON)
                .body(jData)
                .log().all()
                .when()
                .post()
                .then().assertThat()
                .statusCode(201)
                .body("book.author", equalTo("New author"))
                .body("book.id", Matchers.notNullValue())
                .body("book.isElectronicBook", equalTo(false))
                .body("book.name", equalTo("New book"))
                .body("book.year", equalTo(2023))
                .log().all();

    }

    @Test
    void postWithoutName() {
        System.out.println("*****************NEGATIVE***********************");
        System.out.println("***ADD_NEW_BOOK_WITHOUT_NAME***");
        String book = "{\n" +
                " \"author\": \"Джон Стейнбек\",\n" +
                " \"isElectronicBook\": true,\n" +
                " \"year\": 1939\n" +
                "}";

        given().baseUri(BASEURL.baseURL)
                .contentType(ContentType.JSON)
                .body(book)
                .log().all()
                .when()
                .post()
                .then().assertThat()
                .statusCode(400)
                .log().all();
    }

    @Test
    void postNull() {
        System.out.println("*****************NEGATIVE***********************");
        System.out.println("***ADD_NEW_BOOK_WITH_NULL_NAME***");
        String book = "{\n" +
                " \"author\": \"Джоан Роулинг\",\n" +
                " \"isElectronicBook\": true,\n" +
                " \"name\": \"\",\n" +
                " \"year\": 1999\n" +
                "}";

        given().baseUri(BASEURL.baseURL)
                .contentType(ContentType.JSON)
                .body(book)
                .log().all()
                .when()
                .post()
                .then().assertThat()
                .statusCode(400)
                .log().all();

    }

    @Test
    void putNewInfo() {
        System.out.println("***************POSITIVE*********************");
        System.out.println("***ADD_NEW_INFO_BY_ID***");
        File jData = new File("src/test/resources/book.json");

        given().baseUri(BASEURL.baseURL)
                .contentType(ContentType.JSON)
                .body(jData)
                .log().all()
                .when()
                .put(BOOKID.getURL())
                .then().assertThat()
                .statusCode(200)
                .body("book.author", equalTo("New author"))
                .body("book.id", Matchers.notNullValue())
                .body("book.isElectronicBook", equalTo(false))
                .body("book.name", equalTo("New book"))
                .body("book.year", equalTo(2023))
                .log().all();
    }
    @Test
    void putNewInfoByWrongID() {
        System.out.println("*****************NEGATIVE***********************");
        System.out.println("***ADD_NEW_INFO_BY_WRONG_ID***");
        String book = "{\n" +
                " \"author\": \"Джон Стейнбек\",\n" +
                " \"isElectronicBook\": true,\n" +
                " \"name\": \"Гроздья Гнева\",\n" +
                " \"year\": 1939\n" +
                "}";

        given().baseUri(BASEURL.baseURL)
                .contentType(ContentType.JSON)
                .body(book)
                .log().all()
                .when()
                .put(BOOKWRONGID.getURL())
                .then().assertThat()
                .statusCode(404);

    }
    @Test
    void putNewBadInfo() {
        System.out.println("*****************NEGATIVE***********************");
        System.out.println("***UPDATE_BOOK_INFO_WITHOUT_name_year_Boolean***");
        String book = "{\n" +
                " \"author\": \"Джон Стейнбек\",\n" +
                "}";

        given().baseUri(BASEURL.baseURL)
                .contentType(ContentType.JSON)
                .body(book)
                .log().all()
                .when()
                .put(BOOKID.getURL())
                .then().assertThat()
                .statusCode(400);

    }
        @Test
        void deleteById () {
            System.out.println("***************POSITIVE*********************");
            System.out.println("***DELETE_BOOK_BY_ID***");
            given().baseUri(BASEURL.baseURL)
                    .log().all()
                    .when()
                    .delete(IDFORDELETE.baseURL)
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .log().all();
        }

        @Test
        void deleteWith_Wrong_id () {
            System.out.println("*****************NEGATIVE***********************");
            System.out.println("***DELETE_BOOK_BY_WRONG_ID***");
            given().baseUri(BASEURL.baseURL)
                    .log().all()
                    .when()
                    .delete(BOOKWRONGID.baseURL)
                    .then()
                    .assertThat()
                    .statusCode(404)
                    .log().all();
        }


    }
