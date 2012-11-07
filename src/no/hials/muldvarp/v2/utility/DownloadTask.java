package no.hials.muldvarp.v2.utility;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import java.io.IOException;
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
import org.json.JSONException;

/**
 *
 * @author mikael
 */
public class DownloadTask extends AsyncTask<String, Void, Boolean> {
    Intent intent;
    Context ctx;
    MuldvarpService.DataTypes type;
    MuldvarpDataSource mds;
    int itemId;

    public DownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type) {
        this.intent = intent;
        this.ctx = ctx;
        this.type = type;
        mds = new MuldvarpDataSource(ctx);
    }

    public DownloadTask(Context ctx, Intent intent, MuldvarpService.DataTypes type, int itemId) {
        this.intent = intent;
        this.ctx = ctx;
        this.type = type;
        mds = new MuldvarpDataSource(ctx);
        this.itemId = itemId;
    }

    @Override
    protected Boolean doInBackground(String... params) {
        mds.open();
        try {
            String json = JSONUtilities.getData(params[0]);
            List<Domain> items;
            Domain item;
            switch(type) {
                case COURSES:
                    items = JSONUtilities.JSONtoList(json, type);
                    List<Domain> oldCourses;
                    if(itemId != 0) {
                        oldCourses = mds.getCoursesByProgramme(mds.getProgrammeById(String.valueOf(itemId)));
                    } else {
                        oldCourses = mds.getAllCourses();
                    }

                    if(compareOld(oldCourses, items)) {
                        for(int i = 0; i < items.size(); i++) {
                            mds.insertCourse((Course)items.get(i));
                        }
                    }
                    break;
                case PROGRAMS:
                    items = JSONUtilities.JSONtoList(json, type);
                    List<Domain> oldProgs = mds.getAllProgrammes();
                    if(compareOld(oldProgs, items)) {
                        for(int i = 0; i < items.size(); i++) {
                            mds.insertProgramme((Programme)items.get(i));
                        }
                    }
                    break;
                case ARTICLE:
                    item = JSONUtilities.JSONtoObject(json, type);
                    mds.insertArticle((Article)item);
                    break;
                case DOCUMENTS:
                    items = JSONUtilities.JSONtoList(json, type);
                    List<Domain> oldDocs = mds.getAllDocuments();
                    if(compareOld(oldDocs, items)) {
                        for(int i = 0; i < items.size(); i++) {
                            mds.insertDocument((Document)items.get(i));
                        }
                    }
                    break;
                case VIDEOS:
                    items = JSONUtilities.JSONtoList(json, type);
                    List<Domain> oldVideos = mds.getAllProgrammes();
                    if(compareOld(oldVideos, items)) {
                        for(int i = 0; i < items.size(); i++) {
                            mds.insertVideo((Video)items.get(i));
                        }
                    }

                    break;
                case NEWS:
                    items = JSONUtilities.JSONtoList(json, type);
                    List<Domain> oldArticles = mds.getArticlesByCategory("news");
                    if(compareOld(oldArticles, items)) {
                        for(int i = 0; i < items.size(); i++) {
                            mds.insertArticle((Article)items.get(i));
                        }
                    }
                    break;
            }
            mds.close();
            return true;
        } catch (JSONException ex) {
            Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DownloadTask.class.getName()).log(Level.SEVERE, null, ex);
        }
        mds.close();
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
                        mds.deleteProgramme((Programme)oldItem);
                    } else if (oldItem instanceof Course) {
                        mds.deleteCourse((Course)oldItem);
                    } else if (oldItem instanceof Document) {
                        //mds.deleteDocument((Document)oldItem);
                    } else if (oldItem instanceof Video) {
                        //mds.deleteVideo((Video)oldItem);
                    } else if (oldItem instanceof Article) {
                        mds.deleteArticle((Article)oldItem);
                    }
                }
            }
        } catch(NullPointerException ex) {
            Log.e("dl", ex.getMessage());
            return false;
        }
        return true;
    }

}
