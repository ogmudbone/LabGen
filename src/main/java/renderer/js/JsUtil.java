package renderer.js;

import jdk.nashorn.api.scripting.ScriptObjectMirror;
import renderer.PreprocessHandler;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class JsUtil {

    private static PreprocessHandler handler;

    public static Object parseJsVar(Object jsVar){

        if(jsVar instanceof ScriptObjectMirror) {

            String objectClass = ((ScriptObjectMirror) jsVar).getClassName();

            if(objectClass.equals("Array"))
                jsVar = JSObjectToList(((ScriptObjectMirror) jsVar));
            else
                jsVar = JSObjectToMap((ScriptObjectMirror) jsVar);

        }

        return jsVar;

    }

    private static List<Object> JSObjectToList(ScriptObjectMirror object){

        return object.entrySet().stream().
                map(element -> parseJsVar(element.getValue()))
                .collect(Collectors.toList());

    }

    private static Map<String, Object> JSObjectToMap(ScriptObjectMirror object){

        Map<String, Object> result = new HashMap<>();

        for(String key : object.keySet()) {
            result.put(key, parseJsVar(object.getMember(key)));
        }

        return result;

    }

    public static void init(PreprocessHandler handler){
        handler.getScriptHandler().evalFile(
                handler.getResourceManager().getResource("BaseScript.js")
        );
        JsUtil.handler = handler;
    }

    public static void echo(String code){
        handler.echo(code);
    }

    public static void echo(String code, ScriptObjectMirror bundle){
        handler.getTwigProcessor().getModel().addBundle(JSObjectToMap(bundle));
        echo(code);
        handler.getTwigProcessor().getModel().removeBundle();
    }

    public static void include(String fileName){
        handler.process(fileName);
    }

    public static void include(String fileName, ScriptObjectMirror bundle){
        handler.getTwigProcessor().getModel().addBundle(JSObjectToMap(bundle));
        handler.process(fileName);
        handler.getTwigProcessor().getModel().removeBundle();
    }

    public static void includeScript(String scriptName){
        handler.getScriptHandler().evalFile(
                new File(
                        handler.getResourceManager().getAbsoluteFilePath(scriptName)
                )
        );
    }

    public static void bufferStart(){
        handler.startNewBuffer();
    }

    public static String bufferFlush(){
        return handler.flushBuffer();
    }

}
