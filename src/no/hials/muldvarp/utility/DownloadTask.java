package no.hials.muldvarp.utility;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class DownloadTask extends AsyncTask<String, Void, Boolean> {
    Intent intent;
    Context ctx;

    public DownloadTask(Context ctx, Intent intent) {
        this.intent = intent;
        this.ctx = ctx;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        File f = null;
        if(params.length == 3) {
            f = new File(ctx.getCacheDir(), params[1]+params[2]);
        }
        else {
            f = new File(ctx.getCacheDir(), params[1]);
        }
        if ((System.currentTimeMillis() - f.lastModified() > ctx.getResources().getInteger(R.integer.cacheTime))
                && checkServer()) {
            try {
                DownloadUtilities.cacheThis(new InputStreamReader(DownloadUtilities.getJSONData(params[0])), f);
            } catch (Exception ex) {
                Log.e("DownloadTask", "Failed to cache " + f.getName(), ex);
            }
            return true;
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean retVal) {
        if(retVal) {
            LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
        }
    }

    public boolean checkServer() {
        if(isNetworkAvailable()) {
            try {
                if(InetAddress.getByName(ctx.getString(R.string.serverPath)).isReachable(5000)) {
                    return true;
                } 
            } catch (UnknownHostException ex) {
                Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        LocalBroadcastManager.getInstance(ctx).sendBroadcast(new Intent(MuldvarpService.SERVER_NOT_AVAILABLE));
        return false;
    }
    
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager 
            = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
}
