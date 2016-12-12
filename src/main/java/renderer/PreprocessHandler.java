package renderer;

import renderer.js.JsHandler;
import renderer.js.JsUtil;
import renderer.twig.ScriptVarModel;
import renderer.twig.TwigProcessor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Stack;

public class PreprocessHandler {

    private BaseScriptHandler handler;
    private TwigProcessor twigProcessor;
    private Stack<StringBuilder> output;
    private LatexTagProcessor latexTagProcessor;
    private ResourceManager resourceManager;

    public BaseScriptHandler getScriptHandler(){
        return handler;
    }

    public TwigProcessor getTwigProcessor(){
        return twigProcessor;
    }

    PreprocessHandler(String rootPath){
        handler = new JsHandler();
        twigProcessor = new TwigProcessor(new ScriptVarModel(handler));
        latexTagProcessor = new LatexTagProcessor(this);
        resourceManager = new ResourceManager(rootPath);
        JsUtil.init(this);
    }

    private ProcessorInterface[] getProcessorsStack(){
        return new ProcessorInterface[]{twigProcessor, latexTagProcessor};
    }

    private String getFileAsString(String path){

        byte[] encoded = null;
        try {
            encoded = Files.readAllBytes(Paths.get(
                    getResourceManager().getAbsoluteFilePath(path)
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert encoded != null;
        return new String(encoded);

    }

    public void process(String docPath){
        processString(getFileAsString(docPath));
    }

    public void processString(String string){
        DocProcessor.process(string, this, getProcessorsStack());
    }

    public String processRoot(String docPath){

        output = new Stack<>();
        output.push(new StringBuilder());

        process(docPath);
        return output.firstElement().toString();

    }

    public void startNewBuffer(){
        output.push(new StringBuilder());
    }

    public String flushBuffer(){
        if(output.size() >= 2) {
            String buffer =  output.pop().toString();

            ProcessorInterface[] processStack = getProcessorsStack();

            for(ProcessorInterface processor : processStack)
                buffer = processor.process(buffer);

            return buffer;

        }
        else
            return "";
    }

    public void echo(String string){
        output.peek().append(twigProcessor.process(string));
    }

    public LatexTagProcessor getLatexTagProcessor() {
        return latexTagProcessor;
    }


    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}