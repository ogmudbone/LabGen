package renderer.js;

import renderer.BaseScriptHandler;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class JsHandler extends BaseScriptHandler{

    @Override
    protected ScriptEngine getEngine() {
        return  new ScriptEngineManager().getEngineByName("nashorn");
    }

    protected Object onGetVar(Object var){
        return JsUtil.parseJsVar(var);
    }

}