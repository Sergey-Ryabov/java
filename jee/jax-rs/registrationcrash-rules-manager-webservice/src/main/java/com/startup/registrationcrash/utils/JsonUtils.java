package com.startup.registrationcrash.utils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.startup.registrationcrash.resource.response.CustomResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 *
 * @author Сергей
 */
public class JsonUtils {

    public static String getObjectInJson(Object object) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(object);
    }

    public static String getSerializeWith(CustomResponse customResponse) throws JsonProcessingException, IOException {
        JsonFactory f = new JsonFactory();
        StringWriter writer = new StringWriter();

        JsonGenerator g = f.createGenerator(writer);
        g.writeStartObject();

//        g.writeObjectFieldStart("header");
//        for (int i = 0; i < customResponse.getHeader().size(); i++) {
//            String[] fieldParts = customResponse.getHeader().get(i).split(":");
//            g.writeStringField(fieldParts[0], fieldParts[1]);
//        }
//        g.writeEndObject();
//        ObjectMapper mapper = new ObjectMapper();
//        
//        g.writeObjectFieldStart("header");
//        g.writeTree(mapper.readTree(customResponse.getHeader()));
//        g.writeEndObject();
//
//        g.writeArrayFieldStart("rows");
//        g.writeTree(mapper.readTree(customResponse.getRows()));
//        g.writeEndArray();
//
//        g.writeEndObject();
//        g.close();
        return writer.toString();
    }

}
