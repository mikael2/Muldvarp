/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.MuldvarpService;
import no.hials.muldvarp.domain.Question;
import no.hials.muldvarp.domain.Question.Alternative;

/**
 *
 * @author kristoffer
 */
public class CourseDetailQuizActivity extends Activity {
    MuldvarpService mService;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver     mReceiver;
    boolean mBound;
    ProgressDialog dialog;
    List<Question> questions = new ArrayList<Question>();
    
    public void makeTestData() {
        List<Question.Alternative> alt = new ArrayList<Question.Alternative>();
        Question.Alternative a1 = new Question.Alternative("1");
        a1.setId(1);
        alt.add(a1);
        Question.Alternative a2 = new Question.Alternative("1/2");
        a2.setId(2);
        alt.add(a2);
        Question.Alternative a3 = new Question.Alternative("0");
        a3.setId(3);
        alt.add(a3);
        Question.Alternative a4 = new Question.Alternative("-kvadratrot(2)/2");
        a4.setId(4);
        alt.add(a4);
        Question.Alternative a5 = new Question.Alternative("kvadratrot(3)/2");
        a5.setId(5);
        alt.add(a5);
        
        Question q = new Question("sin x", alt, a2);
        questions.add(q);
    }
    
    private boolean checkAnswer(Question q, int altid) {
        if(q.getAnswer().getId() == altid) {
            return true;
        }
        return false;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);        
        //setContentView(R.layout.course_quiz);
        makeTestData();
        
        LinearLayout layout = new LinearLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        for(int i = 0; i < questions.size(); i++) {
            LinearLayout question_layout = new LinearLayout(this);
            question_layout.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            TextView text = new TextView(this);
            text.setText(questions.get(i).getName());
            layout.addView(text);
            final Question question = questions.get(i);
            
            LinearLayout alts = new LinearLayout(this);
            for(int k = 0; k < questions.get(i).getAlternatives().size(); k++) {
                LinearLayout answ = new LinearLayout(this);
                answ.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.FILL_PARENT, 300));
                Alternative alternative = questions.get(i).getAlternatives().get(k);
                TextView quest = new TextView(this);
                quest.setText(alternative.getName());
                CheckBox cb = new CheckBox(this);
                cb.setId(alternative.getId());
                
                cb.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        // Perform action on clicks, depending on whether it's now checked
                        if (((CheckBox) v).isChecked()) {
                            if(checkAnswer(question, v.getId())) {
                                Toast.makeText(v.getContext(), "Correct!", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(v.getContext(), "Wrong!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
                
                answ.addView(quest);
                answ.addView(cb);
                alts.addView(answ);
            }
            
            question_layout.addView(alts);
            layout.addView(question_layout);
        }
        
        setContentView(layout);
        
//        if(savedInstanceState == null) {
//            dialog = new ProgressDialog(CourseDetailQuizActivity.this);
//            dialog.setMessage(getString(R.string.loading));
//            dialog.setIndeterminate(true);
//            dialog.setCancelable(false);
//            dialog.show();
//
//            // We use this to send broadcasts within our local process.
//            mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
//             // We are going to watch for interesting local broadcasts.
//            IntentFilter filter = new IntentFilter();
//            filter.addAction(MuldvarpService.ACTION_COURSE_UPDATE);
//            filter.addAction(MuldvarpService.SERVER_NOT_AVAILABLE);
//            mReceiver = new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
//                    if (intent.getAction().compareTo(MuldvarpService.ACTION_COURSE_UPDATE) == 0) {                    
//                        System.out.println("Toasting" + intent.getAction());
//                        Toast.makeText(context, "Courses updated", Toast.LENGTH_LONG).show();
//                        new CourseActivity.getItemsFromCache().execute(getString(R.string.cacheCourseList));
//                    } else if (intent.getAction().compareTo(MuldvarpService.SERVER_NOT_AVAILABLE) == 0) {
//                        Toast.makeText(context, "Server not available", Toast.LENGTH_LONG).show();
//                        dialog.dismiss();
//                    }
//                }
//            };
//            mLocalBroadcastManager.registerReceiver(mReceiver, filter);
//
//            Intent intent = new Intent(this, MuldvarpService.class);
//            bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//        }
        
    }
    
//    private class getItemsFromCache extends AsyncTask<String, Void, List<Course>> {       
//        
//        protected List<Course> doInBackground(String... urls) {
//            List<Course> items = null;
//            try {
//                File f = new File(getCacheDir(), urls[0]);
//                Type collectionType = new TypeToken<List<Course>>(){}.getType();
//                items = DownloadUtilities.buildGson().fromJson(new FileReader(f), collectionType);                
//            } catch (FileNotFoundException ex) {
//                Logger.getLogger(CourseActivity.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return items;
//        } 
//        
//        @Override
//        protected void onPostExecute(List<Course> items) {
////            courseList = new ArrayList<Course>();
////            courseList.addAll(items);
//            courseList = items;
//            CourseListFragment.itemsReady();
//            
//            dialog.dismiss();
//        }
//    }
//    
//    /** Defines callbacks for service binding, passed to bindService() */
//    private ServiceConnection mConnection = new ServiceConnection() {
//
//        @Override
//        public void onServiceConnected(ComponentName className,
//                IBinder service) {
//            // We've bound to LocalService, cast the IBinder and get LocalService instance
//            MuldvarpService.LocalBinder binder = (MuldvarpService.LocalBinder) service;
//            mService = binder.getService();
//            mBound = true;
//            mService.requestCourses();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName arg0) {
//            mService = null;
//            mBound = false;
//        }
//    };
//    
//    @Override
//    protected void onStop() {
//        super.onStop();
//        // Unbind from the service
//        if (mBound) {
//            unbindService(mConnection);
//            mBound = false;
//        }
//    }
//
//    public Boolean getIsGrid() {
//        return isGrid;
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if(mLocalBroadcastManager != null)
//            mLocalBroadcastManager.unregisterReceiver(mReceiver);
//    }
}
