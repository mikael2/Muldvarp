/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.asyncutilities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import java.io.*;

/**
 * This class provides asynchronous caching functionality using android.os.Handler and threads.
 * 
 * A handler must be implemented for each of the cases: CACHE_START, CACHE_ERROR and CACHE_SUCCEED. 
 * Instantiate this class with the implemented handler, and call the methods in the class.
 * Current version should only support one file at a time, should be expanded to do several on one thread.
 * 
 * @author johan
 */
public class AsyncFileIOUtility implements Runnable{

    //Wrte states
    public static final int IO_START = 0; //Start of write, also running
    public static final int IO_ERROR = 1; //In case of error
    public static final int IO_SUCCEED = 2; //Success!
    
    //Object types
    public static final int READFILE = 0;
    public static final int WRITEFILE = 1;
    public static final int WRITESTRING = 2;
    public static final int APPENDTOFILE = 3;
    
    //Global variables
    Handler handler;
    File currentFile;
    Object objectToBeWritten;
    FileOutputStream fileOutputStream;
    int ioType;
    boolean useHandler;
    Intent intent;
    Context applicationContext;   
    
    /**
     * Constructor for the AsynchIOUtility class. This makes use of a Handler.
     * 
     * @param handler The pre-defined handler
     */
    public AsyncFileIOUtility(Handler handler) {
        
        this.handler = handler;
        useHandler = true;
    }    
    
    public AsyncFileIOUtility(Intent intent, Context context) {
        
        this.intent = intent;
        this.applicationContext = context;
        useHandler = false;
    }
    
    /**
     * This function writes a file as specified using the class run method.
     * 
     * @param filePath
     * @param fileName
     * @param object 
     */
    public void writeFile(String filePath, String fileName, Object object){        
        
        this.ioType = WRITEFILE;
        currentFile = new File(filePath, fileName);
        objectToBeWritten = object;
        
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void appendToFile(FileOutputStream fileOutputStream){        
        
        this.fileOutputStream = fileOutputStream;
        this.ioType = APPENDTOFILE;
        
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void readFile(File file){
        
        this.currentFile = file;
        this.ioType = READFILE;
        
        Thread thread = new Thread(this);
        thread.start();
    }
    
    public void writeString(String filePath, String fileName, String string){
        
        this.ioType = WRITESTRING;
        currentFile = new File(filePath, fileName);
        objectToBeWritten = string;
        
        Thread thread = new Thread(this);
        thread.start();
    }
    
    
    /**
     * Check when file was last modified.
     * 
     * @param file 
     */
    public void checkModified(File file){
        
    }
    
    /**
     * Run method 
     * Contains the
     */   
    public void run() {
        
        try {
            
            //Message handler that the connection has started
            if(useHandler){            
                handler.sendMessage(Message.obtain(handler, AsyncFileIOUtility.IO_START));
            } else {            
            //LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent);            
            }
        
            
            //Defaults to error
            Message message = Message.obtain(handler, AsyncFileIOUtility.IO_ERROR);
            
            //Write file based on object type
            switch(ioType){
                
                case READFILE:
                    
                    System.out.println("AsyncFileIOUtility: Trying to read " + currentFile.getPath());
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(currentFile));
//                    StringBuffer stringBuffer = new StringBuffer();
//                    String result;
//                    
//                    while ((result = bufferedReader.readLine()) != null) {
//
//                        stringBuffer.append(result);
//                        System.out.println(result);
//                        
//                    }

                    char[] buffer = null;
                    buffer = new char[(int)currentFile.length()];
                    int i = 0;
                    int c = bufferedReader.read();
                    
                    
                    while (c != -1) {
                       buffer[i++] = (char)c;
                       c = bufferedReader.read();
                    }
                    
                    String result = new String(buffer);
                    
                    System.out.println("AsyncFileIOUtility: File read from " + currentFile.getPath());
                    message = Message.obtain(handler, AsyncFileIOUtility.IO_SUCCEED, result);
                    
                    break;
                case WRITESTRING:

                    System.out.println("AsyncFileIOUtility: Trying to write " + currentFile.getPath());
                    String stringTobeWritten = (String) objectToBeWritten;        
                    BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(currentFile));
                    bufferedWriter.write(stringTobeWritten);                    
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    System.out.println("AsyncFileIOUtility: File written to " + currentFile.getPath());
                    message = Message.obtain(handler, AsyncFileIOUtility.IO_SUCCEED);
                    
                    break;     
                case WRITEFILE:

                    System.out.println("AsyncFileIOUtility: Trying to write " + currentFile.getPath());
                    
                    
                    
                    System.out.println("AsyncFileIOUtility: File written to " + currentFile.getPath());
                    message = Message.obtain(handler, AsyncFileIOUtility.IO_SUCCEED);
                    
                    break;

                    default:
                    break;
                
            }
            
            if(useHandler){            
                System.out.println("AsyncFileIOUtility: Notifying handler");
                handler.sendMessage(message);
            } else {
                System.out.println("AsyncFileIOUtility: Broadcasting Intent");
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(new Intent(intent)); 
            }
            
            
        } catch (IOException ex) {
            handler.sendMessage(Message.obtain(handler, AsyncFileIOUtility.IO_ERROR));
            Log.e("AsynchWritingUtility", "I/O error: " + currentFile.getName(), ex);            
            if(useHandler){
                handler.sendMessage(Message.obtain(handler, AsyncFileIOUtility.IO_ERROR));
            } else {
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(new Intent("IO FAILED RPLACED")); 
            }
        }
        
        
    }
    
}
