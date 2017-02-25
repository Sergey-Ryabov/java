package com.testtask.webservice.endpoint;

/**
 * Created by Сергей on 21.07.2016.
 */

import com.testtask.webservice.dao.ApplicantDAO;
import com.testtask.webservice.dao.DocsDAO;
import com.testtask.webservice.dao.DocsOptionalDAO;
import com.testtask.webservice.dao.ObjectDAO;
import com.testtask.webservice.endpoint.response.MessageResponsePart;
import com.testtask.webservice.model.Applicant;
import com.testtask.webservice.model.Docs;
import com.testtask.webservice.model.DocsOptional;
import org.apache.commons.io.input.ReaderInputStream;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.xml.sax.SAXException;
import com.testtask.webservice.ApplicationContextProvider;
import com.testtask.webservice.endpoint.response.AppDataResponsePart;
import com.testtask.webservice.endpoint.response.CreateElservicesResponse;
import com.testtask.webservice.endpoint.response.MessageDataResponsePart;
import com.testtask.webservice.utils.Constants;
import com.testtask.webservice.utils.XMLParser;

import javax.annotation.PostConstruct;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.Object;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


@Endpoint
public class ObjectsWriterEndpoint {

    private static Logger logger = Logger.getLogger(ObjectsWriterEndpoint.class.getName());

    private static final String NAMESPACE_URI = "http://testtask.org/spring/ws/api";
    private Map<String, Object> parsedData;
    private Applicant applicant;
    private MessageResponsePart messageResponsePart;

    @Autowired
    ApplicationContextProvider applicationContextProvider;
    @Autowired
    ApplicantDAO applicantDAO;
    @Autowired
    DocsDAO docsDAO;
    @Autowired
    DocsOptionalDAO docsOptionalDAO;

    @Autowired
    ObjectDAO objectDAO;

    @PostConstruct
    public void initIt() throws Exception {
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createTicket")
    @ResponsePayload
    public CreateElservicesResponse handleHolidayRequest(@RequestPayload Element holidayRequest)
            throws Exception {
        String request = new XMLOutputter().outputString(holidayRequest);
        ResponseEntity responseEntity = writeObjectsToDb(request);
        System.out.println("StatusCode=" + responseEntity.getStatusCode());
        try {
            CreateElservicesResponse createElservicesResponse = new CreateElservicesResponse();
            if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                messageResponsePart.setStatus(Constants.STATUS_ACCEPT);
                AppDataResponsePart appDataResponsePart = new AppDataResponsePart(String.valueOf(applicant.getObjects().get(0).getId()));
                MessageDataResponsePart messageDataResponsePart = new MessageDataResponsePart(appDataResponsePart);
                createElservicesResponse.setMessageData(messageDataResponsePart);
            } else {
                messageResponsePart.setStatus(Constants.STATUS_FAILURE);
            }
            createElservicesResponse.setMessage(messageResponsePart);
            return createElservicesResponse;
        } catch (Exception e) {
            logger.warning("Exception: " + e.getMessage());
            return new CreateElservicesResponse(e.getMessage());
        }
    }


    public ResponseEntity writeObjectsToDb(String xmlString) {
        System.out.println("--------------------1111111111111111111111-----------------");
        System.out.println("xmlString = " + xmlString);
        System.out.println("--------------------222222222222-----------------");
        try {
            parsedData = new XMLParser().getParsedData(new ReaderInputStream(new StringReader(xmlString), "UTF8"));
            if (parsedData != null) {
                messageResponsePart = (MessageResponsePart) parsedData.get("Message");
                applicant = (Applicant) parsedData.get("Applicant");

            } else {
                logger.warning("Data for parsing does not exist");
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (ParserConfigurationException e) {
            logger.warning("ParserConfigurationException: " + e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (SAXException e) {
            logger.warning("SAXException: " + e.getMessage());
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            logger.warning("IOException: " + e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        logger.info("Input data parsed successfully");
        com.testtask.webservice.model.Object object = null;
        try {
            if (applicant != null) {
                long applicantId = 0;
                applicantId = applicantDAO.create(applicant);
                applicant.setId(applicantId);
                List<com.testtask.webservice.model.Object> objects = applicant.getObjects();
                if (objects != null) {
                    object = objects.get(0);
                    object.setApplicantId(applicantId);
                    long objectId = objectDAO.create(object);
                    object.setId(objectId);

                    List<DocsOptional> docsOptionals = object.getDocsOptionals();
                    if (docsOptionals != null) {
                        for (int k = 0; k < docsOptionals.size(); k++) {
                            DocsOptional docsOptional = docsOptionals.get(k);
                            docsOptional.setObjectId(objectId);
                            long id = docsOptionalDAO.create(docsOptional);
                            docsOptional.setId(id);
                        }
                    }

                    Docs docsObject = object.getDocs();
                    if (docsObject != null) {
                        docsObject.setObjectId(objectId);
                        long id = docsDAO.create(docsObject);
                        docsObject.setId(id);
                    }
                }
            } else {
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            logger.warning("Exception: " + e.getMessage());
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity(HttpStatus.CREATED);
    }



}