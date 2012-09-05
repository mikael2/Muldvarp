/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

import android.graphics.Bitmap;

/**
 * This class is a generic entityclass to contain metadata for documents and videos.
 * @author terje
 */
public class DocumentVideo {
    private Bitmap thumbnail;
    private String title;
    private String author;
    private String summary;

    public DocumentVideo(Bitmap thumbnail, String title, String author, String summary) {
        this.thumbnail = thumbnail;
        this.title = title;
        this.author = author;
        this.summary = summary;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
    
}
