package com.digital.api.jira;

//import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;


public class BaseTest {

	public RequestSpecification request ;
	public JSONObject requestParams=new JSONObject();

	// Initialize api base URI 
	public void init(String baseURI) {
		RestAssured.baseURI = baseURI ;
		request = RestAssured.given();
	}

	// method to get value of a key from response body
	public String getValueFromResponseString(Response res , String key) throws ParseException {
		JSONParser parser = new JSONParser();
		JSONObject jobj = (JSONObject)parser.parse(res.asString());
		return (jobj.get(key).toString());
	}

	// Post request
	public Response getResponseFromPost(Map<String,String> headerMap,Map<String,String> paramMap,String requestUrl,String reqBody) {

		if(headerMap!=null) {
			for(Map.Entry<String, String> entry : headerMap.entrySet()) {
				request.header(entry.getKey(),entry.getValue());
			}
		}

		if(paramMap!=null) {
			for(Map.Entry<String, String> entry : paramMap.entrySet()) {
				requestParams.put(entry.getKey(),entry.getValue());
			}
		}

		if(paramMap!=null) {
			request.body(requestParams.toJSONString());
		}

		if(!reqBody.equals("NA")) {request.body(reqBody);}

		Response response = request.post(requestUrl);
		return response ;
	}
	
	// Post request
	public Response getResponse(Map<String,String> headerMap,Map<String,String> paramMap,String requestUrl) {

			if(headerMap!=null) {
				for(Map.Entry<String, String> entry : headerMap.entrySet()) {
					request.header(entry.getKey(),entry.getValue());
				}
			}

			if(paramMap!=null) {
				for(Map.Entry<String, String> entry : paramMap.entrySet()) {
					requestParams.put(entry.getKey(),entry.getValue());
				}
			}

			if(paramMap!=null) {
				request.body(requestParams.toJSONString());
			}
			

			Response response = request.get(requestUrl);
			return response ;
			
			
		}

		// reduntant use getListOfMapFromResponse
		public List<HashMap<String, String>> getAddressList(String res) throws ParseException {
			
			JsonPath jsonPath = new JsonPath(res);
			List<HashMap<String, String>> addressList = jsonPath.get("address");
			
			return addressList ;			
					
		}
		
		// reduntant use getListOfMapFromResponse
		public List<HashMap<String, String>> getGeoList(String res) throws ParseException {
			
			JsonPath jsonPath = new JsonPath(res);						
			List<HashMap<String, String>> geoList = jsonPath.get("address.geo");
			
			return geoList;
			
		}
		
		public List<HashMap<String, String>> getListOfMapFromResponse(String res, String parameterPath) throws ParseException {
			
			JsonPath jsonPath = new JsonPath(res);						
			List<HashMap<String, String>> paramList = jsonPath.get(parameterPath);
			
			return paramList;
			
		}
		
		public void getResponse2(Map<String,String> headerMap,Map<String,String> paramMap,String requestUrl) {

			if(headerMap!=null) {
				for(Map.Entry<String, String> entry : headerMap.entrySet()) {
					request.header(entry.getKey(),entry.getValue());
				}
			}

			if(paramMap!=null) {
				for(Map.Entry<String, String> entry : paramMap.entrySet()) {
					requestParams.put(entry.getKey(),entry.getValue());
				}
			}

			if(paramMap!=null) {
				request.body(requestParams.toJSONString());
			}
			
			Response response = request.get(requestUrl) ;

			ResponseClass[] resArr = response.jsonPath().getObject("",ResponseClass[].class);
			
			for(ResponseClass item : resArr) {
				System.out.println("website:::"+item.getWebsite());
			}
			
			
		}
		
		public String getHeaderValue(Response res , String header) {
			String headerValue = "";
			
			return headerValue;
		}
		
		public List<String> getHeaderValues(Response res) {
			List<String> headerValues = new ArrayList<String>();
			
			return headerValues;
		}
		
		public String getStatusLine(Response res) {
			String statusLine = "";
			
			return statusLine;
		}
		
		public void verifySchema() {
			
			RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
			RequestSpecification request = RestAssured.given();
			//request.when().get("/users").then().body(matchesJsonSchemaInClasspath("Schema.json"));
		}
			
		

}
