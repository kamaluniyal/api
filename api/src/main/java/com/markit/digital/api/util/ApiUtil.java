package com.markit.digital.api.util;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import static com.jayway.restassured.RestAssured.*;

import java.util.Map;

import org.json.simple.JSONObject;

public class ApiUtil {

	public static String path; //Rest request path
	public static JSONObject requestParams=new JSONObject();
	public static RequestSpecification request = RestAssured.given();
	 
    /*
    ***Sets Base URI***
    Before starting the test, we should set the RestAssured.baseURI
    */
    public static void setBaseURI (String baseURI){
        RestAssured.baseURI = baseURI;
    }
 
    /*
    ***Sets base path***
    Before starting the test, we should set the RestAssured.basePath
    */
    public static void setBasePath(String basePathTerm){
        RestAssured.basePath = basePathTerm;
    }
 
    /*
    ***Reset Base URI (after test)***
    After the test, we should reset the RestAssured.baseURI
    */
    public static void resetBaseURI (){
        RestAssured.baseURI = null;
    }
 
    /*
    ***Reset base path (after test)***
    After the test, we should reset the RestAssured.basePath
    */
    public static void resetBasePath(){
        RestAssured.basePath = null;
    }
 
    /*
    ***Sets ContentType***
    We should set content type as JSON or XML before starting the test
    */
    public static void setContentType (ContentType Type){
        given().contentType(Type);
    }
 
    /*
    ***set header values***
    */
    public static void  setHeaders(Map<String,String> headerMap) {
       
    }
 
    /*
    ***Returns response***
    We send "path" as a parameter to the Rest Assured'a "get" method
    and "get" method returns response of API
    */
    public static Response getResponse() {
        //System.out.print("path: " + path +"\n");
        return get(path);
    }
    
    
    public static Response getResponse(Map<String,String> bodyMap, String path) {
        
    	/*for (Map.Entry<String,String> entry : bodyMap.entrySet()) {
    		requestParams.put(entry.getKey(),entry.getValue());
    	}*/
    	
    	
    	RestAssured.baseURI="https://projects.ihsmarkit.com/rest/auth/1";
    	RequestSpecification request = RestAssured.given();
    	JSONObject requestParams = new JSONObject();
    	 requestParams.put("username","kamal.uniyal");
         requestParams.put("password","January@2019");
         request.body(requestParams.toJSONString());
         Response response = request.post("/session");
         System.out.println("response::::"+response);
         
    	
    	//System.out.println("path:::"+baseURI+basePath+path);
    	//requestParams = (JSONObject) bodyMap ; 
    	
    	//System.out.println("params:::"+requestParams.toJSONString());
    	//request.body(requestParams.toJSONString());
    	//System.out.println("response::::"+request.post(baseURI+basePath+path));
    	return request.post(baseURI+basePath+path);
    }
 
    /*
    ***Returns JsonPath object***
    * First convert the API's response to String type with "asString()" method.
    * Then, send this String formatted json response to the JsonPath class and return the JsonPath
    */
    public static JsonPath getJsonPath (Response res) {
        String json = res.asString();
        //System.out.print("returned json: " + json +"\n");
        return new JsonPath(json);
    }
	
}
