package renderer.twig;

import org.jtwig.JtwigModel;

import java.util.Map;

public class ModelParamsBundle extends JtwigModel{

    public ModelParamsBundle(Map<String, Object> bundle) {

        for (Map.Entry<String, Object> bundleElement : bundle.entrySet())
            with(bundleElement.getKey(), bundleElement.getValue());

    }

}