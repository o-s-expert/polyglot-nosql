# JAX-RS - Lab 1

In this lab, we will explore the JAX-RS capability of creating a fish API.

## 1. Defining the model object

### :material-play-box-multiple-outline: Steps

1. Open the `02-jax-rs` project and navigate to the `src/main/java`
2. Create a `record` called `Fish` in the `expert.os.labs.persistence` package
3. Add the following parameters as `String`:
    - `id`
    - `name`
    - `color`

### :material-checkbox-multiple-outline: Expected results

* Record `Fish` representing a simple Java record to model a fish created

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    public record Fish(String id, String name, String color) {
    }
    ```

## 2. Defining the database class

### :material-play-box-multiple-outline: Steps

1. Create a `class` called `FishDatabase` in the `expert.os.labs.persistence` package
2. Annotate the class with the `@ApplicationScoped` from the `jakarta.enterprise.context` package
3. Add a field to represent a collection of fishes

    ```java
    private Map<String, Fish> fishes;
    ```

4. Add a constructor to initialize the collection of fishes

    ```java
    public FishDatabase() {
        this.fishes = new HashMap<>();
    }
    ```

5. Add the following methods with its specific return and body:

    | method | method retun | body |
    |--|--|--|
    | `findAll() ` | `List<Fish>` | `return List.copyOf(this.fishes.values());` |
    | `findById(String id)` | `Optional<Fish>` |  `return Optional.ofNullable(this.fishes.get(id));` |
    | `save(Fish fish)` | `void` | `this.fishes.put(fish.id(), fish);` |
    | `delete(String id)` | `void` | `this.fishes.remove(id);` |
    | `clear()` | `void` | `this.fishes.clear();` |

### :material-checkbox-multiple-outline: Expected results

* Database class with methods to support CRUD operations

### :material-check-outline: Solution

??? example "Click to see..."

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

        public List<Fish> findAll() {
            return List.copyOf(this.fishes.values());
        }

        public Optional<Fish> findById(String id) {
            return Optional.ofNullable(this.fishes.get(id));
        }

        public void save(Fish fish) {
            this.fishes.put(fish.id(), fish);
        }

        public void delete(String id) {
            this.fishes.remove(id);
        }

        public void clear() {
            this.fishes.clear();
        }
    }
    ```

## 3. Defining the resource class

### :material-play-box-multiple-outline: Steps

1. Create a `class` called `FishResource` in the `expert.os.labs.persistence` package
2. Annotate the class with the following

    | Annotations | Package |
    |--| -- |
    | `@Path("/fishes")` | `jakarta.ws.rs`
    | `@Consumes(MediaType.APPLICATION_JSON)` | `jakarta.ws.rs` and `jakarta.ws.rs.core` |
    | `@Produces(MediaType.APPLICATION_JSON)` | `jakarta.ws.rs` and `jakarta.ws.rs.core` |

3. Inject the `FishDatabase` class by creating a field called `database`

    ```java
    @Inject
    FishDatabase database;
    ```

4. Implement the `findAll()` method:

     - use the `@GET` annotation
     - the method returnss a `List<Fish>`
     - the body should call the database-related method

         ```java
         @GET
         public List<Fish> findAll() {
            return this.database.findAll();
         }
         ```

5. Implement the `findById()` method:

    - use the `@GET` annotation together with the `@Path` to the `/{id}`
    - the method returns the `Fish` class
    - the method has the `String id` parameter annotated with `@PathParam("id")`
    - the body should call the database-related method, if it's not found throw a `WebApplicationException` exception with HTTP 404

        ```java
        @GET
        @Path("/{id}")
        public Fish findById(@PathParam("id") String id) {
            return this.database.findById(id)
            .orElseThrow(() -> new WebApplicationException("Fish not found", Response.Status.NOT_FOUND));
        }
        ```

6. Implement the `save()` method

    - use the `@POST` annotation
    - the method is a `void`
    - the method has the `Fish fish` parameter
    - the body should call the database-related method

        ```java
        @POST
        public void insert(Fish fish) {
            this.database.save(fish);
        }
        ```

        !!! note

            We know that an HTTP `POST` method should return the status code 201 and the location header for the resource created.
            In this exercise, it does not have a return to ease the persistence understanding.

7. Implement the `deleteById()` method

    - use the `@DELETE` annotation together with the `@Path` to the `/{id}`
    - the method returns `void`
    - the method has the `String id` parameter annotated with `@PathParam("id")`
    - the body should call the database-related method

        ```java
        @DELETE
        @Path("/{id}")
        public void deleteById(@PathParam("id") String id) {
            this.database.delete(id);
        }
        ```

### :material-checkbox-multiple-outline: Expected results

* A simple RESTful web service for managing fish records

### :material-check-outline: Solution

??? example "Click to see..."

    ```java
    import jakarta.inject.Inject;
    import jakarta.ws.rs.*;
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
        public void save(Fish fish) {
            this.database.save(fish);
        }

        @DELETE
        @Path("/{id}")
        public void deleteById(@PathParam("id") String id) {
            this.database.delete(id);
        }
    }
    ```

## 4. Testing the resources

### :material-play-box-multiple-outline: Steps

1. Open your Terminal
2. Navigate to the `02-jax-rs` in the root project folder
3. Execute the following command to start the application

    ```bash
    ./mvnw quarkus:dev
    ```

4. Open a new Terminal window
5. Run the following `curl` commands, looking at the related expected results:

    | Command | Expected result |
    |--|--|
    | `curl -X POST -H "Content-Type: application/json" -d '{"id":"1","name":"Nemo","color":"Orange"}' http://localhost:8080/fishes` | nothing in the terminal |
    | `curl -X GET http://localhost:8080/fishes` | `[{"color":"Orange","id":"1","name":"Nemo"}]` |
    | `curl -X GET http://localhost:8080/fishes/1` | `[{"color":"Orange","id":"1","name":"Nemo"}]` |
    | `curl -X DELETE http://localhost:8080/fishes/1` | nothing in the terminal |

### :material-checkbox-multiple-outline: Expected results

* A simple RESTful web service for managing fish records where
    - the `FishDatabase` class manages the data
    - the `FishResource` class exposes REST endpoints to interact with the data
