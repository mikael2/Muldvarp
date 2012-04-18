/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

/**
 *
 * @author kristoffer
 */
public class LoginActivity extends Activity {
    private String url;

    String username;
    String password;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    
    public void logIn(View view) {
        EditText un = (EditText) findViewById(R.id.username);
        EditText pw = (EditText) findViewById(R.id.password);
        EditText sp = (EditText) findViewById(R.id.serverpath);
        CheckBox rmbr = (CheckBox) findViewById(R.id.rememberme);
        
        url = sp.getText().toString();
        username = un.getText().toString();
        password = pw.getText().toString();
        
        if(rmbr.isChecked()) {
            // cache key/token?
        }
        saveSettings();
        redirect();
        //new auth().execute(username,password);
    }
    
//    private class auth extends AsyncTask<String, Void, String[]> {       
//        
//        protected String[] doInBackground(String... vars) {
//            HttpURLConnection c = null;
//            try {
//                c = (HttpURLConnection) new URL(url).openConnection();
//            } catch (IOException ex) {
//                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            c.setRequestProperty(
//                "Authorization",
//                "basic " + Base64.encode((vars[0] + ":" + vars[1]).getBytes(),
//                                           Base64.DEFAULT)
//                );
//            c.setUseCaches(false);
//            try {
//                c.connect();
//            } catch (IOException ex) {
//                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
//            }
//            return vars;
//        } 
//        
//        @Override
//        protected void onPostExecute(String... vars) {
//            //mService.setHttpHeader("Basic " + Base64.encodeToString((vars[0] + ":" + vars[1]).getBytes(), Base64.NO_WRAP));
//            
//            redirect();
//        }
//    }
    
    public void redirect() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    
    public void saveSettings() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("url", "master.uials.no");
        editor.putString("username", username);
        editor.putString("password", password);
        System.out.println("DEBUG: Username: " + username + " password:" + password);
        editor.commit();
    }
    
    public String loadSettings() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        
        url = settings.getString("url", url);
        username = settings.getString("username", username);
        password = settings.getString("password", password);
        
        return username + ":" + password; 
    }
}