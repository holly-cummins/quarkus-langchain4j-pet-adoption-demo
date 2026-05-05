package org.acme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.langchain4j.data.message.AiMessage;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrail;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrailResult;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AnimalValidator implements OutputGuardrail {
    @Inject
    AnimalIdentifier smartGreeter;

    @Inject
    ObjectMapper mapper;

    @Override
    public OutputGuardrailResult validate(AiMessage responseFromLLM) {
        try {
            Pet pet = mapper.readValue(responseFromLLM.text(), Pet.class);
            if ("Ball Python".equals(pet.type())) {
                return reprompt("Not a type of animal", "No more ball pythons!");
            }

            boolean isCorrectType = smartGreeter.isA(pet.type(), pet.animal());

            if (!isCorrectType) {
                return reprompt("A " + pet.type() + " is not a " + pet.animal(), "Make sure that animal is a type of " + pet.animal());
            }
        } catch (JsonProcessingException e) {
            return reprompt("Invalid JSON " + responseFromLLM.text(), e, "Make sure you return a valid JSON object. Do not add any markdown wrappers to it.");
        }
        return success();
    }

}
