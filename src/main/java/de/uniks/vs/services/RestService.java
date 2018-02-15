package de.uniks.vs.services;

import java.net.URI;
import java.io.IOException;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyWebTarget;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;


import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.grizzly.http.server.HttpServer;

public class RestService  extends JerseyClient {
    private URI baseUri;
    private ResourceConfig resourceConfig;

    public HttpServer startServer(Class cLazz, int port) {
        baseUri = UriBuilder.fromUri("http://localhost/").port(port).build();
        resourceConfig = new ResourceConfig(cLazz);
        System.out.println(cLazz.getSimpleName() +"  is running " + baseUri );
        HttpServer server = GrizzlyHttpServerFactory.createHttpServer(baseUri, resourceConfig);
        register(cLazz.getSimpleName());
        return server;
    }

    private void register(String serviceID) {
        URI registryServiceUri = UriBuilder.fromUri("http://localhost/").port(9500).build();

        if(baseUri == null || serviceID == null ||registryServiceUri.toString().equals(baseUri.toString()))
            return;
        JerseyWebTarget target = target(registryServiceUri);
        String request = target.path("resource/register/" + serviceID +":::"+ baseUri.getPort() ).request().get(String.class);
        System.out.println(request);
    }


    public URI getBaseUri() {
        return baseUri;
    }

    public static String getAlive() {
        return "Service is alive .........";
    }
}