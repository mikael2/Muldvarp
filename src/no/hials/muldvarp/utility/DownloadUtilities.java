/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.utility;

import android.os.Handler;
import android.os.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.InputStream;
import java.net.URI;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;
import no.hials.muldvarp.video.CustomListFragment;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author kristoffer
 */
public class DownloadUtilities {
    
    public static InputStream getJSONData(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception ex) {
            Logger.getLogger(MuldvarpService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return data;
    }
    
    public static Gson buildGson() {
        GsonBuilder builder = new GsonBuilder();
        
        // Register an adapter to manage the date types as long values 
        builder.registerTypeAdapter(Date.class, new DateAdapter());

        return builder.create();
    }
    


}
