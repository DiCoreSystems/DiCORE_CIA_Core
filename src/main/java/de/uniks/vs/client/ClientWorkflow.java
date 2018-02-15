package de.uniks.vs.client;

import de.uniks.vs.client.compostions.ProtoTypeWorkflow2Creator;
import de.uniks.vs.client.compostions.ProtoTypeWorkflowCreator;
import de.uniks.vs.evolution.graph.analysis.manager.BPMNManager;
import de.uniks.vs.evolution.graphmodels.GraphModel;
import de.uniks.vs.evolution.graphmodels.GraphNode;
import de.uniks.vs.evolution.graphmodels.ModelManager;

import java.util.ArrayList;
import java.util.Vector;

public class ClientWorkflow {

    private ClientApplication clientApplication;
    private GraphModel workflow;
    private Vector<GraphNode> startPoints = new Vector<>();
    private GraphNode currentService;

    public ClientWorkflow(ClientApplication clientApplication, int i) {
        this.clientApplication = clientApplication;
        initWorkflow(i);
    }

    private void initWorkflow(int i ) {
        ModelManager modelManager = null;
        if (i < 2)
         modelManager = ProtoTypeWorkflowCreator.createModel();
        else
         modelManager = ProtoTypeWorkflow2Creator.createModel();
//        ModelManager modelManager = loadJsonModel();
        workflow = modelManager.getLastModel();
        ArrayList<GraphNode> eNodes = workflow.getNodesByType(BPMNManager.EVENT);

        for (GraphNode node:eNodes) {

           if (node.getName().startsWith("startevent_"))
            startPoints.add(node);
        }
        ArrayList<GraphNode> sNodes = workflow.getNodesByType(BPMNManager.ACTIVITY);

        for (GraphNode node: sNodes) {
            String service = this.clientApplication.getRequest("registryservice", "resource/getservice/" + node.getName());
            node.withName(node.getName() + "::" + service);
        }
        workflow.printNamedGraph();
    }

    public String getNextServiceCall() {

        if (currentService == null || currentService.getNext().get(0).getNext().isEmpty() )
            currentService = startPoints.get(0).getNext().get(0);

        else {
            currentService = currentService.getNext().get(0);
        }
        return currentService.getName();
    }

    public Vector<GraphNode> getStartPoints() {
        return startPoints;
    }
}
