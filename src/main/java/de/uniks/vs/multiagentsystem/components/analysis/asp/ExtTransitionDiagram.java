package de.uniks.vs.multiagentsystem.components.analysis.asp;

import transDiagram.Action;
import transDiagram.Fluent;
import transDiagram.State;
import transDiagram.TransitionDiagram;

import java.util.ArrayList;
import java.util.List;

public class ExtTransitionDiagram extends TransitionDiagram {

    public ExtTransitionDiagram(List<Fluent> fluents, List<Action> actions, List<State> states, List<State> startingStates) {
        super(fluents, actions, states, startingStates);
    }

    public String createASPCodeAsString(boolean withPrefix) {
        if(withPrefix) {
            this.prefix = "_n_";
            this.constant = "f";
        }
        else {
            this.prefix = "";
            this.constant = "g";
        }
        return createASPCodeAsString();
    }

    public String createASPCodeAsString(){
        System.out.println(constant);


            StringBuilder stringBuilder = new StringBuilder();
            // Step 1: DEFINITION OF FLUENTS
            // For this example graph I'm treating all fluents as inertial
            // But it is necessary to check if a fluent is really inertial or not.

            int n = this.getStates().size();
            stringBuilder.append("#const " + constant + " = " + n + ".\n");
            stringBuilder.append("step(0.." + constant + ").\n");

            stringBuilder.append("\n");

            for(Fluent fluent: fluents){
                String name = fluent.getName();

                if(fluent.isInertial()){
                    stringBuilder.append(prefix + "fluent(inertial, " + name + ").\n");

                    // INERTIA AXIOM FOR FLUENTS
                    stringBuilder.append(prefix + "holds(" + name + ",I+1) :- \n" +
                            "           " + prefix + "fluent(inertial, " + name + "), \n" +
                            "           " + prefix + "holds(" + name + ",I),\n" +
                            "           not -" + prefix + "holds(" + name + ",I+1), step(I).\n");

                    stringBuilder.append("-" + prefix + "holds(" + name + ",I+1) :- \n" +
                            "           " + prefix + "fluent(inertial, " + name + "), \n" +
                            "           -" + prefix + "holds(" + name + ",I),\n" +
                            "           not " + prefix + "holds(" + name + ",I+1), step(I).\n");
                } else {
                    stringBuilder.append("" + prefix + "fluent(defined" + name + ").\n");

                    // CWA FOR FLUENTS
                    stringBuilder.append("-" + prefix + "holds(" + name + ",I+1) :- \n" +
                            "           " + prefix + "fluent(defined" + name + "), \n" +
                            "           not holds(" + name + ",I), step(I).\n");
                }
                stringBuilder.append("\n");
            }

            // DEFINITION OF ACTIONS.
            for(Action a: actions){
                stringBuilder.append("" + prefix + "action(" + a.getName() +").\n");

                // CWA FOR ACTIONS
                stringBuilder.append("-" + prefix + "occurs(" + a.getName() +",I) :-" +
                        " not " + prefix + "occurs(" + a.getName() + ",I), step(I).\n");

                stringBuilder.append("\n");
            }

            // Step 3: Get all Fluents from all starting states
            // and define their holds-Attribute for timestamp 0.
            for(State start: startingStates){
                for(Fluent fluent: start.getFluents()) {
                    if(fluent.getValue()){
                        stringBuilder.append("" + prefix + "holds(" + fluent.getName() + ",0).\n");
                    } else {
                        stringBuilder.append("-" + prefix + "holds(" + fluent.getName() + ",0).\n");
                    }
                }
            }

            stringBuilder.append("\n");

            // Step 4: Search through the graph via broad search.
            // Write ASP Codeblocks for each state and its prede- and successors.

            int i = 0;
            List<State> statesToVisit = new ArrayList<>();
            List<State> nextVisit = new ArrayList<>();
            List<State> visitedStates = new ArrayList<>();

            // Get all successors of our artificial start state.
            for(State startState: startingStates){
                for(Action startAction: startState.getOutgoingActions()){
                    statesToVisit.add(startAction.getEndState());
                }
                states.remove(startState);
            }

            while(true){
                for(State state: statesToVisit){
                    if(visitedStates.contains(state)){
                        continue;
                    }

                    // Standard ASP Codeblock for each predecessor (causal law).
                    if(state.getIngoingActions().isEmpty()){
                        stringBuilder.append("" + prefix + "holds(" + state.getName() + ",T+1) :- \n");
                        stringBuilder.append("           " + prefix + "occurs(do" + state.getName() + ", T).\n\n");
                    } else {
                        for(Action predecessorAction: state.getIngoingActions()){
                            stringBuilder.append("" + prefix + "holds(" + state.getName() + ",T+1) :- \n");

                            // The state "start" is merely used as an orientation where the workflow starts.
                            // It has no further impact on the workflow.
                            if(!predecessorAction.getStartState().getName().equals("start")){
                                stringBuilder.append("           " + prefix + "holds(" + predecessorAction.getStartState().getName() + ",T),\n");
                            }

                            stringBuilder.append("          -" + prefix + "holds(" + state.getName() + ",T),\n");
                            stringBuilder.append("           " + prefix + "occurs(do" + state.getName() + ",T).\n\n");
                            stringBuilder.append("           " + prefix + "edge(" + predecessorAction.getStartState().getName()
                                    + " , " + predecessorAction.getEndState().getName() + ").\n");
                        }
                    }

                    for(Action a: state.getOutgoingActions()){
                        nextVisit.add(a.getEndState());
                    }

                    visitedStates.add(state);

                    stringBuilder.append("" + prefix + "occurs(do" + state.getName() + "," + i + ").\n\n");
                }

                // If nextVisit is empty
                if(nextVisit.isEmpty()){
                    break;
                }

                for(State newState: nextVisit){
                    statesToVisit.add(newState);
                }

                nextVisit.clear();
                i++;
            }

        return stringBuilder.toString();
    }

}
