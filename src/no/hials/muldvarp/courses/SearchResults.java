/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.courses;

import java.util.List;

/**
 *
 * @author kristoffer
 */
public class SearchResults {
    public List<Course> course;

    public List getResults() {
        return course;
    }

    public void setResults(List results) {
        this.course = results;
    }
}
