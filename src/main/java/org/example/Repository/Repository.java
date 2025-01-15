package org.example.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
//import org.example.ex2.Models.Client;
//import org.example.ex2.Models.Movie;

public class Repository<T> implements IRepository<T> {
    private final Map<Integer, T> map = new HashMap<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);

    // Create a new object in the repository
    @Override
    public void create(T obj) {
        // Check if the object is an instance of Client or Product and set an ID
        int id = idCounter.getAndIncrement();

        // If the object is of type Client
        if (obj instanceof Client) {
            ((Client) obj).setId(id);
        }
        // If the object is of type Product
        else if (obj instanceof Movie) {
            ((Movie) obj).setId(id);
        }

        map.putIfAbsent(id, obj);
    }

    // Read an object by its ID
    @Override
    public T read(Integer id) {
        return map.get(id);
    }

    // Update an object in the repository
    @Override
    public void update(Integer id, T obj) {
        map.replace(id, obj);
    }

    // Delete an object from the repository by its ID
    @Override
    public void delete(Integer id) {
        map.remove(id);
    }

    // Retrieve all objects from the repository
    @Override
    public List<T> getAll() {
        return new ArrayList<>(map.values());
    }
}
