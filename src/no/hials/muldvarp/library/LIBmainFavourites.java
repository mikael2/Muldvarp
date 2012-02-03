/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import no.hials.muldvarp.R;

/**
 *
 * @author Nospherus
 */
public class LIBmainFavourites extends Fragment {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        // ToDo add your GUI initialization code here        
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.library_favourite, container, false);
    }
}
