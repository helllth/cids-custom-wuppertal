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
 * Poi_locationinstanceEditor.java
 *
 * Created on 17.08.2009, 15:40:29
 */
package de.cismet.cids.custom.objecteditors.wunda_blau;

import Sirius.navigator.connection.SessionManager;

import Sirius.server.middleware.types.MetaClass;
import Sirius.server.middleware.types.MetaObject;

import org.jdesktop.swingx.JXErrorPane;
import org.jdesktop.swingx.error.ErrorInfo;

import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Level;

import javax.swing.JOptionPane;

import de.cismet.cids.custom.objectrenderer.utils.ObjectRendererUtils;
import de.cismet.cids.custom.objectrenderer.wunda_blau.SignaturListCellRenderer;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.cids.editors.DefaultCustomObjectEditor;
import de.cismet.cids.editors.FastBindableReferenceCombo;

import de.cismet.cids.navigator.utils.ClassCacheMultiple;

import de.cismet.cids.tools.metaobjectrenderer.Titled;

import de.cismet.cismap.cids.geometryeditor.DefaultCismapGeometryComboBoxEditor;

import de.cismet.tools.gui.RoundedPanel;
import de.cismet.tools.gui.StaticSwingTools;

/**
 * DOCUMENT ME!
 *
 * @author   srichter
 * @version  $Revision$, $Date$
 */
public class Poi_locationinstanceEditor extends DefaultCustomObjectEditor implements Titled {

    //~ Instance fields --------------------------------------------------------

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    private String title = "";
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddThema;
    private javax.swing.JButton btnAddZusNamen;
    private javax.swing.JButton btnMenAbort;
    private javax.swing.JButton btnMenOk;
    private javax.swing.JButton btnNamesMenAbort;
    private javax.swing.JButton btnNamesMenOk;
    private javax.swing.JButton btnRemoveThema;
    private javax.swing.JButton btnRemoveZusNamen;
    private javax.swing.JComboBox cbGeomPoint;
    private javax.swing.JComboBox cbMainLocationType;
    private javax.swing.JComboBox cbSignatur;
    private javax.swing.JComboBox cbTypes;
    private javax.swing.JCheckBox chkVeroeffentlicht;
    private javax.swing.JDialog dlgAddLocationType;
    private javax.swing.JDialog dlgAddZusNamen;
    private javax.swing.JLabel lblArt;
    private javax.swing.JLabel lblAuswaehlen;
    private javax.swing.JLabel lblBezeichnung;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFax;
    private javax.swing.JLabel lblGeomPoint;
    private javax.swing.JLabel lblHeader1;
    private javax.swing.JLabel lblHeader2;
    private javax.swing.JLabel lblInfo;
    private javax.swing.JLabel lblLocationTypes;
    private javax.swing.JLabel lblLocationTypes1;
    private javax.swing.JLabel lblMainLocationType;
    private javax.swing.JLabel lblNamesAuswaehlen;
    private javax.swing.JLabel lblPLZ;
    private javax.swing.JLabel lblSignatur;
    private javax.swing.JLabel lblStadt;
    private javax.swing.JLabel lblStrasse;
    private javax.swing.JLabel lblTelefon;
    private javax.swing.JLabel lblUrl;
    private javax.swing.JLabel lblVeroeffentlicht;
    private javax.swing.JList lstLocationTypes;
    private javax.swing.JList lstZusNamen;
    private javax.swing.JPanel panAddLocationType;
    private javax.swing.JPanel panAddName;
    private javax.swing.JPanel panButtons;
    private javax.swing.JPanel panButtons1;
    private javax.swing.JPanel panCenter;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panContent2;
    private javax.swing.JPanel panMenButtons;
    private javax.swing.JPanel panMenNamesButtons;
    private javax.swing.JPanel panSpacing1;
    private javax.swing.JPanel panSpacing2;
    private javax.swing.JScrollPane scpLstLocationTypes;
    private javax.swing.JScrollPane scpTxtInfo;
    private javax.swing.JScrollPane scpZusNamen;
    private javax.swing.JTextField txtArt;
    private javax.swing.JTextField txtBezeichnung;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtFax;
    private javax.swing.JTextField txtPLZ;
    private javax.swing.JTextField txtStadt;
    private javax.swing.JTextField txtStrasse;
    private javax.swing.JTextField txtTelefon;
    private javax.swing.JTextField txtUrl;
    private javax.swing.JTextField txtZusNamen;
    private javax.swing.JTextArea txtaInfo;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates new form Poi_locationinstanceEditor.
     */
    public Poi_locationinstanceEditor() {
        initComponents();
        dlgAddLocationType.pack();
        dlgAddZusNamen.pack();
        dlgAddLocationType.getRootPane().setDefaultButton(btnMenOk);
        dlgAddZusNamen.getRootPane().setDefaultButton(btnNamesMenOk);
    }

