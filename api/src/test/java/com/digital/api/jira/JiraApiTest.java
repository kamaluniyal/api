package com.digital.api.jira;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.authentication.PreemptiveBasicAuthScheme;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.markit.digital.api.util.*;
public class JiraApiTest extends BaseTest {

	private Response res = null; //Response object

	private String sessionId="";

	@BeforeClass
	public void setup (){
		//init("https://projects.ihsmarkit.com/");	// get this from prop file
		init("https://jsonplaceholder.typicode.com");
	}


	@Test(enabled=false)
	public void T01_GetSessionKey() throws ParseException {

		//Create header
		Map<String,String> headerValues = new HashMap<String,String>();
		headerValues.put("Content-Type", "application/json");


		//Create params
		Map<String,String> paramValues = new HashMap<String,String>();
		paramValues.put("username", "kamal.uniyal");
		paramValues.put("password", "January@2019");

		//hit api
		Response res = getResponseFromPost(headerValues,paramValues ,"/rest/auth/1/session","NA");
		sessionId = res.sessionId();
		System.out.println("SESSION ID"+sessionId);


	}

	// tickets created - NIX-1847,1849
	@Test(enabled=false)
	public void T02_CreateJiraIssue() throws ParseException {

		//Create headers
		Map<String,String> headerValues = new HashMap<String,String>();
		headerValues.put("Content-Type", "application/json");
		headerValues.put("Cookie", "JSESSIONID="+sessionId);

		//create body
		String body = "{"+
				"\"fields\": {"+
				"\"project\":{"+
				"\"key\": \"NIX\""+
				"},"+
				"\"summary\": \"Test Jira API\","+
				"\"description\": \"This Jira issue created using api\","+
				"\"issuetype\": {"+
				"\"name\": \"Bug\""+
				"},\"customfield_11200\":{\"value\":\"No\"}"+
				"}}";

		//hit api
		Response res = getResponseFromPost(headerValues,null,"rest/api/2/issue",body);
		String ticketId = getValueFromResponseString(res ,"key");

		System.out.println("Jira ticket created ::::"+ticketId);

	}
	
	
	
	@Test(enabled=false)
	public void T03_getResponse() throws ParseException {

		//Create headers
		Map<String,String> headerValues = new HashMap<String,String>();
		headerValues.put("Content-Type", "application/json");
			

		//hit api
		Response res = getResponse(headerValues,null,"/users");
		
		// user using string 
		String users = res.jsonPath().getString("username");		
		System.out.println("users:::"+users);
		
		// users using list
		List<String> usersList = res.jsonPath().getList("username");		
		for(String user : usersList) {
			System.out.println("usersList:::"+user);
		}
		
				
		// Get values from address map
		List<HashMap<String, String>> addressParams = getListOfMapFromResponse(res.asString(),"address");		
		System.out.println("city ::: "+addressParams.get(0).get("city"));
		
		// Get values from geo map
		List<HashMap<String, String>> geoParams = getListOfMapFromResponse(res.asString(),"address.geo");		
		System.out.println("latitide ::: "+geoParams.get(0).get("lat"));
		
		
		
		
	}
	
	@Test(enabled=true)
	public void T03_getResponse2() throws ParseException {

		//Create headers
		Map<String,String> headerValues = new HashMap<String,String>();
		headerValues.put("Content-Type", "application/json");
			

		//hit api
		getResponse2(headerValues,null,"/users");
		
		
		
		
		
	}


	

}
