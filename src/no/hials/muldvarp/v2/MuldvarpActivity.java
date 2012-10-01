/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import com.darvds.ribbonmenu.RibbonMenuView;
import com.darvds.ribbonmenu.iRibbonMenuCallback;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.MuldvarpService.LocalBinder;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import no.hials.muldvarp.v2.utility.FragmentUtils;

/**
 *
 * @author johan
 */
public class MuldvarpActivity extends Activity implements iRibbonMenuCallback {
    Bundle savedInstanceState;
    public RibbonMenuView rbmView;
    MuldvarpService mService;
    boolean mBound = false;
    boolean loggedIn = false;
    TextView loginname;
    public List<MuldvarpFragment> fragmentList = new ArrayList<MuldvarpFragment>();
    public String activityName;
    public SearchView searchView;
    public Domain domain;

    @Override
    public void onBackPressed() {
        if(rbmView.isMenuVisible()){
            rbmView.hideMenu();
        }
        else if(getActionBar().getSelectedNavigationIndex() > 0){
            getActionBar().setSelectedNavigationItem(0);
        }
        else{
//            showDialog(2);
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.savedInstanceState = savedInstanceState;

        setContentView(R.layout.main);

        rbmView = (RibbonMenuView) findViewById(R.id.ribbonMenuView1);
        rbmView.setMenuClickCallback(this);
        rbmView.setMenuItems(R.menu.ribbon_menu);

        loginname = (TextView) findViewById(R.id.loginname);
        loginname.setText("ikke innlogget");

        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getActionBar().setDisplayShowTitleEnabled(false);

        Intent intent = new Intent(this, MuldvarpService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getIntent().getIntExtra("tab", 0) > 0)
            getActionBar().setSelectedNavigationItem(getIntent().getIntExtra("tab", 0));
        else if(savedInstanceState != null)
            getActionBar().setSelectedNavigationItem(savedInstanceState.getInt("tab"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("tab", getActionBar().getSelectedNavigationIndex());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = null;

        switch (item.getItemId()) {
            case android.R.id.home:
                rbmView.toggleMenu();
                return true;
            case R.id.login:
                showDialog(0);
                return true;
            case R.id.about:
                showDialog(1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Method onCreateDialog, of class MuldvarpActivity.
     * This method displays a focusable dialog on top of the current view.
     * Enter the id of the desired dialog.
     * 0: Login dialog
     * 1: About page for Aalesund University College as defined in directory_campus.xml.
     * 2: AlertDialog asking the user whether he/she really wants to exit the application.
     * No further dialogs supported for now.
     * @param id
     * @return Dialog
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        final Dialog dialog = new Dialog(this);
        switch(id){
            case 0:                                                                                     //Displays the Login dialog to the user.
                dialog.setContentView(R.layout.login);
                dialog.setTitle("Login");
                final Button button;
                button = (Button) dialog.findViewById(R.id.login);
                 button.setOnClickListener(new View.OnClickListener() {
                     public void onClick(View v) {
                        EditText username = (EditText)dialog.findViewById(R.id.username);
                        EditText password = (EditText)dialog.findViewById(R.id.password);
                        if(mService.login(username.getText().toString(), password.getText().toString())){//Sends the logindata to muldvarpservice which authenticates the user, and then stores the user data.
                            loggedIn = true;                                                            //Sets the flag which indicates that the user is logged in.
                            loginname.setText(mService.getUser().getName());                            //Sets the users name in the ribbon menu.
                            Toast toast = Toast.makeText(getApplicationContext(), "logged in as: " + mService.getUser().getName(), Toast.LENGTH_SHORT);
                            toast.show();                                                               //Tells the user that he/she is logged in.
                            dialog.dismiss();
                        }
                        else{                                                                           //If authentification fails
                            Toast toast = Toast.makeText(getApplicationContext(), "Wrong username and/or password.", Toast.LENGTH_SHORT);
                            toast.show();
                        }

             }
         });
                break;
            case 1:
                dialog.setContentView(R.layout.directory_campus);
                dialog.setTitle("Om oss");
                break;

            case 2:     //This dialog is not currently used due to issues with accuratly triggering it when the application would otherwise finish.
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Er du sikker på at du vil avslutte?");
                builder.setCancelable(false);
                builder.setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                                finish();
                           }
                       })
                       .setNegativeButton("Nei", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                           }
                       });
            AlertDialog alert = builder.create();
            return alert;
        }

        return dialog;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);

        searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        //Create Listener for the search bar in the top actionbar
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            public boolean onQueryTextChange(String newText) {
                //Get current Fragment based on the currently selected index
                MuldvarpFragment tempFragment = fragmentList.get(getActionBar().getSelectedNavigationIndex());
                tempFragment.queryText(newText);
                return true;
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);

        return true;
    }

    @Override
    public void RibbonMenuItemClick(int itemId) {
        switch(itemId) {
            case 0:
                Intent intent = new Intent(this, TopActivity.class);
                intent.putExtra("tab", 3);
                startActivity(intent);
                break;
        }
    }

    public void getSpinnerList(final Activity activity, final List<MuldvarpFragment> fragmentList, int strings, int layout) {
        SpinnerAdapter mSpinnerAdapter = ArrayAdapter.createFromResource(this, strings, layout);
        ActionBar.OnNavigationListener mOnNavigationListener = new ActionBar.OnNavigationListener() {
            @Override
            public boolean onNavigationItemSelected(int position, long itemId) {
                if(rbmView.isMenuVisible())
                    rbmView.hideMenu();
                return FragmentUtils.changeFragment(activity, fragmentList, position);
            }
        };
        getActionBar().setListNavigationCallbacks(mSpinnerAdapter, mOnNavigationListener);
    }


     private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LocalBinder binder = (LocalBinder) service;
            mService = binder.getService();
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

     /**
      * Returns the Muldvarpservice object reference.
      * Typically used by the activity's fragments.
      * @return MuldvarpService
      */
     public MuldvarpService getService(){
         return mService;
     }

     /**
      * Returns true if the user is logged in.
      * @return loggedIn
      */
     public boolean getLoggedIn(){
         return loggedIn;
     }

}
