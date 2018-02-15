package de.uniks.vs.services;


import de.uniks.vs.client.ClientApplication;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import static org.junit.Assert.assertEquals;

public class RestServiceTest {

    private RestService restService;

    @Test
    public void testRestService() {
        // start the server
        restService = new RestService();
        HttpServer server = restService.startServer(RestResource.class, 9998);
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and StartServices.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        WebTarget target = c.target(restService.getBaseUri());
        String responseMsg = target.path("resource").request().get(String.class);
        assertEquals(RestService.getAlive(), responseMsg);

        server.shutdown();
    }

    @Test
    public void testTempService() {
        HttpServer server = new RestService().startServer(TemperatureService.class, 9998);
        Client c = new ClientApplication();
        WebTarget target = c.target(restService.getBaseUri());
        String responseMsg = target.path("resource/getTemperature/1").request().get(String.class);
        assertEquals("15", responseMsg);
        server.shutdown();
    }
}