package no.hials.muldvarp.library;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.entities.LibraryItem;

/**
 *
 * @author Nospherus
 */
public class LIBmainAll extends Fragment implements View.OnClickListener {
    private GridView grid;

    private View mainV;
    private GridLibraryAdapter g;
    private List<LibraryItem> libraryList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mainV = inflater.inflate(R.layout.library_all, container, false);
        return mainV;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        grid = (GridView) mainV.findViewById(R.id.allGrid);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent = new Intent(view.getContext(), LibraryDetail.class);
                Bundle b = new Bundle();
                b.putSerializable("item", (LibraryItem) g.getItem(position));
                myIntent.putExtras(b);
                startActivityForResult(myIntent, 0);
            }
        });
        
        g = new GridLibraryAdapter(getActivity(), R.layout.library_adapter_grid, R.id.courselisttext, false);
        if(libraryList != null) {
            g.clear();
            g.addAll(libraryList);            
        }
        grid.setAdapter(g);
    }
    
    
    public void itemsReady(List<LibraryItem> items) {
        libraryList = items;
        if(g != null) {
            g.clear();
            g.addAll(libraryList);
            g.notifyDataSetChanged();
        }
    }

    public void onClick(View view) {
        ((Button) view).setText("*");
        ((Button) view).setEnabled(false);
    }
}
