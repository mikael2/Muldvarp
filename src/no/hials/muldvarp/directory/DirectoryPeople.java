package no.hials.muldvarp.directory;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import no.hials.muldvarp.R;

/**
 *
 * @author Lena
 */
public class DirectoryPeople extends Fragment {
    
     /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedStateInstance) {
        super.onCreate(savedStateInstance);
        // ToDo add your GUI initialization code here        
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        return inflater.inflate(R.layout.directory_people, container, false);
    }
    
}