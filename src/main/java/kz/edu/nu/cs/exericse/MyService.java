package kz.edu.nu.cs.exericse;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

@Path("/items")
public class MyService {
    
    private Map<String, String> entries = new ConcurrentHashMap<String, String>();
    
    @GET
    public Response getItems() {
        Gson gson = new Gson();
        String json = gson.toJson(entries.keySet());
        return Response.ok(json).build();
    }
    @GET
    @Path ("{ key : [a -zA -Z_0-9]+}")
    public Response getByKey (@PathParam ("key") String key) {
    String r = "Requested item with the key \"" + key +  "\" is " + entries.get(key);
    return Response.ok(r).build();
    }
    
    @POST
    public Response createEntry(@FormParam("mykey") String mykey, @FormParam("myentry") String myentry) {
    	
    	if(entries.containsKey(mykey)) {
        	return Response.status(Status.FORBIDDEN).build();
        }
        else if(mykey.contains(" ") || myentry.contains("\n")){
        	return Response.status(Status.BAD_REQUEST).build();
        }
        else if(mykey.equals("")) {
        	return Response.status(Status.NOT_ACCEPTABLE).build();
        }
        else {
	    	entries.put(mykey, myentry);
	    	return Response.ok(entries.keySet()).build();
	        //return Response.status(Status.CREATED).build();
        }
    }
}
