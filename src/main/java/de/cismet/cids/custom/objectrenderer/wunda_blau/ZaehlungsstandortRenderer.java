/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
package de.cismet.cids.custom.objectrenderer.wunda_blau;

import com.vividsolutions.jts.geom.Geometry;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.image.BufferedImage;

import java.sql.Timestamp;

import java.text.DateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import de.cismet.cids.annotations.CidsAttribute;

import de.cismet.cids.client.tools.ReportLookupButton;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.tools.metaobjectrenderer.BlurredMapObjectRenderer;

/**
 * de.cismet.cids.objectrenderer.CoolPassantenfrequenzRenderer.
 *
 * @author   nh
 * @version  $Revision$, $Date$
 */
public class ZaehlungsstandortRenderer extends BlurredMapObjectRenderer {

    //~ Static fields/initializers ---------------------------------------------

    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(
            "de.cismet.cids.objectrenderer.CoolPassantenfrequenzRenderer");
    private static final String SONNE = "sonne";
    private static final String LEICHTER_BEW = "leichter bewoelkt";
    private static final String LEICHT_BEW = "leicht bewoelkt";
    private static final String BEWOELKT = "bewoelkt";
    private static final String STARK_BEW = "stark bewoelkt";
    private static final String REGEN = "regen";
    private static final String GEWITTER = "gewitter";
    private static final String WECHSELHAFT = "wechselhaft";
    private static final String SCHNEE = "schnee";
    private static final String SCHNEE_WECHSEL = "schnee wechselhaft";
    private static final Color COLOR_MO = new Color(255, 255, 0);
    private static final Color COLOR_DI = new Color(255, 150, 0);
    private static final Color COLOR_MI = new Color(200, 0, 0);
    private static final Color COLOR_DO = new Color(180, 50, 180);
    private static final Color COLOR_FR = new Color(0, 0, 230);
    private static final Color COLOR_SA = new Color(0, 170, 0);
    private static final Color COLOR_SO = new Color(50, 50, 50);
    private static final Paint LIGHT_MO = new GradientPaint(0.0f, 0.0f, COLOR_MO, 0.0f, 0.0f, new Color(255, 255, 180));
    private static final Paint LIGHT_DI = new GradientPaint(0.0f, 0.0f, COLOR_DI, 0.0f, 0.0f, new Color(255, 220, 180));
    private static final Paint LIGHT_MI = new GradientPaint(0.0f, 0.0f, COLOR_MI, 0.0f, 0.0f, new Color(240, 175, 150));
    private static final Paint LIGHT_DO = new GradientPaint(0.0f, 0.0f, COLOR_DO, 0.0f, 0.0f, new Color(230, 190, 230));
    private static final Paint LIGHT_FR = new GradientPaint(0.0f, 0.0f, COLOR_FR, 0.0f, 0.0f, new Color(175, 175, 250));
    private static final Paint LIGHT_SA = new GradientPaint(0.0f, 0.0f, COLOR_SA, 0.0f, 0.0f, new Color(150, 210, 150));
    private static final Paint LIGHT_SO = new GradientPaint(0.0f, 0.0f, COLOR_SO, 0.0f, 0.0f, new Color(180, 180, 180));
    private static final String STRING_MO = "Mo";
    private static final String STRING_DI = "Tu";
    private static final String STRING_MI = "We";
    private static final String STRING_DO = "Th";
    private static final String STRING_FR = "Fr";
    private static final String STRING_SA = "Sa";
    private static final String STRING_SO = "Su";
    public static final String TITLE = "Passantenfrequenzz\u00E4hlung";

    //~ Instance fields --------------------------------------------------------

