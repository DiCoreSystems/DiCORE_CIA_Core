package de.uniks.vs.multiagentsystem.components.analysis.asp.wsdl;

import wsdlhelper.MessageTuple;
import wsdlhelper.OperationTuple;
import wsdlhelper.Service;
import wsdlhelper.TypeTriple;

/**
 * Created by CSZ on 14.12.2017.
 * This class translates every information stored in our WSDLDocument directly into ASP.
 */
public class WSDLTranslator {

    private String prefix = "_n_";

    public String translate(WSDLDocument document, boolean withPrefix) {

        if (!withPrefix) {
            prefix = "";
        }

        StringBuilder stringBuilder = new StringBuilder();

        Service service = document.getService();

        if (service != null) {
            if (service.getServiceBinding() != null && !service.getServiceBinding().isEmpty()) {
                stringBuilder.append(prefix + "services(" + firstLetterLowerCase(service.getServiceName()) +
                        ", " + firstLetterLowerCase(service.getServiceBinding()) + ").\n");
            } else {
                stringBuilder.append(prefix + "services(" + firstLetterLowerCase(service.getServiceName()) + ").\n");
            }
        }

        for (String binding : document.getBindings()) {
            stringBuilder.append(prefix + "binding(" + firstLetterLowerCase(binding) + ").\n");
        }

        for (OperationTuple o : document.getOperations()) {
            stringBuilder.append(prefix + "operation(" + firstLetterLowerCase(o.getName())
                    + ", " + firstLetterLowerCase(o.getInput())
                    + ", " + firstLetterLowerCase(o.getOutput()) + ").\n");
        }

        for (TypeTriple t : document.getTypes()) {
            switch (t.getAttribute()) {
                case NORMAL:
                    stringBuilder.append(prefix + "type(" + firstLetterLowerCase(t.getName()) +
                            ", " + firstLetterLowerCase(t.getType()) + ").\n");
                    break;
                case INPUT:
                    stringBuilder.append(prefix + "input(" + firstLetterLowerCase(t.getParent().getName())
                            + ", " + firstLetterLowerCase(t.getName()) +
                            ", " + firstLetterLowerCase(t.getType()) + ").\n");
                    break;
                case OUTPUT:
                    stringBuilder.append(prefix + "output(" + firstLetterLowerCase(t.getParent().getName())
                            + ", " + firstLetterLowerCase(t.getName())
                            + ", " + firstLetterLowerCase(t.getType()) + ").\n");
                    break;
                case FAULT:
                    stringBuilder.append(prefix + "fault(" + firstLetterLowerCase(t.getParent().getName()) +
                            ", " + firstLetterLowerCase(t.getName()) +
                            ", " + firstLetterLowerCase(t.getType()) + ").\n");
                    break;
                default:
                    System.out.println("Your type is not defined.");
            }
        }

        for (MessageTuple m : document.getMessages()) {
            stringBuilder.append(prefix + "messagePart(" + firstLetterLowerCase(m.getMessageName()) + ", " +
                    firstLetterLowerCase(m.getPart()) + ").\n");
        }

        for (String i : document.getImports()) {
            stringBuilder.append(prefix + "import(" + firstLetterLowerCase(i) + ").");
        }

        return stringBuilder.toString();
    }

    private String firstLetterLowerCase(String string) {
        if (string.isEmpty()) {
            return string;
        }

        char c[] = string.toCharArray();
        c[0] = Character.toLowerCase(c[0]);
        String result = new String(c);
        return result;
    }
}
