package renderer.twig;

import org.jtwig.JtwigTemplate;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import renderer.ProcessorInterface;

public class TwigProcessor implements ProcessorInterface {

    private ScriptVarModel model;

    public TwigProcessor(ScriptVarModel model){
        this.model = model;
    }

    public String process(String input){

        EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder.configuration()
                .parser()
                .syntax()
                .withStartCode("{%").withEndCode("%}")
                .withStartOutput("<%").withEndOutput("%>")
                .withStartComment("{#").withEndComment("#}")
                .and()
                .and()
                .build();

        JtwigTemplate jtwigTemplate = JtwigTemplate
                .inlineTemplate(input, configuration);

        return jtwigTemplate.render(model);

    }

    public ScriptVarModel getModel(){
        return model;
    }

}