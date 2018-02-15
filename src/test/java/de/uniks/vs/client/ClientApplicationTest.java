package de.uniks.vs.client;

import de.uniks.vs.services.RestResource;
import de.uniks.vs.services.RestService;
import de.uniks.vs.services.TemperatureService;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ClientApplicationTest {

    private HttpServer server1;
    private HttpServer server2;
    private ClientApplication clientApplication;
    private String tempService;
    private String simpleService;

    @Before
    public void setUp() {
        // start the server
        RestService restService1 = new RestService();
        server2 = restService1.startServer(RestResource.class, 9999);
        RestService restService2 = new RestService();
        server1 = restService2.startServer(TemperatureService.class, 9998);

        // create the client
        clientApplication = new ClientApplication();
        simpleService = "simple";
        clientApplication.addService(simpleService, restService1.getBaseUri());
        tempService = "temperature";
        clientApplication.addService(tempService, restService2.getBaseUri());
    }

    @After
    public void tearDown(){
        server1.shutdown();
        server2.shutdown();
    }

    @Test
    public void testServiceAlive() {
        String ping = clientApplication.pingService(tempService);
        assertEquals(RestService.getAlive(), ping);
         ping = clientApplication.pingService(simpleService);
        assertEquals(RestService.getAlive(), ping);
    }

    @Test
    public void testGetTemperature() {
        String request = clientApplication.getRequest(tempService, "resource/getTemperature/1");
        waitUntil(10);
         request = clientApplication.getRequest(tempService, "resource/getTemperature/1");
        waitUntil(10);
         request = clientApplication.getRequest(tempService, "resource/getTemperature/1");
        waitUntil(10);
         request = clientApplication.getRequest(tempService, "resource/getTemperature/1");
        waitUntil(10);
         request = clientApplication.getRequest(tempService, "resource/getTemperature/1");
        waitUntil(1000);
        assertEquals("4", request);

    }

    private void waitUntil(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
