package org.acme;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkiverse.langchain4j.guardrails.OutputGuardrails;

@RegisterAiService //(tools = Tools.class)
public interface SmartGreeter {

    String greet();

    @UserMessage("Hi, my name is {name}")
    String greet(String name);

    @UserMessage("What time is it?")
    String time();

    @SystemMessage("You are an assistant in a pet adoption centre. Your job is to help people find the right pet for them. Include descriptions of the specific individual to be adopted.")
    @UserMessage("I would like to find a pet. Please suggest something unusual.  Please only suggest one.")
    @OutputGuardrails(AnimalValidator.class)
    Pet pet();
}
