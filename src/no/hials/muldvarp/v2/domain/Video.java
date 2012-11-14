/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.content.Context;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.fragments.DetailFragment;
import no.hials.muldvarp.v2.fragments.ListFragment;
import no.hials.muldvarp.v2.fragments.MuldvarpFragment;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kristoffer
 */
public class Video extends Domain {
    String uri;

    public Video() {
        super.icon = R.drawable.youtube_icon;
    }

    public Video(JSONObject json) throws JSONException {
        super.id = json.getInt("id");
        super.name = json.getString("videoName");
        super.detail = json.getString("videoDescription");
        this.uri = json.getString("videoURI");
        super.icon = R.drawable.youtube_icon;
    }

    public Video(String name, String detail) {
        super(name);
        super.detail = detail;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public void populateList(List<MuldvarpFragment> fragmentList, Context context) {
        fragmentList.add(new DetailFragment("Detail", R.drawable.stolen_smsalt, ListFragment.ListType.VIDEO));
    }
}
