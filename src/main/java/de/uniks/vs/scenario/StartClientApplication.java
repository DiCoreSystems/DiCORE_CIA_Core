package de.uniks.vs.scenario;

import de.uniks.vs.client.ClientApplication;
import de.uniks.vs.services.*;

import javax.ws.rs.core.UriBuilder;

public class StartClientApplication {


    public static void main(String[] args) {

        for (int i = 0; i < 1; i++) {
            ClientApplication clientApplication = new ClientApplication();
            clientApplication.addService(RegistryService.class.getSimpleName().toLowerCase(),
                    UriBuilder.fromUri("http://localhost/").port(RegistryService.REGISTRY_PORT).build());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
