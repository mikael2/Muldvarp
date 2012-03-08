package no.hials.muldvarp.desktop;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

/**
 *
 * @author mikael
 */
public class DesktopService extends Service {
    public static final String ACTION_MESSAGE_UPDATE = "no.hials.muldvarp.ACTION_MESSAGE_UPDATE";
    
    private final IBinder localBinder = new DesktopService.LocalBinder();
    
    @Override
    public IBinder onBind(Intent arg0) {
        return localBinder;
    }

    public void requestMessages() {
        
    }
    
    /**
     * Class for clients to access. Because we know this service always runs in
     * the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public DesktopService getService() {
            return DesktopService.this;
        }
    }    
}
