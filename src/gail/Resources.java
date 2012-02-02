/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
