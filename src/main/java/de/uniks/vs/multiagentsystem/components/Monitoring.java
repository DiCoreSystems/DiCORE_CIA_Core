package de.uniks.vs.multiagentsystem.components;

import com.sun.org.apache.regexp.internal.RE;
import de.uniks.vs.services.RegistryService;
import de.uniks.vs.services.SmartASRService;
import javafx.util.Pair;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Monitoring {

    private final String REGISTRYSERVICE = "registryservice";
    private final String SMARTASRSERVICE = "smartasrservice";

    private Knowledgebase knowledgebase;
    private Communication communication;

    private int smartASR_URI = -1;

    public Monitoring(Knowledgebase knowledgebase, Communication communication) {
        this.knowledgebase = knowledgebase;
        this.communication = communication;
    }

    public void update() {
        checkInterfaceDescriptions();
        checkStructure();
        updateAgents();
    }

    private void updateAgents() {

        if(smartASR_URI < 0 && initSmartASR_URI() < 0) {
            return;
        }
        updateServiceAgents();
        updateApplicationAgents();
    }

    private void updateApplicationAgents() {

    }

    private void updateServiceAgents() {
        CopyOnWriteArrayList<Pair<String, Pair>> messageQueue = this.knowledgebase.getMessageQueue();
        Vector<Pair> indexes = new Vector();

        for (int i = 0; i < messageQueue.size(); i++) {
            Pair<String, Pair> service = messageQueue.get(i);

            if (REGISTRYSERVICE.equals(service.getKey()) || SMARTASRSERVICE.equals(service.getKey())) {
                indexes.add(service);
                continue;
            }

            if (!"SUCCESS".equals(this.communication.getRequest(SMARTASRSERVICE, "resource/register/services/" + service.getKey()))) {
                return;
            }
            this.communication.getRequest(SMARTASRSERVICE, "resource/update/serviceagent/" + service.getKey() + "/"+service.getValue().toString().replace("/", "_|_"));
            indexes.add(service);
        }

        for (Pair pair: indexes) {
            messageQueue.remove(pair);
        }

        if(!messageQueue.isEmpty())
            System.err.println("messages unsubmitted " + messageQueue.size());
    }

    private int initSmartASR_URI() {

        if (smartASR_URI < 0) {
            String result = this.communication.getRequest(REGISTRYSERVICE, "resource/getservice/" + SMARTASRSERVICE);

            if (result == null)
                return smartASR_URI;

            else
                smartASR_URI = Integer.valueOf(result);
                communication.updateService(SMARTASRSERVICE, UriBuilder.fromUri("http://localhost/").port(smartASR_URI).build());
        }
        return smartASR_URI;
    }

    private void checkStructure() { }

    private void checkInterfaceDescriptions() {
        updateInterfaceDescriptions();
    }

    private void updateInterfaceDescriptions() {
        ConcurrentHashMap<String, URI> services = communication.getServices();
        ConcurrentHashMap<String, Pair> monitoredServices = knowledgebase.getMonitoredServices();

        for (String service : services.keySet()) {
            String request = communication.getRequest(service, "resource/getdescription");

            if (!monitoredServices.containsKey(service)) {
                monitoredServices.put(service, new Pair(request, request));
            } else {
                Pair pair = monitoredServices.get(service);
                monitoredServices.put(service, new Pair(pair.getValue(), request));
//                        System.out.println(monitoredServices.get(services));
            }
        }
    }
}
