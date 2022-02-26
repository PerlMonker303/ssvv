package repository;

import domain.Grade;
import domain.Pair;
import domain.Student;
import validation.StudentValidator;
import validation.AssignmentValidator;
import validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GradeXMLRepository extends AbstractXMLRepository<Pair<String, String>, Grade> {

    public GradeXMLRepository(Validator<Grade> validator, String XMLfilename) {
        super(validator, XMLfilename);
        loadFromXmlFile();
    }

    protected Element getElementFromEntity(Grade grade, Document XMLdocument) {
        Element element = XMLdocument.createElement("nota");
        element.setAttribute("IDStudent", grade.getID().getObject1());
        element.setAttribute("IDTema", grade.getID().getObject2());

        element.appendChild(createElement(XMLdocument, "Nota", String.valueOf(grade.getGrade())));
        element.appendChild(createElement(XMLdocument, "SaptamanaPredare", String.valueOf(grade.getSaptamanaPredare())));
        element.appendChild(createElement(XMLdocument, "Feedback", grade.getFeedback()));

        return element;
    }

    protected Grade getEntityFromNode(Element node) {
        String IDStudent = node.getAttributeNode("IDStudent").getValue();
        String IDTema= node.getAttributeNode("IDTema").getValue();
        double nota = Double.parseDouble(node.getElementsByTagName("Nota").item(0).getTextContent());
        int saptamanaPredare = Integer.parseInt(node.getElementsByTagName("SaptamanaPredare").item(0).getTextContent());
        String feedback = node.getElementsByTagName("Feedback").item(0).getTextContent();

        return new Grade(new Pair(IDStudent, IDTema), nota, saptamanaPredare, feedback);
    }
}
