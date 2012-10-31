/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.database.MuldvarpDataSource;
import no.hials.muldvarp.v2.domain.Article;

/**
 *
 * @author kristoffer
 */
public class TextFragment extends MuldvarpFragment {
    View fragmentView;
    private TextView text;
    public int articleId;
    TextView title;
    Article item;

    public TextFragment(String title, int iconResourceID, int articleId) {
        super.fragmentTitle = title;
        super.iconResourceID = iconResourceID;
        this.articleId = articleId;
    }

    public TextFragment(Article item) {
        this.item = item;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.v2_textlayout, container, false);
            title = (TextView)fragmentView.findViewById(R.id.title);
            text = (TextView)fragmentView.findViewById(R.id.text);
        }
        itemsReady();
        return fragmentView;
    }

    public void itemsReady() {
        updateItems();
        title.setText(item.getName());
        text.setText(item.getContent());
    }

    private void updateItems() {
        MuldvarpDataSource mds = new MuldvarpDataSource(getActivity());
        mds.open();

        //item = mds.getArticle("");

        //mds.close(); //crash
    }
}
