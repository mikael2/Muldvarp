/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.fragments.ListFragment.ListType;

/**
 *
 * @author terje
 */
public class DetailFragment extends MuldvarpFragment {
    View fragmentView;
    private TextView textItemTitle;
    private TextView textItemDescription;
    ListType type;
    String url;

    public DetailFragment(String fragmentTitle, int iconResourceID, ListType type) {
        super.fragmentTitle = fragmentTitle;
        super.iconResourceID = iconResourceID;
        this.type = type;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentView = inflater.inflate(R.layout.metadata, container, false);
        textItemTitle = (TextView) fragmentView.findViewById(R.id.item_title);
        textItemDescription = (TextView) fragmentView.findViewById(R.id.item_description);

        textItemTitle.setText(owningActivity.domain.getName());
        textItemDescription.setText(owningActivity.domain.getDetail());
        Document d = (Document)owningActivity.domain;
        url = "http://docs.google.com/viewer?url=";
        url += d.getURI();

        final Button button = (Button) fragmentView.findViewById(R.id.item_button1);

        button.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                System.out.println(url);
                Uri path = Uri.parse(url);
                if (true) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        System.out.println("godamnit!");
                    }
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(path, "text/html");
                startActivity(i);
             }
         });

        final Button button2 = (Button) fragmentView.findViewById(R.id.item_button2);

        button2.setOnClickListener(new View.OnClickListener() {
             public void onClick(View v) {
                 System.out.println(url);
                Uri path = Uri.parse(url);
                if (true) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                    try {
                        startActivity(intent);
                    }
                    catch (ActivityNotFoundException e) {
                        System.out.println("godamnit!");
                    }
                }
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(path, "text/html");
                startActivity(i);
             }
         });

        return fragmentView;
    }
}
