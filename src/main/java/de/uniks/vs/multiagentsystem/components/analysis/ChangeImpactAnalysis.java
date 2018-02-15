package de.uniks.vs.multiagentsystem.components.analysis;

import de.uniks.vs.multiagentsystem.components.ChangeDetection;
import de.uniks.vs.multiagentsystem.components.Communication;
import de.uniks.vs.multiagentsystem.components.Knowledgebase;
import de.uniks.vs.multiagentsystem.components.Monitoring;
import de.uniks.vs.multiagentsystem.components.analysis.plugin.ASPInterfaceAnalysisPlugin;
import de.uniks.vs.multiagentsystem.components.analysis.plugin.ASPWorkflowAnalysisPlugin;
import de.uniks.vs.multiagentsystem.components.analysis.plugin.AnalysePlugin;
import javafx.util.Pair;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class ChangeImpactAnalysis extends Analysis{

    private Monitoring monitoring;
    private ChangeDetection changeDetection;
    private Vector<AnalysePlugin> analysePlugins;

    public ChangeImpactAnalysis(Knowledgebase knowledgebase, Communication communication) {
        super(knowledgebase, communication);
        monitoring          = new Monitoring(knowledgebase, communication);
        changeDetection     = new ChangeDetection(knowledgebase);
        analysePlugins      = new Vector<>();
        analysePlugins.add(new ASPInterfaceAnalysisPlugin(this));
        analysePlugins.add(new ASPWorkflowAnalysisPlugin(this));
    }

    public void monitor() {
        monitoring.update();
    }

    public void detect() {
        changeDetection.check();
    }

    public void analyse() {
        // Interfaces
        ConcurrentHashMap<String, Pair> changedInterfaces = knowledgebase.getChangedInterfaces();

        if(!changedInterfaces.isEmpty()) {

            for (String service : changedInterfaces.keySet()) {
                String result = analyseChange(AnalysePlugin.Type.INTERFACE, changedInterfaces.get(service));
                handleResult(service, result);
            }
            changedInterfaces.clear();
        }

        // Workflow
        ConcurrentHashMap<String, Pair> changedStructures = knowledgebase.getChangedStructures();

        if(!changedStructures.isEmpty()) {

            for (String service : changedStructures.keySet()) {
                String result = analyseChange(AnalysePlugin.Type.WORKFLOW, changedStructures.get(service));
                handleResult(service, result);
            }
            changedStructures.clear();
        }
    }

    private void handleResult(String service, String result) {
        
        if (result == null)
            return;
        knowledgebase.addImpactAnalysisResults(service, result);
    }

    private String analyseChange(AnalysePlugin.Type type, Pair content) {
        String result = null;

        for (AnalysePlugin plugin: analysePlugins) {
            result = plugin.analyse(type, content);

            if (result != null)
                return result;
        }
        return result;
    }

    // Behaviour
//    private void checkMessages() {
//        Vector<Pair<String, Pair>> messageQueue = knowledgebase.getMessageQueue();
//
//        synchronized (messageQueue) {
//            if (!messageQueue.isEmpty()) {
//                System.out.println(messageQueue);
//                monitorMessage(messageQueue.get(0));
//
//                synchronized (messageQueue) {
//                    messageQueue.removeElementAt(0);
//                }
//            }
//        }
//    }
}
