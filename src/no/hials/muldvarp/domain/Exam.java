package no.hials.muldvarp.domain;

import java.util.Date;

/**
 *
 * @author kristoffer
 */
public class Exam {
    Integer id;
    String name;
    String room;
    String info;
    Date examDate;
    //Long examDate;

    public Exam(String name) {
        this.name = name;
    }
    
    public Exam() {
        
    }

    public Integer getId() {
        return id;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

//    public Date getExamDate() {
//        return examDate;
//    }
//
//    public void setExamDate(Date examDate) {
//        this.examDate = examDate;
//    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}

