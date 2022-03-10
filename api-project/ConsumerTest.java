package PactLiveProject;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.PactDslJsonBody;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import bsh.ParseException;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@ExtendWith(PactConsumerTestExt.class)
public class ConsumerTest {
	
	//create map for headers

	Map<String, String> headers = new HashMap<String, String>();
	
	//API route
	
	String createUser = "/api/users";

	@Pact(provider = "UserProvider", consumer = "UserConsumer")
	public RequestResponsePact createPact(PactDslWithProvider builder) throws ParseException { 
	   
		//add header values
			
		headers.put("Content-Type", "application/json");
		headers.put("Accept", "application/json");	
		
		//create request body
		
		DslPart bodySentCreateUser = new PactDslJsonBody()
			    .numberType("id")
			    .stringType("firstName")
			    .stringType("lastName")
			    .stringType("email");
		
		//create response body 
		
		DslPart bodyReceivedCreateUser = new PactDslJsonBody()
			    .numberType("id")
			    .stringType("firstName")
			    .stringType("lastName")
			    .stringType("email");

		return builder.given("A request to create a user")
			    .uponReceiving("A request to create a user")
			        .path(createUser)
			        .method("POST")
			        .headers(headers)
			        .body(bodySentCreateUser)
			    .willRespondWith()
			        .status(201)
			        .body(bodyReceivedCreateUser)
			    .toPact();
		
	}
	
	
   @Test
   @PactTestFor(providerName = "UserProvider", port = "8080")
   public void consumerTest() {
    

	// Mock url
	   
	RestAssured.baseURI = "http://localhost:8080";
	// Create request specification
	RequestSpecification rq = RestAssured.given().headers(headers).when();


	// Create request body
	Map<String, Object> map = new HashMap<String, Object>();
	map.put("id", 1);
	map.put("firstName", "Justin");
	map.put("lastName", "Case");
	map.put("email", "justincase@mail.com");
	


	// Send POST request
	Response response = rq.body(map).post(createUser);
	// Assertion
	assert (response.getStatusCode() == 201);

	   
   }




	

}
