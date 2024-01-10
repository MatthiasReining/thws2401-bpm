
package de.thws.thws2014bpm.demo.resources;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

@Path("hello")
public class RestResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello THWS rocks 🤘🏼";
    }
}
