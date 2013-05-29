package org.nooon.kutya;

import org.nooon.core.model.entity.Person;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/worksheet")
public class WorksheetLogic {

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(@Context HttpHeaders headers) {

        List<Person> response = new ArrayList<Person>();
        for (int i=0; i<10; i++) {
            response.add(new Person());
        }

        return Response.status(200)
                .entity(response)
                .build();

    }

}
