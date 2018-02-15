package de.uniks.vs.multiagentsystem.components.analysis;

import de.uniks.vs.multiagentsystem.components.Communication;
import de.uniks.vs.multiagentsystem.components.Knowledgebase;

public abstract class Analysis {

    protected final Knowledgebase knowledgebase;
    private final Communication communication;

    public Analysis(Knowledgebase knowledgebase, Communication communication) {
        this.knowledgebase = knowledgebase;
        this.communication = communication;
    }

    public abstract void monitor() ;

    public abstract void detect() ;

    public abstract void analyse() ;
}
