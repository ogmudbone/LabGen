package run;

import renderer.HtmlRenderer;

import javax.script.ScriptException;

public class Main
{

    public static void main(String[] args) throws ScriptException {

        if(args.length >= 2){

            String docPath = args[0];
            String outPath = args[1];

            int index = Math.max(docPath.lastIndexOf('\\'), docPath.lastIndexOf('/'));

            String rootPath = docPath.substring(0, index);
            String docRelativePath = docPath.substring(index + 1);

            HtmlRenderer renderer = new HtmlRenderer(rootPath);
            renderer.render(docRelativePath, outPath);
        }
        else
            System.err.print("Invalid program arguments");
    }

}
