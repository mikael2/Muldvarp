/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.utility;

import android.os.Handler;
import android.os.Message;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 * This class provides asynchronous HTTP-request functionality using android.os.Handler and threads.
 * 
 * A handler must be implemented for each of the cases: CON_START, CON_ERROR and CON_SUCCEED. 
 * Instantiate this class with the implemented handler, and call the methods in the class.
 * 
 * @author johan
 */
public class AsyncHTTPRequest implements Runnable{
    
    //Request states
    public static final int CON_START = 0; //Start of request, also running
    public static final int CON_ERROR = 1; //In case of error
    public static final int CON_SUCCEED = 2; //Success!
    
    //Useful states
    public static final int CON_404 = 3; // NOT FOUND
    public static final int CON_200 = 4; // OK
    public static final int CON_500 = 5; // INTERNAL ERROR
    public static final int CON_403 = 6; // ACCESS RESTRICTED
    
    //Request methods
    private static final int GET = 0;
    private static final int POST = 1;
    private static final int PUT = 2;
    private static final int DELETE = 3;
    
    //Global variables
    Handler handler;
    HttpClient httpClient;
    String url;
    int method;
    boolean useHandler;
    
    boolean responseDone = false;
    String result;
    /**
     * Constructor for the AsynchHTTPRequest class. This makes use of a Handler.
     * 
     * @param handler The pre-defined handler.
     */
    public AsyncHTTPRequest(Handler handler) {
        
        this.handler = handler;
        useHandler = true;
    }
    
    
    /**
     * Constructor for the AsynchHTTPRequest class. This does not use Handler.
     * Not yet implemented.
     * 
     */
    public AsyncHTTPRequest() {
        
        useHandler = false;
    }
    
    
    /**
     * Implemented
     * 
     * @param url 
     */
    public void httpGet(String url) {
        
        this.method = GET;
        this.url = url;
        Thread thread = new Thread(this);
        thread.start();
        
    }
    
    /**
     * NYI
     * 
     * @param url
     * @param data 
     */
    public void httpPost(String url, String data) {
        
    }
    
    /**
     * NYI
     * 
     * @param url
     * @param data 
     */
    public void httpPut(String url, String data) {
        
    }
    
    /**
     * NYI
     * 
     * @param url 
     */
    public void httpDelete(String url) {
        
    }
    /**
     * 
     * 
     * @param httpEntity 
     */
    public void processResponse(HttpEntity httpEntity) {
        
        try {
        
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
        
        StringBuilder stringBuilder = new StringBuilder();
        String currentLine = null;
        
        //Run through the BufferedReader
        while((currentLine = bufferedReader.readLine()) != null) {
                
            //Append current line plus newline
            stringBuilder.append(currentLine + "\n");
        }
        
        String result = stringBuilder.toString();
        
        if(useHandler){
        //Create success message with processed request
        Message message = Message.obtain(handler, CON_SUCCEED, result);
        //Send finished string
        handler.sendMessage(message);        
        } else {
            
            //TODO: Implement non-handler functionality.
        }
        
        
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 
     */
    public void run() {
    
        //Message handler that the connection has started
        if(useHandler){
        handler.sendMessage(Message.obtain(handler, AsyncHTTPRequest.CON_START));
        }
        
        httpClient = new DefaultHttpClient();
        
        try {
            //Initialize HttpResponse
            HttpResponse httpResponse = null;
            
            //Execute request based on method
            switch(method) {

                case GET:

                    HttpGet httpGet = new HttpGet(url);
                    httpResponse = httpClient.execute(httpGet);
                    break;
                    
                 case POST:

                    //TODO: Implement POST functionality
                    break;
                     
                 case PUT:

                    //TODO: Implement PUT functionality
                    break;
                     
                  case DELETE:

                    //TODO: Implement DELETE functionality
                    break;

                default:
                    break;


            }
            
            processResponse(httpResponse.getEntity());
            
        } catch(Exception e) {
            //Message handler that the requeset has failed
            e.printStackTrace();
            if(useHandler){
            handler.sendMessage(Message.obtain(handler, AsyncHTTPRequest.CON_ERROR));
            }
        }
        
        
        
    }
    
}
