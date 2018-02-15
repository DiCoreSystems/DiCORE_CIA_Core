package de.uniks.vs.multiagentsystem.components.analysis.asp;

import org.lorislab.clingo4j.api.*;
import parser.ClingoRunner;

import java.io.File;

public class ExtClingoRunner extends ClingoRunner {
//    private final String configPath = System.getProperty("user.dir") + "/src/main/java/de/uniks/vs/services/servicedescriptions/defaults";
    private final String configPath = System.getProperty("user.dir") + "/src/main/java/de/uniks/vs/multiagentsystem/components/analysis/asp/defaults";
    private File workflowDiffFile = new File(configPath + "/workflow/diff.lp");
    private File interfaceDefaultFile = new File(configPath + "/interface/default.lp");
    private File interfaceDiffFile = new File(configPath + "/interface/diff.lp");

    /**
     * In order for this to work properly, one of these files should be created with a prefix and one without.
     * @param aspCode1
     * @param aspCode2
     * @param workflow True if you're running a workflow file, false otherwise.
     */

    public String findDifferences(String aspCode1, String aspCode2, boolean workflow) {
        StringBuilder result = new StringBuilder();

        Clingo.init("../../../Dev.Cloud.Area/Java/clingo4j/src/main/clingo");

        try (Clingo control = Clingo.create()) {
            System.out.println("Clingo Ver. " + control.getVersion());
            control.add("base", aspCode1);
            control.add("base",aspCode2);

            if(!workflow){
                control.add("base", extractLogicProgram(interfaceDefaultFile));
                control.add("base", extractLogicProgram(interfaceDiffFile));
            } else {
                control.add("base", extractLogicProgram(workflowDiffFile));
            }

            control.ground("base");

            try (SolveHandle handle = control.solve()) {
                for (Model model : handle)  {
                    System.out.println("Model type: " + model.getType());
                    for (Symbol atom : model.getSymbols()) {
                        result.append(atom);
                    }
                }
            }
        } catch (ClingoException ex) {
            System.err.println(ex.getMessage());
        }

        return result.toString();
    }
}
