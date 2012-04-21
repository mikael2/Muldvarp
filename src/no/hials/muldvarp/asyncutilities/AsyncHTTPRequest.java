/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.asyncutilities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import no.hials.muldvarp.MuldvarpService;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;

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
    Header header;
    String url;
    int method;
    boolean useHandler;
    Intent intent;
    Context applicationContext;
    
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
     * Constructor for the AsynchHTTPRequest class. This makes use of a BroadCast service.
     * 
     */
    public AsyncHTTPRequest(Intent intent, Context context) {
        
        this.intent = intent;
        this.applicationContext = context;
        useHandler = false;
    }
    
    public void setHandler(Handler handler){
        
        this.useHandler = true;
        this.handler = handler;
    }
    
    public void setHeader(String headerName, String headerValue){
        
        this.header = new BasicHeader(headerName, headerValue);
    }
    
    public void setHeader(Header header){
        
        this.header = header;
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
     * Implemented
     * 
     * @param url
     * @param data 
     */
    public void httpPost(String url, String data) {
        
        this.method = POST;
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
     * This function processes the response from the HTTP Request, and parses it to a String.
     * It is then sent as an object along with the specified handler.
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
            System.out.println("AsyncHTTPRequest: Response processed, notifying handler");
            Message message = Message.obtain(handler, CON_SUCCEED, result);
            //Send finished string
            handler.sendMessage(message);        
        } else {
            
            System.out.println("AsyncHTTPRequest: Broadcasting intent " + intent.getAction());
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent);           
        }       
        
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * run-method
     */
    public void run() {
    
        //Message handler that the connection has started
        if(useHandler){            
            handler.sendMessage(Message.obtain(handler, AsyncHTTPRequest.CON_START));
        } else {            
            //LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent);            
        }
        
        //Initialize DefaultHttpClient and HttpResponse
        httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
                       
            //Execute request based on method
            switch(method) {

                case GET:

                    System.out.println("AsyncHTTPRequest: "+ "GET " + url);
                    HttpGet httpGet = new HttpGet(url);
                    if(header != null){
                        httpGet.setHeader(header);
                    }
                    httpResponse = httpClient.execute(httpGet);
                    break;
                    
                 case POST:

                     System.out.println("AsyncHTTPRequest: "+ "POST " + url);
                    HttpPost httpPost = new HttpPost(url);
                    httpResponse = httpClient.execute(httpPost);
                    break;
                     
                 case PUT:

                     System.out.println("AsyncHTTPRequest: "+ "PUT " + url);
                    //TODO: Implement PUT functionality
                    break;
                     
                  case DELETE:

                    System.out.println("AsyncHTTPRequest: "+ "DELETE " + url);
                    //TODO: Implement DELETE functionality
                    break;

                    default:
                    break;
            }
            System.out.println("AsyncHTTPRequest: Response received");
            processResponse(httpResponse.getEntity());
            
        } catch(Exception e) {
            //Message handler that the request has failed
            e.printStackTrace();
            if(useHandler){
                System.out.println("AsyncHTTPRequest: Notifying Handler");
                handler.sendMessage(Message.obtain(handler, AsyncHTTPRequest.CON_ERROR, httpResponse.getStatusLine().getStatusCode()));
            } else {
                System.out.println("AsyncHTTPRequest: Broadcasting intent");
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(new Intent(MuldvarpService.SERVER_NOT_AVAILABLE)); 
            }
        }
        
    }
    
}
