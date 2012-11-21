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
import no.hials.muldvarp.v2.database.MuldvarpDataSource;
import no.hials.muldvarp.v2.domain.Article;
import no.hials.muldvarp.v2.domain.Course;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.Programme;
import no.hials.muldvarp.v2.domain.Video;
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
    MuldvarpDataSource mds;
    int itemId;
    boolean withItem;

    public NewDownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type) {
        this.intent = intent;
        this.ctx = ctx;
        this.type = type;
        this.withItem = false;
        mds = new MuldvarpDataSource(ctx);
    }
    
    
    
    public NewDownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type, MuldvarpService mService) {
        this.intent = intent;
        this.ctx = ctx;
        this.mService = mService;
        this.type = type;
        this.withItem = false;
        mds = new MuldvarpDataSource(ctx);
    }

    public NewDownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type, int itemId) {
        this.intent = intent;
        this.ctx = ctx;
        this.type = type;
        this.withItem = true;
        mds = new MuldvarpDataSource(ctx);
        this.itemId = itemId;
    }
    
    public NewDownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type, int itemId, MuldvarpService mService) {
        this.intent = intent;
        this.ctx = ctx;
        this.mService = mService;
        this.type = type;
        this.withItem = true;
        mds = new MuldvarpDataSource(ctx);
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
        mds.open();
        try {
            String json = getData(params[0]);
            List<Domain> items;
            Domain item;
            switch(type) {
                case COURSES:
                    items = JSONUtilities.JSONtoList(json, type);
                    List<Domain> oldCourses;
                    oldCourses = mService.mCourses;
                    
                    mService.setmCourses(new ArrayList<Domain>(items));
                    
//
//                    if(compareOld(oldCourses, items)) {
//                        for(int i = 0; i < items.size(); i++) {
//                            mds.insertCourse((Course)items.get(i));
//                        }
//                    }
                    break;
                case PROGRAMS:
                    items = JSONUtilities.JSONtoList(json, type);
                    mService.setmProgrammes(new ArrayList<Domain>(items));
//                    List<Domain> oldProgs = mService.mProgrammes;
//                    if(compareOld(oldProgs, items)) {
//                        for(int i = 0; i < items.size(); i++) {
//                            mds.insertProgramme((Programme)items.get(i));
//                        }
//                    }
                    break;
                case ARTICLE:
                    item = JSONUtilities.JSONtoObject(json, type);
                    mService.setmArticle(item);
//                    mds.insertArticle((Article)item);
                    break;
                case DOCUMENTS:
                    items = JSONUtilities.JSONtoList(json, type);
                    mService.setmDocuments(new ArrayList<Domain>(items));
//                    List<Domain> oldDocs = mService.mDocuments;
//                    if(compareOld(oldDocs, items)) {
//                        for(int i = 0; i < items.size(); i++) {
//                            mds.insertDocument((Document)items.get(i));
//                        }
//                    }
                    break;
                case VIDEOS:
                    items = JSONUtilities.JSONtoList(json, type);
                    mService.setmVideos(new ArrayList<Domain>(items));
//                    List<Domain> oldVideos = mds.getAllVideos();
//                    if(compareOld(oldVideos, items)) {
//                        for(int i = 0; i < items.size(); i++) {
//                            mds.insertVideo((Video)items.get(i));
//                        }
//                    }
                    break;
                case QUIZ:
                    items = JSONUtilities.JSONtoList(json, type);
                    mService.setmQuizzes(new ArrayList<Domain>(items));
                    break;
                case NEWS:
                    items = JSONUtilities.JSONtoList(json, type);
                    mService.setmNews(new ArrayList<Domain>(items));
//                    List<Domain> oldArticles = mds.getArticlesByCategory("news");
//                    if(compareOld(oldArticles, items)) {
//                        for(int i = 0; i < items.size(); i++) {
//                            mds.insertArticle((Article)items.get(i));
//                        }
//                    }
                    break;
            }
            //mds.close();
            return true;
        } catch (JSONException ex) {
            Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        //mds.close();
        return false;
    }

    @Override
    protected void onPostExecute(Boolean retVal) {
        if(retVal) {
            LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent);
        }
    }

//    public static Set<Domain> compareLists(List<Domain> list1, List<Domain> list2) {
//        Set<Domain> intersect = new HashSet<Domain>(list1);
//        intersect.retainAll(list2);
//        return intersect;
//    }

    public boolean compareOld(List<Domain> oldItems, List<Domain> newItems) {
        try {
            for(int i = 0; i < oldItems.size(); i++) {
                Domain oldItem = oldItems.get(i);
                boolean found = false;
                for(int k = 0; k < newItems.size(); k++) {
                    Domain newProg = newItems.get(k);
                    if(newProg.getName().equals(oldItem.getName())) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    if (oldItem instanceof Programme) {
                        mService.mProgrammes.remove(i);
                    } else if (oldItem instanceof Course) {
                        mService.mCourses.remove(i);
                    } else if (oldItem instanceof Document) {
                        mService.mDocuments.remove(i);
                    } else if (oldItem instanceof Video) {
                        mService.mVideos.remove(i);
                    } else if (oldItem instanceof Article) {
                        mService.mArticles.remove(i);
                    }
                }
            }
        } catch(NullPointerException ex) {
            Log.e("dl", "ERROR: " + ex.getMessage());
            return false;
        }
        return true;
    }

}
