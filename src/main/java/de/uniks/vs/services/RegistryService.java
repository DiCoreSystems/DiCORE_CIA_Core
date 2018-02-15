package de.uniks.vs.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Vector;

public class RegistryService extends RestResource {

    public static int REGISTRY_PORT = 9500;
    static HashMap<String, String> services = new HashMap<>();

    @GET
    @Path("/getservice/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doGet(@PathParam("id") String string) {

        if (!services.containsKey(string))
            return "FAILURE";
        return services.get(string);
    }

    @GET
    @Path("/register/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doPost(@PathParam("id") String string) {
        String[] split = string.split(":::");

        if (split.length != 2)
            return "FAILURE";
        services.put(split[0].toLowerCase(), split[1]);

        System.out.println(services);
        return "SUCCESS";
    }

    @Override
    protected String getDescription() {
        String descript = "input:ServiceDescription, output:String";
        return descript;
    }
}