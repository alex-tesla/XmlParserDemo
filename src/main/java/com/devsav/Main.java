package com.devsav;

import com.devsav.parser.ParserXML;

/**
 * Application to add new tag <Actor.Color> in <Neutral> tags with <Origin> child
 * and to modify if tag <Actor.Color> already exist in an xml file.
 *
 * XPath to find and select specific tag.
 * Java DOM Parser to modify xml file.
 * Log4j to logging.
 *
 * @author Alexander Sobolev
 */



public class Main {


    public static void main(String[] args) {

        String inputFile = "src/main/resources/source_file.xml";
        String outputFile = "src/main/resources/target_file.xml";

        String xpathToModify = "//Actor.Color";
        String xpathToAdd = "//Neutral[descendant::Origin and not(descendant::Actor.Color)]";

        ParserXML parserXML = new ParserXML(inputFile);
        parserXML.modifyNode(xpathToModify);
        parserXML.addNode(xpathToAdd);
        parserXML.saveFile(outputFile);

    }
}
