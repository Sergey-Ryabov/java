package com.startup.registrationcrash.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.startup.registrationcrash.dao.CircumstancesAccidentDAO;
import com.startup.registrationcrash.model.CircumstancesAccident;
import com.startup.registrationcrash.utils.JsonUtils;
import java.net.URI;
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
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author Сергей
 */
@Path("circumstancesAccidents")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
public class CircumstancesAccidentResource {

    @EJB
    private CircumstancesAccidentDAO circumstancesAccidentDAO;

    @GET
    @Path("/test")
    public CircumstancesAccident[] getCommon() {
        return new CircumstancesAccident[]{new CircumstancesAccident(), new CircumstancesAccident()};
    }

    @POST
    public Response createCircumstancesAccident(@NotNull CircumstancesAccident circumstancesAccident, @Context UriInfo uriInfo) {
        CircumstancesAccident newCircumstancesAccident = null;
        try {
            newCircumstancesAccident = circumstancesAccidentDAO.persist(circumstancesAccident);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
        String id = String.valueOf(newCircumstancesAccident.getId());
        URI uri = uriInfo.getAbsolutePathBuilder().path(id).build();
//        Response.status(Status.OK).location(uri).entity(newCompany).build(); - other example
        return Response.created(uri).entity(newCircumstancesAccident).build();
    }

    @GET
    public Response getCircumstancesAccidents(@Context UriInfo uriInfo,
            @QueryParam("startIndex") int startIndex,
            @QueryParam("size") int size) {
//        �������� marshalling ������ �������� (���� ��� ����� ������ �����)
        try {
            if (startIndex >= 0 && size > 0) {

                return Response.status(Response.Status.ACCEPTED).location(uriInfo.getAbsolutePathBuilder().build())
                        .entity(JsonUtils.getObjectInJson(circumstancesAccidentDAO.findAllPaginated(startIndex, size))).build();

            }
            return Response.status(Response.Status.ACCEPTED).location(uriInfo.getAbsolutePathBuilder().build())
                    .entity(JsonUtils.getObjectInJson(circumstancesAccidentDAO.findAll())).build();
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
    }

    @GET
    @Path("/{circumstancesAccidentId}")
    public Response getCircumstancesAccident(@Context UriInfo uriInfo, @PathParam("circumstancesAccidentId") long circumstancesAccidentId) {
        CircumstancesAccident findedCircumstancesAccident = findCircumstancesAccident(uriInfo, circumstancesAccidentId);
        if (findedCircumstancesAccident == null) {
            System.out.println("findedCircumstancesAccident == null");
            return Response.status(Response.Status.NOT_FOUND)
                    .location(uriInfo.getAbsolutePathBuilder().build())
                    .entity("Can't find circumstancesAccident with id =  " + circumstancesAccidentId).build();
        }
        System.out.println("CircumstancesAccident is found");
        return Response.status(Response.Status.ACCEPTED)
                .location(uriInfo.getAbsolutePathBuilder().build())
                .entity(findedCircumstancesAccident).build();
    }

    @PUT
    @Path("/{circumstancesAccidentId}")
    public Response updateCircumstancesAccident(@Context UriInfo uriInfo,
            @PathParam("circumstancesAccidentId") long circumstancesAccidentId, @NotNull CircumstancesAccident circumstancesAccident) {
//        ������������� ����������
        CircumstancesAccident findedCircumstancesAccident = findCircumstancesAccident(uriInfo, circumstancesAccidentId);
        if (findedCircumstancesAccident == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build();
        }
        circumstancesAccident.setId(circumstancesAccidentId);
        CircumstancesAccident newCircumstancesAccident = null;
        try {
            newCircumstancesAccident = circumstancesAccidentDAO.merge(circumstancesAccident);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
        if (newCircumstancesAccident == null) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
//        String companyInJson = JsonMarshaller.marshallCompany(newCompany);
        System.out.println("newCircumstancesAccident: newCircumstancesAccident InJson = " + newCircumstancesAccident);
        return Response.status(Response.Status.OK)
                .location(uriInfo.getAbsolutePathBuilder().build())
                .entity(newCircumstancesAccident).build();
    }

    @DELETE
    @Path("/{circumstancesAccidentId}")
    public Response deleteCircumstancesAccident(@Context UriInfo uriInfo,
            @PathParam("circumstancesAccidentId") long circumstancesAccidentId) {
        CircumstancesAccident findedCircumstancesAccident = findCircumstancesAccident(uriInfo, circumstancesAccidentId);
        if (findedCircumstancesAccident == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build();
        }
        try {
            circumstancesAccidentDAO.removeById(circumstancesAccidentId);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
        return Response.status(Response.Status.OK)
                .location(uriInfo.getAbsolutePathBuilder().build()).build();
    }

    private CircumstancesAccident findCircumstancesAccident(UriInfo uriInfo, long circumstancesAccidentId) {
        CircumstancesAccident circumstancesAccident = null;
        try {
            circumstancesAccident = circumstancesAccidentDAO.findById(circumstancesAccidentId, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .location(uriInfo.getAbsolutePathBuilder().build()).build());
        }
        return circumstancesAccident;
    }

}
