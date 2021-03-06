/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.tostringconverter.wunda_blau;

import de.cismet.cids.annotations.CidsAttribute;

import de.cismet.cids.tools.CustomToStringConverter;

/**
 * de.cismet.cids.toStringConverter.Thema_personToStringConverter.
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public class Thema_personToStringConverter extends CustomToStringConverter {

    //~ Static fields/initializers ---------------------------------------------

    private static final String NULL = "null";

    //~ Instance fields --------------------------------------------------------

    @CidsAttribute("anrede")
    public String anrede = null;
    @CidsAttribute("vorname")
    public String vorname = null;
    @CidsAttribute("name")
    public String name = null;

    final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    //~ Methods ----------------------------------------------------------------

    @Override
    public String createString() {
        String ret = "";
        if ((anrede != null) && !anrede.equalsIgnoreCase(NULL)) {
            ret += anrede;
        }
        if ((vorname != null) && !vorname.equalsIgnoreCase(NULL)) {
            ret += " " + vorname;
        }
        if ((name != null) && !name.equalsIgnoreCase(NULL)) {
            ret += " " + name;
        }
        return ret;
    }
}
