package org.acme;

import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;

@RegisterAiService
public interface AnimalIdentifier {

    @UserMessage("Is a {name} a type of {animal}?")
    boolean isA(String name, Animal animal);
}
