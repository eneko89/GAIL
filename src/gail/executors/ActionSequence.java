/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gail.executors;

import gail.animations.Animation;
import gail.GridElement;
import java.awt.Point;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eneko
 */
public class ActionSequence {
    
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public ActionSequence() {
    }
    
    /**
     * Initial delay of this sequence in milliseconds
     * 
     * @param delay initial delay in milliseconds
     */
    public ActionSequence(final int initialDelay) {
        executor.submit(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(initialDelay);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ActionSequence.class.getName()).log(
                                     Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    /**
     * Sequentially execute an action on the GridElement passed as parameter.
     * 
     * The GridElement must be attached (added) to a Grid.
     * 
     * @param targetElem
     * @param actionName 
     */
    public void execute(final GridElement targetElem, final String actionName) {
        executor.submit(new Runnable() {
            public void run() {
                Animation actionAnimation = targetElem.getActionAnimation(actionName);
                Point finalPosition = actionAnimation.animate(targetElem);
                if (finalPosition != null)
                    targetElem.setPositionOnGrid(finalPosition);
                actionAnimation.await();
                if (finalPosition == null)
                    targetElem.getGrid().remove(targetElem);
            }
        });
    }
    
}
