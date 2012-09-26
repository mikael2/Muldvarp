/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import java.io.InputStream;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MuldvarpService;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

/**
 *
 * @author terje
 */
public class ServerConnection {
    Context ctx;        // context of MuldvarpService

    public ServerConnection(Context ctx) {
        this.ctx = ctx;
    }
    
    /**
     * Method checkServer, of class ServerConnection.
     * This method checks whether the device can connect to the server.
     * @return boolean serverIsUp
     */
    public boolean checkServer() {
        int url = 195885416;        //Using www.vg.no for now. There might be a problem in the ip-format, but we'll know more after testing.
        if(isNetworkAvailable()) {
            ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(cm.requestRouteToHost(cm.TYPE_WIFI, url) | cm.requestRouteToHost(cm.TYPE_MOBILE, url)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Method isNetworkAvailable, of class ServerConnection.
     * This method checks whether the device has access to any kind of network.
     * It does not however check whether that network has access to Internet.
     * @return boolean hasNetwork
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        if ( cm.getActiveNetworkInfo() != null ) {
            return true;
        }
        return false;
    }
    
    public static InputStream getJSONData(String url, String header) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        URI uri;
        InputStream data = null;
        try {
            uri = new URI(url);
            HttpGet method = new HttpGet(uri);
            
            if((!header.equals("")) && (header != null))
                method.setHeader("Authorization", header);
            
            HttpResponse response = httpClient.execute(method);
            data = response.getEntity().getContent();
        } catch (Exception ex) {
            Logger.getLogger(MuldvarpService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
}
