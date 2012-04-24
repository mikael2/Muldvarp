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
import java.io.File;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/**
 *
 * @author johan
 */
public class CachedWebRequest {
    
    Handler handler;
    Intent intent;
    Context applicationContext;
    String URI;
    String cacheFileName;
    Header header;
    boolean useHandler;
    
    public CachedWebRequest(Intent intent, Context applicationContext, String URI, String cacheLocation){
        
        this.intent = intent;
        this.useHandler = false;
        this.applicationContext = applicationContext;
        this.URI = URI;
        this.cacheFileName = cacheLocation;
    }
    
    public CachedWebRequest(Handler handler, Context applicationContext, String URI, String cacheLocation){
        
        this.useHandler = true;
        this.applicationContext = applicationContext;
        this.handler = handler;
        this.URI = URI;
        this.cacheFileName = cacheLocation;
    }
    
    public void setHeader(String headerName, String headerValue){
        
        this.header = new BasicHeader(headerName, headerValue);
    }
    
    public void setHeader(Header header){
        
        this.header = header;
    }
    
    public void startRequest(){
        
        System.out.println("AsyncCachedWebRequest: startRequest: Request for " + URI + " received.");
        
        if(checkCache(cacheFileName)){
            
            handler = new Handler(){
              
                @Override
                public void handleMessage(Message message){
     
                    switch (message.what) {
                    
                    //Connection Start
                    case AsyncHTTPRequest.CON_START: {

                        String response = (String) message.obj;

                        break;
                    }
                        
                    //Connection Success
                    case AsyncHTTPRequest.CON_SUCCEED: {

                        System.out.println("AsyncCachedWebRequest: startRequest: Connection successful,.");
                        System.out.println("AsyncCachedWebRequest: startRequest: Writing to cache.");
                        String response = (String) message.obj;
                        AsyncFileIOUtility asyncFileIOUtility = new AsyncFileIOUtility(intent, applicationContext);
                        asyncFileIOUtility.writeString(applicationContext.getCacheDir().getPath(), cacheFileName, response);
                        asyncFileIOUtility.startIO();

                        break;
                    }
                        
                    //Connection Error
                    case AsyncHTTPRequest.CON_ERROR: {
                        
                        String response = (String) message.obj;
                        System.out.println("ERROR: Status code " + response);
                        
                        
                        break;
                    }
                }
                    
                }
                
            };
            System.out.println("AsyncCachedWebRequest: startRequest: Sending Asynchroous HTTP request");
            AsyncHTTPRequest asyncHTTPRequest = new AsyncHTTPRequest(intent, applicationContext);
            asyncHTTPRequest.setHandler(handler);
            asyncHTTPRequest.setHeader(header);
            asyncHTTPRequest.httpGet(URI);
            
                        
        } else {
//            
            //Change broadcast if the cache was up to date
            if(intent.getAction().equals(MuldvarpService.ACTION_VIDEOCOURSE_UPDATE)){
            
                intent = new Intent(MuldvarpService.ACTION_VIDEOCOURSE_LOAD);
            } else if (intent.getAction().equals(MuldvarpService.ACTION_VIDEOCOURSE_UPDATE)){
                
                intent = new Intent(MuldvarpService.ACTION_PROGRAMMES_LOAD);
            }
            
            System.out.println("AsyncCachedWebRequest: startRequest: Broadcasting intent " + intent.getAction());
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent);
        }        
    }
    
    /**
     * Returns true if the file exist in the specified filepath and is outdated 
     * according to the set time.
     * 
     * @param filePath
     * @return boolean true/false
     */
    public boolean checkCache(String filePath){
        
        
        File cacheFile = new File(filePath);
        System.out.println("AsyncCachedWebRequest: checkCache(" + filePath + ")");
        if((System.currentTimeMillis() - cacheFile.lastModified() > applicationContext.getResources().getInteger(R.integer.cacheTime))) {
            
            System.out.println("AsyncCachedWebRequest: checkCache: Cache outdated.");
            return true;
        } else {
            
            System.out.println("AsyncCachedWebRequest: checkCache: Cache up to date.");
            return false;   
        }
    }
}
