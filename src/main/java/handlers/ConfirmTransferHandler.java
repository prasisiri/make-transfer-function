package handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.model.Response;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ConfirmTransferHandler implements com.amazon.ask.dispatcher.request.handler.RequestHandler{

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public boolean canHandle(HandlerInput handlerInput) {
        return handlerInput.matches(intentName("ConfirmTransferIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput handlerInput) {

        Transfer transfer = Transfer.builder().build();

        HttpEntity<Object> requestEntity = new HttpEntity<>(transfer, new HttpHeaders());
        ResponseEntity<String> responseEntity = restTemplate
                .exchange("https://ptyk0d85s3.execute-api.us-east-1.amazonaws.com/confirm/transfer",
                        HttpMethod.POST,
                        requestEntity,
                        String.class);

        String speechText = "Your Transfer is successfully created with reference:"
                + responseEntity.getBody() +
                "Thank you for using MyBank Alexa Transfer Function";
        return handlerInput.getResponseBuilder().withSpeech(speechText).build();
    }
}
