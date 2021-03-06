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
/*
 * Alb_baulastAggregationRendererPanel.java
 *
 * Created on 04.12.2009, 12:17:34
 */
package de.cismet.cids.custom.objectrenderer.wunda_blau;

import java.util.Collection;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import de.cismet.cids.custom.objectrenderer.utils.CidsBeanSupport;
import de.cismet.cids.custom.objectrenderer.utils.ObjectRendererUtils;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanAggregationRenderer;

import de.cismet.tools.collections.TypeSafeCollections;

/**
 * DOCUMENT ME!
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public class Alb_baulastAggregationRendererPanel extends javax.swing.JPanel implements CidsBeanAggregationRenderer {

    //~ Static fields/initializers ---------------------------------------------

    // Spaltenueberschriften
    private static final String[] AGR_COMLUMN_NAMES = new String[] {
            "Blattnummer",
            "Laufende Nummer",
            "Eintragungsdatum",
            "Befristungsdatum",
            "Geschlossen am",
            "Löschungsdatum",
            "Textblatt",
            "Lageplan",
            "Art"
        };
    // Namen der Properties -> Spalten
    private static final String[] AGR_PROPERTY_NAMES = new String[] {
            "laufende_nummer",
            "eintragungsdatum",
            "befristungsdatum",
            "geschlossen_am",
            "loeschungsdatum",
            "textblatt",
            "lageplan",
            "art"
        };

    //~ Instance fields --------------------------------------------------------

    private Collection<CidsBean> cidsBeans;
    private String title = "";
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane scpAggregationTable;
    private javax.swing.JTable tblAggregation;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form Alb_baulastAggregationRendererPanel.
     */
    public Alb_baulastAggregationRendererPanel() {
        initComponents();
        scpAggregationTable.getViewport().setOpaque(false);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * Extracts the date from a CidsBean into an Object[] -> table row. (Collection attributes are flatened to
     * comaseparated lists)
     *
     * @param   baulastBean    DOCUMENT ME!
     * @param   blBlattnummer  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private Object[] cidsBean2Row(final CidsBean baulastBean, final String blBlattnummer) {
        if (baulastBean != null) {
            final Object[] result = new Object[AGR_COMLUMN_NAMES.length];
            result[0] = blBlattnummer;

            for (int i = 0; i < AGR_PROPERTY_NAMES.length; ++i) {
                result[i + 1] = ObjectRendererUtils.propertyPrettyPrint(baulastBean.getProperty(AGR_PROPERTY_NAMES[i]));
            }
            return result;
        }
        return new Object[0];
    }

    /**
     * DOCUMENT ME!
     *
     * @param  blattBeans  DOCUMENT ME!
     */
    @Override
    public void setCidsBeans(final Collection<CidsBean> blattBeans) {
        if (blattBeans != null) {
            this.cidsBeans = blattBeans;
            final List<Object[]> tableData = TypeSafeCollections.newArrayList();
            for (final CidsBean blattBean : blattBeans) {
                final Collection<CidsBean> baulastenBeans = CidsBeanSupport.getBeanCollectionFromProperty(
                        blattBean,
                        "baulasten");
                for (final CidsBean baulastBean : baulastenBeans) {
                    tableData.add(cidsBean2Row(baulastBean, String.valueOf(blattBean.getProperty("blattnummer"))));
                }
            }
            final DefaultTableModel model = new DefaultTableModel(tableData.toArray(new Object[tableData.size()][]),
                    AGR_COMLUMN_NAMES);
            tblAggregation.setModel(model);
            ObjectRendererUtils.decorateTableWithSorter(tblAggregation);
        }
        setTitle(null);
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public Collection<CidsBean> getCidsBeans() {
        return cidsBeans;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public final int getRowCount() {
        return tblAggregation.getRowCount();
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        scpAggregationTable = new javax.swing.JScrollPane();
        tblAggregation = new javax.swing.JTable() {

                @Override
                public boolean isCellEditable(final int x, final int y) {
                    return false;
                }
            };

        setLayout(new java.awt.BorderLayout());

        scpAggregationTable.setOpaque(false);

        tblAggregation.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {},
                new String[] {}));
        tblAggregation.setOpaque(false);
        scpAggregationTable.setViewportView(tblAggregation);

        add(scpAggregationTable, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  title  DOCUMENT ME!
     */
    @Override
    public void setTitle(final String title) {
        String desc = "Punktliste";
        final Collection<CidsBean> beans = cidsBeans;
        if ((beans != null) && (beans.size() > 0)) {
            desc += " - " + beans.size() + " Punkte ausgewählt";
        }
        this.title = desc;
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
    }
}
