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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author eneko
 */
public class ActionLoop {
    
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private int iterations;
    private int initialDelay = 0;
    private LinkedList<GridElement> elementList = new LinkedList<GridElement>();
    private LinkedList<String> actionList = new LinkedList<String>();

    /**
     * Create an executor that will execute a sequence of actions 
     * "repeat" times.
     * 
     * If repeat = 0, it will be executed indefinitely.
     * 
     * @param repeat number of times the actions will be executed
     */
    public ActionLoop(int repeat) {
        this.iterations = repeat;
    }
    
    /**
     * Create an executor that will execute a sequence of actions 
     * "repeat" times.
     * 
     * If repeat = 0, the sequence will be executed indefinitely.
     * 
     * @param repeat number of times the actions will be executed
     * @param initialDelay initial delay in milliseconds
     */
    public ActionLoop(final int initialDelay, int repeat) {
        this.iterations = repeat;
        this.initialDelay = initialDelay;
    }

    public void setInitialDelay(int initialDelay) {
        this.initialDelay = initialDelay;
    }
    
    /**
     * Execute an action on the GridElement passed as parameter.
     * 
     * The GridElement must be attached (added) to a Grid.
     * 
     * @param targetElem
     * @param actionName 
     */
    public void execute(final GridElement targetElem, final String actionName) {
        elementList.add(targetElem);
        actionList.add(actionName);
    }
    
    /**
     * Starts the execution of the submitted actions in a loop.
     * 
     * If the ActionLoop is not infinite (repeat = 0), after loop() is
     * called the ActionLoop can be reused (execute more actions and call
     * loop() again).
     */
    public void loop() {
        if (initialDelay > 0)
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
        if (iterations == 0)
            executeIndefinitely();
        else {
            int i = iterations;
            while (i > 0) {
                Iterator<GridElement> elementIterator = elementList.iterator();
                Iterator<String> actionIterator = actionList.iterator();
                while(elementIterator.hasNext()) {
                    submitToExecutor(elementIterator.next(), actionIterator.next());
                }
            i--;
            }
            elementList.clear();
            actionList.clear();
        }
    }
    
    private void submitToExecutor(final GridElement targetElem,
                                  final String actionName) {
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
    
    private void executeIndefinitely() {
        executor.submit(new Runnable() {
            public void run() {
                while(true) {
                    Iterator<GridElement> elementIterator = elementList.iterator();
                    Iterator<String> actionIterator = actionList.iterator();
                    while (elementIterator.hasNext()) {
                        GridElement elem = elementIterator.next();
                        Animation animation = elem.getActionAnimation(actionIterator.next());
                        Point finalPosition = animation.animate(elem);
                        if (finalPosition != null)
                            elem.setPositionOnGrid(finalPosition);
                        animation.await();
                        if (finalPosition == null)
                            elem.getGrid().remove(elem);
                    }
                }
            }
        });
    }
    
}
