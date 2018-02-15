package de.uniks.vs.client;

import de.uniks.vs.multiagentsystem.EvolutionAgent;
import de.uniks.vs.multiagentsystem.behaviour.AgentBehaviour;
import de.uniks.vs.multiagentsystem.components.analysis.ChangeImpactAnalysis;
import de.uniks.vs.multiagentsystem.components.CoEvolutionCoordination;
import org.glassfish.jersey.client.JerseyClient;
import org.glassfish.jersey.client.JerseyWebTarget;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;

public class ClientApplication extends JerseyClient implements Runnable {

    private HashMap<String, URI> services = new HashMap<>();
    private EvolutionAgent proxy;
    private ClientWorkflow clientWorkflow;
    private boolean running = true;

    public ClientApplication() {
        initEvolutionAgent();
        Thread thread = new Thread(this);
        thread.start();
    }

    private void initEvolutionAgent() {
        proxy = new EvolutionAgent();
        ChangeImpactAnalysis analysis = new ChangeImpactAnalysis(proxy.getKnowledgebase(), proxy.getCommunication());
        CoEvolutionCoordination coordination = new CoEvolutionCoordination(proxy.getKnowledgebase(), proxy.getCommunication());
        proxy.init(analysis, coordination, new AgentBehaviour(analysis, coordination, proxy.getNotification()));
    }

    public void addService(String service, URI baseUri) {
        services.put(service, baseUri);
        updateProxy();
    }

    public String pingService(String service) {
        JerseyWebTarget target = proxy.target(services.get(service));
        return target.path("resource").request().get(String.class);
    }

    @Override
    public void run() {
        System.out.println("Client running ...");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (String service: services.keySet()) {
            System.out.println("Service: " + service+ "  Uri:" + services.get(service));
        }
        clientWorkflow = new ClientWorkflow(this, 1);
        proxy.setWorkflow(clientWorkflow);

        long value = 0;
        long interation = 0;

        while (running) {
            String serviceCall = clientWorkflow.getNextServiceCall();

            if (serviceCall == null) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                continue;
            }
            String[] split = serviceCall.split("::");

            if(!services.containsKey(split[0])) {
                URI uri =  UriBuilder.fromUri("http://localhost/").port(Integer.valueOf(split[1])).build();
                addService(split[0], uri);
            }
            String request = getRequest(split[0], "resource/call/" + value);
            value = Long.valueOf(request);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            interation++;
            if (interation == 30) {
                clientWorkflow = new ClientWorkflow(this, 2);
                proxy.setWorkflow(clientWorkflow);
            }
        }
    }

    public String getRequest(String service, String request) {
         return proxy.getRequest(service, request);
    }

    private void updateProxy() {
        proxy.updateServices(services);
    }

    public boolean isRunning() {
        return running;
    }

    // -----------   override Client methods   -----------
    @Override
    public JerseyWebTarget target(String uri) {
        return super.target(uri);
    }


    //    @Override
//    public void close() {
//        super.close();
//    }
    //    public WebTarget target(URI uri) {
//    @Override
//        return null;
//    }
//
//    @Override
//    public WebTarget target(UriBuilder uriBuilder) {
//        return null;
//    }
//
//    @Override
//    public WebTarget target(Link link) {
//        return null;
//    }
//
//    @Override
//    public Invocation.Builder invocation(Link link) {
//        return null;
//    }
//
//    @Override
//    public SSLContext getSslContext() {
//        return super.getSslContext();
//    }
//
//    @Override
//    public HostnameVerifier getHostnameVerifier() {
//        return super.getHostnameVerifier();
//    }
//
//    @Override
//    public ClientConfig getConfiguration() {
//        return super.getConfiguration();
//    }
//
//    @Override
//    public JerseyClient property(String name, Object value) {
//        return property(name, value);
//    }
//
//    @Override
//    public JerseyClient register(Class<?> componentClass) {
//        return super.register(componentClass);
//    }
//
//    @Override
//    public JerseyClient register(Class<?> componentClass, int priority) {
//        return super.register(componentClass, priority);
//    }
//
//    @Override
//    public JerseyClient register(Class<?> componentClass, Class<?>... contracts) {
//        return super.register(componentClass, contracts);
//    }
//
//    @Override
//    public JerseyClient register(Class<?> componentClass, Map<Class<?>, Integer> contracts) {
//        return super.register(componentClass, contracts);
//    }
//
//    @Override
//    public JerseyClient register(Object component) {
//        return super.register(component);
//    }
//
//    @Override
//    public JerseyClient register(Object component, int priority) {
//        return super.register(component, priority);
//    }
//
//    @Override
//    public JerseyClient register(Object component, Class<?>... contracts) {
//        return super.register(component, contracts);
//    }
//
//    @Override
//    public JerseyClient register(Object component, Map<Class<?>, Integer> contracts) {
//        return super.register(component, contracts);
//    }

}
