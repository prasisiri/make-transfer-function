package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ConfirmTransferHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("ConfirmTransferIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = "Your Transfer is successfully created with reference:" +
                " DF1234. Thank you for using MyBank Alexa Transfer Function";
        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
