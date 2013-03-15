package no.hials.muldvarp.v2.utility;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.hials.muldvarp.v2.MuldvarpService;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Programme;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

/**
 *
 * @author johan
 */
public class NewDownloadTask extends AsyncTask<String, Void, Boolean> {
    Intent intent;
    Context ctx;
    MuldvarpService mService;
    MuldvarpService.DataTypes type;
    int itemId;

    public NewDownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type) {
        this.intent = intent;
        this.ctx = ctx;
        this.type = type;
    }
    
    public NewDownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type, MuldvarpService mService) {
        this.intent = intent;
        this.ctx = ctx;
        this.mService = mService;
        this.type = type;
    }

    public NewDownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type, int itemId) {
        this.intent = intent;
        this.ctx = ctx;
        this.type = type;
        this.itemId = itemId;
    }
    
    public NewDownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type, int itemId, MuldvarpService mService) {
        this.intent = intent;
        this.ctx = ctx;
        this.mService = mService;
        this.type = type;
        this.itemId = itemId;
    }
    
    public String getData(String url) throws IOException {
        System.out.println("DownloadTask: " + url);
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
    

    @Override
    protected Boolean doInBackground(String... params) {
        try {
            String json = getData(params[0]);
            List<Domain> items;
            switch(type) {
                case COURSES:
                    if(itemId > 0) {
                        Course item = (Course)JSONUtilities.JSONtoObject(json, type);
                        mService.setSelectedCourse(item);
                    }
                    break;
                case PROGRAMS:
                    if(itemId > 0) {
                        Programme item = (Programme)JSONUtilities.JSONtoObject(json, type);
                        mService.setSelectedProgramme(item);
                    } else {
                        items = JSONUtilities.JSONtoList(json, type);
                        mService.setmProgrammes(new ArrayList<Domain>(items));
                    }
                    break;
                case FRONTPAGE:
                    items = JSONUtilities.JSONtoList(json, type);
                    items = new ArrayList<Domain>(items);
                    try {
                        mService.setFrontpage(items.get(0));
                    } catch (NullPointerException ex) {
                        Log.e("DOWNLOADTASK", ex.getMessage());
                    }
                    break;
                case NEWS:
                    items = JSONUtilities.JSONtoList(json, type);
                    mService.setmNews(new ArrayList<Domain>(items));
                    break;
                default:
                    System.out.println("ERROR: nothing to do in download task");
                    break;
            }
            return true;
        } catch (JSONException ex) {
            Logger.getLogger(NewDownloadTask.class.getName()).log(Level.SEVERE, null, ex);
            LocalBroadcastManager.getInstance(ctx).sendBroadcast(new Intent(MuldvarpService.ACTION_UPDATE_FAILED));
        } catch (IOException ex) {
            Logger.getLogger(NewDownloadTask.class.getName()).log(Level.SEVERE, null, ex);
            LocalBroadcastManager.getInstance(ctx).sendBroadcast(new Intent(MuldvarpService.ACTION_UPDATE_FAILED));
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean retVal) {
        if(retVal) {
            LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
        }
    }

}
