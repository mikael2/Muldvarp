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
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.domain.Video;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class JSONUtilities {

    public static String getData(String url) throws IOException {
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        return httpclient.execute(httpget, responseHandler);
    }

    public static Object JSONtoObject(String url, MuldvarpService.DataTypes type) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(getData(url));
        } catch (JSONException ex) {
            Logger.getLogger(MuldvarpService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
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
            Domain d = null;
            switch(type) {
                case COURSES:
                    d = new Course();
                    break;
                case PROGRAMS:
                    d = new Programme();
                    break;
                case DOCUMENTS:
                    d = new Document();
                    break;
                case VIDEOS:
                    d = new Video();
                    break;
            }
            JSONObject currentObject = jArray.getJSONObject(i);
            d.setId(currentObject.getInt("id"));
            d.setName(currentObject.getString("name"));
            d.setDetail(currentObject.getString("detail"));
            itemList.add(d);
        }

        return itemList;
    }
}
