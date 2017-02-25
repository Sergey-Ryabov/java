package com.testtask.webservice.endpoint;

import com.testtask.webservice.utils.Utils;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.w3c.dom.Document;

/**
 * Created by ryabov on 01.12.2016.
 */
public class CustomEndpointInterceptor implements EndpointInterceptor {

    public boolean handleRequest(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    public boolean handleResponse(MessageContext messageContext, Object endpoint) throws Exception {

        System.out.println("CustomEndpointInterceptor: interceptor that can sign message for example");
        SaajSoapMessage soapResponse = (SaajSoapMessage) messageContext.getResponse();
        Document doc = soapResponse.getDocument();

        String response = Utils.getStringFromDocument(doc);

//        sign message


        soapResponse.setDocument(Utils.getDocumentFromString(response));

        return true;
    }

    public boolean handleFault(MessageContext messageContext, Object endpoint) throws Exception {
        return true;
    }

    public void afterCompletion(MessageContext messageContext, Object o, Exception e) {

    }


}
