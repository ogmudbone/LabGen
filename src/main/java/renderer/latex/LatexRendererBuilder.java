package renderer.latex;

import org.scilab.forge.jlatexmath.TeXConstants;

import java.awt.*;

public class LatexRendererBuilder {

    private static final int DEFAULT_STYLE = TeXConstants.STYLE_DISPLAY;
    private static final Color DEFAULT_COLOR = Color.BLACK;
    private static final Color DEFAULT_BACKGROUND = Color.WHITE;
    private static final int DEFAULT_FONT_SIZE = 14;

    private int style = DEFAULT_STYLE;
    private Color color = DEFAULT_COLOR;
    private Color backgroundColor = DEFAULT_BACKGROUND;
    private int fontSize = DEFAULT_FONT_SIZE;

    public LatexRenderer build(){

        LatexRenderer renderer = new LatexRenderer();

        renderer.backgroundColor = backgroundColor;
        renderer.color = color;
        renderer.fontSize = fontSize;
        renderer.style = style;

        return renderer;

    }

    public LatexRendererBuilder setStyle(int style) {
        this.style = style;
        return this;
    }

    public LatexRendererBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public LatexRendererBuilder setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public LatexRendererBuilder setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

}
