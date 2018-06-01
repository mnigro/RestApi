import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {

    @Test
    public void validar_status_code_200_Get(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items/123");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode),"200");
    }

    @Test
    public void validar_status_code_200_GetAll(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode),"200");
    }

    @Test
    public void validar_status_code_200_Delete(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.delete("/items/123");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode),"200");
    }

    @Test
    public void validar_status_line_Get(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items/123");
        String statusLine = response.getStatusLine();
        Assert.assertEquals(String.valueOf(statusLine),"HTTP/1.1 200 OK");
    }

    @Test
    public void validar_status_line_GetAll(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items");
        String statusLine = response.getStatusLine();
        Assert.assertEquals(String.valueOf(statusLine),"HTTP/1.1 200 OK");
    }

    @Test
    public void validar_getHeaders(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items/123");
        String contenType = response.header("Content-Type");
        Assert.assertEquals(contenType,"application/json");
    }

    @Test
    public void validar_getAllHeaders(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items");
        String contenType = response.header("Content-Type");
        Assert.assertEquals(contenType,"application/json");
    }

    @Test
    public void validar_postHeaders(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.post("/items/123");
        String contenType = response.header("Content-Type");
        Assert.assertEquals(contenType,"application/json");
    }

    @Test
    public void validar_putHeaders(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.put("/items/123");
        String contenType = response.header("Content-Type");
        Assert.assertEquals(contenType,"application/json");
    }

    @Test
    public void validar_deleteHeaders(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.delete("/items/123");
        String contenType = response.header("Content-Type");
        Assert.assertEquals(contenType,"application/json");
    }

}