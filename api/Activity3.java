package activities;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;



public class Activity3 {
	
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	
	@BeforeClass
	public void setUp()
	{
		
		 requestSpec = new RequestSpecBuilder()
			        .setContentType(ContentType.JSON)
			        .setBaseUri("https://petstore.swagger.io/v2/pet")
			        .build();
		 
		 responseSpec = new ResponseSpecBuilder()				    
				    .expectStatusCode(200)				   
				    .expectContentType("application/json")				    
				    .expectBody("status", equalTo("alive"))				    
				    .build();
	}
	


	
  @Test(priority=1)
  public void PostRequest() throws IOException {
	  
	  String reqBody = "{\"id\": 77235, \"name\": \"Riley\", \"status\": \"alive\"}";
	  
	  Response response = given().spec(requestSpec) 
		                 .body(reqBody)
		                 .when().post();
		        

		    // Print response
	  String body = response.getBody().asPrettyString();
	  System.out.println(body);

	  response.then().spec(responseSpec);
	  
      reqBody = "{\"id\": 77236, \"name\": \"Hansel\", \"status\": \"alive\"}";
	  
	  response = given().spec(requestSpec) 
		                 .body(reqBody)
		                 .when().post();
		        

		    // Print response
	  body = response.getBody().asPrettyString();
	  System.out.println(body);

	  response.then().spec(responseSpec);
	  
  }
  
  @DataProvider
	public Object[][] petIdProvider() {
	  
	  Object[][] testData = new Object[][] { 
		    { 77235, "Riley", "alive" }, 
		    { 77236, "Hansel", "alive" } 
		};
	    return testData;
	}
  
  
  
  @Test(dataProvider = "petIdProvider",priority=2)
  public void GetRequest(int id,String name,String status) {
	  
	  Response response = 
		        given().spec(requestSpec) 
		        .pathParam("petId", id) 
		        .get("/{petId}"); 

		    
     String body = response.getBody().asPrettyString();
     System.out.println(body);

		    // Assertion
     response.then().spec(responseSpec);
	  
	 
  }
  
  @Test(dataProvider = "petIdProvider",priority=3)
  public void DeleteRequest(int id,String name,String status) {
	  
	  Response response = 
		        given().spec(requestSpec) 
		        .pathParam("petId", id) 
		        .delete("/{petId}");
	  
	  String body = response.getBody().asPrettyString();
	  System.out.println(body);

	  response.then().body("code", equalTo(200));
  }
}
