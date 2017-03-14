package com.startup.registrationcrash.resource;

import com.startup.registrationcrash.resource.response.CustomResponse;
import com.startup.registrationcrash.resource.response.ResponseHeader;
import com.startup.registrationcrash.resource.response.RuleResponseHeader;
import com.startup.registrationcrash.resource.response.NewRuleResponseRow;
import com.startup.registrationcrash.dao.CircumstancesAccidentDAO;
import com.startup.registrationcrash.dao.RuleDAO;
import com.startup.registrationcrash.model.CircumstancesAccident;
import com.startup.registrationcrash.model.Rule;
import com.startup.registrationcrash.utils.JsonUtils;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Сергей
 */
@Path("rules")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class RuleResource {

    /* Response codes:
     1** - Informational
     2** - Success
     3** - Redirection 
     4** - Client error
     5** - Server error
     */
//--------------------    idempotent
//    GET - read 
//    DELETE - delete
//    PUT - update 
//--------------------   NON-idempotent
//    POST - create
//    
//     private final static String[] RESPONSE_HEADER = {"id:�����", "result:���������", "description:��������", "actions:��������"};
//    private final static String RESPONSE_HEADER = "{\"id\": \"�����\", \"result\": \"���������\", \"description\": \"��������\", \"actions\": \"��������\"}";
    //todo: replace to DB
    private final static ResponseHeader NEW_RULE_RESPONSE_HEADER = new NewRuleResponseRow("Ответы первого водителя", "Действия", "Ответы второго водителя");
    private final static String DRIVER_ANSWER_ACTION = "t-checkbox";
    private final static ResponseHeader RULE_RESPONSE_HEADER = new RuleResponseHeader("Номер правила", "Виноват водитель №", "Описание", "Действия");
    private final static String RULE_ACTIONS = "t-button:ev-click-edit:v-edit|t-button:ev-click-delete:v-delete";

    @EJB
    private RuleDAO ruleDAO;
    @EJB
    private CircumstancesAccidentDAO circumstancesAccidentDAO;

    @GET
    @Path("/test")
    public Rule[] getCommon() {
        return new Rule[]{new Rule(), new Rule()};
    }

    @GET
    @Path("/contentForNewRule")
    public Response getContentForNewRule(@Context UriInfo uriInfo) {
        try {

            CustomResponse customResponse = new CustomResponse();
            customResponse.setHeader(NEW_RULE_RESPONSE_HEADER);
//            List<NewRuleResponseRow> newRuleResponseRows = getNewRuleResponseRows();
            customResponse.setRows(getNewRuleResponseRows());
            return Response.status(Response.Status.ACCEPTED).location(uriInfo.getAbsolutePathBuilder().build())
                    .entity(JsonUtils.getObjectInJson(customResponse)).build();
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
    }

    @POST
    public Response createRule(@NotNull Rule rule, @Context UriInfo uriInfo) {
        try {
            CircumstancesAccident firstCircumstancesAccident = circumstancesAccidentDAO.persist(rule.getFirstDriverAnswer());
            CircumstancesAccident secondCircumstancesAccident = circumstancesAccidentDAO.persist(rule.getSecondDriverAnswer());
            rule.setFirstDriverAnswer(firstCircumstancesAccident);
            rule.setSecondDriverAnswer(secondCircumstancesAccident);
            rule.setActions(RULE_ACTIONS);
            System.out.println("rule.getDescription = " + new String(rule.getDescription().getBytes(), "UTF-8"));
            System.out.println("rule.getDescription2 = " + rule.getDescription());
            Rule newRule = ruleDAO.persist(rule);

            CustomResponse customResponse = new CustomResponse();
            customResponse.setHeader(RULE_RESPONSE_HEADER);
            customResponse.setRows(Arrays.asList(newRule));

            String id = String.valueOf(newRule.getId());
            URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();

            return Response.created(uri).entity(JsonUtils.getObjectInJson(customResponse)).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
    }

    @GET
    public Response getRules(@Context UriInfo uriInfo,
            @QueryParam("startIndex") int startIndex,
            @QueryParam("size") int size) {
        try {
            CustomResponse customResponse = null;
            List rules;
            if (startIndex >= 0 && size > 0) {
                rules = ruleDAO.findAllPaginated(startIndex, size);
            } else {
                rules = ruleDAO.findAll();
            }
            customResponse = new CustomResponse();
            customResponse.setHeader(RULE_RESPONSE_HEADER);
            customResponse.setRows(rules);
            return Response.status(Response.Status.ACCEPTED).location(uriInfo.getAbsolutePathBuilder().build())
                    .entity(JsonUtils.getObjectInJson(customResponse)).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
    }

    @GET
    @Path("/{ruleId}")
    public Response getRule(@Context UriInfo uriInfo, @PathParam("ruleId") long ruleId) {
        try {
            Rule findedRule = findCircumstancesAccident(uriInfo, ruleId);
            CustomResponse customResponse = new CustomResponse();
            customResponse.setHeader(RULE_RESPONSE_HEADER);
            ResponseBuilder responseBuilder;
            if (findedRule == null) {
                responseBuilder = Response.status(Status.NOT_FOUND);
                customResponse.setRows(null);
            } else {
                responseBuilder = Response.status(Status.ACCEPTED);
                customResponse.setRows(Arrays.asList(findedRule));
            }
            return responseBuilder.location(uriInfo.getAbsolutePathBuilder().build())
                    .entity(JsonUtils.getObjectInJson(customResponse)).build();
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
    }

    @PUT
    @Path("/{ruleId}")
    public Response updateRule(@Context UriInfo uriInfo,
            @PathParam("ruleId") long ruleId, @NotNull Rule rule) {
        Rule findedRule = findCircumstancesAccident(uriInfo, ruleId);
        if (findedRule == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .location(uriInfo.getAbsolutePathBuilder().build())
                    .entity("Can't find rule with id =  " + ruleId).build();
        }
        rule.setId(ruleId);
        rule.setActions(RULE_ACTIONS);
        Rule newRule = null;
        try {
            circumstancesAccidentDAO.merge(rule.getFirstDriverAnswer());
            circumstancesAccidentDAO.merge(rule.getSecondDriverAnswer());

            newRule = ruleDAO.merge(rule);
            if (newRule == null) {
                throw new WebApplicationException(Response.status(Status.NOT_FOUND)
                        .location(uriInfo.getAbsolutePathBuilder().build()).build());
            }
            CustomResponse customResponse = new CustomResponse();
            customResponse.setHeader(RULE_RESPONSE_HEADER);
            customResponse.setRows(Arrays.asList(newRule));

            return Response.status(Status.OK)
                    .location(uriInfo.getAbsolutePathBuilder().build())
                    .entity(JsonUtils.getObjectInJson(customResponse)).build();
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }

    }

    @DELETE
    @Path("/{ruleId}")
    public Response deleteRule(@Context UriInfo uriInfo,
            @PathParam("ruleId") long ruleId) {
        Rule findedRule = findCircumstancesAccident(uriInfo, ruleId);
        if (findedRule == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .location(uriInfo.getAbsolutePathBuilder().build())
                    .entity("Can't find rule with id =  " + ruleId).build();
        }
        try {
            ruleDAO.removeById(ruleId);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
        return Response.status(Status.OK)
                .location(uriInfo.getAbsolutePathBuilder().build()).build();
    }

    private Rule findCircumstancesAccident(UriInfo uriInfo, long ruleId) {
        Rule rule = null;
        try {
            rule = ruleDAO.findById(ruleId, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
        return rule;
    }

    private List<NewRuleResponseRow> getNewRuleResponseRows() {
        List<NewRuleResponseRow> newRuleResponseRows = new ArrayList();
        newRuleResponseRows.add(new NewRuleResponseRow(DRIVER_ANSWER_ACTION, "driverAbsent:Двигался вперёд", DRIVER_ANSWER_ACTION));
        newRuleResponseRows.add(new NewRuleResponseRow(DRIVER_ANSWER_ACTION, "vehicleStood:Двигался назад", DRIVER_ANSWER_ACTION));
        newRuleResponseRows.add(new NewRuleResponseRow(DRIVER_ANSWER_ACTION, "driverDroveAtParking:Двигался направо", DRIVER_ANSWER_ACTION));
        newRuleResponseRows.add(new NewRuleResponseRow(DRIVER_ANSWER_ACTION, "driverDroveStraight:Двигался налево", DRIVER_ANSWER_ACTION));

        return newRuleResponseRows;
    }

}
