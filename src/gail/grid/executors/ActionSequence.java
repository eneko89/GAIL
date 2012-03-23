/*
 * This file is part of GAIL.
 *
 * GAIL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GAIL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with GAIL.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright © 2011 Eneko Sanz Blanco <nkogear@gmail.com>
 *
 */

package gail.grid.executors;

import gail.grid.GridElement;
import gail.grid.animations.Animation;
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
                Animation actionAnimation =
                                       targetElem.getActionAnimation(actionName);
                Point finalPosition = actionAnimation.animate(targetElem);
                if (finalPosition != null)
                    targetElem.setPositionOnGrid(finalPosition);
                actionAnimation.await();
                if (finalPosition == null)
                    targetElem.getGrid().remove(targetElem);
            }
        });
    }

    /**
     * Concurrently execute this actions into this sequence.
     * 
     * The first action parameter should be the one whose animation takes most
     * time, because it will be the one for which this sequence will wait until
     * it ends to continue.
     * 
     * The GridElements must be attached (added) to a Grid.
     * 
     * @param targetElem
     * @param actionName 
     */
    public void executeConcurrent(final Action... actions) {
        Runnable concurrentSubmission = new Runnable() {
            public void run() {
                execute(actions[0].gridElement, actions[0].actionName);
                for(int i = 1; i < actions.length; i++) {
                    final GridElement targetElem = actions[i].gridElement;
                    final String actionName = actions[i].actionName;
                    Runnable runnableAction = new Runnable() {
                        public void run() {
                            Animation actionAnimation = 
                                       targetElem.getActionAnimation(actionName);
                            Point finalPosition = actionAnimation
                                                            .animate(targetElem);
                            if (finalPosition != null)
                                targetElem.setPositionOnGrid(finalPosition);
                            actionAnimation.await();
                            if (finalPosition == null)
                                targetElem.getGrid().remove(targetElem);
                        }
                    };
                    new Thread(runnableAction).start();
                }                
            }
        };
        executor.submit(concurrentSubmission);
    }
    
    /**
     * Concurrently execute this actions on the given element into this sequence.
     * 
     * The first action parameter should be the one whose animation takes most
     * time, because it will be the one for which this sequence will wait until
     * it ends to continue.
     * 
     * The GridElement must be attached (added) to a Grid.
     * 
     * @param targetElem
     * @param actionName 
     */
    public void executeConcurrent(final GridElement targetElem,
                                  final String... actionNames) {
        Action[] actions = new Action[actionNames.length];
        int index = 0;
        for(String str : actionNames) {
            actions[index] = new Action(targetElem, str);
            index++;
        }
        executeConcurrent(actions);
    }
    
    public class Action {

        GridElement gridElement;
        String actionName;

        public Action(GridElement gridElement, String actionName) {
            this.gridElement = gridElement;
            this.actionName = actionName;
        }
        
    }
    
}
