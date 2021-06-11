package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import static com.amazon.ask.request.Predicates.intentName;

public class InternalTransferCaptureHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{

    public static final String AMOUNT = "amount";
    public static final String FROM_ACCOUNT = "myBankFromAccount";
    public static final String TO_ACCOUNT = "myBankToAccount";
    public static final String TRANSFER_DATE = "transferDate";

    private RestTemplate restTemplate = new RestTemplate();

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

        Transfer transfer = Transfer.builder()
                .transferDate( slots.get(TRANSFER_DATE).getValue())
                .amount(slots.get(AMOUNT).getValue())
                .fromAccount(slots.get(FROM_ACCOUNT).getValue())
                .toAccount(slots.get(TO_ACCOUNT).getValue())
                .build();

        HttpEntity<Object> requestEntity = new HttpEntity<>(transfer, new HttpHeaders());
        ResponseEntity<String> responseEntity = restTemplate
                .exchange("https://ptyk0d85s3.execute-api.us-east-1.amazonaws.com/verify/transfer",
                HttpMethod.POST,
                requestEntity,
                String.class);


        if(responseEntity.getBody().equalsIgnoreCase("valid")) {
            speechText = "Basing on Transfer details provided, your transfer can be scheduled succesfully. " +
                    "Please say Confirm Transfer to Proceed";
        } else {
            speechText = "Transfer Details Invalid. Please re-submit";
        }
        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
