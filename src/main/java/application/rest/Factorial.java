package application.rest;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Dependent
@Path("/{p}")
public class Factorial {

    @Inject
    private CalcClient calc;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response factorial( @PathParam("p") long p ) {
        try {
            long factorial = 1L;
            for ( long i = 2L; i <= p; i++ ) {
                factorial = calc.multiply( factorial, i );
            }
            JsonObjectBuilder ob = Json.createObjectBuilder();
            ob.add( "number",    p );
            ob.add( "factorial", factorial );
            JsonObject obj = ob.build();
            return Response.ok( obj ).build();
        } catch ( Exception e ) {
            JsonObjectBuilder ob = Json.createObjectBuilder();
            ob.add("exception", e.toString() );
            JsonObject obj = ob.build();
            return Response.status(500).entity(obj).build();
        }
    }
}