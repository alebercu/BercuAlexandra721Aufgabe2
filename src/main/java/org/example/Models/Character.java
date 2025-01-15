package org.example.Models;

import java.util.List;

public class Character {
    int id;
    String name;
    String place;
    List<Product> products;

    public Character(int id, String name, String place, List<Product> products) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Character{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", place='" + place + '\'' +
                ", products=" + products +
                '}';
    }
}
