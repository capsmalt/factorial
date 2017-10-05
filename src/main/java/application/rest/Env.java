package application.rest;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/env")
public class Env {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        JsonObjectBuilder ob = Json.createObjectBuilder();
        Map<String,String> env = System.getenv();
        for ( String key : env.keySet() ) {
            ob.add( key, env.get( key ) );
        }
        JsonObject obj = ob.build();
        Map<String, Object> conf = new HashMap<>();
        conf.put( JsonGenerator.PRETTY_PRINTING, true );
        StringWriter sw = new StringWriter( 1024 );
        JsonWriter jw = Json.createWriterFactory( conf ).createWriter( sw );
        jw.writeObject( obj );
        String res = sw.toString();
        return Response.ok( res, MediaType.APPLICATION_JSON_TYPE ).build();
    }

}