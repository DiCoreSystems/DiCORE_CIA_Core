package de.uniks.vs.multiagentsystem.components;

import de.uniks.vs.client.ClientWorkflow;
import javafx.util.Pair;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ChangeDetection {
    private Knowledgebase knowledgebase;

    public ChangeDetection(Knowledgebase knowledgebase) {
        this.knowledgebase = knowledgebase;
    }

    public void check() {
        // Interfaces
        checkInterfaces();

        // Structure
        checkStructure();

        // Behaviour
        checkBehaviour();
    }

    private void checkInterfaces() {
        ConcurrentHashMap<String, Pair> monitoredServices = this.knowledgebase.getMonitoredServices();

        for (String service : monitoredServices.keySet()) {
            analyseInterfaces(service, monitoredServices.get(service));
        }
    }

    private void analyseInterfaces(String service, Pair<String, String> descriptions) {

        if (descriptions.getKey().equals(descriptions.getValue())) {
            return;
        }
        knowledgebase.addToInterfaceChanges(service);
    }

    private void checkStructure() {
        Vector<ClientWorkflow> workflows = knowledgebase.getWorkflows();

        if (workflows.isEmpty())
            return;
        ClientWorkflow workflow = workflows.get(0);
        boolean changed = false;

        for (ClientWorkflow workflow2:workflows) {

            if (workflow.hashCode() != workflow2.hashCode()) {
                this.knowledgebase.addToStructuralChanges("workflow_"+knowledgebase.getOwnID(), workflow, workflow2);
                changed = true;
                break;
            }
        }

        if (workflows.size() > 1 && changed) {
            workflows.clear();
            workflows.add(workflow);
        }
    }

    private void checkBehaviour() {
//        CommonUtils.aboutNoImpl();
    }
}
