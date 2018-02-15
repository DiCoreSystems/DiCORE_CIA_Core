package de.uniks.vs.multiagentsystem.behaviour;

import de.uniks.vs.multiagentsystem.components.analysis.Analysis;
import de.uniks.vs.multiagentsystem.components.analysis.ChangeImpactAnalysis;
import de.uniks.vs.multiagentsystem.components.CoEvolutionCoordination;
import de.uniks.vs.multiagentsystem.components.NotificationManagement;

public class AgentBehaviour {

    private Analysis analysis;
    private CoEvolutionCoordination coordination;
    private NotificationManagement notification;
    private boolean running;

    public AgentBehaviour(Analysis analysis, CoEvolutionCoordination coordination, NotificationManagement notification) {
        this.analysis = analysis;
        this.coordination = coordination;
        this.notification = notification;
        this.running = true;
    }

    public void run() {

        while (running) {
            // Monitoring
            analysis.monitor();

            // Change Detection
            analysis.detect();

            // Change Analysis
            analysis.analyse();

            //Change Notification
            notification.generateNotifications();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
