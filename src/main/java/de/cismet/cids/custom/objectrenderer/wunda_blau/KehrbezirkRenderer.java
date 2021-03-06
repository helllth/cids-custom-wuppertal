/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wunda_blau;

import com.vividsolutions.jts.geom.Geometry;

import org.apache.log4j.Logger;

import de.cismet.cids.annotations.CidsAttribute;

import de.cismet.cids.custom.deprecated.JLoadDots;

import de.cismet.cids.tools.metaobjectrenderer.BlurredMapObjectRenderer;

/**
 * de.cismet.cids.objectrenderer.CoolKehrbezirkRenderer.
 *
 * @author   nh
 * @version  $Revision$, $Date$
 */
public class KehrbezirkRenderer extends BlurredMapObjectRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final String TITLE = "Kehrbezirk";

    //~ Instance fields --------------------------------------------------------

    @CidsAttribute("K_BEZIRK")
    public String kehrbezirk = "";

    @CidsAttribute("AUSDEHNUNG.GEO_STRING")
    public Geometry geometry = null;
    private final Logger log = Logger.getLogger(this.getClass());

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panInter;
    private javax.swing.JPanel panMap;
    private javax.swing.JPanel panSpinner;
    private javax.swing.JPanel panTitle;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form CoolKehrbezirkRenderer.
     */
    public KehrbezirkRenderer() {
        initComponents();
        setPanContent(panContent);
        setPanInter(null);
        setPanMap(panMap);
        setPanTitle(panTitle);
        setSpinner(panSpinner);
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void assignSingle() {
        if (geometry != null) {
            setGeometry(geometry);
        }

        if (kehrbezirk != null) {
            lblTitle.setText(TITLE + " - " + kehrbezirk);
        } else {
            lblTitle.setText(TITLE);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        panContent = new javax.swing.JPanel();
        panInter = new javax.swing.JPanel();
        panMap = new javax.swing.JPanel();
        panSpinner = new JLoadDots();

        setMinimumSize(new java.awt.Dimension(420, 250));
        setPreferredSize(new java.awt.Dimension(420, 300));
        setLayout(new java.awt.BorderLayout());

        panTitle.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18));
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Kehrbezirk - 4");

        final javax.swing.GroupLayout panTitleLayout = new javax.swing.GroupLayout(panTitle);
        panTitle.setLayout(panTitleLayout);
        panTitleLayout.setHorizontalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panTitleLayout.createSequentialGroup().addContainerGap().addComponent(lblTitle).addContainerGap(
                    282,
                    Short.MAX_VALUE)));
        panTitleLayout.setVerticalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
                panTitleLayout.createSequentialGroup().addContainerGap().addComponent(lblTitle).addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE)));

        add(panTitle, java.awt.BorderLayout.NORTH);

        panContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 0, 0));
        panContent.setOpaque(false);
        panContent.setLayout(new java.awt.BorderLayout());
        add(panContent, java.awt.BorderLayout.WEST);

        panInter.setOpaque(false);
        panInter.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 10));
        add(panInter, java.awt.BorderLayout.SOUTH);

        panMap.setMinimumSize(new java.awt.Dimension(100, 230));
        panMap.setOpaque(false);
        panMap.setLayout(new java.awt.GridBagLayout());

        panSpinner.setMaximumSize(new java.awt.Dimension(100, 100));
        panSpinner.setMinimumSize(new java.awt.Dimension(100, 100));
        panSpinner.setOpaque(false);

        final javax.swing.GroupLayout panSpinnerLayout = new javax.swing.GroupLayout(panSpinner);
        panSpinner.setLayout(panSpinnerLayout);
        panSpinnerLayout.setHorizontalGroup(
            panSpinnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                100,
                Short.MAX_VALUE));
        panSpinnerLayout.setVerticalGroup(
            panSpinnerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                100,
                Short.MAX_VALUE));

        panMap.add(panSpinner, new java.awt.GridBagConstraints());

        add(panMap, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents
}
