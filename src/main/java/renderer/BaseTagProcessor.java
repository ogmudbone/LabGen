package renderer;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseTagProcessor implements ProcessorInterface {

    protected abstract String getTagName();

    private PreprocessHandler handler;
    private StringBuilder output;

    protected void onCommonTextFound(String commonText){

    }

    protected void onScriptFound(String script, Map<String, String> attributes){

    }

    BaseTagProcessor(PreprocessHandler handler){
        this.handler = handler;
    }

    public String process(String inputText){

        int index = 0;
        output = new StringBuilder();

        AttributeParser attributeParser = new AttributeParser();

        // ↓
        // <tag ... >
        int tagBeginIndex = inputText.indexOf("<" + getTagName());
        Map<String, String> attributes;

        while (tagBeginIndex != -1){

            attributes = attributeParser.parse(
                    inputText.substring(
                            tagBeginIndex + getTagName().length() + 1, inputText.indexOf('>', tagBeginIndex)
                    )
            );

            onCommonTextFound(inputText.substring(index, tagBeginIndex));
            onScriptFound(
                    inputText.substring(
                            inputText.indexOf('>',                      tagBeginIndex) + 1,
                            inputText.indexOf("</" + getTagName() + ">", tagBeginIndex)
                    ),
                    attributes
            );

            index =  inputText.indexOf("</" + getTagName() + ">", tagBeginIndex) + getTagName().length() + 3;
            tagBeginIndex = inputText.indexOf("<" + getTagName(), index);

        }

        if(index < inputText.length())
            onCommonTextFound(inputText.substring(index));

        String outputString = output.toString();
        output = null;
        return outputString;

    }

    protected void echo(String content){
        output.append(content);
    }

    protected PreprocessHandler getHandler() {
        return handler;
    }

    private static class AttributeParser{

        private String attributesString;
        private Map<String, String> attributes;
        private int index;

        private void init(String attributesString){
            this.index = 0;
            this.attributes = new HashMap<>();
            this.attributesString = attributesString;
        }

        private boolean isValidNameChar(char symbol){

            return Character.isLetterOrDigit(symbol) ||
                    symbol == '_' || symbol == '-';

        }

        private String parseName(){

            StringBuilder name = new StringBuilder();
            boolean inName = false;

            for (;
                 index < attributesString.length() &&
                 (isValidNameChar(attributesString.charAt(index)) || !inName);
                 index++){

                if(inName){
                    name.append(attributesString.charAt(index));
                }
                else if(isValidNameChar(attributesString.charAt(index))){
                    index--;
                    inName = true;
                }

            }

            return name.toString();

        }

        private void skipToValue(){

            while (attributesString.charAt(index) != '"')
                index++;

            index++;

        }

        private String parseValue(){

            StringBuilder value = new StringBuilder();

            while (attributesString.charAt(index) != '"') {
                value.append(attributesString.charAt(index));
                index++;
            }

            index++;

            return value.toString();

        }

        public Map<String, String> parse(String attributesString){

            init(attributesString);

            String name;
            String value;

            while (!(name = parseName()).equals("")){
                skipToValue();
                value = parseValue();
                attributes.put(name, value);
            }

            return attributes;

        }

    }

}
