package renderer;

import java.text.ParseException;

class DocProcessor {

    private static final String BEGIN_TAG = "<?js";
    private static final String END_TAG   = "?>";

    private String inputText;

    private int index = 0;
    private PreprocessHandler handler;
    private ProcessorInterface[] processors;

    static void process(String inputText, PreprocessHandler handler, ProcessorInterface[] processors){
        DocProcessor processor = new DocProcessor(inputText, handler);
        processor.processors = processors;
        processor.process();
    }

    private DocProcessor(String inputText, PreprocessHandler handler){
        this.inputText = inputText;
        this.handler = handler;
    }

    private String postprocessText(String text){

        for(ProcessorInterface processor : processors)
            text = processor.process(text);

        return text;

    }

    private void process(){

        int scriptBeginIndex = inputText.indexOf(BEGIN_TAG, index);
        int scriptEndIndex;

        while (scriptBeginIndex != -1){

            scriptEndIndex = inputText.indexOf(END_TAG, scriptBeginIndex);

            handler.echo(
                    postprocessText(
                            inputText.substring(
                                    index,
                                    scriptBeginIndex
                            )
                    )
            );

            try {
                if(scriptEndIndex == -1)
                    throw new ParseException("Can`t find end of script", scriptBeginIndex);
                handler.getScriptHandler().eval(inputText.substring(scriptBeginIndex + BEGIN_TAG.length(), scriptEndIndex));
                index = scriptEndIndex + END_TAG.length();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            scriptBeginIndex = inputText.indexOf(BEGIN_TAG, index);

        }

        if (index < inputText.length())
            handler.echo(
                    postprocessText(inputText.substring(index))
            );

    }

}
