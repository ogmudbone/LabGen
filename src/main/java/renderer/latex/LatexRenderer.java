package renderer.latex;

import org.scilab.forge.jlatexmath.TeXFormula;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class LatexRenderer {

    Color backgroundColor;
    Color color;
    int style;
    int fontSize;

    LatexRenderer(){

    }

    private String encodeToString(BufferedImage image, String type) {
        String imageString = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, type, bos);
            byte[] imageBytes = bos.toByteArray();

            BASE64Encoder encoder = new BASE64Encoder();
            imageString = encoder.encode(imageBytes);

            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageString;

    }

    public String toBase64(String latex){

        TeXFormula formula = new TeXFormula(latex);

        BufferedImage img = (BufferedImage)formula.createBufferedImage(
                style, fontSize, color, backgroundColor
        );


        return this.encodeToString(img, "gif");

    }

}
