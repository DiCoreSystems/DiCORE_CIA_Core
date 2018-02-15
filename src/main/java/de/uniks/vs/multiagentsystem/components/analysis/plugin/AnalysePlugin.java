package de.uniks.vs.multiagentsystem.components.analysis.plugin;

import de.uniks.vs.multiagentsystem.components.analysis.ChangeImpactAnalysis;
import javafx.util.Pair;

public abstract class AnalysePlugin {

    private ChangeImpactAnalysis impactAnalysis;

    public static enum Type {
        INTERFACE,
        WORKFLOW;
    }

    public AnalysePlugin(ChangeImpactAnalysis impactAnalysis) {
        this.impactAnalysis = impactAnalysis;
    }

    public String analyse(Type type, Pair content) {

        if (getType() != type)
            return null;

        return analyseContent(type, content);
    }

    public abstract Type getType();

    public abstract String analyseContent(Type type, Pair content);
}
