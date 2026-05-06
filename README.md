# Pet Ai-doptery demo

This demo uses Quarkus, along with Quarkus LangChain4j.

## Quickstart

```
./mvnw quarkus:dev
```

Then click 'w' to bring up the welcome page, which will list the available endpoints.
You will need to configure an API key to interact with a backend LLM.

The UI will be on http://localhost:8080/quinoa:

![screen cap of pet card](/readme-images/example-card.png)

## Live coding

To build up the capability in this app from scratch, these are the steps.

### General shortcuts

Ctrl-`,6,1 to enter presenter mode in IDEA
Ctrl-G to rename multiple occurrences of something (useful for adjusting duplicated resource endpoints)
Cmd-tab to choose app on Mac


### Prep

1. Set up an `OPENAI_API_KEY` environment variable (having it in .zshrc is most convenient)
2. Move the UI into `/downloads`, *without* node_modules -
   `cp -r ./src/main/webui ~/Downloads && rm -rf ~/Downloads/webui/node_modules`

### Demo

#### Setup

1. Visit http://code.quarkus.io, select npm ui,
2. filter for langchain4j, there are lots, for extensions are different LLMs or different vector stores
3. Filter for open ai, Select langchain openAI
4. Generate, unzip,
5. Show pom, has rest and langchain4j
6. Do the copying of the web ui at this stage
7. Run quarkus dev

#### Hello world

1. Visit http://localhost:8080
2. Exercise hello world on `/hello`
3. Rename `GreetingResource` to `BackEndResource`
4. Edit `BackEndResource`, `@Inject` a `AiHelper`
4. Let the AI create it, then annotate it with `@RegisterAiService`
5. Make a method called greet, and call it
6. Try the `/hello` endpoint
7. It will fail, this is expected
8. Press `w` to go to dev ui
9. Add it to application properties, using ${OPENAI_API_KEY}
10. Visit endpoint. It should work.
11. Visit dev ui. Go to OpenAI extension. It has my key. Do a prompt, make an image. Can prompt it do a picture of a
    cute pet

#### Hello world with parameters

1. But of course web browser with chat is what we’re trying to get away from, and with live reload
2. Make interface, annotate with `@RegisterAiService`
3. Do it with no user message, just a method call.
4. Add user message
5. Add parameters for name
6. Make method, greet, annotate with `@UserMessage(“Hello, my name is Holly”)`
7. `@Inject` it into rest service, call it in method
8. Parameterise it with `{name}`, add String name argument, update call

#### System messages

1. Add a system message: “You are a greeter. No matter what language the user uses, respond in French."
2. Try again. It should respond in French.

#### Tools

1. Change the resource path to `/api`, and make a hello and time sub-path
2. Make a time method which asks for the time
3. You should get a message saying it doesn’t know the time
4. Add `(tool = Tools.class)`, then create the tool
5. Annotate it `@ApplicationScoped`
6. Add a method which does `LocalTime.now().toString()`;
7. Annotate it `@Tool`
8. This could be called agentic
9. Enable logging, by searching in the dev ui, search for “log-re”
10. Show the logs, it’s passing in extra metadata about tools, and then langchain4j invokes the tools
11. Add a format argument, show how that appears in the logs, too

#### Types

1. Make an `api/pet` endpoint method
2. Make it return a `Pet` record, with a diet, description, hexColour, colour, name, type, and Animal enum
3. Make animals include bird, snake, cat, dog
4. Make the return type be json
5. Try the endpoint
6. Copy webui from the main repo into the project's web ui
7. Visit http://localhost:8080/quinoa/

#### User Preferences (Optional)

1. The UI now includes a text input field where users can specify what kind of pet they're looking for
2. Add a `@QueryParam("preference")` parameter to the `pet()` method in `BackendResource`
3. Create an overloaded `pet(String preference)` method in `AiHelper` that incorporates the user's preference into the prompt
4. Update the `@UserMessage` to include `{preference}` placeholder
5. The UI will automatically submit the preference when the user presses Enter
6. Try entering preferences like "a friendly dog", "something unusual", or "a quiet pet"
7. Show how the AI adapts its suggestions based on user input

#### Guardrails

1. Some aspects of the output will be disappointing, so this is a good opportunity for a guard rail
2. Add an `@OutputGuardrail`
2. Add a method: `public OutputGuardrailResult validate(AiMessage responseFromLLM) {`
3. First, make it not return ball pythons
4. Parse json: `pet = mapper.readValue(responseFromLLM.text(), Pet.class);`
5. As a side effect, this ensures the response json
6. Can also do an `isA` check to make sure the animal is the right type

#### Optional: memory

1. The pets may become repetitive, because earlier messages are held in memory. Try changing the memory store or
   reducing how many messages are held, in config.

#### Optional: fallback

1. Shorten the timeout
2. `@Fallback(fallBackMethod=“fallback”)`, which is just a micro profile thing. This ia strength to be able to leverage
   these existing things.

### Troubleshooting

#### Timeout

- opportunity to use a fallback or retry
  Use dev UI to update config and set a bigger langchain timeout

Add please be brief to the system prompt

#### Chat view missing in dev ui

Need to do `@RegisterAiService` in app first, ugh

#### Missing name

Did you pass it in on the query param?

#### General

In dev ui, turn on logging of requests and responses

#### Pet not showing in the card

Turn on request logging
Is the return type `application/json`?

#### Quinoa won’t start

Delete node_modules, then maybe nom install, then try again

#### Guard rail does not work

Did you remember `.text()` on the llm message?
