/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.MuldvarpService;
import no.hials.muldvarp.v2.MuldvarpService.DataTypes;
import no.hials.muldvarp.v2.database.MuldvarpDataSource;
import no.hials.muldvarp.v2.domain.Article;

/**
 *
 * @author kristoffer
 */
public class TextFragment extends MuldvarpFragment {
    View fragmentView;
    private TextView text;
    int articleId;
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

        // We use this to send broadcasts within our local process.
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(getActivity().getApplicationContext());
         // We are going to watch for interesting local broadcasts.
        IntentFilter filter = new IntentFilter();
        filter.addAction(MuldvarpService.ACTION_ARTICLE_UPDATE);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                System.out.println("Got onReceive in BroadcastReceiver " + intent.getAction());
                updateItems();
                itemsReady();
            }
        };
        mLocalBroadcastManager.registerReceiver(mReceiver, filter);
        if(owningActivity.mService != null) {
            owningActivity.mService.updateSingleItem(DataTypes.ARTICLE, articleId);
        }
        updateItems();
        itemsReady();
        return fragmentView;
    }

    public void itemsReady() {
        if(item != null) {
            title.setText(item.getName());
            text.setText(Html.fromHtml(item.getContent()));
        }
    }

    private void updateItems() {
        MuldvarpDataSource mds = new MuldvarpDataSource(getActivity());
        mds.open();

        item = mds.getArticleById(articleId);
        //mds.close(); //crash
    }
}
