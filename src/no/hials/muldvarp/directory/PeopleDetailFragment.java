package no.hials.muldvarp.directory;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import no.hials.muldvarp.R;
import no.hials.muldvarp.domain.Person;

/**
 *
 * @author mikael
 */
public class PeopleDetailFragment extends Fragment {
    Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        View retVal = inflater.inflate(R.layout.directory_campus, container, false);
        
        //retVal.findViewById(R.id.ansatte);
        
        return retVal;
    }
}
