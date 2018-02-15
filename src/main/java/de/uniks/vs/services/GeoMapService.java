package de.uniks.vs.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class GeoMapService extends RestResource {

    static int i = 0;

    @GET
    @Path("/getTemperature/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String post(@PathParam("id") String string) {
        return "" + i++;
    }

    @Override
    protected String getDescription() {
        return this.getClass().getName();
    }
}