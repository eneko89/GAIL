/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.jdesktop.core.animation.timing.Animator;
import org.jdesktop.core.animation.timing.PropertySetter;
import org.jdesktop.core.animation.timing.TimingTarget;
import org.jdesktop.core.animation.timing.TimingTargetAdapter;
import org.jdesktop.core.animation.timing.interpolators.AccelerationInterpolator;
import org.jdesktop.swing.animation.timing.sources.SwingTimerTimingSource;

/**
 *
 * @author eneko
 */
public class PresentationScreen extends JPanel{
    
    private BufferedImage logo;
    private JXLabel label;
    private JFrame attachedFrame;
    private float opacity = 0.8f;
    private Animator blinkAnimator;
    private static final int topMargin = 40;
    private static final SwingTimerTimingSource timingSource =
                                                  new SwingTimerTimingSource();

    static {
        timingSource.init();
    }
    
    public PresentationScreen(final String text, final BufferedImage logo) {
        this.label = new JXLabel();
        this.logo = logo;
        label.setText(text);
        setLayout(null);
        setOpaque(false);
        label.setVerticalAlignment(JLabel.CENTER);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setLocation(0, topMargin);
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                label.setSize(getWidth(), getHeight() - topMargin);
            }
        });
        label.setFont(label.getFont().deriveFont(18f));
        this.add(label);
        label.setForeground(Color.darkGray);
        textBlink();
    }        

    public void attach(JFrame f) {
        attachedFrame = f;
    }
    
    public void showScreen() {
        attachedFrame.setGlassPane(this);
        this.setVisible(true);
        label.setVisible(true);
        this.setSize(attachedFrame.getSize());
    }
    
    public void hideScreen() {
        Animator animator = new Animator.Builder(timingSource)
                        .setInterpolator(new AccelerationInterpolator(0.2, 0.7))
                        .setDuration(600, TimeUnit.MILLISECONDS)
                        .build();
        TimingTarget setter = PropertySetter.getTarget(this,
                                                       "opacity", opacity, 0f);
        animator.addTarget(setter);
        animator.addTarget(new TimingTargetAdapter() {
            @Override
            public void end(Animator source) {
                setVisible(false);
                timingSource.dispose();
            }
            @Override
            public void timingEvent(Animator source, double fraction) {
                repaint();
            }   
        });
        label.setVisible(false);
        cancelTextBlink();
        animator.start();
    }
    
    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                            RenderingHints.VALUE_RENDER_QUALITY);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                    opacity));
        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.drawImage(logo,
                     (attachedFrame.getWidth() / 2) - (logo.getWidth() / 2),
                     topMargin,
                     null);
    }
    
    private void textBlink() {
        blinkAnimator = new Animator.Builder(timingSource)
                .setInterpolator(new AccelerationInterpolator(0.2, 0.2))
                .setDuration(1500, TimeUnit.MILLISECONDS)
                .setRepeatCount(Animator.INFINITE)
                .build();
        TimingTarget setter = PropertySetter.getTarget(label, "opacity",
                                                       1f, 0.2f, 1f);
        blinkAnimator.addTarget(setter);
        blinkAnimator.addTarget(new TimingTargetAdapter() {
            @Override
            public void timingEvent(Animator source, double fraction) {
                label.repaint();
            }   
        });
        blinkAnimator.start();
    }
    
    private void cancelTextBlink() {
        if (blinkAnimator != null)
            blinkAnimator.cancel();
    }
    
    public class JXLabel extends JLabel {
        
        private float opacity = 1f;
        
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                                RenderingHints.VALUE_RENDER_QUALITY);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
                                                        opacity));
            super.paintComponent(g);
        }

        public float getOpacity() {
            return opacity;
        }

        public void setOpacity(float opacity) {
            this.opacity = opacity;
        }
    
    }
    
}
