/***************************************************
*
* cismet GmbH, Saarbruecken, Germany
*
*              ... and it just works.
*
****************************************************/
/*
 * FlurstueckSelectionDialoge.java
 *
 * Created on 13.12.2010, 11:02:41
 */
package de.cismet.cids.custom.objecteditors.wunda_blau;

import Sirius.server.middleware.types.LightweightMetaObject;
import Sirius.server.middleware.types.MetaObject;

import java.awt.Color;
import java.awt.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

import de.cismet.cids.custom.objectrenderer.utils.AlphanumComparator;
import de.cismet.cids.custom.objectrenderer.utils.CidsBeanSupport;
import de.cismet.cids.custom.objectrenderer.utils.FlurstueckFinder;
import de.cismet.cids.custom.objectrenderer.utils.ObjectRendererUtils;

import de.cismet.cids.dynamics.CidsBean;

import de.cismet.tools.CismetThreadPool;

import de.cismet.tools.collections.TypeSafeCollections;

/**
 * DOCUMENT ME!
 *
 * @author   stefan
 * @version  $Revision$, $Date$
 */
public class FlurstueckSelectionDialoge extends javax.swing.JDialog {

    //~ Static fields/initializers ---------------------------------------------

    static final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(FlurstueckSelectionDialoge.class);
    private static final ComboBoxModel WAIT_MODEL = new DefaultComboBoxModel(new String[] { "Wird geladen..." });
    private static final DefaultComboBoxModel NO_SELECTION_MODEL = new DefaultComboBoxModel(new Object[] {});
    private static final String CB_EDITED_ACTION_COMMAND = "comboBoxEdited";

    //~ Instance fields --------------------------------------------------------

    private List<CidsBean> currentListToAdd;
    private final boolean createEnabled;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFlurstueckAddMenCancel;
    private javax.swing.JButton btnFlurstueckAddMenOk;
    private javax.swing.JComboBox cbParcels1;
    private javax.swing.JComboBox cbParcels2;
    private javax.swing.JComboBox cbParcels3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblFlur;
    private javax.swing.JLabel lblFlurstueck;
    private javax.swing.JLabel lblFlurstueckAuswaehlen;
    private javax.swing.JLabel lblGemarkung;
    private javax.swing.JLabel lblGemarkungsname;
    private javax.swing.JPanel panAddLandParcel1;
    private javax.swing.JPanel panMenButtons2;
    // End of variables declaration//GEN-END:variables

    //~ Constructors -----------------------------------------------------------

    /**
     * Creates a new FlurstueckSelectionDialoge object.
     */
    public FlurstueckSelectionDialoge() {
        this(true);
    }

    /**
     * Creates new form FlurstueckSelectionDialoge.
     *
     * @param  createEnabled  DOCUMENT ME!
     */
    public FlurstueckSelectionDialoge(final boolean createEnabled) {
        this.createEnabled = createEnabled;
        initComponents();
        CismetThreadPool.execute(new AbstractFlurstueckComboModelWorker(cbParcels1, true) {

                @Override
                protected ComboBoxModel doInBackground() throws Exception {
                    return new DefaultComboBoxModel(FlurstueckFinder.getLWGemarkungen());
                }

                @Override
                protected void done() {
                    super.done();
//                cbParcels1.actionPerformed(null);
                    cbParcels1.setSelectedIndex(0);
                    cbParcels1.requestFocusInWindow();
                    ObjectRendererUtils.selectAllTextInEditableCombobox(cbParcels1);
                }
            });
    }

    //~ Methods ----------------------------------------------------------------

    @Override
    public void setVisible(final boolean b) {
        btnFlurstueckAddMenOk.setEnabled(false);
        super.setVisible(b);
    }

    /**
     * DOCUMENT ME!
     *
     * @param  currentListToAdd  DOCUMENT ME!
     */
    public void setCurrentListToAdd(final List<CidsBean> currentListToAdd) {
        this.currentListToAdd = currentListToAdd;
    }

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    public List<CidsBean> getCurrentListToAdd() {
        return currentListToAdd;
    }

