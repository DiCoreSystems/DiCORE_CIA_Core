package de.uniks.vs.multiagentsystem.components.analysis.asp;

import graph.Edge;
import graph.Vertex;
import graph.WorkflowGraph;
import transDiagram.Action;
import transDiagram.Fluent;
import transDiagram.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;

public class ExtWorkflowGraph extends WorkflowGraph{


    public ExtWorkflowGraph(List<Vertex> vertices, List<Edge> edges) {
        super(vertices, edges);
    }

    public ExtTransitionDiagram translate(String startNodeName) {

        Queue<Vertex> verticesToCheck = new LinkedBlockingQueue<>();
        verticesToCheck.add(this.getVertices().get(0));

        vertices = this.getVertices();

        // Create a fluent for each vertex (except for the start vertex)
        for (Vertex v: vertices){
//            if(v.getName() != "start"){
            if(v.getName() != startNodeName){
                Fluent f = new Fluent(v.getName());
                // At the start of our Workflow, no Actions have been resolved yet.
                // Because of this their fluents are negative at first.
                fluents.add(f.getNegation());
            } else {
                startingVertex = v;
            }
        }

        // Create a start state (used only for orientation)
        State startState = new State(UUID.randomUUID(), startNodeName, fluents);
//        State startState = new State(UUID.randomUUID(), "start", fluents);
        states.add(startState);

        // Now we follow the graph vertex by vertex, until we reach the end.
        // Each passed vertex will yield a new state.
        verticesToCheck.offer(startingVertex);

        while(!verticesToCheck.isEmpty()){
            Vertex currentVertex = verticesToCheck.poll();

            if(visitedVertices.contains(currentVertex)){
                continue;
            }

            for(Edge e: currentVertex.getOutgoingEdges()){
                Vertex nextVertex = e.getEnd();
                verticesToCheck.offer(nextVertex);
                if(nextVertex == null)
                    break;

                // Find out which fluent is changed by our action.
                List<Fluent> newFluents = new ArrayList<>();
                for(Fluent f: fluents){
                    // TODO
                    // This one needs improvement. Currently we're connecting a action Vertex with
                    // the changed fluents just by the name.
                    if(f.getName().contains(nextVertex.getName())){
                        newFluents.add(f.getNegation());
                    } else {
                        newFluents.add(f);
                    }
                }

                // Find the corresponding state of our vertex.
                State currentState = checkForName(currentVertex.getName());

                // Check if our vertex already has a corresponding state.
                State nextState = checkForName(nextVertex.getName());
                if(nextState == null){
                    // Our state does not exist. Create a new one.
                    nextState = new State(UUID.randomUUID(), nextVertex.getName(), newFluents);
                    states.add(nextState);
                }

                Action a = new Action(UUID.randomUUID(), currentState, nextState, "do" + nextVertex.getName());
                actions.add(a);
                currentState.addOutgoingAction(a);
                nextState.addIngoingAction(a);
            }
            visitedVertices.add(currentVertex);
        }

        List<State> start = new ArrayList<>();
        start.add(startState);
        states.remove(startState);

        ExtTransitionDiagram t = new ExtTransitionDiagram(fluents, actions, states, start);

        return t;
    }
}
