package renderer;

import org.scilab.forge.jlatexmath.TeXConstants;
import renderer.latex.LatexRenderer;
import renderer.latex.LatexRendererBuilder;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

class LatexTagProcessor extends BaseTagProcessor {

    LatexTagProcessor(PreprocessHandler handler) {
        super(handler);
    }

    private static Map<String, String> getDefaultStyle(){

        Map<String, String> style = new HashMap<>();

        style.put("font-size", "16");
        style.put("color", "black");
        style.put("background", "white");
        style.put("style", "display");
        style.put("class", "");

        return style;

    }

    @Override
    protected String getTagName() {
        return "latex";
    }

    protected void onCommonTextFound(String commonText){
        echo(commonText);
    }

    private int parseStyleConst(String constString){

        switch (constString){

            case "display":
                return TeXConstants.STYLE_DISPLAY;
            case "text":
                return TeXConstants.STYLE_TEXT;
            case "script":
                return TeXConstants.STYLE_SCRIPT;
            case "script_script":
                return TeXConstants.STYLE_SCRIPT_SCRIPT;
            default:
                return TeXConstants.STYLE_DISPLAY;

        }

    }

    private String getImgLatexTag(String base64, Map<String, String> style){

        return "<img src=\"data:image/gif;base64," + base64 + "\" class=\"" + style.get("class") + "\"/>";

    }

    protected void onScriptFound(String script, Map<String, String> attributes){

        Map<String, String> style = getDefaultStyle();
        style.putAll(attributes);

        Color bg;
        Color color;
        int fontSize;
        int styleConst = parseStyleConst(style.get("style"));

        try {
            bg =  (Color) Color.class.getField(style.get("background")).get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            bg = Color.WHITE;
        }

        try {
            color =  (Color) Color.class.getField(style.get("color")).get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            color = Color.BLACK;
        }

        try{
            fontSize = Integer.valueOf(style.get("font-size"));
        }catch (NumberFormatException e){
            fontSize = 14;
        }

        LatexRenderer renderer = new LatexRendererBuilder()
                .setBackgroundColor(bg)
                .setColor(color)
                .setFontSize(fontSize)
                .setStyle(styleConst)
                .build();

        echo(getImgLatexTag(renderer.toBase64(script), style));

    }

}