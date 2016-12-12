package renderer.twig;

import com.google.common.base.Optional;
import org.jtwig.JtwigModel;
import org.jtwig.reflection.model.Value;
import renderer.BaseScriptHandler;

import java.util.Map;
import java.util.Stack;

public class ScriptVarModel extends JtwigModel {

    private BaseScriptHandler handler;
    private Stack<ModelParamsBundle> bundleStack = new Stack<>();

    public ScriptVarModel(BaseScriptHandler handler) {
        this.handler = handler;
    }

    public void addBundle(Map<String, Object> binding){
        bundleStack.add(new ModelParamsBundle(binding));
    }

    public void removeBundle(){
        if(bundleStack.size() != 0)
            bundleStack.pop();
    }

    private Optional<Value> getFromBundle(String key){
        @SuppressWarnings("unchecked cast")
        Stack<ModelParamsBundle> bundleStackCopy = (Stack<ModelParamsBundle>) bundleStack.clone();

        while(bundleStackCopy.size() != 0){
            Optional<Value> value = bundleStackCopy.pop().get(key);
            if(value.isPresent())
                return value;
        }

        return  Optional.absent();

    }

    @Override
    public Optional<Value> get(String key){

        Optional<Value> bindingValue = getFromBundle(key);

        if(bindingValue.isPresent())
            return bindingValue;

        Optional<Value> parentValue = super.get(key);

        if(parentValue.isPresent())
            return parentValue;
        else {
            Object var = handler.getVar(key);
            return var != null ?
                    Optional.of(new Value(var)) :
                    Optional.absent();
        }

    }

}
