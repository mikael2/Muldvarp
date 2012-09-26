/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.utility;

import android.content.Context;
import java.util.ArrayList;
import java.util.List;
import no.hials.muldvarp.R;
import no.hials.muldvarp.v2.QuizActivity;
import no.hials.muldvarp.v2.TopActivity;
import no.hials.muldvarp.v2.database.MuldvarpDataSource;
import no.hials.muldvarp.v2.domain.*;

/**
 *  Temporary class to provide already formatted data based on String arrays in XML files.
 *  This is only temporary, proper database solution is needed.
 * 
 * @author johan
 */
public class DummyDataProvider {
    
    MuldvarpDataSource muldvarpDataSource;
    
    public static ArrayList<Domain> getFromDatabase(Context context) {    
        MuldvarpDataSource muldvarpDataSource = new MuldvarpDataSource(context);
        muldvarpDataSource.open();
        
        muldvarpDataSource.createprogramme("hurrrrrr5");
        muldvarpDataSource.createprogramme("hurrrrrr4");
        muldvarpDataSource.createprogramme("hurrrrrr3");
        muldvarpDataSource.createprogramme("hurrrrrr2");
        muldvarpDataSource.createprogramme("hurrrrrr1");
        
        return muldvarpDataSource.getAllProgrammes();
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
    
    public static ArrayList<Domain> getCourseList(Context context) {
        ArrayList<Domain> courseList = new ArrayList<Domain>();
        
        //Get arraylist from XML resource, create Programme objects and place them in an array.
        String[] tempList = context.getResources().getStringArray(R.array.course_list_dummy);   
        
        if (tempList != null) {
            for (int i = 0; i < tempList.length; i++) {
                Course currentCourse = new Course(tempList[i]);
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
            
            Alternative correctAnswer = new Alternative("Svaralternativ " + i);
            
            Question currentQuestion = new Question("Hvilket svaralternativ er riktig?", alternatives, correctAnswer);
            questions.add(currentQuestion);
            
        }       
        
        //Create quizzes and add in the same questions
        ArrayList<Domain> quizzes = new ArrayList<Domain>();
        for (int n = 0; n < 10; n++) {
            
            Quiz tempQuiz = new Quiz("Quiz no."+n);
            tempQuiz.setQuestions(questions);
            tempQuiz.setActivity(QuizActivity.class);
            quizzes.add(tempQuiz);
        }
        
        return quizzes;
    }
    
}
