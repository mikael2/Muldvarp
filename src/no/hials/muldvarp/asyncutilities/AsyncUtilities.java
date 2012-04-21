/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.asyncutilities;

import android.os.Handler;
import android.os.Message;

/**
 *
 * @author johan
 */
public class AsyncUtilities {
    
    public static Handler getAsyncHTTPRequestHandler(){
        
        Handler handler = new Handler() {

            @Override
            public void handleMessage(Message message) {
                
                //TODO: Comment
                switch (message.what) {
                    
                    //Connection Start
                    case AsyncHTTPRequest.CON_START: {

                        String response = (String) message.obj;

                        break;
                    }
                        
                    //Connection Success
                    case AsyncHTTPRequest.CON_SUCCEED: {

                        String response = (String) message.obj;                        

                        break;
                    }
                        
                    //Connection Error
                    case AsyncHTTPRequest.CON_ERROR: {
                        
                        String response = (String) message.obj;

                        break;
                    }
                }
            }
        };
        
        return handler;
    }
    
}
