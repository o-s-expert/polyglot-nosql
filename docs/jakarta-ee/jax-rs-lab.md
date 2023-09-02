# Jakarta RS - Lab 1

## 1. JAX-RS

In this lab, we will explore the JAX-RS capability of creating a fish API.

material-play-box-multiple-outline: Steps

**Step 1:** Create a `Fish` Record
```java
public record Fish(String id, String name, String color) {
}
```
- The `Fish` record is a simple Java record class representing fish.
- It has three properties: `id`, `name`, and `color`.

**Step 2:** Create a `FishDatabase` Class
```java
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
```
- The `FishDatabase` class is an `ApplicationScoped` CDI bean.
- It maintains a collection of `Fish` objects using a `Map` where the key is the `id` of the fish.
- It provides methods for saving, deleting, finding by ID, clearing, and fetching all fish records.

**Step 3:** Create a `FishResource` RESTful Web Service
```java
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
- The `FishResource` class is a JAX-RS (Java API for RESTful Web Services) resource class.
- It defines a RESTful web service for managing fish records.
- It uses CDI to inject an instance of `FishDatabase`.
- The `findAll` method returns a list of all fish records.
- The `insert` method saves a new fish record.
- The `findById` method retrieves a fish by its `id`.
- The `deleteById` method deletes a fish by its `id`.
- It handles exceptions and returns appropriate HTTP responses.

In summary, this code demonstrates a simple RESTful web service for managing fish records. The `FishDatabase` class manages the data, and the `FishResource` class exposes REST endpoints to interact with the data.

**Step 4: Test the API**

1. **GET all fishes:**

```bash
curl -X GET http://localhost:8080/fishes
```

2. **POST a new fish:**

```bash
curl -X POST -H "Content-Type: application/json" -d '{"id":"1","name":"Nemo","color":"Orange"}' http://localhost:8080/fishes
```

3. **GET a specific fish by ID:**

```bash
curl -X GET http://localhost:8080/fishes/1
```

4. **DELETE a fish by ID:**

```bash
curl -X DELETE http://localhost:8080/fishes/1
```


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