/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

/**
 *
 * @author kristoffer
 */
public class Video extends Domain {

    public Video(String name, String detail) {
        super(name);
        super.detail = detail;
    }
}
