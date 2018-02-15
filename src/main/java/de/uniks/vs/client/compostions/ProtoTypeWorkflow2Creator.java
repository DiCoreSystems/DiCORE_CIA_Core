package de.uniks.vs.client.compostions;

import de.uniks.vs.evolution.graphmodels.GraphNode;
import de.uniks.vs.evolution.graphmodels.GraphEdge;
import de.uniks.vs.evolution.graphmodels.GraphModel;
import de.uniks.vs.evolution.graphmodels.ModelManager;

import java.util.ArrayList;

/**
 * Created by alex jahl code generation.
 */
public class ProtoTypeWorkflow2Creator {

    public static ModelManager createModel() {
        ModelManager modelManager = new ModelManager();
        modelManager.addGraphModel(new GraphModel(), "ProtoTypeWorkflow2");
        modelManager.createGraphNote("e", "startevent__2", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("e", "endevent__3", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("a", "webviewservice", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("a", "dataanalysisservice", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("a", "wlanservice", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("a", "pollutionservice", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("a", "poiservice", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("a", "gpsservice", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("a", "geomapservice", "ProtoTypeWorkflow2");
        modelManager.createGraphNote("a", "dbservice", "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(9, 2, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(2, 1, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(3, 9, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(4, 7, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(5, 3, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(7, 5, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(0, 6, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(6, 4, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(7, 8, "ProtoTypeWorkflow2");
        modelManager.createGraphEdge(8, 3, "ProtoTypeWorkflow2");
        modelManager.setLastModelID("ProtoTypeWorkflow2");
        return modelManager;
    }
}
