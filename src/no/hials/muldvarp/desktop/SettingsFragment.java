package no.hials.muldvarp.desktop;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import no.hials.muldvarp.R;

/**
 *
 * @author mikael
 */
public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
    }
}
