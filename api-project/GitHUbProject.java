package liveProject;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class GitHUbProject {

    // Declare request specification
    RequestSpecification requestSpec;
    String ssh = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCQXpI2xbkB3l4cqlz9I1r4gsL7Bx/SttbLCVhDvRtkDVRZVKj/n13hkODyMpbi4f6A0nTkH3+yZvgwB5CJx97T0HW+NOIkmLtv6U7IiEbF4wE6E0ZmbnYxkxV1uQigxv6MmU2Ih0vlWpny0BFnrKPmE45e5auA1duQM/zeuRNomDUk6XctqmXMhha1Q5muIw7Aa3Emu6L1Me9wGX/9FjV9gsGT0CnDRcMUKC8YAXAROR7sy+Pdirwn2b2FRXNdRVzrsd0opGe/kNyWMU/R6/CDEy58A/t727O/5FaYMcatQVajUiA85WF4b5mdohtyyS02xHuHneFzCqqmExbnbJVR";
    String token = "token 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";
    int id;

    @BeforeClass
    public void setUp() {
        // Create request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://api.github.com")
                .addHeader("Authorization", token)
                .build();

     }

    @Test(priority = 1)
    public void postRequestTest() {
        //Request Body
        String reqBody = "{\"title\": \"TestAPIKey\",\"key\": \"" + ssh + "\"}";

        //Generate Response
        Response response = given().spec(requestSpec)
                .body(reqBody)
                .when().post("/user/keys");

        id = response.then().extract().body().path("id");

        System.out.println(id);
        System.out.println(response.getBody().asPrettyString());

        response.then().statusCode(201);
    }

    @Test(priority = 2)
    public void getRequestTest() {
        //Generate Response
        Response response = given().spec(requestSpec)
                .when().get("/user/keys/" + id);

        //System.out.println(response.getBody().asPrettyString());

        //Assertions
        response.then().statusCode(200);
    }

    @Test(priority = 3)
    public void deleteRequestTest() {
        //Generate Response
        Response response = given().spec(requestSpec)
                .when().delete("/user/keys/" + id);

        //Assertions
        response.then().statusCode(204);

    }

}
