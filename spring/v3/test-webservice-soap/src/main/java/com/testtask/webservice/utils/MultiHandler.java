package com.testtask.webservice.utils;

import com.testtask.webservice.endpoint.response.MessageResponsePart;
import com.testtask.webservice.model.Applicant;
import com.testtask.webservice.model.Docs;
import com.testtask.webservice.model.DocsOptional;
import org.joda.time.LocalDateTime;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.testtask.webservice.endpoint.response.Role;

import java.lang.Object;
import java.util.*;


/**
 * Created by ryabov on 29.04.2016.
 */
public class MultiHandler extends DefaultHandler {


    private Map<String, Object> parsedData;

    private Role senderRole;
    private Role recipientRole;
    private Role originatorRole;
    private MessageResponsePart messageResponsePart;
    List<DocsOptional> docsOptionals = null;
    Applicant applicant = null;
    com.testtask.webservice.model.Object object = null;
    Docs docs = null;
    DocsOptional docsOptional = null;


    public List<Applicant> getApplicants() {
        return Arrays.asList(applicant);
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public Map<String, Object> getParsedData() {
        return parsedData;
    }

    //messageTag
    private boolean roleCode;
    private boolean roleName;
    private boolean messageTag;
    private boolean sender;
    private boolean recipient;
    private boolean originator;
    private boolean typeCode;
    //    private boolean status;
    private boolean date;
    private boolean exchangeType;
    private boolean requestIdRef;
    private boolean originRequestIdRef;
    private boolean serviceCode;
    private boolean caseNumber;

    private boolean xmlBody;
    //    applicant's fields
    private boolean lastName;
    private boolean firstName;
    private boolean middleName;
    private boolean personINN;
    private boolean personEMail;

    //    object's fields
    private boolean objectTypeId;
    private boolean nameObject;
    private boolean power;

    //    docs's fields
    private boolean requiredInfo;
    //    docsOptional's fields
    private boolean namePart;
    private boolean shifr;



    private StringBuilder stringBuilder = new StringBuilder();


    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        if (qName.endsWith("Message")) {
            messageResponsePart = new MessageResponsePart();
            messageTag = true;
        } else if (messageTag) {
            startMessageElement(uri, localName, qName, attributes);
        }
        if (qName.equalsIgnoreCase("xmlBody")) {
            xmlBody = true;
        } else if (xmlBody) {
            startApplicantElement(uri, localName, qName, attributes);
            startObjectElement(uri, localName, qName, attributes);
            startDocsElement(uri, localName, qName, attributes);
            startDocsOptionalElement(uri, localName, qName, attributes);
        }
    }

