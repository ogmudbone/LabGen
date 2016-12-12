package renderer;

import javax.script.ScriptEngine;
import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class BaseScriptHandler {

    private ScriptEngine engine;

    protected abstract ScriptEngine getEngine();

    public BaseScriptHandler(){
        engine = getEngine();
    }

    protected Object onGetVar(Object var){
        return var;
    }

    public void eval(String script){
        try {
            engine.eval(script);
        } catch (ScriptException e) {
            e.printStackTrace();
        }
    }

    public void evalFile(File file){

        try {
            engine.eval(new FileReader(file));
        } catch (ScriptException | FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public Object getVar(String varName){
        try {
            return onGetVar(
                    engine.get(varName)
            );
        }catch (IllegalArgumentException e){
            return null;
        }
    }

}
