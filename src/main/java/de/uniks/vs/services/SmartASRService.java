package de.uniks.vs.services;

import de.uniks.vs.multiagentsystem.EvolutionAgent;
import de.uniks.vs.multiagentsystem.behaviour.AgentBehaviour;
import de.uniks.vs.multiagentsystem.components.analysis.Analysis;
import de.uniks.vs.multiagentsystem.components.analysis.ApplicationImpactAnalysis;
import de.uniks.vs.multiagentsystem.components.analysis.ChangeImpactAnalysis;
import de.uniks.vs.multiagentsystem.components.CoEvolutionCoordination;
import de.uniks.vs.multiagentsystem.components.analysis.ServiceImpactAnalysis;
import javafx.util.Pair;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;

public class SmartASRService extends RestResource {

    static HashMap<String, String> services = new HashMap<>();
    static HashMap<String, EvolutionAgent> serviceAgents = new HashMap<>();
    static HashMap<String, EvolutionAgent> applicationAgents = new HashMap<>();

    @GET
    @Path("/register/services/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doRegisterService(@PathParam("id") String service) {

        if (!serviceAgents.containsKey(service)) {
            EvolutionAgent serviceAgent = new EvolutionAgent();
            Analysis analysis = new ServiceImpactAnalysis(serviceAgent.getKnowledgebase(), serviceAgent.getCommunication());
            CoEvolutionCoordination coordination = new CoEvolutionCoordination(serviceAgent.getKnowledgebase(), serviceAgent.getCommunication());
            serviceAgent.init(analysis, coordination, new AgentBehaviour(analysis, coordination, serviceAgent.getNotification()));
            serviceAgents.put(service, serviceAgent);
        }

        if (serviceAgents.get(service) == null) {
            return "FAILURE";
        }
        return "SUCCESS";
    }
    @GET
    @Path("/register/application/{id}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doRegisterApplication(@PathParam("id") String application) {

        if (!applicationAgents.containsKey(application)) {
            EvolutionAgent applicationAgent = new EvolutionAgent();
            Analysis analysis = new ApplicationImpactAnalysis(applicationAgent.getKnowledgebase(), applicationAgent.getCommunication());
            CoEvolutionCoordination coordination = new CoEvolutionCoordination(applicationAgent.getKnowledgebase(), applicationAgent.getCommunication());
            applicationAgent.init(analysis, coordination, new AgentBehaviour(analysis, coordination, applicationAgent.getNotification()));
            applicationAgents.put(application, applicationAgent);
        }

        if (applicationAgents.get(application) == null) {
            return "FAILURE";
        }
        return "SUCCESS";
    }

    @GET
    @Path("/update/serviceagent/{id}/{data}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doGetSA(@PathParam("id") String string, @PathParam("data") String data) {

        if (!serviceAgents.containsKey(string))
            return "FAILURE";
        EvolutionAgent agent = serviceAgents.get(string);
        System.out.println(string + " " + data);
        agent.getKnowledgebase().addToMessageQueue(new Pair(string, data));
        return "SUCCESS";
    }

    @GET
    @Path("/update/applicationagent/{id}/{data}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String doGetAA(@PathParam("id") String string, @PathParam("data") String data) {

        if (!applicationAgents.containsKey(string))
            return "FAILURE";
        EvolutionAgent agent = applicationAgents.get(string);
        agent.getKnowledgebase().addToMessageQueue(new Pair(string, data));
        return "SUCCESS";
    }



    @Override
    protected String getDescription() {
        String descript = "input:ServiceDescription, output:String";
        return descript;
    }
}