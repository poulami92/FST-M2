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
import org.testng.annotations.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;



public class Activity2 {
	
	
	@BeforeClass
	public void setUpBaseUri()
	{
		
		baseURI = "https://petstore.swagger.io/v2/user";
	}
	


	
  @Test(priority=1)
  public void PostRequest() throws IOException {
	  
	    File file = new File("src/test/java/activities/input.json");
	    
	    FileInputStream inputJSON = new FileInputStream(file);
	    
	
	    byte[] bytes = new byte[(int) file.length()];
	    inputJSON.read(bytes);
	    
	    
	    String reqBody = new String(bytes, "UTF-8");

	    Response response = given().contentType(ContentType.JSON) 
	                        .body(reqBody)
	                        .when().post(baseURI);
	    
	    inputJSON.close();
	    System.out.println(response.asPrettyString());
	    
	    response.then().body("code", equalTo(200));
        response.then().body("message", equalTo("5901"));
  }
  
  @Test(priority=2)
  public void GetRequest() {
	  
	  Response response = 
	            given().contentType(ContentType.JSON) 
	            .when().pathParam("username", "poulami124") 
	            .get(baseURI + "/{username}"); 
	  
	  String resBody = response.asPrettyString();
	 
	        // Assertion
	  response.then().body("id", equalTo(5901));
      response.then().body("username", equalTo("poulami124"));
      response.then().body("firstName", equalTo("Justin"));
      response.then().body("lastName", equalTo("Case"));
      response.then().body("email", equalTo("justincase@mail.com"));
      response.then().body("password", equalTo("password123"));
      response.then().body("phone", equalTo("9812763450"));
  
	        
	        File resJSONFile = new File("src/test/java/activities/resLog.json");
	        
	        try {
		     
		        resJSONFile.createNewFile();
		        FileWriter writer = new FileWriter(resJSONFile.getPath());
		        writer.write(resBody);
		        writer.close();
		    } catch (IOException excp) {
		        excp.printStackTrace();
		    }
  }
  
  @Test(priority=3)
  public void DeleteRequest() {
	  
	  Response response = given().header("Content-Type","application/json")    
	                     .pathParam("username", "poulami124") 
	                     .when().delete(baseURI + "/{username}");
	 
	        // Assertion
	        response.then().body("code", equalTo(200));
	        response.then().body("message", equalTo("poulami124"));
	    
  }
}
