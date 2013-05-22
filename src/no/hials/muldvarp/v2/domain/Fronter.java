/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author kb
 */
public class Fronter extends Domain implements Serializable {
    List<Message> messages;
    List<Document> documents;
    List<Innlevering> innleveringer;


    public Fronter(JSONObject json) throws JSONException {
        this.messages = JSONArrayToMessages(json.getJSONArray("messages"));
        this.documents = JSONArrayToDocuments(json.getJSONArray("documents"));
        this.innleveringer = JSONArrayToInnleveringer(json.getJSONArray("innleveringer"));
    }
    
    public static List<Message> JSONArrayToMessages(JSONArray jsonArray) throws JSONException {
        List<Message> retVal = new ArrayList<Message>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new Message(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }
    
    public static List<Document> JSONArrayToDocuments(JSONArray jsonArray) throws JSONException {
        List<Document> retVal = new ArrayList<Document>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new Document(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }
    
    public static List<Innlevering> JSONArrayToInnleveringer(JSONArray jsonArray) throws JSONException {
        List<Innlevering> retVal = new ArrayList<Innlevering>();        
        for (int i = 0; i < jsonArray.length(); i++) {
            retVal.add(new Innlevering(jsonArray.getJSONObject(i)));
        }        
        return retVal;
    }

    public List<Innlevering> getInnleveringer() {
        return innleveringer;
    }

    public void setInnleveringer(List<Innlevering> innleveringer) {
        this.innleveringer = innleveringer;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
        
    public static class Innlevering implements Serializable {
        String navn;
        String status;

        public Innlevering(JSONObject json) throws JSONException {
            this.navn = json.getString("navn");
            this.status = json.getString("status");
        }
        
        public String getNavn() {
            return navn;
        }

        public void setNavn(String navn) {
            this.navn = navn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public static class Message implements Serializable {
        String message;
        String date;
        
        public Message(JSONObject json) throws JSONException {
            this.message = json.getString("message");
            this.date = json.getString("date");
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class Document implements Serializable {
        String name;
        String date;
        String url;
        
        public Document(JSONObject json) throws JSONException {
            this.name = json.getString("name");
            this.date = json.getString("date");
            this.url = json.getString("url");
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
