package no.hials.muldvarp.directory;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import no.hials.muldvarp.R;

/**
 *
 * @author Lena
 */
public class DirectoryPeople extends ListFragment {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // ToDo add your GUI initialization code here  

        String[] people = getResources().getStringArray(R.array.people_array);
        this.setListAdapter(new ArrayAdapter<String>(getActivity(), R.layout.directory_people, R.id.people, people));

        this.getListView().setTextFilterEnabled(true);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(getActivity(), l.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();
    }
    
    //@Override
    //public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
    //  View result = inflater.inflate(R.layout.directory_people, container, false);
    //  return result;
    //}
    //@Override
    //public void onSaveInstanceState(Bundle outState) {
    //  super.onSaveInstanceState(outState);
    //  setUserVisibleHint(true);
    //}    
}
