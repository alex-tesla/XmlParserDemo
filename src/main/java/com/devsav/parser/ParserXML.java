package com.devsav.parser;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.IOException;

public class ParserXML {

    private static final Logger LOGGER = Logger.getLogger(ParserXML.class);

    private Document document;



    public ParserXML(String inputFile) {

        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            document = builderFactory.newDocumentBuilder().parse(inputFile);

        } catch (ParserConfigurationException pce) {
            LOGGER.error("Exception while loading file " + pce.getMessage());
        } catch (SAXException saxe) {
            LOGGER.error("Exception while loading file " + saxe.getMessage());
        } catch (IOException ioe) {
            LOGGER.error("Exception while loading file " + ioe.getMessage());
        }
    }


    public void modifyNode(String expression) {

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            NodeList nodesToModify = (NodeList) xpath.evaluate
                    (expression, document, XPathConstants.NODESET);

            for (int i = 0; i < nodesToModify.getLength(); i++) {

                Element actorColor = (Element) nodesToModify.item(i);
                actorColor.setAttribute("R", "100");
                actorColor.setAttribute("G", "155");
                actorColor.setAttribute("B", "255");

                LOGGER.info("Node: " + (i + 1) + " " + actorColor.getNodeName() + " modified.");
            }
        } catch (XPathExpressionException xpe) {
            LOGGER.error("Exception in XPath expression " + xpe.getMessage());
        }
    }


    public void addNode(String expression) {

        XPath xpath = XPathFactory.newInstance().newXPath();

        try {
            NodeList nodesToAdd = (NodeList) xpath.evaluate
                    (expression, document, XPathConstants.NODESET);

            for (int i = 0; i < nodesToAdd.getLength(); i++) {

                Element actorColor = document.createElement("Actor.Color");
                actorColor.setAttribute("R", "100");
                actorColor.setAttribute("G", "155");
                actorColor.setAttribute("B", "255");

                Node theNode = nodesToAdd.item(i);
                theNode.insertBefore(actorColor, theNode.getFirstChild());

                LOGGER.info("Node: " + (i + 1) + " " + actorColor.getNodeName()
                        + " added in " + theNode.getNodeName());
            }
        } catch (XPathExpressionException xpe) {
            LOGGER.error("Exception in XPath expression " + xpe.getMessage());
        }
    }


    public void saveFile(String outputFile) {

        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Result output = new StreamResult(new File(outputFile));
            Source input = new DOMSource(document);
            transformer.transform(input, output);

        } catch (TransformerConfigurationException tce) {
            LOGGER.error("Exception while saving file " + tce.getMessage());
        }catch (TransformerException te) {
            LOGGER.error("Exception while saving file " + te.getMessage());
        }
    }
}