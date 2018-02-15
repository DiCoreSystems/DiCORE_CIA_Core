package de.uniks.vs.multiagentsystem.components.analysis.plugin;

import de.uniks.vs.multiagentsystem.components.analysis.ChangeImpactAnalysis;
import de.uniks.vs.client.ClientWorkflow;
import de.uniks.vs.evolution.graphmodels.GraphEdge;
import de.uniks.vs.evolution.graphmodels.GraphNode;
import de.uniks.vs.multiagentsystem.components.analysis.asp.ExtClingoRunner;
import de.uniks.vs.multiagentsystem.components.analysis.asp.ExtTransitionDiagram;
import de.uniks.vs.multiagentsystem.components.analysis.asp.ExtWorkflowGraph;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.util.Pair;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class ASPWorkflowAnalysisPlugin extends AnalysePlugin {


    public ASPWorkflowAnalysisPlugin(ChangeImpactAnalysis impactAnalysis) {
        super(impactAnalysis);
    }

    @Override
    public Type getType() {
        return Type.WORKFLOW;
    }

    @Override
    public String analyseContent(Type type, Pair content) {
        String results = "";
        ClientWorkflow first = (ClientWorkflow) content.getKey();
        ClientWorkflow second = (ClientWorkflow) content.getValue();
        String aspCode1 = convertWorkflowToASP(first, false);
        String aspCode2 = convertWorkflowToASP(second, true);
//        System.out.println("===================================================================");
//        System.out.println(aspCode1);
//        try { Files.write(Paths.get("aspcode1.lp"), aspCode1.getBytes()); } catch (IOException e) { e.printStackTrace(); }
//        System.out.println("===================================================================");
//        System.out.println(aspCode2);
//        try { Files.write(Paths.get("aspcode2.lp"), aspCode2.getBytes()); } catch (IOException e) { e.printStackTrace(); }
//        System.out.println("===================================================================");
        ExtClingoRunner clingo = new ExtClingoRunner();
        results = clingo.findDifferences(aspCode1, aspCode2, true);
//        try { Files.write(Paths.get("aspcode_result.lp"), results.getBytes()); } catch (IOException e) { e.printStackTrace(); }
        return results;
    }

    private String convertWorkflowToASP(ClientWorkflow workflow, boolean prefix) {
        Graph graph = new Graph();
        GraphNode node = workflow.getStartPoints().get(0);
        Vertex vertex = new Vertex(UUID.randomUUID(), node.getName());
        graph.addVertex(vertex);
        convertEdges(graph, node, vertex);
        ExtWorkflowGraph defaultWorkflow = new ExtWorkflowGraph(graph.getVertices(), graph.getEdges());
        ExtTransitionDiagram t = defaultWorkflow.translate(node.getName());
        return t.createASPCodeAsString(prefix);
    }

    private void convertEdges(Graph graph, GraphNode node, Vertex vertex) {
        CopyOnWriteArrayList<GraphEdge> edges = node.getOutEdges();

        for (GraphEdge edge: edges) {
            GraphNode node2 = edge.getTarget();
            Vertex vertex2 = new Vertex(UUID.randomUUID(), node2.getName().replace("::","_"));
            graph.addVertex(vertex2);
            graph.addEdge(new Edge(UUID.randomUUID(), vertex, vertex2));
            convertEdges(graph, node2, vertex2);
        }
    }
}
