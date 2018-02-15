package de.uniks.vs.multiagentsystem.components.analysis.plugin;

import de.uniks.vs.multiagentsystem.components.analysis.ChangeImpactAnalysis;
import de.uniks.vs.multiagentsystem.components.analysis.asp.ExtClingoRunner;
import de.uniks.vs.multiagentsystem.components.analysis.asp.wsdl.WSDLTranslator;
import de.uniks.vs.multiagentsystem.components.analysis.asp.wsdl.WSDLDocument;
import de.uniks.vs.multiagentsystem.components.analysis.asp.wsdl.WSDLParser;
import javafx.util.Pair;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ASPInterfaceAnalysisPlugin extends AnalysePlugin {

    public ASPInterfaceAnalysisPlugin(ChangeImpactAnalysis impactAnalysis) {
        super(impactAnalysis);
    }

    @Override
    public Type getType() {
        return Type.INTERFACE;
    }

    @Override
    public String analyseContent(Type type, Pair content) {
        String results = "";
        String first = (String) content.getKey();
        String second = (String) content.getValue();

        WSDLDocument document1 = new WSDLDocument(first);
        WSDLDocument document2 = new WSDLDocument(second);
        WSDLParser parser = new WSDLParser();
        try {
            parser.parse(document1, true);
            parser.parse(document2, true);

            String aspCode1 = new WSDLTranslator().translate(document1, false);
            String aspCode2 = new WSDLTranslator().translate(document2, true);

            ExtClingoRunner clingo = new ExtClingoRunner();
            results = clingo.findDifferences(aspCode1, aspCode2, false);

        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }

        return results;
    }




}
