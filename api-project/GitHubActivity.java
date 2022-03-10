package ProjectActivity;


import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;



public class GitHubActivity {
	
	RequestSpecification requestSpec;
	ResponseSpecification responseSpec;
	String SSHKey;
	int id;
	
	@BeforeClass
	public void setUp()
	{
		
		 requestSpec = new RequestSpecBuilder()
			        .setContentType(ContentType.JSON)
			        .setBaseUri("https://api.github.com")
			        .addHeader("Authorization", "token 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")
			        
			        .build();
		 
		 responseSpec = new ResponseSpecBuilder()				    
				    .expectStatusCode(200)				   
				    .expectContentType("application/json")				    
				    .expectBody("status", equalTo("alive"))				    
				    .build();
	}
	


	
  @Test(priority=1)
  public void PostRequest() throws IOException {
	  
	  String reqBody = "{\"title\":\"TestAPIKey\",\"key\":\"ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQC3bybYH/GS/IbH87tPDbXh4mr6P9a3qm0gdUD8SUdZX76ws6EFFoPThqAA6wL5wdbN/lhq9UzckZ03avu7paWRLML+j2FhQjfcsKMfyhdzlxKq/Y9FnGDH7Ecy2J4zNf8QIeO1Wy9sKKMI3Y/jPdIOmBEYceYmiY9BC07qQ1k6dyxEQ1SvgPX7ofydlf50HM0gTlV2lpgnJo/gjkBHfW08SiTOS6sxu1y5RCw8jfixPzMKbi8UNFfnUVJ1Eli/VFzr9GC1Pgt9bmgdXVWK9a7NgmImNCLfpoyL6IWlM3nPQ+74axTCH7bOXXanQ3W+oocJppwmfJquGhV8lTgc3dMB\"}";
	  		
	  Response response = given().spec(requestSpec) 
		                 .body(reqBody)
		                 .when().post("/user/keys");
	  
	  System.out.println(response.asPrettyString());
	  response.then().statusCode(201);
	  
	  id= response.then().extract().path("id");	  
	  
  }
  
  @Test(priority=2)
  public void GetRequest() {
	  
	  Response response = 
			  given().spec(requestSpec) 
			  .pathParam("key_id",id)
              .when().get("/user/keys/{key_id}");


		    
     String body = response.getBody().asPrettyString();
     System.out.println(body);

     response.then().statusCode(200);
	  
	 
  }
  
  @Test(priority=3)
  public void DeleteRequest() {
	  
	  Response response = 
			  given().spec(requestSpec) 
			  .pathParam("key_id",id)
              .when().delete("/user/keys/{key_id}");


		    
     String body = response.getBody().asPrettyString();
     System.out.println(body);

     response.then().statusCode(204);
	  
  }
  
  


  
  
  
  
}
