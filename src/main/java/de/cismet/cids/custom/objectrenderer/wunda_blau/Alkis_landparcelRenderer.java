/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Alkis_pointRenderer.java
 *
 * Created on 10.09.2009, 15:52:16
 */
package de.cismet.cids.custom.objectrenderer.wunda_blau;

import com.vividsolutions.jts.geom.Geometry;
import de.aedsicad.aaaweb.service.alkis.info.ALKISInfoServices;
import de.aedsicad.aaaweb.service.util.Buchungsblatt;
import de.aedsicad.aaaweb.service.util.LandParcel;
import de.cismet.cids.custom.objectrenderer.utils.ObjectRendererUIUtils;
import de.cismet.cids.custom.objectrenderer.utils.StyleListCellRenderer;
import de.cismet.cids.custom.objectrenderer.utils.alkis.AlkisCommons;
import de.cismet.cids.custom.objectrenderer.utils.alkis.SOAPAccessProvider;
import de.cismet.cids.dynamics.CidsBean;
import de.cismet.cids.tools.metaobjectrenderer.CidsBeanRenderer;
import de.cismet.cismap.commons.BoundingBox;
import de.cismet.cismap.commons.XBoundingBox;
import de.cismet.cismap.commons.features.DefaultStyledFeature;
import de.cismet.cismap.commons.featureservice.DefaultFeatureServiceFeature;
import de.cismet.cismap.commons.gui.MappingComponent;
import de.cismet.cismap.commons.gui.layerwidget.ActiveLayerModel;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWMS;
import de.cismet.cismap.commons.raster.wms.simple.SimpleWmsGetMapUrl;
import de.cismet.tools.CismetThreadPool;
import de.cismet.tools.collections.TypeSafeCollections;
import de.cismet.tools.gui.BorderProvider;
import de.cismet.tools.gui.FooterComponentProvider;
import de.cismet.tools.gui.RoundedPanel;
import de.cismet.tools.gui.SemiRoundedPanel;
import de.cismet.tools.gui.TitleComponentProvider;
import edu.umd.cs.piccolo.event.PBasicInputEventHandler;
import edu.umd.cs.piccolo.event.PInputEvent;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import org.jdesktop.swingx.graphics.ReflectionRenderer;

/**
 * TODO!!!!
 * @author srichter
 */
public class Alkis_landparcelRenderer extends javax.swing.JPanel implements BorderProvider, CidsBeanRenderer, TitleComponentProvider, FooterComponentProvider {
    
    private static final String BUCHUNGSBLATT_TABLENAME = "ALKIS_BUCHUNGSBLATT";
    private static final String ICON_RES_PACKAGE = "/de/cismet/cids/custom/wunda_blau/res/";
    private static final String ALKIS_RES_PACKAGE = ICON_RES_PACKAGE + "alkis/";
    private static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Alkis_landparcelRenderer.class);
    private static final String CARD_1 = "CARD_1";
    private static final String CARD_2 = "CARD_2";

//    private static final ImageIcon FORWARD_PLAIN;
    private static final ImageIcon FORWARD_PRESSED;
    private static final ImageIcon FORWARD_SELECTED;
//    private static final ImageIcon BACKWARD_PLAIN;
    private static final ImageIcon BACKWARD_PRESSED;
    private static final ImageIcon BACKWARD_SELECTED;
    private static final ImageIcon BUCH_PDF;
    private static final ImageIcon BUCH_HTML;
    private static final ImageIcon BUCH_EIG_PDF;
    private static final ImageIcon BUCH_EIG_HTML;
