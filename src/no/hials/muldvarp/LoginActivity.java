/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kristoffer
 */
public class LoginActivity extends Activity {
    private String url = "http://master.uials.no:8080/muldvarp/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
    }
    
    public void logIn(View view) {
        EditText un = (EditText) findViewById(R.id.username);
        EditText pw = (EditText) findViewById(R.id.password);
        CheckBox rmbr = (CheckBox) findViewById(R.id.rememberme);
        
        final String username = un.getText().toString();
        final String password = pw.getText().toString();
        
        if(rmbr.isChecked()) {
            // cache key/token?
        }
        new auth().execute(username,password);
    }
    
    private class auth extends AsyncTask<String, Void, Void> {       
        
        protected Void doInBackground(String... vars) {
            HttpURLConnection c = null;
            try {
                c = (HttpURLConnection) new URL(url).openConnection();
            } catch (IOException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            c.setRequestProperty(
                "Authorization",
                "basic " + Base64.encode((vars[0] + ":" + vars[1]).getBytes(),
                                           Base64.DEFAULT)
                );
            c.setUseCaches(false);
            try {
                c.connect();
            } catch (IOException ex) {
                Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        } 
        
        @Override
        protected void onPostExecute(Void not) {
            redirect();
        }
    }
    
    public void redirect() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
