package de.uniks.vs.multiagentsystem.components;

import de.uniks.vs.client.ClientWorkflow;
import javafx.util.Pair;

import java.util.HashMap;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.sun.tools.internal.xjc.reader.Ring.add;

public class Knowledgebase {
    private CopyOnWriteArrayList<Pair<String, Pair>> messageQueue = new CopyOnWriteArrayList<>();
    private ConcurrentHashMap<String, Pair> monitoredServices = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Pair> changedInterfaces = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Pair> changedStructures = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Vector<String>> impactAnalysisResults = new ConcurrentHashMap<>();
    private Vector<ClientWorkflow> workflows = new Vector<>();
    private int id;

    public void addToMessageQueue(Pair pair) {
        synchronized (messageQueue) {
            messageQueue.add(pair);
            System.out.println(pair);
        }
    }

    public void addImpactAnalysisResults(String service, String result) {

        if(!impactAnalysisResults.contains(service)) {
            Vector<String> results = new Vector<>();
            results.add(result);
            impactAnalysisResults.put(service, results);
        }
        else {
            impactAnalysisResults.get(service).add(result);
        }
    }

    public void addToInterfaceChanges(String service) {
        changedInterfaces.put(service, monitoredServices.get(service));
    }

    public void addToStructuralChanges(String entity, ClientWorkflow workflow, ClientWorkflow workflow2) {
        changedStructures.put(entity, new Pair(workflow, workflow2));
    }

    public void setWorkflow(ClientWorkflow workflow) {
        this.workflows.add(workflow);
    }

    public Knowledgebase setId(int id) {
        this.id = id;
        return this;
    }

    public Vector<ClientWorkflow> getWorkflows() {
        return workflows;
    }

    public ConcurrentHashMap<String, Pair> getMonitoredServices() {
        return monitoredServices;
    }

    public CopyOnWriteArrayList<Pair<String, Pair>> getMessageQueue() {
        return messageQueue;
    }

    public ConcurrentHashMap<String, Pair> getChangedInterfaces() {
        return changedInterfaces;
    }

    public ConcurrentHashMap<String, Pair> getChangedStructures() {
        return changedStructures;
    }

    public ConcurrentHashMap<String, Vector<String>> getImpactAnalysisResults() {
        return impactAnalysisResults;
    }

    public int getOwnID() {
        return id;
    }
}
