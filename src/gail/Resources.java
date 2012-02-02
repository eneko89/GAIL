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

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author eneko
 */
public class Resources {
    
    public enum Robot {
        BLACK,
        BLUE,
        GRAY,
        GREEN,
        RED
    }
    
    public enum Target {
        BLACK,
        BLUE,
        GRAY,
        GREEN,
        RED
    }
    
    public enum Block {
        BLACK,
        BLUE,
        GRAY,
        GREEN,
        RED
    }

    public static BufferedImage getRobot(Robot robot) {
        return Resources.loadImageResource("/gail/resources/"
                                            + robot.name().toLowerCase()
                                            + "robot.png");
    }

    public static BufferedImage getTarget(Target target) {
        return Resources.loadImageResource("/gail/resources/"
                                            + target.name().toLowerCase()
                                            + "target.png");
    }

    public static BufferedImage getBlock(Block block) {
        return Resources.loadImageResource("/gail/resources/"
                                            + block.name().toLowerCase()
                                            + "block.png");
    }

    private static BufferedImage loadImageResource(String resourceLocation) {
        URL url = Resources.class.getResource(resourceLocation);
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException ex) {
            Logger.getLogger(Resources.class.getName()).log(Level.SEVERE,
                                                            null,
                                                            ex);
        }
        return img;
    }

}
