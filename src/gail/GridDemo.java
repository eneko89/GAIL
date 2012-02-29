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

package gail;

import gail.grid.Grid;
import gail.grid.GridElement;
import gail.grid.Resources;
import gail.grid.Resources.Block;
import gail.grid.Resources.Robot;
import gail.grid.Resources.Target;
import gail.grid.animations.*;
import gail.grid.executors.ActionLoop;
import gail.grid.executors.ActionSequence;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import javax.swing.WindowConstants;

/**
 *
 * @author eneko
 */
public class GridDemo extends javax.swing.JFrame {

    private Point dragStart;

    /**
     * Creates an ExampleFrame
     */
    public GridDemo() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(700, 700));

        // Create an 8x8 grid.
        Grid g = new Grid(8, 8);
        g.setBackground(Color.white);
        g.setForeground(Color.lightGray);
        this.setContentPane(g);

        // Create an element and define actions on them.
        // Each needs one instance of an Animation, even if it's the same type.
        // Animations are easy to code (extending Animation class) but the
        // basic ones are already provided.
        GridElement elem = new GridElement(Resources.getRobot(Robot.BLUE));
        elem.defineAction("moveRight", new MoveRightAnimation());
        elem.defineAction("moveLeft", new MoveLeftAnimation());
        elem.defineAction("moveDown", new MoveDownAnimation());
        elem.defineAction("moveUp", new MoveUpAnimation());

        // Add it to the grid in (1,1).
        g.add(elem, new Point(1,1));

        // Create an ActionSequence to execute some actions on the element
        // sequentially, with an initial delay of 500ms.
        ActionSequence actions = new ActionSequence(500);
        actions.execute(elem, "moveRight");
        actions.execute(elem, "moveRight");
        actions.execute(elem, "moveDown");
        actions.execute(elem, "moveDown");
        actions.execute(elem, "moveLeft");
        actions.execute(elem, "moveLeft");
        actions.execute(elem, "moveUp");
        actions.execute(elem, "moveUp");

        // Create and add another element to the grid, and
        // animate it with another ActionSequence, so it will run
        // concurrently with the previously created ActionSequence.
        GridElement elem2 = new GridElement(Resources.getRobot(Robot.RED));
        g.add(elem2, new Point(4,4));
        elem2.defineAction("moveRight", new MoveRightAnimation());
        elem2.defineAction("moveLeft", new MoveLeftAnimation());
        elem2.defineAction("moveDown", new MoveDownAnimation());
        elem2.defineAction("moveUp", new MoveUpAnimation());
        ActionSequence actions2 = new ActionSequence(500);
        actions2.execute(elem2, "moveRight");
        actions2.execute(elem2, "moveRight");
        actions2.execute(elem2, "moveDown");
        actions2.execute(elem2, "moveDown");
        actions2.execute(elem2, "moveLeft");
        actions2.execute(elem2, "moveLeft");
        actions2.execute(elem2, "moveUp");
        actions2.execute(elem2, "moveUp");

        // Some elements more, this time static; without actions.
        GridElement elem3 = new GridElement(Resources.getBlock(Block.GRAY));
        GridElement elem4 = new GridElement(Resources.getBlock(Block.GRAY));
        g.add(elem3, new Point(2,2));
        g.add(elem4, new Point(5, 5));

        // We are going to add some elements more, this time with "Target"
        // look.
        GridElement elem5 = new GridElement(Resources.getTarget(Target.GRAY));
        GridElement elem6 = new GridElement(Resources.getTarget(Target.GRAY));
        g.add(elem5, new Point(1,1));
        g.add(elem6, new Point(4,4));

        // Now, ¿why not make targets blink a little? First we define the
        // action.
        elem5.defineAction("blink", new BlinkAnimation());
        elem6.defineAction("blink", new BlinkAnimation());

        // With ActionLoop you can make the same that you could with
        // ActionSequence but repeat it "n" times or indefinitely
        // if "repeat" parameter equals 0.
        ActionLoop actions3 = new ActionLoop(500, 0); // Infinite loop
        actions3.execute(elem5, "blink");
        actions3.loop();

        // We use two ActionLoops because we want them to run concurrently and
        // not sequentially
        ActionLoop actions4 = new ActionLoop(500, 0); //Infinite loop
        actions4.execute(elem6, "blink");
        actions4.loop();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GridDemo().setVisible(true);
            }
        });
    }

}