    /**
     * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The
     * content of this method is always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panAddLandParcel1 = new javax.swing.JPanel();
        lblFlurstueckAuswaehlen = new javax.swing.JLabel();
        cbParcels1 = new javax.swing.JComboBox();
        panMenButtons2 = new javax.swing.JPanel();
        btnFlurstueckAddMenCancel = new javax.swing.JButton();
        btnFlurstueckAddMenOk = new javax.swing.JButton();
        cbParcels2 = new javax.swing.JComboBox(NO_SELECTION_MODEL);
        cbParcels3 = new javax.swing.JComboBox(NO_SELECTION_MODEL);
        lblGemarkung = new javax.swing.JLabel();
        lblFlur = new javax.swing.JLabel();
        lblFlurstueck = new javax.swing.JLabel();
        lblGemarkungsname = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();

        panAddLandParcel1.setMaximumSize(new java.awt.Dimension(250, 180));
        panAddLandParcel1.setMinimumSize(new java.awt.Dimension(250, 180));
        panAddLandParcel1.setPreferredSize(new java.awt.Dimension(250, 180));
        panAddLandParcel1.setLayout(new java.awt.GridBagLayout());

        lblFlurstueckAuswaehlen.setFont(new java.awt.Font("Tahoma", 1, 11));
        lblFlurstueckAuswaehlen.setText("Bitte Flurstück auswählen:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(15, 10, 20, 10);
        panAddLandParcel1.add(lblFlurstueckAuswaehlen, gridBagConstraints);

        cbParcels1.setEditable(true);
        cbParcels1.setMaximumSize(new java.awt.Dimension(100, 18));
        cbParcels1.setMinimumSize(new java.awt.Dimension(100, 18));
        cbParcels1.setPreferredSize(new java.awt.Dimension(100, 18));
        cbParcels1.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbParcels1ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLandParcel1.add(cbParcels1, gridBagConstraints);

        panMenButtons2.setLayout(new java.awt.GridBagLayout());

        btnFlurstueckAddMenCancel.setText("Abbrechen");
        btnFlurstueckAddMenCancel.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnFlurstueckAddMenCancelActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtons2.add(btnFlurstueckAddMenCancel, gridBagConstraints);

        btnFlurstueckAddMenOk.setText("Ok");
        btnFlurstueckAddMenOk.setMaximumSize(new java.awt.Dimension(85, 23));
        btnFlurstueckAddMenOk.setMinimumSize(new java.awt.Dimension(85, 23));
        btnFlurstueckAddMenOk.setPreferredSize(new java.awt.Dimension(85, 23));
        btnFlurstueckAddMenOk.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    btnFlurstueckAddMenOkActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panMenButtons2.add(btnFlurstueckAddMenOk, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLandParcel1.add(panMenButtons2, gridBagConstraints);

        cbParcels2.setEditable(true);
        cbParcels2.setEnabled(false);
        cbParcels2.setMaximumSize(new java.awt.Dimension(100, 18));
        cbParcels2.setMinimumSize(new java.awt.Dimension(100, 18));
        cbParcels2.setPreferredSize(new java.awt.Dimension(100, 18));
        cbParcels2.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbParcels2ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLandParcel1.add(cbParcels2, gridBagConstraints);

        cbParcels3.setEditable(true);
        cbParcels3.setEnabled(false);
        cbParcels3.setMaximumSize(new java.awt.Dimension(100, 18));
        cbParcels3.setMinimumSize(new java.awt.Dimension(100, 18));
        cbParcels3.setPreferredSize(new java.awt.Dimension(100, 18));
        cbParcels3.addActionListener(new java.awt.event.ActionListener() {

                @Override
                public void actionPerformed(final java.awt.event.ActionEvent evt) {
                    cbParcels3ActionPerformed(evt);
                }
            });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLandParcel1.add(cbParcels3, gridBagConstraints);

        lblGemarkung.setText("Gemarkung");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLandParcel1.add(lblGemarkung, gridBagConstraints);

        lblFlur.setText("Flur");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLandParcel1.add(lblFlur, gridBagConstraints);

        lblFlurstueck.setText("Flurstück");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLandParcel1.add(lblFlurstueck, gridBagConstraints);

        lblGemarkungsname.setText(" ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        panAddLandParcel1.add(lblGemarkungsname, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        panAddLandParcel1.add(jSeparator1, gridBagConstraints);

        getContentPane().add(panAddLandParcel1, java.awt.BorderLayout.CENTER);
    } // </editor-fold>//GEN-END:initComponents

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbParcels1ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbParcels1ActionPerformed
        final Object selection = cbParcels1.getSelectedItem();
        cbParcels3.setEnabled(false);
        btnFlurstueckAddMenOk.setEnabled(false);
        if (selection instanceof LightweightMetaObject) {
            final LightweightMetaObject lwmo = (LightweightMetaObject)selection;
            final String selGemarkungsNr = String.valueOf(selection);
            CismetThreadPool.execute(new AbstractFlurstueckComboModelWorker(
                    cbParcels2,
                    CB_EDITED_ACTION_COMMAND.equals(evt.getActionCommand())) {

                    @Override
                    protected ComboBoxModel doInBackground() throws Exception {
                        return new DefaultComboBoxModel(FlurstueckFinder.getLWFlure(selGemarkungsNr));
                    }
                });

            final String gemarkungsname = String.valueOf(lwmo.getLWAttribute(FlurstueckFinder.GEMARKUNG_NAME));
            lblGemarkungsname.setText("(" + gemarkungsname + ")");
            cbParcels1.getEditor().getEditorComponent().setBackground(Color.WHITE);
        } else {
            final int foundBeanIndex = ObjectRendererUtils.findComboBoxItemForString(
                    cbParcels1,
                    String.valueOf(selection));
            if (foundBeanIndex < 0) {
                if (createEnabled) {
                    cbParcels2.setModel(new DefaultComboBoxModel());
                    try {
                        Integer.parseInt(String.valueOf(selection));
                        cbParcels1.getEditor().getEditorComponent().setBackground(Color.YELLOW);
                        cbParcels2.setEnabled(true);
                        if (CB_EDITED_ACTION_COMMAND.equals(evt.getActionCommand())) {
                            cbParcels2.requestFocusInWindow();
                        }
                    } catch (Exception notANumberEx) {
                        if (log.isDebugEnabled()) {
                            log.debug(selection + " is not a number!", notANumberEx);
                        }
                        cbParcels2.setEnabled(false);
                        cbParcels1.getEditor().getEditorComponent().setBackground(Color.RED);
                        lblGemarkungsname.setText("(Ist keine Zahl)");
                    }
                    lblGemarkungsname.setText(" ");
                } else {
                    cbParcels1.getEditor().getEditorComponent().setBackground(Color.RED);
                    cbParcels2.setEnabled(false);
                }
            } else {
                cbParcels1.setSelectedIndex(foundBeanIndex);
                cbParcels2.getEditor().getEditorComponent().setBackground(Color.WHITE);
                cbParcels3.getEditor().getEditorComponent().setBackground(Color.WHITE);
            }
        }
    } //GEN-LAST:event_cbParcels1ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnFlurstueckAddMenCancelActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnFlurstueckAddMenCancelActionPerformed
        setVisible(false);
        cancelHook();
    }                                                                                             //GEN-LAST:event_btnFlurstueckAddMenCancelActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void btnFlurstueckAddMenOkActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_btnFlurstueckAddMenOkActionPerformed
        final Object selection = cbParcels3.getSelectedItem();
        if (selection instanceof LightweightMetaObject) {
            final CidsBean selectedBean = ((LightweightMetaObject)selection).getBean();
            if (currentListToAdd != null) {
                final int position = Collections.binarySearch(
                        currentListToAdd,
                        selectedBean,
                        AlphanumComparator.getInstance());
                if (position < 0) {
                    currentListToAdd.add(-position - 1, selectedBean);
                }
            }
        } else if ((selection instanceof String) && createEnabled) {
            final int result = JOptionPane.showConfirmDialog(
                    this,
                    "Das Flurstück befindet sich nicht im Datenbestand der aktuellen Flurstücke. Soll es als historisch angelegt werden?",
                    "Historisches Flurstück anlegen",
                    JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                CidsBean beanToAdd = landParcelBeanFromComboBoxes(selection.toString());
                if (beanToAdd != null) {
                    final int position = Collections.binarySearch(
                            currentListToAdd,
                            beanToAdd,
                            AlphanumComparator.getInstance());
                    if (position < 0) {
                        try {
                            if (MetaObject.NEW == beanToAdd.getMetaObject().getStatus()) {
                                beanToAdd = beanToAdd.persist();
                            }
                            currentListToAdd.add(-position - 1, beanToAdd);
                        } catch (Exception ex) {
                            log.error(ex, ex);
                        }
                    }
                }
            }
        }
        setVisible(false);
        okHook();
    }                                                                                         //GEN-LAST:event_btnFlurstueckAddMenOkActionPerformed

    /**
     * DOCUMENT ME!
     */
    public void okHook() {
    }
    /**
     * DOCUMENT ME!
     */
    public void cancelHook() {
    }

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbParcels2ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbParcels2ActionPerformed
        final Object selection = cbParcels2.getSelectedItem();
        if (selection instanceof MetaObject) {
            final String selGem = String.valueOf(cbParcels1.getSelectedItem());
            final StringBuffer selFlurNr = new StringBuffer(String.valueOf(cbParcels2.getSelectedItem()));
            while (selFlurNr.length() < 3) {
                selFlurNr.insert(0, 0);
            }
            btnFlurstueckAddMenOk.setEnabled(false);
            cbParcels2.getEditor().getEditorComponent().setBackground(Color.WHITE);
            CismetThreadPool.execute(new AbstractFlurstueckComboModelWorker(
                    cbParcels3,
                    CB_EDITED_ACTION_COMMAND.equals(evt.getActionCommand())) {

                    @Override
                    protected ComboBoxModel doInBackground() throws Exception {
                        return new DefaultComboBoxModel(
                                FlurstueckFinder.getLWFurstuecksZaehlerNenner(selGem, selFlurNr.toString()));
                    }
                });
        } else {
            String selString = String.valueOf(selection);
            while (selString.length() < 3) {
                selString = "0" + selString;
            }
            final int foundBeanIndex = ObjectRendererUtils.findComboBoxItemForString(cbParcels2, selString);
            if (foundBeanIndex < 0) {
                if (createEnabled) {
                    cbParcels2.getEditor().getEditorComponent().setBackground(Color.YELLOW);
                    cbParcels3.setModel(new DefaultComboBoxModel());
                    cbParcels3.setEnabled(true);
                    if (CB_EDITED_ACTION_COMMAND.equals(evt.getActionCommand())) {
                        cbParcels3.requestFocusInWindow();
                    }
                } else {
                    cbParcels2.getEditor().getEditorComponent().setBackground(Color.RED);
                    cbParcels3.setModel(new DefaultComboBoxModel());
                    cbParcels3.setEnabled(false);
                }
            } else {
                cbParcels2.setSelectedIndex(foundBeanIndex);
                cbParcels3.getEditor().getEditorComponent().setBackground(Color.WHITE);
            }
        }
    } //GEN-LAST:event_cbParcels2ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @param  evt  DOCUMENT ME!
     */
    private void cbParcels3ActionPerformed(final java.awt.event.ActionEvent evt) { //GEN-FIRST:event_cbParcels3ActionPerformed
        btnFlurstueckAddMenOk.setEnabled(checkFlurstueckSelectionComplete());
        if (CB_EDITED_ACTION_COMMAND.equals(evt.getActionCommand())) {
            btnFlurstueckAddMenOk.requestFocusInWindow();
        }
        final Component editor = cbParcels3.getEditor().getEditorComponent();
        if (cbParcels3.getSelectedItem() instanceof MetaObject) {
            editor.setBackground(Color.WHITE);
        } else {
            String parcelNo = String.valueOf(cbParcels3.getSelectedItem());
            if (!parcelNo.contains("/")) {
                parcelNo += "/0";
                if (editor instanceof JTextField) {
                    final JTextField textEditor = (JTextField)editor;
                    textEditor.setText(parcelNo);
                }
            }
            final int foundBeanIndex = ObjectRendererUtils.findComboBoxItemForString(cbParcels3, parcelNo);
            if (foundBeanIndex < 0) {
                if (createEnabled) {
                    cbParcels3.getEditor().getEditorComponent().setBackground(Color.YELLOW);
                } else {
                    cbParcels3.getEditor().getEditorComponent().setBackground(Color.RED);
                }
            } else {
                cbParcels3.setSelectedIndex(foundBeanIndex);
            }
        }
    }                                                                              //GEN-LAST:event_cbParcels3ActionPerformed

    /**
     * DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private boolean checkFlurstueckSelectionComplete() {
        if (cbParcels2.isEnabled() && cbParcels3.isEnabled()) {
            final Object sel2 = cbParcels2.getSelectedItem();
            final Object sel3 = cbParcels3.getSelectedItem();
            if ((sel2 != null) && (sel3 != null)) {
                if (createEnabled || (sel3 instanceof MetaObject)) {
                    if ((sel2.toString().length() > 0) && (sel3.toString().length() > 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * DOCUMENT ME!
     *
     * @param   zaehlerNenner  DOCUMENT ME!
     *
     * @return  DOCUMENT ME!
     */
    private CidsBean landParcelBeanFromComboBoxes(final String zaehlerNenner) {
        int result = JOptionPane.YES_OPTION;
        try {
            final Map<String, Object> newLandParcelProperties = TypeSafeCollections.newHashMap();
            final String gemarkung = String.valueOf(cbParcels1.getSelectedItem());
            final String flur = String.valueOf(cbParcels2.getSelectedItem());
            if (flur.length() != 3) {
                result = JOptionPane.showConfirmDialog(
                        this,
                        "Das neue Flurstück entspricht nicht der Namenskonvention: Flur sollte dreistellig sein (mit führenden Nullen, z.B. 007). Datensatz trotzdem abspeichern?",
                        "Warnung: Format",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);
            }
            if (result == JOptionPane.YES_OPTION) {
                final String[] zaehlerNennerTiles = zaehlerNenner.split("/");
                final String zaehler = zaehlerNennerTiles[0];
                newLandParcelProperties.put(FlurstueckFinder.FLURSTUECK_GEMARKUNG, Integer.valueOf(gemarkung));
                newLandParcelProperties.put(FlurstueckFinder.FLURSTUECK_FLUR, flur);
                newLandParcelProperties.put(FlurstueckFinder.FLURSTUECK_ZAEHLER, zaehler);
                String nenner = "0";
                if (zaehlerNennerTiles.length == 2) {
                    nenner = zaehlerNennerTiles[1];
                }
                newLandParcelProperties.put(FlurstueckFinder.FLURSTUECK_NENNER, nenner);
                // the following code tries to avoid the creation of multiple entries for the same landparcel. however,
                // there *might* be a chance that a historic landparcel is created multiple times when more then one
                // client creates the same parcel at the "same time".
                final MetaObject[] searchResult = FlurstueckFinder.getLWLandparcel(gemarkung, flur, zaehler, nenner);
                if ((searchResult != null) && (searchResult.length > 0)) {
                    return searchResult[0].getBean();
                } else {
//                    final String compountParcelData = gemarkung + "-" + flur + "-" + zaehler + "/" + nenner;
//                    CidsBean newBean = unpersistedHistoricLandparcels.get(compountParcelData);
//                    if (newBean == null) {
                    final CidsBean newBean = CidsBeanSupport.createNewCidsBeanFromTableName(
                            FlurstueckFinder.FLURSTUECK_TABLE_NAME,
                            newLandParcelProperties);
//                        unpersistedHistoricLandparcels.put(compountParcelData, newBean);
//                    }
                    return newBean;
                }
            }
        } catch (Exception ex) {
            log.error(ex, ex);
        }
        return null;
    }

    //~ Inner Classes ----------------------------------------------------------

    /**
     * DOCUMENT ME!
     *
     * @version  $Revision$, $Date$
     */
    abstract class AbstractFlurstueckComboModelWorker extends SwingWorker<ComboBoxModel, Void> {

        //~ Instance fields ----------------------------------------------------

        private final JComboBox box;
        private final boolean switchToBox;

        //~ Constructors -------------------------------------------------------

        /**
         * Creates a new AbstractFlurstueckComboModelWorker object.
         *
         * @param  box          DOCUMENT ME!
         * @param  switchToBox  DOCUMENT ME!
         */
        public AbstractFlurstueckComboModelWorker(final JComboBox box, final boolean switchToBox) {
            this.box = box;
            this.switchToBox = switchToBox;
            box.setVisible(true);
            box.setEnabled(false);
            box.setModel(WAIT_MODEL);
        }

        //~ Methods ------------------------------------------------------------

        @Override
        protected void done() {
            try {
                box.setModel(get());
                if (switchToBox) {
                    box.requestFocus();
                }
            } catch (InterruptedException ex) {
                if (log.isDebugEnabled()) {
                    log.debug(ex, ex);
                }
            } catch (ExecutionException ex) {
                log.error(ex, ex);
            } finally {
                box.setEnabled(true);
                ObjectRendererUtils.selectAllTextInEditableCombobox(box);
            }
        }
    }
}