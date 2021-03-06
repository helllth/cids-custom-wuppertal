/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * PrintingWaitDialog.java
 *
 * Created on 3. August 2006, 09:40
 */
package de.cismet.cids.custom.objectrenderer.utils;

import java.awt.EventQueue;

import javax.swing.JFrame;

/**
 * DOCUMENT ME!
 *
 * @author   thorsten.hell@cismet.de
 * @version  $Revision$, $Date$
 */
public class PrintingWaitDialog extends javax.swing.JDialog {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar2;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form PrintingWaitDialog.
     *
     * @param  parent  DOCUMENT ME!
     * @param  modal   DOCUMENT ME!
     */
    public PrintingWaitDialog(final java.awt.Frame parent, final boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setBackground(getBackground());
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(javax.swing.UIManager.getDefaults().getColor("PropSheet.disabledForeground"));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setUndecorated(true);

        jProgressBar2.setBorderPainted(false);
        jProgressBar2.setIndeterminate(true);

        jLabel1.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/tools/metaobjectrenderer/examples/printerBig.png"))); // NOI18N

        jLabel2.setText("Bild wird geladen ...");

        final org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                jPanel1Layout.createSequentialGroup().addContainerGap().add(jLabel1).addPreferredGap(
                    org.jdesktop.layout.LayoutStyle.RELATED).add(
                    jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false).add(
                        jLabel2,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        Short.MAX_VALUE).add(
                        jProgressBar2,
                        org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                        132,
                        Short.MAX_VALUE)).addContainerGap()));
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(
                jPanel1Layout.createSequentialGroup().addContainerGap().add(
                    jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING, false).add(
                        jPanel1Layout.createSequentialGroup().add(jLabel2).addPreferredGap(
                            org.jdesktop.layout.LayoutStyle.RELATED,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE).add(
                            jProgressBar2,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE,
                            org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                            org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).add(jLabel1)).addContainerGap(
                    org.jdesktop.layout.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  args  the command line arguments
     */
    public static void main(final String[] args) {
        EventQueue.invokeLater(new Runnable() {

                @Override
                public void run() {
                    new PrintingWaitDialog(new JFrame(), true).setVisible(true);
                }
            });
    }
}
