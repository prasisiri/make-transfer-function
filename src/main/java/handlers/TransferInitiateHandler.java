package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static com.amazon.ask.request.Predicates.intentName;

public class TransferInitiateHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{

    public static final String SMS_TEXT = "smsText";
    @Override
    public boolean canHandle(HandlerInput handlerInput) {

        Request request = handlerInput.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;

        return handlerInput.matches(intentName("MakeTransferIntent"))
                && (intentRequest.getDialogState() == DialogState.COMPLETED);
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText;
        Request request = handlerInput.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String,Slot> slots = intent.getSlots();
        String smsCode = slots.get(SMS_TEXT).getValue();
        if(smsCode.equalsIgnoreCase("234567")) {
            speechText = "SMS Code Verification Successful. For making Internal Transfer, " +
                    "please say Internal Transfer. For external Bank Transfer, Please say External Transfer  ";
        } else {
            speechText = "Invalid SMS Code. Please say correct SMS code";
        }
        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
