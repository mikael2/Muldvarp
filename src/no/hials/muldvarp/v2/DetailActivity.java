/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2;

import android.os.Bundle;
import android.widget.TextView;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Document;
import no.hials.muldvarp.v2.domain.Domain;
import no.hials.muldvarp.v2.domain.News;
import no.hials.muldvarp.v2.domain.Video;

/**
 * This class defines a Activity used for displaying information about an item, 
 * as well as providing functionality for interacting with it.
 * 
 * @author johan
 */
public class DetailActivity extends MuldvarpActivity {
    private TextView textItemTitle;
    private TextView textItemDescription;
    public enum Type {NEWS, VIDEO, DOCUMENTS}
    Type type;
    Domain item;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = Type.valueOf(getIntent().getType());
        switch(type) {
            case NEWS:
                item = new News("Nyhetstittel", "Tekst");
                setContentView(R.layout.v2_textlayout);
                textItemTitle = (TextView) findViewById(R.id.title);
                textItemDescription = (TextView) findViewById(R.id.text);
                break;
            case VIDEO:
                item = new Video("Videotittel", "Beskrivelse");
                setContentView(R.layout.metadata);
                textItemTitle = (TextView) findViewById(R.id.item_title);
                textItemDescription = (TextView) findViewById(R.id.item_description);
                break;
            case DOCUMENTS:
                item = new Document("Dokumenttittel", "Beskrivelse");
                setContentView(R.layout.metadata);
                textItemTitle = (TextView) findViewById(R.id.item_title);
                textItemDescription = (TextView) findViewById(R.id.item_description);
                break;
        }
        
        textItemTitle.setText(item.getName());
        textItemDescription.setText(item.getDetail());
    }
}
