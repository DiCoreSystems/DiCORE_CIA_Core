package de.uniks.vs.multiagentsystem.components;

import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

public class NotificationManagement {

    private final Knowledgebase knowledgebase;
    private final Communication communication;

    public NotificationManagement(Knowledgebase knowledgebase, Communication communication) {
        this.knowledgebase = knowledgebase;
        this.communication = communication;
    }

    public void generateNotifications() {
        ConcurrentHashMap<String, Vector<String>> analysisResults = this.knowledgebase.getImpactAnalysisResults();

        for (String  service: analysisResults.keySet()) {
            System.out.println(service + "  ->  " + analysisResults.get(service));
        }
    }
}
