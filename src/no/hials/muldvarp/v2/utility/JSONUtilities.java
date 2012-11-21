/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.v2.MuldvarpService;
import no.hials.muldvarp.v2.domain.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class JSONUtilities {

    public static String getData(String url) throws IOException {
        System.out.println(url);
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
            public String handleResponse(final HttpResponse response)
                throws HttpResponseException, IOException {
                StatusLine statusLine = response.getStatusLine();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(statusLine.getStatusCode(),
                            statusLine.getReasonPhrase());
                }

                HttpEntity entity = response.getEntity();
                return entity == null ? null : EntityUtils.toString(entity, "UTF-8");
            }
        }; 
        return httpclient.execute(httpget, responseHandler);
    }

    public static Domain JSONtoObject(String json, MuldvarpService.DataTypes type) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json);
        } catch (JSONException ex) {
            Logger.getLogger(MuldvarpService.class.getName()).log(Level.SEVERE, null, ex);
        }

        Domain d = null;
        try {
            switch(type){
                case COURSES:
                    d = new Course(jsonObject);
                    break;
                case VIDEOS:
                    d = new Video(jsonObject);
                    break;
                case DOCUMENTS:
                    d = new Document(jsonObject);
                    break;
                case PROGRAMS:
                    d = new Programme(jsonObject);
                    break;
                case ARTICLE:
                case NEWS:
                    d = new Article(jsonObject);
                    break;
                default:
                    d = new Domain(jsonObject);
                    break;
            }
        } catch (JSONException ex) {
            Logger.getLogger(MuldvarpService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return d;
    }

    /**
     * Converts a JSON array to list
     *
     * @param jsonString
     * @param type
     * @return
     * @throws JSONException
     */
    public static List<Domain> JSONtoList(String jsonString, MuldvarpService.DataTypes type) throws JSONException {
        ArrayList<Domain> itemList = new ArrayList<Domain>();
        JSONArray jArray = new JSONArray(jsonString);

        for(int i = 0; i < jArray.length(); i++) {
            Domain d;
            switch(type) {
                case COURSES:
                    d = new Course(jArray.getJSONObject(i));
                    break;
                case PROGRAMS:
                    d = new Programme(jArray.getJSONObject(i));
                    break;
                case DOCUMENTS:
                    d = new Document(jArray.getJSONObject(i));
                    System.out.println(d);
                    break;
                case VIDEOS:
                    d = new Video(jArray.getJSONObject(i));
                    break;
                case NEWS:
                    d = new Article(jArray.getJSONObject(i));
                    break;
                 case QUIZ:
                    d = new Quiz(jArray.getJSONObject(i));
                    break;   
                default:
                    d = new Domain(jArray.getJSONObject(i));
                    break;
            }
            itemList.add(d);
        }
        return itemList;
    }
    
    /**
     * This function converts a JSONArray to a List of Courses.
     * 
     * @param jsonArray
     * @return
     * @throws JSONException 
     */
    public static List<Course> JSONArrayToCourses(JSONArray jsonArray) throws JSONException{
        List<Course> retVal = new ArrayList<Course>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new Course(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }
}