    //~ Methods ----------------------------------------------------------------

    /**
     * DOCUMENT ME!
     */
    @Override
    public void dispose() {
        super.dispose();
        dlgAddLocationType.dispose();
        dlgAddZusNamen.dispose();
        ((DefaultCismapGeometryComboBoxEditor)cbGeomPoint).dispose();
    }

    /**
     * DOCUMENT ME!
     *
     * @param  propertyName           DOCUMENT ME!
     * @param  value                  DOCUMENT ME!
     * @param  andDeleteObjectFromDB  DOCUMENT ME!
     */
    private void deleteItemFromList(final String propertyName,
            final Object value,
            final boolean andDeleteObjectFromDB) {
        if ((value instanceof CidsBean) && (propertyName != null)) {
            final CidsBean bean = (CidsBean)value;
            if (andDeleteObjectFromDB) {
                try {
                    bean.delete();
                } catch (Exception ex) {
                    log.error(ex, ex);
                }
            } else {
                final Object coll = cidsBean.getProperty(propertyName);
                if (coll instanceof Collection) {
                    ((Collection)coll).remove(bean);
                }
            }
        }
    }
//
//    @Override
//    public synchronized void setCidsBean(CidsBean cidsBean) {
//        super.setCidsBean(cidsBean);
//    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        dlgAddLocationType = new javax.swing.JDialog();
        panAddLocationType = new javax.swing.JPanel();
        lblAuswaehlen = new javax.swing.JLabel();
        final MetaObject[] locationtypes = ObjectRendererUtils.getLightweightMetaObjectsForTable(
                "poi_locationtype",
                new String[] { "identification" });
        if (locationtypes != null) {
            Arrays.sort(locationtypes);
            cbTypes = new javax.swing.JComboBox(locationtypes);
            panMenButtons = new javax.swing.JPanel();
            btnMenAbort = new javax.swing.JButton();
            btnMenOk = new javax.swing.JButton();
            dlgAddZusNamen = new javax.swing.JDialog();
            panAddName = new javax.swing.JPanel();
            lblNamesAuswaehlen = new javax.swing.JLabel();
            panMenNamesButtons = new javax.swing.JPanel();
            btnNamesMenAbort = new javax.swing.JButton();
            btnNamesMenOk = new javax.swing.JButton();
            txtZusNamen = new javax.swing.JTextField();
            panCenter = new javax.swing.JPanel();
            panContent = new RoundedPanel();
            lblFax = new javax.swing.JLabel();
            lblEmail = new javax.swing.JLabel();
            lblUrl = new javax.swing.JLabel();
            lblStrasse = new javax.swing.JLabel();
            lblTelefon = new javax.swing.JLabel();
            lblPLZ = new javax.swing.JLabel();
            lblInfo = new javax.swing.JLabel();
            lblStadt = new javax.swing.JLabel();
            lblArt = new javax.swing.JLabel();
            txtFax = new javax.swing.JTextField();
            txtStrasse = new javax.swing.JTextField();
            txtStadt = new javax.swing.JTextField();
            txtPLZ = new javax.swing.JTextField();
            txtTelefon = new javax.swing.JTextField();
            txtEmail = new javax.swing.JTextField();
            scpTxtInfo = new javax.swing.JScrollPane();
            txtaInfo = new javax.swing.JTextArea();
            txtArt = new javax.swing.JTextField();
            txtUrl = new javax.swing.JTextField();
            lblBezeichnung = new javax.swing.JLabel();
            txtBezeichnung = new javax.swing.JTextField();
            panSpacing1 = new javax.swing.JPanel();
            lblHeader1 = new javax.swing.JLabel();
            lblGeomPoint = new javax.swing.JLabel();
            cbGeomPoint = new DefaultCismapGeometryComboBoxEditor();
            panContent2 = new RoundedPanel();
            lblMainLocationType = new javax.swing.JLabel();
            cbMainLocationType = new javax.swing.JComboBox();
            lblLocationTypes = new javax.swing.JLabel();
            scpLstLocationTypes = new javax.swing.JScrollPane();
            lstLocationTypes = new javax.swing.JList();
            panButtons = new javax.swing.JPanel();
            btnAddThema = new javax.swing.JButton();
            btnRemoveThema = new javax.swing.JButton();
            panButtons1 = new javax.swing.JPanel();
            btnAddZusNamen = new javax.swing.JButton();
            btnRemoveZusNamen = new javax.swing.JButton();
            scpZusNamen = new javax.swing.JScrollPane();
            lstZusNamen = new javax.swing.JList();
            lblLocationTypes1 = new javax.swing.JLabel();
            lblSignatur = new javax.swing.JLabel();
            cbSignatur = new FastBindableReferenceCombo("%1$2s", new String[] { "definition", "filename" });
            panSpacing2 = new javax.swing.JPanel();
            lblVeroeffentlicht = new javax.swing.JLabel();
            chkVeroeffentlicht = new javax.swing.JCheckBox();
            lblHeader2 = new javax.swing.JLabel();

            dlgAddLocationType.setModal(true);

            panAddLocationType.setMaximumSize(new java.awt.Dimension(180, 120));
            panAddLocationType.setMinimumSize(new java.awt.Dimension(180, 120));
            panAddLocationType.setPreferredSize(new java.awt.Dimension(180, 120));
            panAddLocationType.setLayout(new java.awt.GridBagLayout());

            lblAuswaehlen.setText("Bitte Thema auswählen:");
            gridBagConstraints = new java.awt.GridBagConstraints();
            gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
            panAddLocationType.add(lblAuswaehlen, gridBagConstraints);
        }
        cbTypes.setMaximumSize(new java.awt.Dimension(100, 20));
        cbTypes.setMinimumSize(new java.awt.Dimension(100, 20));
        cbTypes.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLocationType.add(cbTypes, gridBagConstraints);

