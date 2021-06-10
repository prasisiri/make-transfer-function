package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static com.amazon.ask.request.Predicates.intentName;

public class InternalTransferCaptureHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{

    public static final String AMOUNT = "amount";
    public static final String FROM_ACCOUNT = "discoverBankFromAccount";
    public static final String TO_ACCOUNT = "discoverBankToAccount";
    public static final String TRANSFER_DATE = "transferDate";
    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("InternalTransferCaptureIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {
        String speechText;
        Request request = handlerInput.getRequestEnvelope().getRequest();
        IntentRequest intentRequest = (IntentRequest) request;
        Intent intent = intentRequest.getIntent();
        Map<String,Slot> slots = intent.getSlots();
        String amount = slots.get(AMOUNT).getValue();
        String fromAccount = slots.get(FROM_ACCOUNT).getValue();
        String toAccount = slots.get(TO_ACCOUNT).getValue();
        String transferDate = slots.get(TRANSFER_DATE).getValue();
        if(fromAccount.equalsIgnoreCase("123")) {
            speechText = "Basing on Transfer details provided, your transfer will be effective immediately. " +
                    "Please say Confirm Transfer to Proceed";
        } else {
            speechText = "Transfer Details Invalid";
        }
        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
