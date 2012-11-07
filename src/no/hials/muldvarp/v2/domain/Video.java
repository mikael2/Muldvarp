/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.DetailActivity;
import no.hials.muldvarp.v2.fragments.DetailFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class Video extends Domain {

    public Video() {
        
    }

    public Video(JSONObject json) throws JSONException {
        this.id = json.getInt("id");
        this.name = json.getString("videoName");
    }

    public Video(String name, String detail) {
        super(name);
        super.detail = detail;
    }

    @Override
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        fragmentList.add(new DetailFragment("Detail", R.drawable.stolen_smsalt));
    }
}
