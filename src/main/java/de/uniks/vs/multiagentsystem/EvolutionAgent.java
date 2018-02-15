package de.uniks.vs.multiagentsystem;

import de.uniks.vs.multiagentsystem.behaviour.AgentBehaviour;
import de.uniks.vs.multiagentsystem.components.*;
import de.uniks.vs.multiagentsystem.components.analysis.Analysis;
import de.uniks.vs.multiagentsystem.components.analysis.ChangeImpactAnalysis;
import de.uniks.vs.client.ClientWorkflow;
import javafx.util.Pair;
import org.glassfish.jersey.client.JerseyWebTarget;

import java.net.URI;
import java.util.HashMap;

public class EvolutionAgent implements Runnable {

    private Knowledgebase knowledgebase;
    private Communication communication;
    private NotificationManagement notification;

    private AgentBehaviour behaviour;

    private Analysis analysis;
    private CoEvolutionCoordination coordination;

    public EvolutionAgent() {
        communication = new Communication();
        knowledgebase = new Knowledgebase().setId(this.hashCode());
        notification  = new NotificationManagement(knowledgebase, communication);

//        analysis      = new ChangeImpactAnalysis(knowledgebase, communication);
//        coordination  = new CoEvolutionCoordination(knowledgebase, communication);
//        behaviour     = new AgentBehaviour(analysis, coordination, notification);
    }

    public EvolutionAgent init(Analysis analysis, CoEvolutionCoordination coordination, AgentBehaviour behaviour) {
        this.analysis = analysis;
        this.coordination = coordination;
        this.behaviour = behaviour;
        Thread thread = new Thread(this);
        thread.start();
        return this;
    }

    @Override
    public void run() {
        behaviour.run();
    }

    public JerseyWebTarget target(URI uri) {
        return communication.target(uri);
    }

    public void updateServices(HashMap<String, URI> services) {
        communication.updateServices(services);
    }

    public String getRequest(String service, String request) {
        String result = communication.getRequest(service, request);
        knowledgebase.addToMessageQueue(new Pair(service, new Pair(request, result )));
        return result;
    }

    public void setWorkflow(ClientWorkflow clientWorkflow) {
        knowledgebase.setWorkflow(clientWorkflow);
    }

    public Knowledgebase getKnowledgebase() {
        return knowledgebase;
    }

    public Communication getCommunication() {
        return communication;
    }

    public NotificationManagement getNotification() {
        return notification;
    }
}


