/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package no.hials.muldvarp.v2.domain;

/**
 *
 * @author kristoffer
 */
public class Document extends Domain {

    public Document(String name, String detail) {
        super(name);
        super.detail = detail;
    }
    
}
