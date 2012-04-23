package no.hials.muldvarp.utility;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.R;

/**
 *
 * @author mikael
 */
public class BookMarkTask extends AsyncTask<String, Void, Boolean> {
    Intent intent;
    Context ctx;
    String header;

    public BookMarkTask(Context ctx, Intent intent, String header) {
        this.intent = intent;
        this.ctx = ctx;
        this.header = header;
    }

    @Override
    protected Boolean doInBackground(String... params) {       
        
        File file = new File(params[0]);
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
            char[] buffer = null;
                    buffer = new char[(int)file.length()];
                    int i = 0;
                    int c = bufferedReader.read();
                    
                    
                    while (c != -1) {
                       buffer[i++] = (char)c;
                       c = bufferedReader.read();
                    }
                    
                    String result = new String(buffer);
//                    DownloadUtilities.cacheThis(result, file);
                    
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BookMarkTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(BookMarkTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }

    @Override
    protected void onPostExecute(Boolean retVal) {
        
    }

    public boolean checkServer() {
        if(isNetworkAvailable()) {
            try {                
                if(InetAddress.getByName("http://master.uials.no:8080").isReachable(5000)) {
                    // dinna funka ikkje
                    return true;
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(BookMarkTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(BookMarkTask.class.getName()).log(Level.SEVERE, null, ex);
            }
            return true; // fjern dinna når den over fungera
        }
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(new Intent(MuldvarpService.SERVER_NOT_AVAILABLE));
        return true;
    }
    
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
            = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
