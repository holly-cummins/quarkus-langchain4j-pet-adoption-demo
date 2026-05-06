package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api")
public class BackendResource {

    @Inject
    AiHelper ai;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("name") String name) {
        return ai.greet(name);
    }

    @Path("time")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String time() {
        return ai.time();
    }

    @Path("pet")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Pet pet(@QueryParam("preference") String preference) {
        if (preference != null && !preference.isEmpty()) {
            return ai.pet(preference);
        }
        return ai.pet();
    }
}
