package com.startup.registrationcrash;

import com.startup.registrationcrash.resource.CircumstancesAccidentResource;
import com.startup.registrationcrash.resource.RuleResource;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Configures a JAX-RS endpoint. Delete this class, if you are not exposing
 * JAX-RS resources in your application.
 *
 * @author airhacks.com
 */
@ApplicationPath("/api/")
public class JAXRSConfiguration extends Application { // implements Feature {

    @Override
    public Set<Class<?>> getClasses() {
        final Set<Class<?>> classes = new HashSet<>();
        // register root resource
        classes.add(RuleResource.class);

        classes.add(JacksonFeature.class);

        return classes;
    }

//    @Override
//    public boolean configure(FeatureContext context) {
//        String postfix = '.' + context.getConfiguration().getRuntimeType().name().toLowerCase();
//        context.property(CommonProperties.MOXY_JSON_FEATURE_DISABLE + postfix, true);
//        return true;
//    }
//    @Override
//    public boolean configure(final FeatureContext context) {
//        context.property(CommonProperties.MOXY_JSON_FEATURE_DISABLE_SERVER, true);
//        return true;
//    }
}
