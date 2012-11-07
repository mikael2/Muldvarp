/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.utility;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.DetailActivity;
import no.hials.muldvarp.v2.QuizActivity;
import no.hials.muldvarp.v2.TopActivity;
import no.hials.muldvarp.v2.database.MuldvarpDataSource;
import no.hials.muldvarp.v2.domain.*;

/**
 *  Temporary class to provide already formatted data based on String arrays in XML files.
 *  Some database functionality implemented.
 * 
 * @author johan
 */
public class DummyDataProvider {
    
    MuldvarpDataSource muldvarpDataSource;
    
    public static ArrayList<Domain> requestProgrammes(Context context) {    
        
        return getProgrammesFromDB(context);
    }
    
    public static ArrayList<Domain> getProgrammesFromDB(Context context) {    
        
        MuldvarpDataSource muldvarpDataSource = new MuldvarpDataSource(context);
        muldvarpDataSource.open();
        makeAndInsertProgrammes(muldvarpDataSource, context);
        
        return muldvarpDataSource.getAllProgrammes();
    }
    
    public static ArrayList<Domain> requestCoursesbyProgrammeFromDB(Context context, Programme programme) {    
        
        MuldvarpDataSource muldvarpDataSource = new MuldvarpDataSource(context);
        muldvarpDataSource.open();
        makeAndInsertCourses(muldvarpDataSource, context);
        
        return muldvarpDataSource.getCoursesByProgramme(programme);
    }
    
    public static void makeAndInsertProgrammes(MuldvarpDataSource muldvarpDataSource, Context context) {    
                
        //Get arraylist from XML resource, create Programme objects and place them in an array.
        String[] tempList = context.getResources().getStringArray(R.array.programme_list_dummmy);   
        
        if (tempList != null) {
            
            for (int i = 0; i < tempList.length; i++) {
             
                Programme currentProgram = new Programme(tempList[i]);
                currentProgram.setId(i*2);
                currentProgram.setRevision(i);
                currentProgram.setActivity(TopActivity.class);
                muldvarpDataSource.insertProgramme(currentProgram);

                //debug check:
                System.out.println("Created:" + currentProgram.getName());
            }
        }       
    }
    
