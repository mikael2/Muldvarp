/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.utility;

import android.content.Context;
import java.util.ArrayList;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.domain.Programme;

/**
 *  Temporary class to provide already formatted data based on String arrays in XML files.
 *  This is only temporary, proper database solution is needed.
 * 
 * @author johan
 */
public class DummyDataProvider {
    
    //Return list of programmes. Should be rewritten to just accept something else than a context.
    public static ArrayList<Programme> getProgrammeList(Context context){
        
        ArrayList<Programme> programmeList = new ArrayList<Programme>();
        
        //Get arraylist from XML resource, create Programme objects and place them in an array.
        String[] tempList = context.getResources().getStringArray(R.array.programme_list_dummmy);   
        
        if (tempList != null) {
            
            for (int i = 0; i < tempList.length; i++) {
             
                Programme currentProgram = new Programme(tempList[i]);
                programmeList.add(currentProgram);

                //debug check:
                System.out.println("Created:" + currentProgram.getName());
            }
            
        }
        
        
        //Check if list wasn't empty just to be sure, and generate dumb data if not
        if(programmeList.isEmpty()) {
            
            for (int n = 0; n < 10; n++) {
                
                Programme dumbProgram = new Programme("Program " + n);
                programmeList.add(dumbProgram);
            }
            
        }
        
        return programmeList;
    }
    
}
