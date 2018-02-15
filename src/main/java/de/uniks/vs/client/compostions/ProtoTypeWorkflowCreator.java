package de.uniks.vs.client.compostions;

import de.uniks.vs.evolution.graphmodels.GraphNode;
import de.uniks.vs.evolution.graphmodels.GraphEdge;
import de.uniks.vs.evolution.graphmodels.GraphModel;
import de.uniks.vs.evolution.graphmodels.ModelManager;

import java.util.ArrayList;

/**
 * Created by alex jahl code generation.
 */
public class ProtoTypeWorkflowCreator {

    public static ModelManager createModel() {
        ModelManager modelManager = new ModelManager();
        modelManager.addGraphModel(new GraphModel(), "ProtoTypeWorkflow");
        modelManager.createGraphNote("e", "startevent__2", "ProtoTypeWorkflow");
        modelManager.createGraphNote("e", "endevent__3", "ProtoTypeWorkflow");
        modelManager.createGraphNote("a", "webviewservice", "ProtoTypeWorkflow");
        modelManager.createGraphNote("a", "dataanalysisservice", "ProtoTypeWorkflow");
        modelManager.createGraphNote("a", "poiservice", "ProtoTypeWorkflow");
        modelManager.createGraphNote("a", "gpsservice", "ProtoTypeWorkflow");
        modelManager.createGraphNote("a", "mapservice", "ProtoTypeWorkflow");
        modelManager.createGraphNote("a", "dbservice", "ProtoTypeWorkflow");
        modelManager.createGraphEdge(7, 2, "ProtoTypeWorkflow");
        modelManager.createGraphEdge(2, 1, "ProtoTypeWorkflow");
        modelManager.createGraphEdge(3, 7, "ProtoTypeWorkflow");
        modelManager.createGraphEdge(0, 4, "ProtoTypeWorkflow");
        modelManager.createGraphEdge(4, 5, "ProtoTypeWorkflow");
        modelManager.createGraphEdge(5, 6, "ProtoTypeWorkflow");
        modelManager.createGraphEdge(6, 3, "ProtoTypeWorkflow");
        modelManager.setLastModelID("ProtoTypeWorkflow");
        return modelManager;
    }
}
