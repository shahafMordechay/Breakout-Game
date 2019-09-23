package other;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.io.InputStream;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Parsing a color or image by a given string.
 *
 * @author Shahaf Mordechay
 */
public class ColorsParser {

    /**
     * Parse color definition and return the specified color.
     *
     * @param s color represented by string.
     * @return a new color.
     */
    public static Color colorFromString(String s) {
        if (s.contains("RGB")) {
            String rgb = s.split("\\(")[2].split("\\)")[0];
            String[] splittedRGB = rgb.split(",");
            int r = Integer.parseInt(splittedRGB[0]);
            int g = Integer.parseInt(splittedRGB[1]);
            int b = Integer.parseInt(splittedRGB[2]);

            return new Color(r, g, b);
        } else if (s.contains("color")) {
            String colorName = s.split("\\(")[1].split("\\)")[0];
            try {
                // try to get a color by name using reflection
                final Field f = Color.class.getField(colorName);

                return (Color) f.get(null);
            } catch (Exception ce) {
                // if we can't get any color return black
                return Color.black;
            }
        }

        return null;
    }

    /**
     * Parse image definition and return the specified image.
     *
     * @param s image represented by string.
     * @return a new image.
     */
    public static Image imageFromString(String s) {
        if (s.contains("image")) {
            String fileName = s.split("\\(")[1].split("\\)")[0];

            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
            try {
                return ImageIO.read(is);
            } catch (IOException e) {
                System.err.println("Failed reading image");
                e.printStackTrace(System.err);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    System.err.println("Failed closing file");
                }
            }
        }

        return null;
    }
}
