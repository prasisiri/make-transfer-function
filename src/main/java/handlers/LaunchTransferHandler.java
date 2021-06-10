package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class LaunchTransferHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("LaunchTransferIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText = "Welcome to My Bank Transfer Alexa Function." +
                " Please say Funds Transfer for Initiating Transfer." +
                " For inquiry on past initiated Transfer status, say Transfer Status";
        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
