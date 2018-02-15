package de.uniks.vs.services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Scanner;

@Path("resource")
public abstract class RestResource {

    public RestResource() {
//        System.out.println(this.getClass().getSimpleName() + " running ...");
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getIt() {
        return RestService.getAlive();
    }

    @GET
    @Path("/call/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doGet(@PathParam("id") String string) {
        Long value = Long.valueOf(string);
        call();
        return "" + ++value;
    }

    @GET
    @Path("/getdescription")
    @Produces(MediaType.TEXT_PLAIN)
    public String doGet() {
        return getDescription();
    }

    abstract protected String getDescription();

    protected int call() {
        return -1;
    }

    String loadWADL(String file) {
        StringBuilder stringBuilder = new StringBuilder();

        try (Scanner scanner = new Scanner(new File(file))) {

            while (scanner.hasNext()){
                stringBuilder.append(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}