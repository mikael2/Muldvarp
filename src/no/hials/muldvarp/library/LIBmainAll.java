/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.*;
import android.widget.*;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.LibraryItem;

   
/**
 *
 * @author Nospherus
 */
public class LIBmainAll extends Fragment implements View.OnClickListener {

private View mainV;
    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        mainV = inflater.inflate(R.layout.library_all, container, false);
        return mainV;
    }
    
     private void createButton(View Detailview, int buttonid, final Class action, final LibraryItem li) {
        Button button = (Button) Detailview.findViewById(buttonid);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent myIntent = new Intent(view.getContext(), action);
                Bundle b = new Bundle();
                b.putSerializable("item", li);
                myIntent.putExtras(b);
                startActivityForResult(myIntent, 0);
            }
        });
    }
     
     public void itemsReady(List l){
        
        LibraryItem li = (LibraryItem)l.get(0);
        if(li!=null){createButton(mainV,R.id.libraryitem1, LibraryDetail.class, li);}
        li = (LibraryItem)l.get(1);
        if(li!=null){createButton(mainV,R.id.libraryitem2, LibraryDetail.class, li);}
        li = (LibraryItem)l.get(2);
        if(li!=null){createButton(mainV,R.id.libraryitem3, LibraryDetail.class, li);}
        li = (LibraryItem)l.get(3);
        if(li!=null){createButton(mainV,R.id.libraryitem4, LibraryDetail.class, li);}
        createButton(mainV,R.id.libraryitem5, LibraryDetail.class, li);
        createButton(mainV,R.id.libraryitem6, LibraryDetail.class, li);
        createButton(mainV,R.id.libraryitem7, LibraryDetail.class, li);
        createButton(mainV,R.id.libraryitem8, LibraryDetail.class, li);
        createButton(mainV,R.id.libraryitem9, LibraryDetail.class, li);
        createButton(mainV,R.id.libraryitem10, LibraryDetail.class, li);
        createButton(mainV,R.id.libraryitem11, LibraryDetail.class, li);
        createButton(mainV,R.id.libraryitem12, LibraryDetail.class, li);
        createButton(mainV,R.id.libraryitem13, LibraryDetail.class, li);
     }
     public void onClick(View view) {
            ((Button) view).setText("*");
            ((Button) view).setEnabled(false);
        }
     @Override
        public void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
       }
}
