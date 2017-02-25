package com.testtask.webservice.utils;

import com.testtask.webservice.model.Applicant;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by ryabov on 26.04.2016.
 */
public class XMLParser extends DefaultHandler {


    public List<Applicant> getApplicants(InputStream inputSource) throws ParserConfigurationException, SAXException, IOException {
        List<Applicant> applicants = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        MultiHandler multiHandler = new MultiHandler();
        saxParser.parse(inputSource, multiHandler);
        applicants = multiHandler.getApplicants();
        return applicants;
    }

    public Applicant getApplicant(InputStream inputSource) throws ParserConfigurationException, SAXException, IOException {
        Applicant applicant = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        MultiHandler multiHandler = new MultiHandler();
        saxParser.parse(inputSource, multiHandler);
        applicant = multiHandler.getApplicant();
        return applicant;
    }

    //getApplicant + parsed data from <Message>
    public Map<String, Object> getParsedData(InputStream inputSource) throws ParserConfigurationException, SAXException, IOException {
        Map<String, Object> parsedData = null;
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        MultiHandler multiHandler = new MultiHandler();
        saxParser.parse(inputSource, multiHandler);
        parsedData = multiHandler.getParsedData();
        return parsedData;
    }

}

