package ind.gopinnath.trenzz;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ind.gopinnath.trenzz.bean.Summaries;

@Path("/api")
public class Resource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/trenzz")
    public Summaries summarize() {
    	Summaries summaries = new Summaries();
    	return summaries;
    }
}