        panMenButtons.setLayout(new java.awt.GridBagLayout());

        btnMenAbort.setText("Abbrechen");
        btnMenAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtons.add(btnMenAbort, gridBagConstraints);

        btnMenOk.setText("Ok");
        btnMenOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnMenOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtons.add(btnMenOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLocationType.add(panMenButtons, gridBagConstraints);

        dlgAddLocationType.getContentPane().add(panAddLocationType, java.awt.BorderLayout.CENTER);

        dlgAddZusNamen.setModal(true);

        panAddName.setMaximumSize(new java.awt.Dimension(180, 120));
        panAddName.setMinimumSize(new java.awt.Dimension(180, 120));
        panAddName.setPreferredSize(new java.awt.Dimension(180, 120));
        panAddName.setLayout(new java.awt.GridBagLayout());

        lblNamesAuswaehlen.setText("Bitte Namen eingeben:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 10, 10);
        panAddName.add(lblNamesAuswaehlen, gridBagConstraints);

        panMenNamesButtons.setLayout(new java.awt.GridBagLayout());

        btnNamesMenAbort.setText("Abbrechen");
        btnNamesMenAbort.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnNamesMenAbortActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenNamesButtons.add(btnNamesMenAbort, gridBagConstraints);

        btnNamesMenOk.setText("Ok");
        btnNamesMenOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnNamesMenOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenNamesButtons.add(btnNamesMenOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddName.add(panMenNamesButtons, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddName.add(txtZusNamen, gridBagConstraints);

        dlgAddZusNamen.getContentPane().add(panAddName, java.awt.BorderLayout.CENTER);

        setLayout(new java.awt.GridBagLayout());

        panCenter.setOpaque(false);
        panCenter.setLayout(new java.awt.GridBagLayout());

        panContent.setOpaque(false);
        panContent.setLayout(new java.awt.GridBagLayout());

        lblFax.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblFax.setText("Fax:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblFax, gridBagConstraints);

        lblEmail.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblEmail.setText("Email:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblEmail, gridBagConstraints);

        lblUrl.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblUrl.setText("URL:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblUrl, gridBagConstraints);

        lblStrasse.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblStrasse.setText("Strasse:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblStrasse, gridBagConstraints);

        lblTelefon.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblTelefon.setText("Telefon:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblTelefon, gridBagConstraints);

        lblPLZ.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblPLZ.setText("PLZ:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblPLZ, gridBagConstraints);

        lblInfo.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblInfo.setText("Info:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 5, 5);
        panContent.add(lblInfo, gridBagConstraints);

        lblStadt.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblStadt.setText("Stadt:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblStadt, gridBagConstraints);

        lblArt.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblArt.setText("Art der Info:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblArt, gridBagConstraints);

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.fax}"),
                txtFax,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtFax, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.strasse}"),
                txtStrasse,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtStrasse, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.stadt}"),
                txtStadt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtStadt, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.plz}"),
                txtPLZ,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtPLZ, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.telefon}"),
                txtTelefon,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtTelefon, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.email}"),
                txtEmail,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtEmail, gridBagConstraints);

        txtaInfo.setColumns(5);
        txtaInfo.setFont(new java.awt.Font("Tahoma", 0, 11));
        txtaInfo.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.info}"),
                txtaInfo,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        scpTxtInfo.setViewportView(txtaInfo);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(scpTxtInfo, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.art_info}"),
                txtArt,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtArt, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.url}"),
                txtUrl,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtUrl, gridBagConstraints);

        lblBezeichnung.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblBezeichnung.setText("Bezeichnung:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblBezeichnung, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.geographicidentifier}"),
                txtBezeichnung,
                org.jdesktop.beansbinding.BeanProperty.create("text"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(txtBezeichnung, gridBagConstraints);

        panSpacing1.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panContent.add(panSpacing1, gridBagConstraints);

        lblHeader1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblHeader1.setText("Beschreibung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblHeader1, gridBagConstraints);

        lblGeomPoint.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblGeomPoint.setText("Geometrie:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(lblGeomPoint, gridBagConstraints);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.pos}"),
                cbGeomPoint,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        binding.setConverter(((DefaultCismapGeometryComboBoxEditor)cbGeomPoint).getConverter());
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent.add(cbGeomPoint, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panCenter.add(panContent, gridBagConstraints);

        panContent2.setOpaque(false);
        panContent2.setLayout(new java.awt.GridBagLayout());

        lblMainLocationType.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblMainLocationType.setText("Haupthema:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(lblMainLocationType, gridBagConstraints);

        org.jdesktop.beansbinding.ELProperty eLProperty = org.jdesktop.beansbinding.ELProperty.create(
                "${cidsBean.locationtypes}");
        final org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJComboBoxBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        cbMainLocationType);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.mainlocationtype}"),
                cbMainLocationType,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(cbMainLocationType, gridBagConstraints);

        lblLocationTypes.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLocationTypes.setText("Themen:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 5, 5);
        panContent2.add(lblLocationTypes, gridBagConstraints);

        lstLocationTypes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.locationtypes}");
        org.jdesktop.swingbinding.JListBinding jListBinding = org.jdesktop.swingbinding.SwingBindings
                    .createJListBinding(
                        org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                        this,
                        eLProperty,
                        lstLocationTypes);
        bindingGroup.addBinding(jListBinding);

        scpLstLocationTypes.setViewportView(lstLocationTypes);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(scpLstLocationTypes, gridBagConstraints);

        panButtons.setOpaque(false);
        panButtons.setLayout(new java.awt.GridBagLayout());

        btnAddThema.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wunda_blau/edit_add_mini.png"))); // NOI18N
        btnAddThema.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddThemaActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panButtons.add(btnAddThema, gridBagConstraints);

        btnRemoveThema.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wunda_blau/edit_remove_mini.png"))); // NOI18N
        btnRemoveThema.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemoveThemaActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panButtons.add(btnRemoveThema, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(panButtons, gridBagConstraints);

        panButtons1.setOpaque(false);
        panButtons1.setLayout(new java.awt.GridBagLayout());

        btnAddZusNamen.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wunda_blau/edit_add_mini.png"))); // NOI18N
        btnAddZusNamen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnAddZusNamenActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panButtons1.add(btnAddZusNamen, gridBagConstraints);

        btnRemoveZusNamen.setIcon(new javax.swing.ImageIcon(
                getClass().getResource("/de/cismet/cids/custom/objecteditors/wunda_blau/edit_remove_mini.png"))); // NOI18N
        btnRemoveZusNamen.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnRemoveZusNamenActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panButtons1.add(btnRemoveZusNamen, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(panButtons1, gridBagConstraints);

        eLProperty = org.jdesktop.beansbinding.ELProperty.create("${cidsBean.alternativegeographicidentifier}");
        jListBinding = org.jdesktop.swingbinding.SwingBindings.createJListBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                eLProperty,
                lstZusNamen);
        bindingGroup.addBinding(jListBinding);

        scpZusNamen.setViewportView(lstZusNamen);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(scpZusNamen, gridBagConstraints);

        lblLocationTypes1.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblLocationTypes1.setText("Zusätzliche Namen:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 5, 5, 5);
        panContent2.add(lblLocationTypes1, gridBagConstraints);

        lblSignatur.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblSignatur.setText("Signatur:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(lblSignatur, gridBagConstraints);

        ((FastBindableReferenceCombo)cbSignatur).setSorted(true);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.signatur}"),
                cbSignatur,
                org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        binding.setSourceNullValue("");
        binding.setSourceUnreadableValue("<Error>");
        bindingGroup.addBinding(binding);

        cbSignatur.setRenderer(new SignaturListCellRenderer());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 5, 4, 4);
        panContent2.add(cbSignatur, gridBagConstraints);

        panSpacing2.setOpaque(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panContent2.add(panSpacing2, gridBagConstraints);

        lblVeroeffentlicht.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblVeroeffentlicht.setText("Veröffentlicht:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(lblVeroeffentlicht, gridBagConstraints);

        chkVeroeffentlicht.setOpaque(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(
                org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE,
                this,
                org.jdesktop.beansbinding.ELProperty.create("${cidsBean.to_publish}"),
                chkVeroeffentlicht,
                org.jdesktop.beansbinding.BeanProperty.create("selected"));
        binding.setSourceNullValue(false);
        binding.setSourceUnreadableValue(false);
        bindingGroup.addBinding(binding);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        panContent2.add(chkVeroeffentlicht, gridBagConstraints);

        lblHeader2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblHeader2.setText("Einordnung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panContent2.add(lblHeader2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panCenter.add(panContent2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        add(panCenter, gridBagConstraints);

        bindingGroup.bind();
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddThemaActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddThemaActionPerformed
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(Poi_locationinstanceEditor.this),
            dlgAddLocationType,
            true);
    }                                                                               //GEN-LAST:event_btnAddThemaActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemoveThemaActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemoveThemaActionPerformed
        final Object selection = lstLocationTypes.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll das Thema wirklich gelöscht werden?",
                    "Thema entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    deleteItemFromList("locationtypes", selection, false);
                } catch (Exception ex) {
                    final ErrorInfo ei = new ErrorInfo(
                            "Fehler beim Löschen",
                            "Beim Löschen des Themas ist ein Fehler aufgetreten",
                            null,
                            null,
                            ex,
                            Level.SEVERE,
                            null);
                    JXErrorPane.showDialog(this, ei);
                }
            }
        }
    }                                                                                  //GEN-LAST:event_btnRemoveThemaActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenAbortActionPerformed
        dlgAddLocationType.setVisible(false);
    }                                                                               //GEN-LAST:event_btnMenAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnMenOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnMenOkActionPerformed
        try {
            final Object selItem = cbTypes.getSelectedItem();
            if (selItem instanceof MetaObject) {
                addBeanToCollection("locationtypes", ((MetaObject)selItem).getBean());
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        } finally {
            dlgAddLocationType.setVisible(false);
        }
    }                                                                            //GEN-LAST:event_btnMenOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnAddZusNamenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnAddZusNamenActionPerformed
        StaticSwingTools.showDialog(StaticSwingTools.getParentFrame(Poi_locationinstanceEditor.this),
            dlgAddZusNamen,
            true);
    }                                                                                  //GEN-LAST:event_btnAddZusNamenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnRemoveZusNamenActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnRemoveZusNamenActionPerformed
        final Object selection = lstZusNamen.getSelectedValue();
        if (selection != null) {
            final int answer = JOptionPane.showConfirmDialog(
                    StaticSwingTools.getParentFrame(this),
                    "Soll der zusätzliche Name wirklich gelöscht werden?",
                    "Name entfernen",
                    JOptionPane.YES_NO_OPTION);
            if (answer == JOptionPane.YES_OPTION) {
                try {
                    deleteItemFromList("alternativegeographicidentifier", selection, true);
                } catch (Exception ex) {
                    final ErrorInfo ei = new ErrorInfo(
                            "Fehler beim Löschen",
                            "Beim Löschen des zusätzlichen Namens ist ein Fehler aufgetreten",
                            null,
                            null,
                            ex,
                            Level.SEVERE,
                            null);
                    JXErrorPane.showDialog(this, ei);
                }
            }
        }
    }                                                                                     //GEN-LAST:event_btnRemoveZusNamenActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNamesMenAbortActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnNamesMenAbortActionPerformed
        dlgAddZusNamen.setVisible(false);
        txtZusNamen.setText("");
    }                                                                                    //GEN-LAST:event_btnNamesMenAbortActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnNamesMenOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnNamesMenOkActionPerformed
        try {
            final String addName = txtZusNamen.getText();
            if (addName.length() > 0) {
                final MetaClass alternativeGeoIdentifierMC = ClassCacheMultiple.getMetaClass(SessionManager.getSession()
                                .getUser().getDomain(),
                        "poi_alternativegeographicidentifier");
                final MetaObject newAGI = alternativeGeoIdentifierMC.getEmptyInstance();
                final CidsBean newAGIBean = newAGI.getBean();
                newAGIBean.setProperty("alternativegeographicidentifier", addName);
                addBeanToCollection("alternativegeographicidentifier", newAGIBean);
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        } finally {
            txtZusNamen.setText("");
            dlgAddZusNamen.setVisible(false);
        }
    }                                                                                 //GEN-LAST:event_btnNamesMenOkActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  propName     DOCUMENT ME!
     * @param  newTypeBean  DOCUMENT ME!
     */
    private void addBeanToCollection(final String propName, final CidsBean newTypeBean) {
        if ((newTypeBean != null) && (propName != null)) {
            final Object o = cidsBean.getProperty(propName);
            if (o instanceof Collection) {
                try {
                    final Collection<CidsBean> col = (Collection)o;
                    for (final CidsBean bean : col) {
                        if (newTypeBean.equals(bean)) {
                            log.info("Bean " + newTypeBean + " already present in " + propName + "!");
                            return;
                        }
                    }
                    col.add(newTypeBean);
                } catch (Exception ex) {
                    log.error(ex, ex);
                }
            }
        }
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    @Override
    public String getTitle() {
        if ((title == null) || (title.length() == 0)) {
            title = getTitleBackup();
        }
        return title;
    }

    /**
     * DOCUMENT ME!
     *
     * @param  title  DOCUMENT ME!
     */
    @Override
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public String getTitleBackup() {
        final CidsBean bean = cidsBean;
        if (bean != null) {
            return bean.getMetaObject().getMetaClass().getName();
        }
        return "Point of Interest (POI)";
    }
}
