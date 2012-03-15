package no.hials.muldvarp.desktop;

/**
 *
 * @author mikael
 */
public class IntentLink {
    int titleStringResource;
    int imageResource;
    Class action;

    public IntentLink(int stringResource, int imageResource, Class action) {
        this.titleStringResource     = stringResource;
        this.imageResource = imageResource;
        this.action     = action;
    }    
    
    public int getNameStringResource() {
        return titleStringResource;
    }

    public int getImageResource() {
        return imageResource;
    }

    public Class getAction() {
        return action;
    }        
}