    //Return list of programmes. Should be rewritten to just accept something else than a context.
    public static ArrayList<Domain> getProgrammeList(Context context) {
        ArrayList<Domain> programmeList = new ArrayList<Domain>();
        
        //Get arraylist from XML resource, create Programme objects and place them in an array.
        String[] tempList = context.getResources().getStringArray(R.array.programme_list_dummmy);   
        
        if (tempList != null) {            
            for (int i = 0; i < tempList.length; i++) {             
                Programme currentProgram = new Programme(tempList[i]);
                currentProgram.setActivity(TopActivity.class);
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
    
    public static void makeAndInsertCourses(MuldvarpDataSource muldvarpDataSource, Context context) {    
                
        //Get arraylist from XML resource, create Programme objects and place them in an array.
        String[] tempList = context.getResources().getStringArray(R.array.course_list_dummy);   
        
        if (tempList != null) {
            
            for (int i = 0; i < tempList.length; i++) {
             
                Course currentCourse = new Course(tempList[i]);
                currentCourse.setId(i*2);
                currentCourse.setRevision(i);
                currentCourse.setActivity(TopActivity.class);
                long insertCourse = muldvarpDataSource.insertCourse(currentCourse);
                muldvarpDataSource.createProgrammeCourseRelation(1, insertCourse);

                //debug check:
                System.out.println("Created:" + currentCourse.getName());
            }
        }       
    }
    
    public static ArrayList<Domain> getCourseList(Context context) {
        
        ArrayList<Domain> courseList = new ArrayList<Domain>();
        
        //Get arraylist from XML resource, create Programme objects and place them in an array.
        String[] tempList = context.getResources().getStringArray(R.array.course_list_dummy);   
        
        if (tempList != null) {
            for (int i = 0; i < tempList.length; i++) {
                
                Course currentCourse = new Course(tempList[i]);
                currentCourse.setActivity(TopActivity.class);
                courseList.add(currentCourse);

                //debug check:
                System.out.println("Created:" + currentCourse.getName());
            }
        }       
        
        //Check if list wasn't empty just to be sure, and generate dumb data if not
        if(courseList.isEmpty()) {
            for (int n = 0; n < 10; n++) {
                
                Course dumbCourse = new Course("Course " + n);
                courseList.add(dumbCourse);
            }
        }
        
        return courseList;
    }
    
    public static ArrayList<Domain> getTopicList(Context context) {
        
        ArrayList<Domain> topicList = new ArrayList<Domain>();
        
        //Get arraylist from XML resource, create Programme objects and place them in an array.
        String[] tempList = context.getResources().getStringArray(R.array.topic_list_dummy);   
        
        if (tempList != null) {
            for (int i = 0; i < tempList.length; i++) {
                
                Task currentTopic = new Task(tempList[i]);
                currentTopic.setActivity(TopActivity.class);
                topicList.add(currentTopic);

                //debug check:
                System.out.println("Created:" + currentTopic.getName());
            }
        }       
        
        //Check if list wasn't empty just to be sure, and generate dumb data if not
        if(topicList.isEmpty()) {            
            for (int n = 0; n < 10; n++) {
                
                Task dumbTopic = new Task("Topic " + n);
                topicList.add(dumbTopic);
            }
        }
        
        return topicList;
    }
    
    public static ArrayList<Domain> getQuizList() {
        
        //Setup questions
        ArrayList<Question> questions = new ArrayList<Question>();
        for (int i = 1; i < 4; i++) {
            
            List<Alternative> alternatives = new ArrayList<Alternative>();
            for (int n = 1; n < 6; n++) {
                Alternative currentAlternative = new Alternative("Svaralternativ " + n);
                currentAlternative.setId(i);
                alternatives.add(currentAlternative);
            }
            
            Question currentQuestion;
            if (i % 2 == 0) {
                currentQuestion = new Question("Hvilket svaralternativ er riktig?", alternatives, Question.QuestionType.SINGLE);
            } else {
                currentQuestion = new Question("Hvilket svaralternativ er riktig?", alternatives, Question.QuestionType.MULTIPLE);
            }
            currentQuestion.setId(i);
            questions.add(currentQuestion);            
        }       
        
        //Create quizzes and add in the same questions
        ArrayList<Domain> quizzes = new ArrayList<Domain>();
        Quiz shitQuiz = new Quiz("drittquiz");
        shitQuiz.setActivity(QuizActivity.class);
        shitQuiz.setDescription(">2012\n>lage en quiz uten spørsmål\nhåper virkelig at det er ingen som gjør dette");
        quizzes.add(shitQuiz);
        for (int n = 0; n < 10; n++) {
            
            Quiz tempQuiz = new Quiz("Quiz no."+n);
            tempQuiz.setQuestions(questions);
            tempQuiz.setActivity(QuizActivity.class);
            quizzes.add(tempQuiz);
        }
        
        return quizzes;
    }    
    
    public static ArrayList<Domain> getDocumentList(Context context) {
        
        ArrayList<Domain> documentList = new ArrayList<Domain>();
        
        //Get arraylist from XML resource, create Programme objects and place them in an array.
        String[] tempList = context.getResources().getStringArray(R.array.document_list_dummy);   
        
        if (tempList != null) {
            for (int i = 0; i < tempList.length; i++) {
                
                Document currentDocument = new Document(tempList[i], "Lorem Ipsum bla bla bla! HØASDJLSAHDOISAHD!");
                currentDocument.setActivity(DetailActivity.class);
                documentList.add(currentDocument);

                //debug check:
                System.out.println("Created:" + currentDocument.getName());
            }
        }       
        
        //Check if list wasn't empty just to be sure, and generate dumb data if not
        if(documentList.isEmpty()) {            
            for (int n = 0; n < 10; n++) {
                
                Task dumbTopic = new Task("Topic " + n);
                documentList.add(dumbTopic);
            }
        }
        
        return documentList;
    }
    
    
}
