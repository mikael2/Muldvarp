package no.hials.muldvarp.desktop;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import no.hials.muldvarp.R;

/**
 *
 * @author mikael
 */
public class MainPreferenceActivity extends PreferenceActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
