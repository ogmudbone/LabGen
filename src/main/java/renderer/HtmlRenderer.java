package renderer;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import org.w3c.dom.Document;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

public class HtmlRenderer {

    private PreprocessHandler preprocessHandler;

    public HtmlRenderer(String rootPath){
        preprocessHandler = new PreprocessHandler(rootPath);
    }

    public void render(String input, String outPath, String debugHtmlPath){

        String processedHtml = preprocessHandler.processRoot(input);

        OutputStream os;
        try {

            os = new FileOutputStream(outPath);

            ITextRenderer renderer = new ITextRenderer();



            renderer.getFontResolver().addFont("C:/Windows/Fonts/times.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            if(debugHtmlPath != null) {
                FileOutputStream stream = new FileOutputStream(debugHtmlPath);
                stream.write(processedHtml.getBytes());
                stream.close();
            }

            Document doc = builder.parse(
                    new InputSource(new StringReader(processedHtml))
            );

            renderer.setDocument(doc, preprocessHandler.getResourceManager().getRootDirFileUrl());
            renderer.layout();
            renderer.createPDF(os);
            os.close();

        } catch (DocumentException | SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

    }

}