    @Override
    public void characters(char ch[], int start, int length)
            throws SAXException {
        if (messageTag || xmlBody) {
            stringBuilder.append(new String(ch, start, length).trim());
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        if (messageTag) {
            charactersMessage();
            if (qName.endsWith("Message")) {
                if (parsedData == null) {
//                    System.out.println("parsedData was null 1");
                    parsedData = new HashMap<String, Object>();
                }
                //recipientRole and senderRole replaced for response
                messageResponsePart.setSender(recipientRole);
                messageResponsePart.setRecipient(senderRole);
                messageResponsePart.setOriginator(originatorRole);
                parsedData.put("Message", messageResponsePart);
//                System.out.println("parsedData size now 1 = " + parsedData.size());
                messageTag = false;
            }
        }
        if (xmlBody) {
            charactersApplicant();
            charactersObject();
            charactersDocs();
            charactersDocsOptional();
            if (qName.equalsIgnoreCase("DocsOptional")) {
                docsOptionals.add(docsOptional);
            }
            else if (qName.equalsIgnoreCase("Object")) {
                object.setDocs(docs);
                object.setDocsOptionals(docsOptionals);
                docsOptionals = null;
            } else if (qName.equalsIgnoreCase("Applicant")) {
                applicant.setObjects(Arrays.asList(object));
            } else if (qName.equalsIgnoreCase("xmlBody")) {
                if (parsedData == null) {
                    parsedData = new HashMap<String, Object>();
                }
                parsedData.put("Applicant", applicant);
                xmlBody = false;
            }
        }
    }

    private void startMessageElement(String uri, String localName, String qName, Attributes attributes) {
//        messageResponsePart
//        System.out.println("qName = " + qName);
        if (qName.endsWith("Sender")) {
//            System.out.println("Sender true");
            sender = true;
            senderRole = new Role();
        } else if (qName.endsWith("Recipient")) {
//            System.out.println("Recipient true");
            recipient = true;
            recipientRole = new Role();
        } else if (qName.endsWith("Originator")) {
            originator = true;
            originatorRole = new Role();
        } else if (qName.endsWith("TypeCode")) {
            typeCode = true;
        } else if (qName.endsWith("ServiceCode")) {
            serviceCode = true;
//            System.out.println("serviceCode true");
        } else if (qName.endsWith("Code")) {
            roleCode = true;
        } else if (qName.endsWith("Name")) {
            roleName = true;
        } else if (qName.endsWith("Date")) {
            date = true;
        } else if (qName.endsWith("ExchangeType")) {
            exchangeType = true;
        } else if (qName.endsWith("OriginRequestIdRef")) {
            originRequestIdRef = true;
        } else if (qName.endsWith("RequestIdRef")) {
            requestIdRef = true;
        } else if (qName.endsWith("CaseNumber")) {
            caseNumber = true;
        }
    }

    private void startApplicantElement(String uri, String localName, String qName,
                                       Attributes attributes) {
        //    applicant's fields
        if (qName.equalsIgnoreCase("Applicant")) {
            applicant = new Applicant();
//            if (applicants == null) {
//                applicants = new ArrayList<Applicant>();
//            }

        } else if (qName.equalsIgnoreCase("lastName")) {
            lastName = true;
        } else if (qName.equalsIgnoreCase("firstName")) {
            firstName = true;
        } else if (qName.equalsIgnoreCase("middleName")) {
            middleName = true;
        }  else if (qName.equalsIgnoreCase("personINN")) {
            personINN = true;
        }  else if (qName.equalsIgnoreCase("personEMail")) {
            personEMail = true;
        }
    }

    private void startObjectElement(String uri, String localName, String qName,
                                    Attributes attributes) {

        //    object's fields
        if (qName.equalsIgnoreCase("Object")) {
            object = new com.testtask.webservice.model.Object();
//            if (objects == null) {
//                objects = new ArrayList<Object>();
//            }
        } else if (qName.equalsIgnoreCase("objectTypeId")) {
            objectTypeId = true;
        } else if (qName.equalsIgnoreCase("nameObject")) {
            nameObject = true;
        } else if (qName.equalsIgnoreCase("power")) {
            power = true;
        }
    }

    private void startDocsElement(String uri, String localName, String qName,
                                  Attributes attributes) {
//            //    docs's fields
        if (qName.equalsIgnoreCase("docs")) {
            docs = new Docs();
        } else if (qName.equalsIgnoreCase("requiredInfo")) {
            requiredInfo = true;
        }
    }

    private void startDocsOptionalElement(String uri, String localName, String qName,
                                          Attributes attributes) {
        //    docsOptional's fields
        if (qName.equalsIgnoreCase("DocsOptional")) {
            docsOptional = new DocsOptional();
            if (docsOptionals == null) {
                docsOptionals = new ArrayList<DocsOptional>();
            }
        } else if (qName.equalsIgnoreCase("namePart")) {
            namePart = true;
        } else if (qName.equalsIgnoreCase("shifr")) {
            shifr = true;
        }
    }

    private void charactersMessage() {
        if (sender & roleCode) {
//            System.out.println("sender & roleCode");
            senderRole.setCode(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            roleCode = false;
        } else if (sender & roleName) {
//            System.out.println("sender & roleName");
            senderRole.setName(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            roleName = false;
        } else if (recipient & roleCode) {
            recipientRole.setCode(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            roleCode = false;
        } else if (recipient & roleName) {
            recipientRole.setName(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            roleName = false;
        } else if (originator & roleCode) {
            originatorRole.setCode(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            roleCode = false;
        } else if (originator & roleName) {
            originatorRole.setName(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            roleName = false;
        }else if (sender) {
            sender = false;
        } else if (recipient) {
            recipient = false;
        } else if (originator) {
            originator = false;
        } else if (typeCode) {
            messageResponsePart.setTypeCode(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            typeCode = false;
        } else if (date) {
            messageResponsePart.setDate(LocalDateTime.now().toString());
            stringBuilder.delete(0, stringBuilder.length());
            date = false;
        } else if (exchangeType) {
            messageResponsePart.setExchangeType(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            exchangeType = false;
        } else if (requestIdRef) {
            messageResponsePart.setRequestIdRef(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            requestIdRef = false;
        } else if (originRequestIdRef) {
            messageResponsePart.setOriginRequestIdRef(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            originRequestIdRef = false;
        } else if (serviceCode) {
//            System.out.println("serviceCode: stringBuilder.toString() = " + stringBuilder.toString());
            messageResponsePart.setServiceCode(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            serviceCode = false;
        } else if (caseNumber) {
            messageResponsePart.setCaseNumber(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            caseNumber = false;
        }
    }

    private void charactersApplicant() {

        //applicant
        if (lastName) {
            applicant.setLastName(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            lastName = false;
        } else if (firstName) {
            applicant.setFirstName(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            firstName = false;
        } else if (middleName) {
            applicant.setMiddleName(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            middleName = false;
        } else if (personINN) {
            applicant.setPersonINN(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            personINN = false;
        } else if (personEMail) {
            applicant.setPersonEMail(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            personEMail = false;
        }
    }

    private void charactersObject() {
        //        object
        if (objectTypeId) {
            object.setObjectTypeId(Long.parseLong(stringBuilder.toString()));
            stringBuilder.delete(0, stringBuilder.length());
            objectTypeId = false;
        } else if (nameObject) {
            object.setNameObject(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            nameObject = false;
        }else if (power) {
            object.setPower(Integer.parseInt(stringBuilder.toString()));
            stringBuilder.delete(0, stringBuilder.length());
            power = false;
        }
    }

    private void charactersDocs() {
        if (requiredInfo) {
            docs.setRequiredInfo(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            requiredInfo = false;
        }
    }

    private void charactersDocsOptional() {
        //        docsOptional
        if (namePart) {
            docsOptional.setNamePart(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            namePart = false;
        } else if (shifr) {
            docsOptional.setShifr(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());
            shifr = false;
        }
    }

}
