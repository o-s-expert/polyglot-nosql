package expert.os.labs.persistence;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/fishes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FishResource {

    @Inject
    FishDatabase database;

    @GET
    public List<Fish> findAll() {
        return this.database.findAll();
    }

    @GET
    @Path("/{id}")
    public Fish findById(@PathParam("id") String id) {
        return this.database.findById(id)
                .orElseThrow(() -> new WebApplicationException("Fish not found", Response.Status.NOT_FOUND));
    }

    @POST
    public void insert(Fish fish) {
        this.database.save(fish);
    }

    @DELETE
    @Path("/{id}")
    public void deleteById(@PathParam("id") String id) {
        this.database.delete(id);
    }
}
