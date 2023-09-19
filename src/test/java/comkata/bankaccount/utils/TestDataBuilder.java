package comkata.bankaccount.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import comkata.bankaccount.infrastructure.dto.TransactionRequest;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class TestDataBuilder {

    public TransactionRequest buildTransactionRequest(Long id, BigDecimal amount){
        return new TransactionRequest(id,amount);
    }

    public String buildRequestBody(Long id, BigDecimal amount) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(buildTransactionRequest(id,amount));
    }
}
