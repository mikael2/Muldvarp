/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.library;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        button.setTextSize(12.0f);
        button.setText(li.getTitle());
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
//        li = (LibraryItem)l.get(3);
//        if(li!=null){createButton(mainV,R.id.libraryitem4, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(4);
//        if(li!=null){createButton(mainV,R.id.libraryitem5, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(5);
//        if(li!=null){createButton(mainV,R.id.libraryitem6, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(6);
//        if(li!=null){createButton(mainV,R.id.libraryitem7, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(7);
//        if(li!=null){createButton(mainV,R.id.libraryitem8, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(8);
//        if(li!=null){createButton(mainV,R.id.libraryitem9, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(9);
//        if(li!=null){createButton(mainV,R.id.libraryitem10, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(10);
//        if(li!=null){createButton(mainV,R.id.libraryitem11, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(11);
//        if(li!=null){createButton(mainV,R.id.libraryitem12, LibraryDetail.class, li);}
//        li = (LibraryItem)l.get(12);
//        if(li!=null){createButton(mainV,R.id.libraryitem13, LibraryDetail.class, li);}
        
        
//        GridLayout allGrid = new GridLayout(mainV.getContext());
//        GridLayout itemGrid = new GridLayout(mainV.getContext());
//        itemGrid.setColumnCount(1);
//        ImageButton iB = new ImageButton(mainV.getContext());
//        itemGrid.addView(iB);
//        TextView text = new TextView(mainV.getContext());
//        text.setText("title");
//        itemGrid.addView(text);
//        allGrid.addView(itemGrid);
        
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
