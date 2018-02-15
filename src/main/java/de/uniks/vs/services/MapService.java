package de.uniks.vs.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

public class MapService extends RestResource {

    static int i = 0;

    @GET
    @Path("/getMap/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String post(@PathParam("id") String string) {
        return "" + call();
    }

    @Override
    protected String getDescription() {
        String descript = loadWADL("src/main/java/de/uniks/vs/services/servicedescriptions/descript1.wsdl");

        if (i > 2 && i < 10) {
            descript = loadWADL("src/main/java/de/uniks/vs/services/servicedescriptions/descript2.wsdl");
        }
        return descript;
    }

    @Override
    protected int call() {
        return i++;
    }
}