//    private final Collection<JLabel> retrieveableLabels;
    private final Map<CidsBean, Buchungsblatt> buchungsblaetter;
    private final Map<Object, ImageIcon> productPreviewImages;
    private final CardLayout cardLayout;
    private final MappingComponent map;
    private SOAPAccessProvider soapProvider;
    private ALKISInfoServices infoService;
    private LandParcel landparcel;
    private CidsBean cidsBean;
    private String title;
    private RetrieveBuchungsblaetterWorker retrieveBuchungsblaetterWorker;
    private RetrieveBuchungsblaetterWorker newWorker;
    private PropertyChangeListener workerDoneListender;
    //volatie == changes to this variable by one thread are immediatly visible to
    //other accessing threads.

    static {
        final ReflectionRenderer reflectionRenderer = new ReflectionRenderer(0.5f, 0.15f, false);
        BACKWARD_SELECTED = new ImageIcon(Object.class.getResource(ICON_RES_PACKAGE + "arrow-left-sel.png"));
        BACKWARD_PRESSED = new ImageIcon(Object.class.getResource(ICON_RES_PACKAGE + "arrow-left-pressed.png"));

        FORWARD_SELECTED = new ImageIcon(Object.class.getResource(ICON_RES_PACKAGE + "arrow-right-sel.png"));
        FORWARD_PRESSED = new ImageIcon(Object.class.getResource(ICON_RES_PACKAGE + "arrow-right-pressed.png"));
        BufferedImage i1 = null, i2 = null, i3 = null, i4 = null;
        try {
            i1 = reflectionRenderer.appendReflection(ImageIO.read(Object.class.getResource(ALKIS_RES_PACKAGE + "buchnachweispdf.png")));
            i2 = reflectionRenderer.appendReflection(ImageIO.read(Object.class.getResource(ALKIS_RES_PACKAGE + "buchnachweishtml.png")));
            i3 = reflectionRenderer.appendReflection(ImageIO.read(Object.class.getResource(ALKIS_RES_PACKAGE + "bucheignachweispdf.png")));
            i4 = reflectionRenderer.appendReflection(ImageIO.read(Object.class.getResource(ALKIS_RES_PACKAGE + "bucheignachweishtml.png")));
        } catch (Exception ex) {
            log.error(ex, ex);
        }
        BUCH_PDF = new ImageIcon(i1);
        BUCH_HTML = new ImageIcon(i2);
        BUCH_EIG_PDF = new ImageIcon(i3);
        BUCH_EIG_HTML = new ImageIcon(i4);

    }

    /** Creates new form Alkis_pointRenderer */
    public Alkis_landparcelRenderer() {
//        retrieveableLabels = TypeSafeCollections.newArrayList();
        buchungsblaetter = TypeSafeCollections.newHashMap();
        productPreviewImages = TypeSafeCollections.newHashMap();
        initSoapServiceAccess();
        initComponents();
        initFooterElements();

        initProductPreview();
        scpInhaltBuchungsblatt.getViewport().setOpaque(false);
        scpLage.getViewport().setOpaque(false);
        blWait.setVisible(false);
        final LayoutManager layoutManager = getLayout();
        if (layoutManager instanceof CardLayout) {
            cardLayout = (CardLayout) layoutManager;
            cardLayout.show(this, CARD_1);
        } else {
            cardLayout = new CardLayout();
            log.error("Alkis_landparcelRenderer exspects CardLayout as major layout manager, but has " + getLayout() + "!");
        }
        lblEnthalteneFlurstuecke.setVisible(false);
        scpBuchungsblattFlurstuecke.setVisible(false);
        lstBuchungsblaetter.setCellRenderer(new StyleListCellRenderer());
        lstBuchungsblattFlurstuecke.setCellRenderer(new StyleListCellRenderer());
        epInhaltBuchungsblatt.addHyperlinkListener(new HyperlinkListener() {

            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                log.fatal("TODO " + e.getDescription() + " " + e.getURL());
            }
        });
        map = new MappingComponent();
        panFlurstueckMap.add(map, BorderLayout.CENTER);
        initEditorPanes();
    }



    private final void initProductPreview() {
        initProductPreviewImages();
        int maxX = 0, maxY = 0;
        for (ImageIcon ii : productPreviewImages.values()) {
            if (ii.getIconWidth() > maxX) {
                maxX = ii.getIconWidth();
            }
            if (ii.getIconHeight() > maxY) {
                maxY = ii.getIconHeight();
            }
        }
        final Dimension previewDim = new Dimension(maxX + 20, maxY + 40);
        setAllDimensions(panProductPreview, previewDim);
    }

    private final void initEditorPanes() {
        //Font and Layout
        final Font font = UIManager.getFont("Label.font");
        final String bodyRule = "body { font-family: " + font.getFamily() + "; " +
                "font-size: " + font.getSize() + "pt; }";
        final String tableRule = "td { padding-right : 15px; }";
        final String tableHeadRule = "th { padding-right : 15px; }";
        final StyleSheet css = ((HTMLEditorKit) epInhaltBuchungsblatt.getEditorKit()).getStyleSheet();
        final StyleSheet css2 = ((HTMLEditorKit) epLage.getEditorKit()).getStyleSheet();
        css.addRule(bodyRule);
        css.addRule(tableRule);
        css.addRule(tableHeadRule);
        css2.addRule(bodyRule);
        //Change scroll behaviour: avoid autoscrolls on setText(...)
        final Caret caret = epInhaltBuchungsblatt.getCaret();
        if (caret instanceof DefaultCaret) {
            final DefaultCaret dCaret = (DefaultCaret) caret;
            dCaret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        }
    }

    private final void initProductPreviewImages() {
        productPreviewImages.put(hlFlurstuecksEigentumsnachweisHtml, BUCH_EIG_HTML);
        productPreviewImages.put(hlFlurstuecksEigentumsnachweisPdf, BUCH_EIG_PDF);
        productPreviewImages.put(hlFlurstuecksnachweisHtml, BUCH_HTML);
        productPreviewImages.put(hlFlurstuecksnachweisPdf, BUCH_PDF);
        final ProductLabelMouseAdaper productListener = new ProductLabelMouseAdaper();
        hlFlurstuecksEigentumsnachweisHtml.addMouseListener(productListener);
        hlFlurstuecksEigentumsnachweisPdf.addMouseListener(productListener);
        hlFlurstuecksnachweisHtml.addMouseListener(productListener);
        hlFlurstuecksnachweisPdf.addMouseListener(productListener);
    }

    /**
     *
     * @param buchungsblattBean
     * @return
     * @throws Exception
     */
    private final Buchungsblatt getBuchungsblatt(CidsBean buchungsblattBean) throws Exception {
        Buchungsblatt buchungsblatt = null;
        if (buchungsblattBean != null) {
            buchungsblatt = buchungsblaetter.get(buchungsblattBean);
            if (buchungsblatt == null) {
                final String buchungsblattcode = String.valueOf(buchungsblattBean.getProperty("buchungsblattcode"));
                if (buchungsblattcode != null && buchungsblattcode.length() > 5) {
                    buchungsblatt = infoService.getBuchungsblatt(soapProvider.getIdentityCard(), soapProvider.getService(), Alkis_buchungsblattRenderer.fixBuchungslattCode(buchungsblattcode));
                    buchungsblaetter.put(buchungsblattBean, buchungsblatt);
                }
            }
        }
        return buchungsblatt;
    }

    private final void setWaiting(boolean waiting) {
        blWait.setVisible(waiting);
        blWait.setBusy(waiting);
    }

    private final boolean isWaiting() {
        return blWait.isBusy();
    }

    private final void initFooterElements() {
        final MouseListener labelForwListener = new FooterLabelMouseAdapter(lblForw);
        final MouseListener btnForwListener = new FooterButtonMouseAdapter(btnForward, FORWARD_SELECTED, FORWARD_PRESSED);
        final MouseListener labelBackwListener = new FooterLabelMouseAdapter(lblBack);
        final MouseListener btnBackwListener = new FooterButtonMouseAdapter(btnBack, BACKWARD_SELECTED, BACKWARD_PRESSED);

        lblForw.addMouseListener(labelForwListener);
        lblForw.addMouseListener(btnForwListener);
        btnForward.addMouseListener(labelForwListener);
        btnForward.addMouseListener(btnForwListener);
        lblBack.addMouseListener(labelBackwListener);
        lblBack.addMouseListener(btnBackwListener);
        btnBack.addMouseListener(labelBackwListener);
        btnBack.addMouseListener(btnBackwListener);
    }

    private final void initSoapServiceAccess() {
        try {
            soapProvider = new SOAPAccessProvider();
            infoService = soapProvider.getAlkisInfoService();
        } catch (Exception ex) {
            log.fatal(ex, ex);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        panTitle = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        blWait = new org.jdesktop.swingx.JXBusyLabel();
        panFooter = new javax.swing.JPanel();
        panButtons = new javax.swing.JPanel();
        panFooterLeft = new javax.swing.JPanel();
        lblBack = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        panFooterRight = new javax.swing.JPanel();
        btnForward = new javax.swing.JButton();
        lblForw = new javax.swing.JLabel();
        panDescription = new javax.swing.JPanel();
        panBuchungEigentum = new RoundedPanel();
        scpBuchungsblaetter = new javax.swing.JScrollPane();
        lstBuchungsblaetter = new javax.swing.JList();
        scpBuchungsblattFlurstuecke = new javax.swing.JScrollPane();
        lstBuchungsblattFlurstuecke = new javax.swing.JList();
        SemiRoundedPanel pan2 = new SemiRoundedPanel();
        pan2.setBackground(Color.DARK_GRAY);
        jPanel6 = pan2;
        jLabel1 = new javax.swing.JLabel();
        lblBuchungsblaetter = new javax.swing.JLabel();
        lblInhalt = new javax.swing.JLabel();
        lblEnthalteneFlurstuecke = new javax.swing.JLabel();
        panInhaltBuchungsblatt = new javax.swing.JPanel();
        scpInhaltBuchungsblatt = new javax.swing.JScrollPane();
        epInhaltBuchungsblatt = new javax.swing.JEditorPane();
        panMainInfo = new RoundedPanel();
        lblLandparcelCode = new javax.swing.JLabel();
        lblDescLandparcelCode = new javax.swing.JLabel();
        lblDescGemeinde = new javax.swing.JLabel();
        lblGemeinde = new javax.swing.JLabel();
        lblDescGemarkung = new javax.swing.JLabel();
        lblGemarkung = new javax.swing.JLabel();
        lblDescLage = new javax.swing.JLabel();
        lblGroesse = new javax.swing.JLabel();
        lblDescGroesse = new javax.swing.JLabel();
        lblDescTatsNutzung = new javax.swing.JLabel();
        lblTatsNutzung = new javax.swing.JLabel();
        SemiRoundedPanel pan = new SemiRoundedPanel();
        pan.setBackground(Color.DARK_GRAY);
        jPanel5 = pan;
        jLabel6 = new javax.swing.JLabel();
        scpLage = new javax.swing.JScrollPane();
        epLage = new javax.swing.JEditorPane();
        panFlurstueckMap = new javax.swing.JPanel();
        panProducts = new javax.swing.JPanel();
        panPdfProducts = new RoundedPanel();
        hlKarte = new org.jdesktop.swingx.JXHyperlink();
        hlFlurstuecksEigentumsnachweisPdf = new org.jdesktop.swingx.JXHyperlink();
        hlFlurstuecksnachweisPdf = new org.jdesktop.swingx.JXHyperlink();
        jPanel1 = new javax.swing.JPanel();
        SemiRoundedPanel srp = new SemiRoundedPanel();
        srp.setBackground(Color.DARK_GRAY);
        jPanel3 = srp;
        jLabel4 = new javax.swing.JLabel();
        panHtmlProducts = new RoundedPanel();
        hlFlurstuecksEigentumsnachweisHtml = new org.jdesktop.swingx.JXHyperlink();
        hlFlurstuecksnachweisHtml = new org.jdesktop.swingx.JXHyperlink();
        jPanel2 = new javax.swing.JPanel();
        srp = new SemiRoundedPanel();
        srp.setBackground(Color.DARK_GRAY);
        jPanel4 = srp;
        jLabel5 = new javax.swing.JLabel();
        panSpacing = new javax.swing.JPanel();
        panProductPreview = new RoundedPanel();
        lblProductPreview = new javax.swing.JLabel();
        SemiRoundedPanel srp2 = new SemiRoundedPanel();
        srp2.setBackground(Color.DARK_GRAY);
        panProductPreviewHead = srp2;
        lblPreviewHead = new javax.swing.JLabel();

        panTitle.setOpaque(false);
        panTitle.setLayout(new java.awt.GridBagLayout());

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18));
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("TITLE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panTitle.add(lblTitle, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 5);
        panTitle.add(blWait, gridBagConstraints);

        panFooter.setOpaque(false);
        panFooter.setLayout(new java.awt.BorderLayout());

        panButtons.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 6, 0));
        panButtons.setOpaque(false);
        panButtons.setLayout(new java.awt.GridBagLayout());

        panFooterLeft.setMaximumSize(new java.awt.Dimension(124, 40));
        panFooterLeft.setMinimumSize(new java.awt.Dimension(124, 40));
        panFooterLeft.setOpaque(false);
        panFooterLeft.setPreferredSize(new java.awt.Dimension(124, 40));
        panFooterLeft.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 10, 5));

        lblBack.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblBack.setForeground(new java.awt.Color(255, 255, 255));
        lblBack.setText("Info");
        lblBack.setEnabled(false);
        lblBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBackMouseClicked(evt);
            }
        });
        panFooterLeft.add(lblBack);

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/wunda_blau/res/arrow-left.png"))); // NOI18N
        btnBack.setBorder(null);
        btnBack.setBorderPainted(false);
        btnBack.setContentAreaFilled(false);
        btnBack.setEnabled(false);
        btnBack.setFocusPainted(false);
        btnBack.setMaximumSize(new java.awt.Dimension(30, 30));
        btnBack.setMinimumSize(new java.awt.Dimension(30, 30));
        btnBack.setPreferredSize(new java.awt.Dimension(30, 30));
        btnBack.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/wunda_blau/res/arrow-left-pressed.png"))); // NOI18N
        btnBack.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/wunda_blau/res/arrow-left-sel.png"))); // NOI18N
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        panFooterLeft.add(btnBack);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panButtons.add(panFooterLeft, gridBagConstraints);

        panFooterRight.setMaximumSize(new java.awt.Dimension(124, 40));
        panFooterRight.setOpaque(false);
        panFooterRight.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        btnForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/wunda_blau/res/arrow-right.png"))); // NOI18N
        btnForward.setBorder(null);
        btnForward.setBorderPainted(false);
        btnForward.setContentAreaFilled(false);
        btnForward.setFocusPainted(false);
        btnForward.setMaximumSize(new java.awt.Dimension(30, 30));
        btnForward.setMinimumSize(new java.awt.Dimension(30, 30));
        btnForward.setPreferredSize(new java.awt.Dimension(30, 30));
        btnForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForwardActionPerformed(evt);
            }
        });
        panFooterRight.add(btnForward);

        lblForw.setFont(new java.awt.Font("Tahoma", 1, 14));
        lblForw.setForeground(new java.awt.Color(255, 255, 255));
        lblForw.setText("Produkte");
        lblForw.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblForwMouseClicked(evt);
            }
        });
        panFooterRight.add(lblForw);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panButtons.add(panFooterRight, gridBagConstraints);

        panFooter.add(panButtons, java.awt.BorderLayout.CENTER);

        setLayout(new java.awt.CardLayout());

        panDescription.setOpaque(false);
        panDescription.setLayout(new java.awt.GridBagLayout());

        panBuchungEigentum.setLayout(new java.awt.GridBagLayout());

        scpBuchungsblaetter.setMaximumSize(new java.awt.Dimension(100, 200));
        scpBuchungsblaetter.setMinimumSize(new java.awt.Dimension(100, 200));
        scpBuchungsblaetter.setOpaque(false);
        scpBuchungsblaetter.setPreferredSize(new java.awt.Dimension(100, 200));

        lstBuchungsblaetter.setOpaque(false);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.buchungsblaetter}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, eLProperty, lstBuchungsblaetter);
        bindingGroup.addBinding(jListBinding);

        lstBuchungsblaetter.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstBuchungsblaetterValueChanged(evt);
            }
        });
        scpBuchungsblaetter.setViewportView(lstBuchungsblaetter);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        panBuchungEigentum.add(scpBuchungsblaetter, gridBagConstraints);

        scpBuchungsblattFlurstuecke.setMaximumSize(new java.awt.Dimension(140, 200));
        scpBuchungsblattFlurstuecke.setMinimumSize(new java.awt.Dimension(140, 200));
        scpBuchungsblattFlurstuecke.setOpaque(false);
        scpBuchungsblattFlurstuecke.setPreferredSize(new java.awt.Dimension(140, 200));

        lstBuchungsblattFlurstuecke.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstBuchungsblattFlurstuecke.setOpaque(false);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${selectedElement.landparcels}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, lstBuchungsblaetter, eLProperty, lstBuchungsblattFlurstuecke);
        bindingGroup.addBinding(jListBinding);

        scpBuchungsblattFlurstuecke.setViewportView(lstBuchungsblattFlurstuecke);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 10);
        panBuchungEigentum.add(scpBuchungsblattFlurstuecke, gridBagConstraints);

        jPanel6.setLayout(new java.awt.GridBagLayout());

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Buchungsblätter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel6.add(jLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panBuchungEigentum.add(jPanel6, gridBagConstraints);

        lblBuchungsblaetter.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblBuchungsblaetter.setText("Buchungsblätter:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        panBuchungEigentum.add(lblBuchungsblaetter, gridBagConstraints);

        lblInhalt.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblInhalt.setText("Inhalt:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        panBuchungEigentum.add(lblInhalt, gridBagConstraints);

        lblEnthalteneFlurstuecke.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblEnthalteneFlurstuecke.setText("Enthaltene Flurstücke:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        panBuchungEigentum.add(lblEnthalteneFlurstuecke, gridBagConstraints);

        panInhaltBuchungsblatt.setOpaque(false);
        panInhaltBuchungsblatt.setLayout(new java.awt.BorderLayout());

        scpInhaltBuchungsblatt.setBorder(null);
        scpInhaltBuchungsblatt.setMaximumSize(new java.awt.Dimension(250, 200));
        scpInhaltBuchungsblatt.setMinimumSize(new java.awt.Dimension(250, 200));
        scpInhaltBuchungsblatt.setOpaque(false);
        scpInhaltBuchungsblatt.setPreferredSize(new java.awt.Dimension(250, 200));

        epInhaltBuchungsblatt.setBorder(null);
        epInhaltBuchungsblatt.setContentType("text/html");
        epInhaltBuchungsblatt.setEditable(false);
        epInhaltBuchungsblatt.setText("\n");
        epInhaltBuchungsblatt.setMaximumSize(new java.awt.Dimension(250, 200));
        epInhaltBuchungsblatt.setMinimumSize(new java.awt.Dimension(250, 200));
        epInhaltBuchungsblatt.setOpaque(false);
        epInhaltBuchungsblatt.setPreferredSize(new java.awt.Dimension(250, 200));
        scpInhaltBuchungsblatt.setViewportView(epInhaltBuchungsblatt);

        panInhaltBuchungsblatt.add(scpInhaltBuchungsblatt, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 10, 15);
        panBuchungEigentum.add(panInhaltBuchungsblatt, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 5);
        panDescription.add(panBuchungEigentum, gridBagConstraints);

        panMainInfo.setLayout(new java.awt.GridBagLayout());

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.alkis_id}"), lblLandparcelCode, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 5, 10);
        panMainInfo.add(lblLandparcelCode, gridBagConstraints);

        lblDescLandparcelCode.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDescLandparcelCode.setText("Flurstückskennzeichen:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 5);
        panMainInfo.add(lblDescLandparcelCode, gridBagConstraints);

        lblDescGemeinde.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDescGemeinde.setText("Gemeinde:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panMainInfo.add(lblDescGemeinde, gridBagConstraints);

        lblGemeinde.setText("Wuppertal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panMainInfo.add(lblGemeinde, gridBagConstraints);

        lblDescGemarkung.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDescGemarkung.setText("Gemarkung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panMainInfo.add(lblDescGemarkung, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.gemarkung}"), lblGemarkung, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panMainInfo.add(lblGemarkung, gridBagConstraints);

        lblDescLage.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDescLage.setText("Lage:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panMainInfo.add(lblDescLage, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ, this, org.jdesktop.beansbinding.ELProperty.create("${cidsBean.groesse} m²"), lblGroesse, org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 10);
        panMainInfo.add(lblGroesse, gridBagConstraints);

        lblDescGroesse.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDescGroesse.setText("Größe:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 5, 5);
        panMainInfo.add(lblDescGroesse, gridBagConstraints);

        lblDescTatsNutzung.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblDescTatsNutzung.setText("Tatsächliche Nutzung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 10, 10, 5);
        panMainInfo.add(lblDescTatsNutzung, gridBagConstraints);

        lblTatsNutzung.setText("Attribut existiert noch nicht!");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 10);
        panMainInfo.add(lblTatsNutzung, gridBagConstraints);

        jPanel5.setLayout(new java.awt.GridBagLayout());

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Flurstücksinformation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel5.add(jLabel6, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panMainInfo.add(jPanel5, gridBagConstraints);

        scpLage.setBorder(null);
        scpLage.setMaximumSize(new java.awt.Dimension(250, 20));
        scpLage.setMinimumSize(new java.awt.Dimension(250, 20));
        scpLage.setOpaque(false);
        scpLage.setPreferredSize(new java.awt.Dimension(250, 20));

        epLage.setBorder(null);
        epLage.setContentType("text/html");
        epLage.setEditable(false);
        epLage.setOpaque(false);
        scpLage.setViewportView(epLage);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMainInfo.add(scpLage, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 5);
        panDescription.add(panMainInfo, gridBagConstraints);

        panFlurstueckMap.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panFlurstueckMap.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 10, 5);
        panDescription.add(panFlurstueckMap, gridBagConstraints);

        add(panDescription, "CARD_1");

        panProducts.setOpaque(false);
        panProducts.setLayout(new java.awt.GridBagLayout());

        panPdfProducts.setOpaque(false);
        panPdfProducts.setLayout(new java.awt.GridBagLayout());

        hlKarte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/icons/pdf.png"))); // NOI18N
        hlKarte.setText("Karte");
        hlKarte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlKarteActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 10, 7);
        panPdfProducts.add(hlKarte, gridBagConstraints);

        hlFlurstuecksEigentumsnachweisPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/icons/pdf.png"))); // NOI18N
        hlFlurstuecksEigentumsnachweisPdf.setText("Flurstücks- und Eigentumsnachweis");
        hlFlurstuecksEigentumsnachweisPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlFlurstuecksEigentumsnachweisPdfActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 7, 7);
        panPdfProducts.add(hlFlurstuecksEigentumsnachweisPdf, gridBagConstraints);

        hlFlurstuecksnachweisPdf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/icons/pdf.png"))); // NOI18N
        hlFlurstuecksnachweisPdf.setText("Flurstücksnachweis");
        hlFlurstuecksnachweisPdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlFlurstuecksnachweisPdfActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 7, 7, 7);
        panPdfProducts.add(hlFlurstuecksnachweisPdf, gridBagConstraints);

        jPanel1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panPdfProducts.add(jPanel1, gridBagConstraints);

        jPanel3.setLayout(new java.awt.GridBagLayout());

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("PDF Produkte");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel3.add(jLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panPdfProducts.add(jPanel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 9, 5);
        panProducts.add(panPdfProducts, gridBagConstraints);

        panHtmlProducts.setOpaque(false);
        panHtmlProducts.setLayout(new java.awt.GridBagLayout());

        hlFlurstuecksEigentumsnachweisHtml.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/icons/text-html.png"))); // NOI18N
        hlFlurstuecksEigentumsnachweisHtml.setText("Flurstücks- und Eigentumsnachweis");
        hlFlurstuecksEigentumsnachweisHtml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlFlurstuecksEigentumsnachweisHtmlActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 7, 10, 7);
        panHtmlProducts.add(hlFlurstuecksEigentumsnachweisHtml, gridBagConstraints);

        hlFlurstuecksnachweisHtml.setIcon(new javax.swing.ImageIcon(getClass().getResource("/de/cismet/cids/custom/icons/text-html.png"))); // NOI18N
        hlFlurstuecksnachweisHtml.setText("Flurstücksnachweis");
        hlFlurstuecksnachweisHtml.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hlFlurstuecksnachweisHtmlActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(17, 7, 7, 7);
        panHtmlProducts.add(hlFlurstuecksnachweisHtml, gridBagConstraints);

        jPanel2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panHtmlProducts.add(jPanel2, gridBagConstraints);

        jPanel4.setLayout(new java.awt.GridBagLayout());

        jLabel5.setText("HTML Produkte");
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        jPanel4.add(jLabel5, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        panHtmlProducts.add(jPanel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 5, 5, 5);
        panProducts.add(panHtmlProducts, gridBagConstraints);

        panSpacing.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panProducts.add(panSpacing, gridBagConstraints);

        panProductPreview.setOpaque(false);
        panProductPreview.setLayout(new java.awt.BorderLayout());

        lblProductPreview.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProductPreview.setBorder(javax.swing.BorderFactory.createEmptyBorder(7, 7, 7, 7));
        panProductPreview.add(lblProductPreview, java.awt.BorderLayout.CENTER);

        panProductPreviewHead.setLayout(new java.awt.GridBagLayout());

        lblPreviewHead.setForeground(new java.awt.Color(255, 255, 255));
        lblPreviewHead.setText("Vorschau");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panProductPreviewHead.add(lblPreviewHead, gridBagConstraints);

        panProductPreview.add(panProductPreviewHead, java.awt.BorderLayout.NORTH);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panProducts.add(panProductPreview, gridBagConstraints);

        add(panProducts, "CARD_2");

        bindingGroup.bind();
    }// </editor-fold>//GEN-END:initComponents

    private final String getLandparcelCode() {
        if (cidsBean != null) {
            final Object parcelCodeObj = cidsBean.getProperty("alkis_id");
            if (parcelCodeObj != null) {
                return parcelCodeObj.toString();
            }
        }
        return "";
    }

    private void hlKarteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlKarteActionPerformed
        try {
            String parcelCode = getLandparcelCode();
            if (parcelCode.length() > 0) {
                String url = "http://s102x083:8080/ASWeb34/ASA_AAAWeb/ALKISLiegenschaftskarte?user=3atest&password=3atest&service=wuppertal&landparcel=" + parcelCode;
                ObjectRendererUIUtils.openURL(url);
            }
        } catch (Exception ex) {
            ObjectRendererUIUtils.showExceptionWindowToUser("Fehler beim Aufruf des Produkts", ex, Alkis_landparcelRenderer.this);
            log.error(ex);
        }
    }//GEN-LAST:event_hlKarteActionPerformed

    private void hlFlurstuecksnachweisPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlFlurstuecksnachweisPdfActionPerformed
        try {
            String parcelCode = getLandparcelCode();
            if (parcelCode.length() > 0) {
                String url = "http://s102x083:8080/ASWeb34/ASA_AAAWeb/ALKISBuchNachweis?user=3atest&password=3atest&service=wuppertal&product=LB.NRW.FENW.G&id=" + parcelCode + "&contentType=PDF&certificationType=9511";
                ObjectRendererUIUtils.openURL(url);
            }
        } catch (Exception ex) {
            ObjectRendererUIUtils.showExceptionWindowToUser("Fehler beim Aufruf des Produkts", ex, Alkis_landparcelRenderer.this);
            log.error(ex);
        }
}//GEN-LAST:event_hlFlurstuecksnachweisPdfActionPerformed

    private void hlFlurstuecksEigentumsnachweisHtmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlFlurstuecksEigentumsnachweisHtmlActionPerformed
        try {
            String parcelCode = getLandparcelCode();
            if (parcelCode.length() > 0) {
                String url = "http://s102x083:8080/ASWeb34/ASA_AAAWeb/ALKISBuchNachweis?user=3atest&password=3atest&service=wuppertal&product=LB.A.FENW.G.NRW&id=" + parcelCode + "&contentType=HTML&certificationType=9551";
                ObjectRendererUIUtils.openURL(url);
            }
        } catch (Exception ex) {
            ObjectRendererUIUtils.showExceptionWindowToUser("Fehler beim Aufruf des Produkts", ex, Alkis_landparcelRenderer.this);
            log.error(ex);
        }
}//GEN-LAST:event_hlFlurstuecksEigentumsnachweisHtmlActionPerformed

    private void hlFlurstuecksnachweisHtmlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlFlurstuecksnachweisHtmlActionPerformed
        try {
            String parcelCode = getLandparcelCode();
            if (parcelCode.length() > 0) {
                String url = "http://s102x083:8080/ASWeb34/ASA_AAAWeb/ALKISBuchNachweis?user=3atest&password=3atest&service=wuppertal&product=LB.NRW.FENW.G&id=" + parcelCode + "&contentType=HTML&certificationType=9511";
                ObjectRendererUIUtils.openURL(url);
            }
        } catch (Exception ex) {
            ObjectRendererUIUtils.showExceptionWindowToUser("Fehler beim Aufruf des Produkts", ex, Alkis_landparcelRenderer.this);
            log.error(ex);
        }
}//GEN-LAST:event_hlFlurstuecksnachweisHtmlActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        cardLayout.show(this, CARD_1);
        btnBack.setEnabled(false);
        btnForward.setEnabled(true);
        lblBack.setEnabled(false);
        lblForw.setEnabled(true);
}//GEN-LAST:event_btnBackActionPerformed

    private void btnForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForwardActionPerformed
        cardLayout.show(this, CARD_2);
        btnBack.setEnabled(true);
        btnForward.setEnabled(false);
        lblBack.setEnabled(true);
        lblForw.setEnabled(false);
}//GEN-LAST:event_btnForwardActionPerformed

    private void lblBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBackMouseClicked
        btnBackActionPerformed(null);
    }//GEN-LAST:event_lblBackMouseClicked

    private void lblForwMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblForwMouseClicked
        btnForwardActionPerformed(null);
    }//GEN-LAST:event_lblForwMouseClicked

    private void lstBuchungsblaetterValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstBuchungsblaetterValueChanged
        if (!evt.getValueIsAdjusting()) {
            final Object[] selectedObjs = lstBuchungsblaetter.getSelectedValues();
            if (selectedObjs != null && selectedObjs.length > 0) {
                final Collection<CidsBean> selectedBeans = TypeSafeCollections.newArrayList(selectedObjs.length);
                for (Object selectedObj : selectedObjs) {
                    if (selectedObj instanceof CidsBean) {
                        selectedBeans.add((CidsBean) selectedObj);
                    }
                }

                final RetrieveBuchungsblaetterWorker oldWorker = retrieveBuchungsblaetterWorker;
                newWorker = new RetrieveBuchungsblaetterWorker(selectedBeans);
                if (oldWorker != null) {
                    oldWorker.removePropertyChangeListener(workerDoneListender);
                    oldWorker.cancel(true);
                    workerDoneListender = new PropertyChangeListener() {

                        @Override
                        public void propertyChange(PropertyChangeEvent evt) {
                            if (SwingWorker.StateValue.DONE.equals(oldWorker.getState())) {
                                if (newWorker != null && retrieveBuchungsblaetterWorker != newWorker) {
                                    retrieveBuchungsblaetterWorker = newWorker;
                                    newWorker = null;
                                    CismetThreadPool.execute(retrieveBuchungsblaetterWorker);
                                }
                            }
                        }
                    };
                    oldWorker.addPropertyChangeListener(workerDoneListender);
                    workerDoneListender.propertyChange(null);
                } else {
                    retrieveBuchungsblaetterWorker = newWorker;
                    newWorker = null;
                    CismetThreadPool.execute(retrieveBuchungsblaetterWorker);
                }
            }
        }
    }//GEN-LAST:event_lstBuchungsblaetterValueChanged

    private void hlFlurstuecksEigentumsnachweisPdfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hlFlurstuecksEigentumsnachweisPdfActionPerformed
        try {
            String parcelCode = getLandparcelCode();
            if (parcelCode.length() > 0) {
                String url = "http://s102x083:8080/ASWeb34/ASA_AAAWeb/ALKISBuchNachweis?user=3atest&password=3atest&service=wuppertal&product=LB.A.FENW.G.NRW&id=" + parcelCode + "&contentType=PDF&certificationType=9551";
                ObjectRendererUIUtils.openURL(url);
            }
        } catch (Exception ex) {
            ObjectRendererUIUtils.showExceptionWindowToUser("Fehler beim Aufruf des Produkts", ex, Alkis_landparcelRenderer.this);
            log.error(ex);
        }
}//GEN-LAST:event_hlFlurstuecksEigentumsnachweisPdfActionPerformed

    @Override
    public CidsBean getCidsBean() {
        return cidsBean;
    }

    @Override
    public void setCidsBean(CidsBean cb) {
        if (cb != null) {
            this.cidsBean = cb;
            initMap(cb);
            initLage(cb);
            bindingGroup.unbind();
            bindingGroup.bind();
            final int anzahlBuchungsblaetter = lstBuchungsblaetter.getModel().getSize();
            if (anzahlBuchungsblaetter < 5) {
                lblBuchungsblaetter.setVisible(false);
                scpBuchungsblaetter.setVisible(false);
                lblInhalt.setVisible(false);
                final int[] selection = new int[anzahlBuchungsblaetter];
                for (int i = 0; i < selection.length; ++i) {
                    selection[i] = i;
                }
                lstBuchungsblaetter.setSelectedIndices(selection);
            }
        }
    }

    private final void initLage(CidsBean cidsBean) {
        final Map<String, List<CidsBean>> streetToBeans = TypeSafeCollections.newHashMap();
        final Object adressenObj = cidsBean.getProperty("adressen");
        if (adressenObj instanceof List) {
            final List<CidsBean> adressenBeans = (List<CidsBean>) adressenObj;
            for (final CidsBean adresse : adressenBeans) {
                final Object strasseObj = adresse.getProperty("strasse");
                List<CidsBean> beansWithThisStreet;
                if (strasseObj != null) {
                    final String strasse = strasseObj.toString();
                    beansWithThisStreet = streetToBeans.get(strasse);
                    if (beansWithThisStreet == null) {
                        beansWithThisStreet = TypeSafeCollections.newArrayList();
                        streetToBeans.put(strasse, beansWithThisStreet);
                    }
                    beansWithThisStreet.add(adresse);
                }
            }
        }
        final StringBuilder adressenContent = new StringBuilder("<html><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" align=\"left\" valign=\"top\">");
        //sort by street
        final List<String> sortStrassen = TypeSafeCollections.newArrayList(streetToBeans.keySet());
        Collections.sort(sortStrassen);
        int entryCount = sortStrassen.size();
        for (final String strasse : sortStrassen) {
            final List<CidsBean> beansWithThisStreet = streetToBeans.get(strasse);
            final Map<String, CidsBean> hausnummernToBeans = TypeSafeCollections.newHashMap();
            for (final CidsBean adresse : beansWithThisStreet) {
                final Object hausnummerObj = adresse.getProperty("nummer");
                if (hausnummerObj != null) {
                    hausnummernToBeans.put(hausnummerObj.toString(), adresse);
                }
            }
            if (hausnummernToBeans.isEmpty()) {
                for (final CidsBean bean : beansWithThisStreet) {
                    adressenContent.append("<tr><td>");
                    adressenContent.append(AlkisCommons.generateLinkFromCidsBean(bean, strasse));
                    adressenContent.append("</td></tr>");
                }
            } else {
                //allocate an extra line if number of housenumbers is big
                entryCount += (hausnummernToBeans.size() / 7);
                adressenContent.append("<tr><td>");
                adressenContent.append(strasse).append("&nbsp;");
                adressenContent.append("</td>");
                final List<String> sortNummern = TypeSafeCollections.newArrayList(hausnummernToBeans.keySet());
                adressenContent.append("<td>");
                for (int i = 0; i < sortNummern.size(); ++i) {
//                for (final String nummer : sortNummern) {
                    final String nummer = sortNummern.get(i);
                    final CidsBean numberBean = hausnummernToBeans.get(nummer);
                    adressenContent.append(AlkisCommons.generateLinkFromCidsBean(numberBean, nummer));
                    if (i != (sortNummern.size() - 1)) {
                        adressenContent.append(", ");
                    }
                }
                adressenContent.append("</td>");
                adressenContent.append("</tr>");
            }
        }
        adressenContent.append("</table></html>");
        epLage.setText(adressenContent.toString());
//        final Element element = epLage.getDocument().getDefaultRootElement();
//        final int linecount = element.getElementCount();
        final int linecount = entryCount;
        if (linecount > 1) {
            if (linecount < 5) {
                setAllDimensions(scpLage, new Dimension(scpLage.getPreferredSize().width, 20 * linecount));
            } else {
                setAllDimensions(scpLage, new Dimension(scpLage.getPreferredSize().width, 100));
            }
        }
    }

    private final void setAllDimensions(JComponent comp, Dimension dim) {
        comp.setMaximumSize(dim);
        comp.setMinimumSize(dim);
        comp.setPreferredSize(dim);
    }

    private final void initMap(CidsBean cidsBean) {
        final Object geoObj = cidsBean.getProperty("geometrie.geo_field");
        if (geoObj instanceof Geometry) {
            final Geometry pureGeom = (Geometry) geoObj;
            final BoundingBox box = new BoundingBox(pureGeom.getEnvelope().buffer(AlkisCommons.MAP_CONSTANTS.GEO_BUFFER));

            final Runnable mapRunnable = new Runnable() {

                @Override
                public void run() {
                    final ActiveLayerModel mappingModel = new ActiveLayerModel();
                    mappingModel.addHome(new XBoundingBox(box.getX1(), box.getY1(), box.getX2(), box.getY2(), AlkisCommons.MAP_CONSTANTS.SRS, true));
                    mappingModel.setSrs(AlkisCommons.MAP_CONSTANTS.SRS);
                    SimpleWMS swms = new SimpleWMS(new SimpleWmsGetMapUrl(AlkisCommons.MAP_CONSTANTS.CALL_STRING));
                    swms.setName("Flurstueck");
                    DefaultStyledFeature dsf = new DefaultFeatureServiceFeature();
                    dsf.setGeometry(pureGeom);
                    dsf.setFillingPaint(new Color(1, 0, 0, 0.5f));
                    //add the raster layer to the model
                    mappingModel.addLayer(swms);
                    //set the model
                    map.setMappingModel(mappingModel);
                    //initial positioning of the map
                    final int duration = map.getAnimationDuration();
                    map.setAnimationDuration(0);
                    map.gotoInitialBoundingBox();
//                        map.gotoBoundingBox(box, false, true, 0);
                    //interaction mode
                    map.setInteractionMode(MappingComponent.ZOOM);
                    //finally when all configurations are done ...
                    map.unlock();
                    map.addCustomInputListener("MUTE", new PBasicInputEventHandler() {

                        @Override
                        public void mouseClicked(PInputEvent arg0) {
                            log.fatal("TODO!");

                        }
                    });
                    map.setInteractionMode("MUTE");
                    map.getFeatureCollection().addFeature(dsf);
                    map.setAnimationDuration(duration);
                }
            };
            if (EventQueue.isDispatchThread()) {
                mapRunnable.run();
            } else {
                EventQueue.invokeLater(mapRunnable);
            }
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        if (title == null) {
            title = "<Error>";
        } else {
            title = AlkisCommons.prettyPrintLandparcelCode(title);
        }
        this.title = title;
        lblTitle.setText(this.title);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXBusyLabel blWait;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnForward;
    private javax.swing.JEditorPane epInhaltBuchungsblatt;
    private javax.swing.JEditorPane epLage;
    private org.jdesktop.swingx.JXHyperlink hlFlurstuecksEigentumsnachweisHtml;
    private org.jdesktop.swingx.JXHyperlink hlFlurstuecksEigentumsnachweisPdf;
    private org.jdesktop.swingx.JXHyperlink hlFlurstuecksnachweisHtml;
    private org.jdesktop.swingx.JXHyperlink hlFlurstuecksnachweisPdf;
    private org.jdesktop.swingx.JXHyperlink hlKarte;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JLabel lblBack;
    private javax.swing.JLabel lblBuchungsblaetter;
    private javax.swing.JLabel lblDescGemarkung;
    private javax.swing.JLabel lblDescGemeinde;
    private javax.swing.JLabel lblDescGroesse;
    private javax.swing.JLabel lblDescLage;
    private javax.swing.JLabel lblDescLandparcelCode;
    private javax.swing.JLabel lblDescTatsNutzung;
    private javax.swing.JLabel lblEnthalteneFlurstuecke;
    private javax.swing.JLabel lblForw;
    private javax.swing.JLabel lblGemarkung;
    private javax.swing.JLabel lblGemeinde;
    private javax.swing.JLabel lblGroesse;
    private javax.swing.JLabel lblInhalt;
    private javax.swing.JLabel lblLandparcelCode;
    private javax.swing.JLabel lblPreviewHead;
    private javax.swing.JLabel lblProductPreview;
    private javax.swing.JLabel lblTatsNutzung;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JList lstBuchungsblaetter;
    private javax.swing.JList lstBuchungsblattFlurstuecke;
    private javax.swing.JPanel panBuchungEigentum;
    private javax.swing.JPanel panButtons;
    private javax.swing.JPanel panDescription;
    private javax.swing.JPanel panFlurstueckMap;
    private javax.swing.JPanel panFooter;
    private javax.swing.JPanel panFooterLeft;
    private javax.swing.JPanel panFooterRight;
    private javax.swing.JPanel panHtmlProducts;
    private javax.swing.JPanel panInhaltBuchungsblatt;
    private javax.swing.JPanel panMainInfo;
    private javax.swing.JPanel panPdfProducts;
    private javax.swing.JPanel panProductPreview;
    private javax.swing.JPanel panProductPreviewHead;
    private javax.swing.JPanel panProducts;
    private javax.swing.JPanel panSpacing;
    private javax.swing.JPanel panTitle;
    private javax.swing.JScrollPane scpBuchungsblaetter;
    private javax.swing.JScrollPane scpBuchungsblattFlurstuecke;
    private javax.swing.JScrollPane scpInhaltBuchungsblatt;
    private javax.swing.JScrollPane scpLage;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    // <editor-fold defaultstate="collapsed" desc="Border- and Titleprovider method implementations">
    @Override
    public JComponent getTitleComponent() {
        return panTitle;
    }

    @Override
    public JComponent getFooterComponent() {
        return panFooter;
    }

    /**
     * @return the landparcel
     */
    public Object getLandparcel() {
        return landparcel;
    }

    @Override
    public Border getTitleBorder() {
        return new EmptyBorder(10, 10, 10, 10);
    }

    @Override
    public Border getFooterBorder() {
        return new EmptyBorder(5, 5, 5, 5);
    }

    @Override
    public Border getCenterrBorder() {
        return new EmptyBorder(5, 5, 5, 5);
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Retrieve Worker">
    final class RetrieveBuchungsblaetterWorker extends SwingWorker<String, String> {

        private static final String LOAD_TEXT = "Weitere werden geladen...";
        private final Collection<CidsBean> buchungsblaetterBeans;
        private final StringBuilder currentInfoText;
        private int current;

        public RetrieveBuchungsblaetterWorker(Collection<CidsBean> buchungsblatterBeans) {
            this.buchungsblaetterBeans = buchungsblatterBeans;
            this.currentInfoText = new StringBuilder();
            setWaiting(true);
            epInhaltBuchungsblatt.setText("Wird geladen... (" + buchungsblatterBeans.size() + ")");
            current = 1;
        }

        @Override
        protected String doInBackground() throws Exception {
            for (final CidsBean buchungsblattBean : buchungsblaetterBeans) {
                if (isCancelled()) {
                    return currentInfoText.toString();
                }
                if (buchungsblattBean != null) {
                    Buchungsblatt buchungsblatt = getBuchungsblatt(buchungsblattBean);
                    currentInfoText.append(AlkisCommons.buchungsblattToString(buchungsblatt));
                    publish(currentInfoText.toString());
                }
            }
            return currentInfoText.toString();
        }

        @Override
        protected void process(List<String> chunks) {
            final StringBuilder infos = new StringBuilder(chunks.get(chunks.size() - 1));
            infos.append(LOAD_TEXT).append(" (").append((current += chunks.size())).append(" / ").append(buchungsblaetterBeans.size()).append(")");
            if (!isCancelled()) {
                epInhaltBuchungsblatt.setText("<table>" + infos.toString() + "</table>");
//                epInhaltBuchungsblatt.setText("<font face=\"" + FONT + "\" size=\"11\">" + "<table>" + infos.toString() + "</table>" + "</font>");
//                epInhaltBuchungsblatt.setText("<pre>" + infos.toString() + "</pre>");
            }
        }

        @Override
        protected void done() {
            if (!isCancelled()) {
                if (retrieveBuchungsblaetterWorker == this) {
                    retrieveBuchungsblaetterWorker = null;
                    setWaiting(false);
                }
                try {
                    epInhaltBuchungsblatt.setText(get());
//                    epInhaltBuchungsblatt.setText("<pre>" + get() + "</pre>");
                } catch (InterruptedException ex) {
                    log.warn(ex, ex);
                } catch (Exception ex) {
                    ObjectRendererUIUtils.showExceptionWindowToUser("Fehler beim Retrieve", ex, Alkis_landparcelRenderer.this);
                    epInhaltBuchungsblatt.setText(epInhaltBuchungsblatt.getText() + "<br>" + "Aufgrund eines Ausnahmefehlers vermutlich unvollständig!");
                    log.error(ex, ex);
                }
            }
        }
    }

// </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Listeners">
    class ProductLabelMouseAdaper extends MouseAdapter {

        @Override
        public void mouseEntered(MouseEvent e) {
            final Object srcObj = e.getSource();
            final ImageIcon imageIcon = productPreviewImages.get(srcObj);
            if (imageIcon != null) {
                lblProductPreview.setIcon(imageIcon);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            lblProductPreview.setIcon(null);
        }
    }

    static class FooterLabelMouseAdapter extends MouseAdapter {

        public FooterLabelMouseAdapter(JLabel label) {
            this.label = label;
            plain = label.getFont();
            final Map<TextAttribute, Object> attributesMap = (Map<TextAttribute, Object>) plain.getAttributes();
            attributesMap.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
            underlined = plain.deriveFont(attributesMap);

        }
        private final Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        private final Font underlined;
        private final Font plain;
        protected final JLabel label;

        @Override
        public void mouseEntered(MouseEvent e) {
            label.setCursor(handCursor);
            if (label.isEnabled() && label.getFont() != underlined) {
                label.setFont(underlined);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            label.setCursor(Cursor.getDefaultCursor());
            if (label.getFont() != plain) {
                label.setFont(plain);
            }
        }
    }

    static class FooterButtonMouseAdapter extends MouseAdapter {

        public FooterButtonMouseAdapter(JButton button, Icon plain, Icon highlight, Icon pressed) {
            this.button = button;
            this.plainIcon = plain;
            this.highlightIcon = highlight;
            this.pressedIcon = pressed;
        }

        public FooterButtonMouseAdapter(JButton button, Icon highlight, Icon pressed) {
            this.button = button;
            this.plainIcon = button.getIcon();
            this.highlightIcon = highlight;
            this.pressedIcon = pressed;
        }
        private final Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
        private final Icon plainIcon;
        private final Icon highlightIcon;
        private final Icon pressedIcon;
        protected final JButton button;
        protected boolean over = false;
        protected boolean pressed = false;

        @Override
        public void mouseEntered(MouseEvent e) {
            over = true;
            button.setCursor(handCursor);
            handleEvent(e);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            over = false;
            button.setCursor(Cursor.getDefaultCursor());
            handleEvent(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            pressed = true;
            handleEvent(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pressed = false;
            handleEvent(e);
        }

        private final void testAndSet(Icon icon) {
            if (button.getIcon() != icon) {
                button.setIcon(icon);
            }
        }

        protected void handleEvent(MouseEvent e) {
            if (button.isEnabled()) {
                if (pressed && over) {
                    testAndSet(pressedIcon);
                } else if (over) {
                    testAndSet(highlightIcon);
                } else {
                    testAndSet(plainIcon);
                }
            } else {
                testAndSet(plainIcon);
            }
        }
    }
// </editor-fold>
}