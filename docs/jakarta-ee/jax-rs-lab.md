# Jakarta RS - Lab 1

## 1. JAX-RS

In this lab, we will explore the JAX-RS capability of creating a fish API.

material-play-box-multiple-outline: Steps

1. Create a `Fish` as a record with id, name, and color as String.
2. Create a `FishDatabase` that will hold the fish information using a Map, where the key is the fish ID and the value will be the fish itself, exploring the CRUD operation.
3. Create a `FishResource` that will return, create, return by id, and delete by id as resources exploring the good practices and the proper HTTP verb exploring the Glory of Rest.


### :material-check-outline: Solution

??? example "Click to see..."

```java
public record Fish(String id, String name, String color) {
}


import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class FishDatabase {

    private Map<String, Fish> fishes;

    public FishDatabase() {
        this.fishes = new HashMap<>();
    }

    public void save(Fish fish) {
        this.fishes.put(fish.id(), fish);
    }

    public void delete(String id) {
        this.fishes.remove(id);
    }

    public Optional<Fish> findById(String id) {
        return Optional.ofNullable(this.fishes.get(id));
    }

    public void clear() {
        this.fishes.clear();
    }
    public List<Fish> findAll() {
        return List.copyOf(this.fishes.values());
    }
}


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

    @POST
    public void insert(Fish fish) {
        this.database.save(fish);
    }

    @GET
    @Path("/{id}")
    public Fish findById(@PathParam("id") String id) {
        return this.database.findById(id)
                .orElseThrow(() -> new WebApplicationException("Fish not found", Response.Status.NOT_FOUND));
    }

    @DELETE
    @Path("/{id}")
    public void deleteById(@PathParam("id") String id) {
        this.database.delete(id);
    }
}

```