    @CidsAttribute("Standpunkt")
    public Integer standpunkt;
    @CidsAttribute("Lage")
    public String lage;
    @CidsAttribute("Zählbezirk")
    public Integer bezirk;
    @CidsAttribute("Stadtbezirk.Stadtbezirk")
    public String stadtteil;
    @CidsAttribute("Zählungen[].Zählung.Datum")
    public Vector<Timestamp> datum = new Vector();
    @CidsAttribute("Zählungen[].Zählung.Anzahl")
    public Vector<Integer> anzahl = new Vector();
    @CidsAttribute("Zählungen[].Zählung.Ereignis")
    public Vector<String> ereignis = new Vector();
    @CidsAttribute("Zählungen[].Zählung.Wetter.Wetter")
    public Vector<String> wetter = new Vector();
    @CidsAttribute("Georeferenz.GEO_STRING")
    public Geometry geom;
    private final ImageIcon ICON_SONNE = new ImageIcon(getClass().getResource("/res/16/sonne.png"));
    private final ImageIcon ICON_LEICHTER_BEW = new ImageIcon(getClass().getResource("/res/16/leichter_bew.png"));
    private final ImageIcon ICON_LEICHT_BEW = new ImageIcon(getClass().getResource("/res/16/leicht_bew.png"));
    private final ImageIcon ICON_BEWOELKT = new ImageIcon(getClass().getResource("/res/16/bewoelkt.png"));
    private final ImageIcon ICON_STARK_BEW = new ImageIcon(getClass().getResource("/res/16/stark_bewoelkt.png"));
    private final ImageIcon ICON_REGEN = new ImageIcon(getClass().getResource("/res/16/regen.png"));
    private final ImageIcon ICON_GEWITTER = new ImageIcon(getClass().getResource("/res/16/gewitter.png"));
    private final ImageIcon ICON_WECHSELHAFT = new ImageIcon(getClass().getResource("/res/16/wechselhaft.png"));
    private final ImageIcon ICON_SCHNEE = new ImageIcon(getClass().getResource("/res/16/schnee.png"));
    private final ImageIcon ICON_SCHNEE_WECHSEL = new ImageIcon(getClass().getResource("/res/16/schnee_wechsel.png"));
    private Vector colNames = new Vector();
    private boolean fillEreignis = true;
    private boolean fillWetter = true;
    private DefaultCategoryDataset dataset;
    private TreeMap<Date, Integer> tmAnzahl = new TreeMap<Date, Integer>();
    private TreeMap<Date, String> tmEreignis = new TreeMap<Date, String>();
    private TreeMap<Date, String> tmWetter = new TreeMap<Date, String>();

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cmdPrint;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblChart;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panInhalt;
    private javax.swing.JPanel panInter;
    private javax.swing.JPanel panLegend;
    private javax.swing.JPanel panMap;
    private javax.swing.JPanel panTitle;
    private javax.swing.JTable tabFrequenzen;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form CoolPassantenfrequenzRenderer.
     */
    public ZaehlungsstandortRenderer() {
        colNames.add("Datum");
        colNames.add("Anzahl/h");
        colNames.add("Ereignis");
        colNames.add("Wetter");
        initComponents();
        setPanContent(panInhalt);
        setPanInter(null);
        setPanMap(null);
        setPanTitle(panTitle);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        panInter = new javax.swing.JPanel();
        panInhalt = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabFrequenzen = new JTable() {

                @Override
                public boolean isCellEditable(final int x, final int y) {
                    return false;
                }
            };
        lblChart = new javax.swing.JLabel();
        panLegend = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        cmdPrint = new ReportLookupButton("PFZReport");
        jPanel1 = new javax.swing.JPanel();
        panMap = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        panInter.setOpaque(false);

        final javax.swing.GroupLayout panInterLayout = new javax.swing.GroupLayout(panInter);
        panInter.setLayout(panInterLayout);
        panInterLayout.setHorizontalGroup(
            panInterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                878,
                Short.MAX_VALUE));
        panInterLayout.setVerticalGroup(
            panInterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                14,
                Short.MAX_VALUE));

        add(panInter, java.awt.BorderLayout.SOUTH);

        panInhalt.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 15, 20, 20));
        panInhalt.setOpaque(false);
        panInhalt.setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setPreferredSize(new java.awt.Dimension(200, 200));

        tabFrequenzen.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                    { null, null }
                },
                new String[] { "Datum", "Anzahl" }) {

                Class[] types = new Class[] { java.lang.String.class, java.lang.Integer.class };
                boolean[] canEdit = new boolean[] { false, false };

                @Override
                public Class getColumnClass(final int columnIndex) {
                    return types[columnIndex];
                }

                @Override
                public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
        tabFrequenzen.setGridColor(new java.awt.Color(153, 153, 153));
        tabFrequenzen.setSelectionBackground(new java.awt.Color(153, 204, 255));
        tabFrequenzen.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tabFrequenzen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 20);
        panInhalt.add(jScrollPane1, gridBagConstraints);

        lblChart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChart.setIcon(new javax.swing.ImageIcon(getClass().getResource("/res/load.png"))); // NOI18N
        lblChart.setPreferredSize(new java.awt.Dimension(500, 300));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 1.0;
        panInhalt.add(lblChart, gridBagConstraints);

        panLegend.setMinimumSize(new java.awt.Dimension(125, 35));
        panLegend.setOpaque(false);
        panLegend.setPreferredSize(new java.awt.Dimension(125, 35));
        panLegend.setLayout(new java.awt.GridBagLayout());

        jLabel4.setText("Mo");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jLabel4, gridBagConstraints);

        jLabel5.setText("Di");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jLabel5, gridBagConstraints);

        jLabel6.setText("Mi");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jLabel6, gridBagConstraints);

        jLabel7.setText("Do");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jLabel7, gridBagConstraints);

        jLabel8.setText("Fr");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jLabel8, gridBagConstraints);

        jLabel9.setText("Sa");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jLabel9, gridBagConstraints);

        jLabel10.setText("So");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jLabel10, gridBagConstraints);

        jPanel8.setBackground(new java.awt.Color(255, 255, 0));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel8.setMaximumSize(new java.awt.Dimension(10, 10));

        final javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 8, Short.MAX_VALUE));
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 8, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jPanel8, gridBagConstraints);

        jPanel9.setBackground(new java.awt.Color(255, 153, 0));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel9.setMaximumSize(new java.awt.Dimension(10, 10));

        final javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 8, Short.MAX_VALUE));
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 8, Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jPanel9, gridBagConstraints);

        jPanel10.setBackground(new java.awt.Color(204, 0, 0));
        jPanel10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel10.setMaximumSize(new java.awt.Dimension(10, 10));

        final javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jPanel10, gridBagConstraints);

        jPanel11.setBackground(new java.awt.Color(180, 50, 180));
        jPanel11.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel11.setMaximumSize(new java.awt.Dimension(10, 10));

        final javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jPanel11, gridBagConstraints);

        jPanel12.setBackground(new java.awt.Color(51, 51, 51));
        jPanel12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel12.setMaximumSize(new java.awt.Dimension(10, 10));

        final javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jPanel12, gridBagConstraints);

        jPanel13.setBackground(new java.awt.Color(0, 170, 0));
        jPanel13.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel13.setMaximumSize(new java.awt.Dimension(10, 10));

        final javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jPanel13, gridBagConstraints);

        jPanel14.setBackground(new java.awt.Color(0, 0, 230));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jPanel14.setMaximumSize(new java.awt.Dimension(10, 10));

        final javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                8,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        panLegend.add(jPanel14, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        panInhalt.add(panLegend, gridBagConstraints);

        add(panInhalt, java.awt.BorderLayout.CENTER);

        panTitle.setOpaque(false);
        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Passantenfrequenzzählung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(20, 20, 20, 0);
        panTitle.add(lblTitle, gridBagConstraints);

        cmdPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/icons/printer.png"))); // NOI18N
        cmdPrint.setBorderPainted(false);
        cmdPrint.setContentAreaFilled(false);
        cmdPrint.setFocusPainted(false);
        cmdPrint.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cmdPrintActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 22);
        panTitle.add(cmdPrint, gridBagConstraints);

        jPanel1.setMaximumSize(new java.awt.Dimension(10, 10));
        jPanel1.setMinimumSize(new java.awt.Dimension(10, 10));
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(10, 10));
        jPanel1.setSize(new java.awt.Dimension(10, 10));

        final javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                551,
                Short.MAX_VALUE));
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(
                0,
                10,
                Short.MAX_VALUE));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        panTitle.add(jPanel1, gridBagConstraints);

        add(panTitle, java.awt.BorderLayout.NORTH);

        panMap.setOpaque(false);
        panMap.setLayout(new java.awt.GridBagLayout());
        add(panMap, java.awt.BorderLayout.EAST);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cmdPrintActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cmdPrintActionPerformed
    }                                                                            //GEN-LAST:event_cmdPrintActionPerformed
    /**
     * DOCUMENT ME!
     */
    @Override
    public void assignSingle() {
        if (geom != null) {
            setGeometry(geom);
        }

        final CidsBean bean = MetaObject.getBean();
        ((ReportLookupButton)(cmdPrint)).setBean(bean);

        if ((stadtteil != null) && !stadtteil.equals("")) {
            lblTitle.setText(lblTitle.getText() + " - " + stadtteil);
        }

        if (bezirk != null) {
            lblTitle.setText(lblTitle.getText() + " (Bezirk " + bezirk.toString());
        }

        if (standpunkt != null) {
            lblTitle.setText(lblTitle.getText() + ", Standort " + standpunkt.toString() + ")");
        } else {
            lblTitle.setText(lblTitle.getText() + ")");
        }

        if ((wetter == null) || (wetter.size() == 0)) {
            fillWetter = false;
            colNames.remove("Wetter");
        }

        if ((ereignis == null) || (ereignis.size() == 0)) {
            fillEreignis = false;
            colNames.remove("Ereignis");
        }

        if ((datum != null) && (anzahl != null) && (datum.size() > 0) && (anzahl.size() > 0)) {
            final Thread t = new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                dataset = new DefaultCategoryDataset();
                                final DefaultTableModel model = new DefaultTableModel(colNames, datum.size() + 1);
                                tabFrequenzen.setModel(model);
                                if (fillWetter && (tabFrequenzen.getColumn("Wetter") != null)) {
                                    tabFrequenzen.getColumn("Wetter").setCellRenderer(new WetterRenderer());
                                }
                                double avg = 0.0d;
                                for (int i = 0; i < datum.size(); i++) {
                                    final Date key = new Date(datum.get(i).getTime());
                                    tmAnzahl.put(key, anzahl.get(i));
                                    if (ereignis.size() > i) {
                                        tmEreignis.put(key, ereignis.get(i));
                                    }
                                    if (wetter.size() > i) {
                                        tmWetter.put(key, wetter.get(i));
                                    }
                                }

                                int i = 0;
                                for (final Date s : tmAnzahl.keySet()) {
                                    if (log.isDebugEnabled()) {
                                        log.debug("KEY=" + s);
                                        log.debug("ANZAHL=" + tmAnzahl.get(s));
                                    }
                                    if (log.isDebugEnabled()) {
                                        log.debug("WETTER=" + tmWetter.get(s));
                                    }
                                    if (log.isDebugEnabled()) {
                                        log.debug("EREIGN=" + tmEreignis.get(s));
                                    }
                                    final int wert = tmAnzahl.get(s) * 12;
                                    avg += wert;
                                    model.setValueAt(
                                        DateFormat.getDateTimeInstance(
                                            DateFormat.SHORT,
                                            DateFormat.SHORT,
                                            Locale.GERMANY).format(s),
                                        i
                                                + 1,
                                        0);
                                    model.setValueAt(wert, i + 1, 1);
                                    if (fillEreignis && (tmEreignis.get(s) != null)) {
                                        model.setValueAt(
                                            tmEreignis.get(s),
                                            i
                                                    + 1,
                                            tabFrequenzen.getColumn("Ereignis").getModelIndex());
                                    }
                                    if (fillWetter && (tmWetter.get(s) != null)) {
                                        model.setValueAt(
                                            tmWetter.get(s),
                                            i
                                                    + 1,
                                            tabFrequenzen.getColumn("Wetter").getModelIndex());
                                    }
//                            String jahr = DateFormat.getDateInstance(DateFormat.YEAR_FIELD, Locale.GERMANY).format(datum.get(i));
//                            jahr = jahr.substring(jahr.length()-4);
                                    dataset.addValue(
                                        wert,
                                        "Daten",
                                        DateFormat.getDateTimeInstance(
                                            DateFormat.SHORT,
                                            DateFormat.SHORT,
                                            Locale.GERMANY).format(s));
                                    i++;
                                }
                                avg = avg / datum.size();
                                model.setValueAt("Durchschnitt \u00D8", 0, 0);
                                model.setValueAt(Math.round(avg), 0, 1);
                                final JFreeChart chart = createChart(dataset, avg);
                                chart.setBackgroundPaint(new Color(200, 200, 200));

                                final BufferedImage icon = chart.createBufferedImage(500, 300);
                                EventQueue.invokeLater(new Runnable() {

                                        @Override
                                        public void run() {
                                            lblChart.setIcon(new ImageIcon(icon));
                                        }
                                    });
                            } catch (Exception exception) {
                                log.error("FEHLER", exception);
                            }
                        }
                    });
            t.start();
        }
    }

    /**
     * DOCUMENT ME!
     */
    @Override
    public void assignAggregation() {
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public double getWidthRatio() {
        return 1.0;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  geometry  DOCUMENT ME!
     */
    @Override
    public void setGeometry(final Geometry geometry) {
        super.setGeometry(geometry);
    }

    /**
     * Erzeugt ein Diagramm f\u00FCr Passantenfrequenzen.
     *
     * @param   dataset  anzuzeigende Daten
     * @param   average  DOCUMENT ME!
     *
     * @return  JFreeChart-Objekt
     */
    JFreeChart createChart(final CategoryDataset dataset, final double average) {
        final JFreeChart chart = ChartFactory.createBarChart3D(
                null,
                null,
                null,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        final CategoryPlot plot = chart.getCategoryPlot();
        final CustomBarRenderer renderer = new CustomBarRenderer();
        plot.setRenderer(renderer);

        // X-Achsen Label nicht anzeigen
        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setVisible(true);
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        final ValueMarker markerAvg = new ValueMarker(average,
                new Color(100, 100, 255),
                new BasicStroke(2.5f));

//        // add a category marker
//        CategoryMarker marker = new CategoryMarker("2007",
//                new Color(0, 0, 255, 25), new BasicStroke(1.0f));
//        marker.setDrawAsLine(false);
////        marker.setLabel("Marker Label");
////        marker.setLabelFont(new Font("Dialog", Font.PLAIN, 11));
////        marker.setLabelTextAnchor(TextAnchor.TOP_RIGHT);
////        marker.setLabelOffset(new RectangleInsets(2, 5, 2, 5));
//        plot.addDomainMarker(marker, Layer.BACKGROUND);

        plot.addRangeMarker(markerAvg, Layer.BACKGROUND);
        final NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setVisible(true);
        rangeAxis.setAutoRange(true);
        return chart;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class WetterRenderer extends DefaultTableCellRenderer {

        //~ Methods ------------------------------------------------------------

        /**
         * DOCUMENT ME!
         *
         * @param   table       DOCUMENT ME!
         * @param   value       DOCUMENT ME!
         * @param   isSelected  DOCUMENT ME!
         * @param   hasFocus    DOCUMENT ME!
         * @param   row         DOCUMENT ME!
         * @param   column      DOCUMENT ME!
         *
         * @return  DOCUMENT ME!
         */
        @Override
        public Component getTableCellRendererComponent(final JTable table,
                final Object value,
                final boolean isSelected,
                final boolean hasFocus,
                final int row,
                final int column) {
            try {
                if (value != null) {
                    final String wetter = value.toString().toLowerCase();
                    if (wetter.equals(SONNE)) {
                        return new JLabel(ICON_SONNE);
                    } else if (wetter.equals(LEICHTER_BEW)) {
                        return new JLabel(ICON_LEICHTER_BEW);
                    } else if (wetter.equals(LEICHT_BEW)) {
                        return new JLabel(ICON_LEICHT_BEW);
                    } else if (wetter.equals(WECHSELHAFT)) {
                        return new JLabel(ICON_WECHSELHAFT);
                    } else if (wetter.equals(BEWOELKT)) {
                        return new JLabel(ICON_BEWOELKT);
                    } else if (wetter.equals(STARK_BEW)) {
                        return new JLabel(ICON_STARK_BEW);
                    } else if (wetter.equals(REGEN)) {
                        return new JLabel(ICON_REGEN);
                    } else if (wetter.equals(GEWITTER)) {
                        return new JLabel(ICON_GEWITTER);
                    } else if (wetter.equals(SCHNEE)) {
                        return new JLabel(ICON_SCHNEE);
                    } else if (wetter.equals(SCHNEE_WECHSEL)) {
                        return new JLabel(ICON_SCHNEE_WECHSEL);
                    } else {
                        return new JLabel("");
                    }
                }
            } catch (Exception exception) {
                log.warn("Wetterrendererfehler", exception);
            }
//            return new JLabel("");
            return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    class CustomBarRenderer extends BarRenderer {

        //~ Instance fields ----------------------------------------------------

        private boolean paintLight = false;

        //~ Constructors -------------------------------------------------------

        /**
         * public CustomBarRenderer3D(double xOffset, double yOffset) { super(xOffset, yOffset); }.
         */
        public CustomBarRenderer() {
        }

        //~ Methods ------------------------------------------------------------

        /**
         * Returns the paint for an item. Overrides the default behaviour inherited from AbstractSeriesRenderer.
         *
         * @param   row     the series.
         * @param   column  the category.
         *
         * @return  The item color.
         */
        @Override
        public Paint getItemPaint(final int row, final int column) {
            try {
                final CategoryDataset dataset = getPlot().getDataset();
                final String key = (String)dataset.getColumnKey(column);
                final Date date = DateFormat.getInstance().parse(key);
                final Date noon = new Date(date.getTime());
                noon.setHours(12);
                noon.setMinutes(0);
                noon.setSeconds(0);
                if (date.before(noon)) {
                    paintLight = true;
                } else {
                    paintLight = false;
                }
                if (date.toString().startsWith(STRING_MO)) {
                    if (paintLight) {
                        return LIGHT_MO;
                    } else {
                        return COLOR_MO;
                    }
                } else if (date.toString().startsWith(STRING_DI)) {
                    if (paintLight) {
                        return LIGHT_DI;
                    } else {
                        return COLOR_DI;
                    }
                } else if (date.toString().startsWith(STRING_MI)) {
                    if (paintLight) {
                        return LIGHT_MI;
                    } else {
                        return COLOR_MI;
                    }
                } else if (date.toString().startsWith(STRING_DO)) {
                    if (paintLight) {
                        return LIGHT_DO;
                    } else {
                        return COLOR_DO;
                    }
                } else if (date.toString().startsWith(STRING_FR)) {
                    if (paintLight) {
                        return LIGHT_FR;
                    } else {
                        return COLOR_FR;
                    }
                } else if (date.toString().startsWith(STRING_SA)) {
                    if (paintLight) {
                        return LIGHT_SA;
                    } else {
                        return COLOR_SA;
                    }
                } else if (date.toString().startsWith(STRING_SO)) {
                    if (paintLight) {
                        return LIGHT_SO;
                    } else {
                        return COLOR_SO;
                    }
                } else {
                    return Color.BLACK;
                }
            } catch (Exception exception) {
                log.error("FEHLER", exception);
                return Color.BLACK;
            }
        }

//        @Override
//        public Paint getItemOutlinePaint(int arg0, int arg1) {
//            return Color.GRAY;
    }
}
