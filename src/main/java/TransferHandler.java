import com.amazon.ask.Skill;
import com.amazon.ask.builder.StandardSkillBuilder;
import com.amazon.ask.model.RequestEnvelope;
import com.amazon.ask.model.ResponseEnvelope;
import com.amazon.ask.util.JacksonSerializer;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.util.IOUtils;
import handlers.ConfirmTransferHandler;
import handlers.TransferInitiateHandler;
import handlers.InternalTransferCaptureHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class TransferHandler implements RequestStreamHandler {
    private final Skill skill;
    private final JacksonSerializer serializer;

    public TransferHandler() {
        skill = new StandardSkillBuilder()
                .addRequestHandler(new TransferInitiateHandler())
                .addRequestHandler(new InternalTransferCaptureHandler())
                .addRequestHandler(new ConfirmTransferHandler())
                .build();
        serializer = new JacksonSerializer();
    }

    @Override
    public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        String request = IOUtils.toString(inputStream);
        RequestEnvelope requestEnvelope = serializer.deserialize(request, RequestEnvelope.class);
        ResponseEnvelope responseEnvelope = skill.invoke(requestEnvelope);
        byte[] response = serializer.serialize(responseEnvelope).getBytes(StandardCharsets.UTF_8);
        outputStream.write(response);
    }
}
