package com.startup.registrationcrash.resource.filters;

import java.io.IOException;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Сергей
 */
@Provider
@PreMatching
public class EncodeFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        MultivaluedMap<String, String> headers = requestContext.getHeaders();
        System.out.println("filter test");
        List<String> contentTypes = headers.remove(HttpHeaders.CONTENT_TYPE);
        System.out.println("CONTENT_TYPE before = " + requestContext.getHeaderString(HttpHeaders.CONTENT_TYPE));
        if (contentTypes != null && !contentTypes.isEmpty()) {
            String contentType = contentTypes.get(0);
//            String sanitizedContentType = contentType.replaceFirst("; charset=UTF-8", "");
//            System.out.println("sanitizedContentType = " + sanitizedContentType);
            headers.add(HttpHeaders.CONTENT_TYPE, contentType + "; charset=UTF-8");
        }
        System.out.println("CONTENT_TYPE after = " + requestContext.getHeaderString(HttpHeaders.CONTENT_TYPE));
    }

}
