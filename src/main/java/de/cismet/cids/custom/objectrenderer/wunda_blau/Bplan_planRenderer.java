/*
 * BPlanRenderer.java
 *
 * Created on 18. Februar 2008, 16:26
 */
package de.cismet.cids.custom.objectrenderer.wunda_blau;

import de.cismet.cids.annotations.CidsAttribute;
import de.cismet.cids.annotations.CidsRendererTitle;
import de.cismet.cids.custom.deprecated.IndentLabel;
import de.cismet.cids.tools.metaobjectrenderer.CoolPanel;
import java.awt.EventQueue;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * de.cismet.cids.objectrenderer.CoolBplaeneRenderer
 * @author  nh
 */
public class Bplan_planRenderer extends CoolPanel {

    private final org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(this.getClass());
    @CidsRendererTitle
    public String compTitle;
    @CidsAttribute("Status")
    public String status = "";
    @CidsAttribute("NAMEPLUS")
    public String nameplus = "";
    private ImageIcon bild;

    /** Creates new form BplaeneObjectRenderer */
    public Bplan_planRenderer() {
        initComponents();
        setPanContent(panContent);
        setPanTitle(null);
        setPanMap(null);
        setPanInter(null);
        setSpinner(null);
        
        jxivBild.addComponentListener(new ComponentListener() {

            public void componentHidden(ComponentEvent e) {
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                log.debug("Resized " + e.getComponent().getHeight() + " " + e.getComponent().getWidth());
            }

            public void componentShown(ComponentEvent e) {
                log.debug("Shown " + e.getComponent().getHeight() + " " + e.getComponent().getWidth());
            }
        });
    }

    private void resize(ComponentEvent evt) {
        log.info("formComponentShown " + jxivBild.getHeight());
        jxivBild.setScale(2.0);
    }

    @Override
    @SuppressWarnings("empty-statement")
    public void assignSingle() {
        try {
        compTitle = compTitle.substring(0, compTitle.indexOf("(")-1);
        }
        catch (Exception ArrayIndexOutOfBoundsException)
        {};
        if (status.equals("rechtskräftig")) {
            status = "rechtsverbindlich";
        } else {
            status = "nicht rechtsverbindlich";
        }
        lblTitle.setText(lblTitle.getText() + " - " + compTitle + " (" + status + ")");
        log.debug(lblTitle.getText());

        try {
            String host = "http://s10221.wuppertal-intra.de:80/";
            String btyp;
            String plan;
            String path;
            String r = (String) nameplus;
            btyp = r.substring(0, 1);

            plan = "B" + r.substring(1);
            path = "bplaene/images/rechtskraeftig/";
            if (btyp.equals("N")) {
                path = "bplaene/images/nicht_rechtskraeftig/";
            }

            final String url = host + path + plan + "_TEXT.gif";

            Thread loader = new Thread() {

                @Override
                public void run() {
                    try {
                        EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                jxivBild.setImage(new javax.swing.ImageIcon(getClass().getResource("/res/load.png")).getImage());
                            }
                        });


                        bild = new ImageIcon(new URL(url));
                        EventQueue.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                double bh = bild.getIconHeight();
                                double bw = bild.getIconWidth();
                                jxivBild.setImage(bild.getImage());
                                jxivBild.setScale(0.25);
                                jxivBild.setVisible(false);
                                jxivBild.setVisible(true);
                                revalidate();
                                double ch = jxivBild.getHeight();
                                double cw = jxivBild.getWidth();
                                double cs = jxivBild.getScale();
                                double bsh = bh / ch;
                                double bsw = bw / cw;
                                double sm = Math.max(bsh, bsw);
                                log.debug("EDT?:" + EventQueue.isDispatchThread());
                                log.info("bh: " + bh + " bw: " + bw + "ch: " + ch + "cw: " + cw + "cs:  " + cs + "sm:" + sm);
                                jxivBild.setEditable(true);
                                jxivBild.setDragEnabled(false);
                            }
                        });
                    } catch (Exception threadedException) {
                        threadedException.printStackTrace();
                    }

                }
            };
            loader.start();
        } catch (Exception ex) {
            ex.printStackTrace();


        }
    }

    @Override
    public double getWidthRatio() {
        return 1.0;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panTitle = new javax.swing.JPanel();
        lblTitle = new IndentLabel();
        panContent = new javax.swing.JPanel();
        jxivBild = new org.jdesktop.swingx.JXImageView();

        setLayout(new java.awt.BorderLayout());

        panTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 0, 0));
        panTitle.setOpaque(false);

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 18));
        lblTitle.setForeground(new java.awt.Color(51, 51, 51));
        lblTitle.setText("Bebauungsplan");

        javax.swing.GroupLayout panTitleLayout = new javax.swing.GroupLayout(panTitle);
        panTitle.setLayout(panTitleLayout);
        panTitleLayout.setHorizontalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTitleLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                .addContainerGap())
        );
        panTitleLayout.setVerticalGroup(
            panTitleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panTitleLayout.createSequentialGroup()
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblTitle.getAccessibleContext().setAccessibleName("Bauplan");

        add(panTitle, java.awt.BorderLayout.NORTH);

        panContent.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 20, 20, 20));
        panContent.setOpaque(false);
        panContent.setLayout(new java.awt.BorderLayout());

        jxivBild.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jxivBild.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                jxivBildMouseWheelMoved(evt);
            }
        });

        javax.swing.GroupLayout jxivBildLayout = new javax.swing.GroupLayout(jxivBild);
        jxivBild.setLayout(jxivBildLayout);
        jxivBildLayout.setHorizontalGroup(
            jxivBildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 436, Short.MAX_VALUE)
        );
        jxivBildLayout.setVerticalGroup(
            jxivBildLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 529, Short.MAX_VALUE)
        );

        panContent.add(jxivBild, java.awt.BorderLayout.CENTER);

        add(panContent, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    private void jxivBildMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_jxivBildMouseWheelMoved
        if (evt.getWheelRotation() < 0) {
            jxivBild.setScale(jxivBild.getScale() * 0.95);
        } else {
            jxivBild.setScale(jxivBild.getScale() * 1.05);
        }
    }//GEN-LAST:event_jxivBildMouseWheelMoved
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.JXImageView jxivBild;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JPanel panContent;
    private javax.swing.JPanel panTitle;
    // End of variables declaration//GEN-END:variables
}