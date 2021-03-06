/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 *  Copyright (C) 2010 thorsten
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.cismet.cids.custom.extensionfactories.wunda_blau;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.objectextension.ObjectExtensionFactory;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten
 * @version  $Revision$, $Date$
 */
public class Poi_locationtypeExtensionFactory_ extends ObjectExtensionFactory {

    //~ Instance fields --------------------------------------------------------

    private final transient org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());

    //~ Methods ----------------------------------------------------------------

    @Override
    public void extend(final CidsBean bean) {
        try {
            String val = "kein Wert gefunden";
            Class.forName("org.postgresql.Driver").newInstance();
            final String url = "jdbc:postgresql://localhost:5432/wunda_demo";
            final Connection conn = DriverManager.getConnection(url, "postgres", "x");
            final Statement stmnt = conn.createStatement();
            final ResultSet rs = stmnt.executeQuery("select leiter from kigaeinrichtung where id="
                            + bean.getProperty("number").toString());
            rs.next();
            final String s = rs.getString(1);
            if (s != null) {
                val = s;
            }
            conn.close();

            bean.setProperty("firstextensiontest", val + " (" + System.currentTimeMillis() + ")");
        } catch (Exception ex) {
            log.error("Error during extension", ex);
        }
    }
}
