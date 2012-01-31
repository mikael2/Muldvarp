/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import no.hials.muldvarp.R;

/**
 *
 * @author Nospherus
 */
public class LIBmainAll extends Fragment {

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View mainV = inflater.inflate(R.layout.library_all, container, false);
        
        createButton(mainV,R.id.libraryitem1, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem2, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem3, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem4, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem5, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem6, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem7, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem8, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem9, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem10, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem11, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem12, LibraryDetail.class);
        createButton(mainV,R.id.libraryitem13, LibraryDetail.class);
        return mainV;
    }
    
     private void createButton(View view, int buttonid, final Class action) {
        Button button = (Button) view.findViewById(buttonid);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), action);
                startActivityForResult(myIntent, 0);
            }
        });
    }
}
