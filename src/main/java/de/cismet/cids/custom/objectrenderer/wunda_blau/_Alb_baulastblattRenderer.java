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
 * Arc_stadtbildRenderer.java
 *
 * Created on 22.06.2009, 14:48:33
 */
package de.cismet.cids.custom.objectrenderer.wunda_blau;

import javax.swing.JComponent;

import de.cismet.cids.custom.objecteditors.wunda_blau.Alb_baulastblattEditor;

import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;

import de.cismet.tools.gui.TitleComponentProvider;

/**
 * DOCUMENT ME!
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public class _Alb_baulastblattRenderer extends Alb_baulastblattEditor implements CidsBeanRenderer,
    TitleComponentProvider {

    //~ Instance fields --------------------------------------------------------

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private String title;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDescBlattnummer;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList lstBaulasten;
    private javax.swing.JList lstBeguenstigteFlurstuecke;
    private javax.swing.JList lstBelasteteFlurstuecke;
    private javax.swing.JPanel panTitle;
    private javax.swing.JPanel panTitleString;
    private javax.swing.JScrollPane scpBaulasten;
    private javax.swing.JScrollPane scpBeguenstigteFlurstuecke;
    private javax.swing.JScrollPane scpBelasteteFlurstuecke;
    private de.cismet.cids.custom.objectrenderer.converter.SQLDateToStringConverter sqlDateToStringConverter;
    private de.cismet.cids.editors.converters.SqlDateToUtilDateConverter sqlDateToUtilDateConverter;
    private javax.swing.JTextField txtBlattnummer;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form Arc_stadtbildRenderer.
     */
    public _Alb_baulastblattRenderer() {
        super(false);
        initComponents();
        title = "";
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        sqlDateToUtilDateConverter = new de.cismet.cids.editors.converters.SqlDateToUtilDateConverter();
        sqlDateToStringConverter = new de.cismet.cids.custom.objectrenderer.converter.SQLDateToStringConverter();
        panTitle = new javax.swing.JPanel();
        panTitleString = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblDescBlattnummer = new javax.swing.JLabel();
        txtBlattnummer = new javax.swing.JTextField();
        scpBaulasten = new javax.swing.JScrollPane();
        lstBaulasten = new javax.swing.JList();
        scpBeguenstigteFlurstuecke = new javax.swing.JScrollPane();
        lstBeguenstigteFlurstuecke = new javax.swing.JList();
        scpBelasteteFlurstuecke = new javax.swing.JScrollPane();
        lstBelasteteFlurstuecke = new javax.swing.JList();

        panTitle.setOpaque(false);
        panTitle.setLayout(new java.awt.BorderLayout());

        panTitleString.setOpaque(false);
        panTitleString.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18));
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("TITLE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panTitleString.add(lblTitle, gridBagConstraints);

        panTitle.add(panTitleString, java.awt.BorderLayout.CENTER);

        setMinimumSize(new java.awt.Dimension(100, 100));
        setPreferredSize(new java.awt.Dimension(100, 100));
        setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setOpaque(false);
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lblDescBlattnummer.setText("Blattnummer:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(lblDescBlattnummer, gridBagConstraints);

        txtBlattnummer.setText("jTextField1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(txtBlattnummer, gridBagConstraints);

        lstBaulasten.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });
        scpBaulasten.setViewportView(lstBaulasten);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(scpBaulasten, gridBagConstraints);

        lstBeguenstigteFlurstuecke.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });
        scpBeguenstigteFlurstuecke.setViewportView(lstBeguenstigteFlurstuecke);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(scpBeguenstigteFlurstuecke, gridBagConstraints);

        lstBelasteteFlurstuecke.setModel(new javax.swing.AbstractListModel() {

                String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };

                @Override
                public int getSize() {
                    return strings.length;
                }
                @Override
                public Object getElementAt(final int i) {
                    return strings[i];
                }
            });
        scpBelasteteFlurstuecke.setViewportView(lstBelasteteFlurstuecke);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel1.add(scpBelasteteFlurstuecke, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(final String title) {
        this.title = "Baulastblatt " + title;
        lblTitle.setText(this.title);
    }

    @Override
    public JComponent getTitleComponent() {
        return panTitle;
    }
}
