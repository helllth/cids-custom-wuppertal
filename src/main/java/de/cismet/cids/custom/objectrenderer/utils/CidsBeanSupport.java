/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.cismet.cids.custom.objectrenderer.utils;

import Sirius.server.middleware.types.MetaClass;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.utils.ClassCacheMultiple;
import java.util.Collection;

/**
 *
 * @author srichter
 */
public final class CidsBeanSupport {

    private CidsBeanSupport() {
        throw new AssertionError();
    }
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(CidsBeanSupport.class);
    public static final String DOMAIN_NAME = "WUNDA_BLAU";

    public static final CidsBean createNewCidsBeanFromTableName(final String tableName) throws Exception {
        if (tableName != null) {
            final MetaClass metaClass = ClassCacheMultiple.getMetaClass(DOMAIN_NAME, tableName);
            if (metaClass != null) {
                return metaClass.getEmptyInstance().getBean();
            }
        }
        throw new Exception("Could not find MetaClass for table " + tableName);
    }

    public static final Collection<CidsBean> getBeanCollectionFromProperty(CidsBean bean, String collectionProperty) {
        if (bean != null && collectionProperty != null) {
            final Object colObj = bean.getProperty(collectionProperty);
            if (colObj instanceof Collection) {
                return (Collection<CidsBean>) colObj;
            }
        }
        return null;
    }

}