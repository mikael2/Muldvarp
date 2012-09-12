/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

/**
 *
 * @author kristoffer
 */
public class News extends Domain {

    public News(String name, String detail) {
        super(name);
        super.detail = detail;
    }
}
