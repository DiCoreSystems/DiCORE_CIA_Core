package de.uniks.vs.multiagentsystem.components;

import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyWebTarget;

import java.net.URI;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Communication extends JerseyClient {

    private ConcurrentHashMap<String, URI> services = new ConcurrentHashMap<>();
    private ConcurrentHashMap<URI, JerseyWebTarget> targets = new ConcurrentHashMap<>();

    public void updateServices(HashMap<String, URI> services) {

        for (String service : services.keySet()) {

            updateService(service, services.get(service));
        }
    }

    public void updateService(String service, URI serviceURI) {
        if (!this.services.containsKey(service)) {
            this.services.put(service, serviceURI);
            String request = getRequest(service, "resource");
            System.out.println("Service " + service + " add to Proxy, services " + request);
        }
    }

    public String getRequest(String service, String request) {
        if (services.get(service) == null)
            return null;

//        JerseyWebTarget target = target(RestService.getBaseUri());
//        target.path("resource/getTemperature/1").request().get(String.class);
//        JerseyWebTarget target = target(services.get(services));
        JerseyWebTarget target = getTarget(services.get(service));
        return target.path(request).request().get(String.class);
    }

//    private URI getService(String services) {
//        if (!services.containsKey(services)) {
//            services.put(services, target(uri));
//        }
//        return services.get(uri);
//    }


    private JerseyWebTarget getTarget(URI uri) {

        if (!targets.containsKey(uri)) {
            targets.put(uri, target(uri));
        }
        return targets.get(uri);
    }

    public ConcurrentHashMap<String, URI> getServices() {
        return services;
    }

    public ConcurrentHashMap<URI, JerseyWebTarget> getTargets() {
        return targets;
    }
}
