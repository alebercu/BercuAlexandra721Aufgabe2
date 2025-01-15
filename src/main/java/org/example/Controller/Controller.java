package org.example.Controller;

import org.example.Models.Characters;
import org.example.Models.Product;
import org.example.Repository.IRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Controller {
    public final IRepository<Characters> charrepository;
    public final IRepository<Product> prodrepository;
    
    public Controller(IRepository<Characters> charrepository, IRepository<Product> prodrepository) {
        this.charrepository = charrepository;
        this.prodrepository = prodrepository;
    }

    public void addCharacters(Characters Characters) {
        if (Characters.getName().isEmpty()){
            throw new IllegalArgumentException("Characters name cannot be empty");
        }

        if (Characters.getPlace().isEmpty()){
            throw new IllegalArgumentException("Characters place cannot be empty");
        }

        charrepository.create(Characters);

    }

    public List<Characters> getCharacterss() {
        return charrepository.getAll();
    }

    public void listAllCharacterss() {
        List<Characters> Characterss = getCharacterss();

        if(Characterss.isEmpty()){
            System.out.println("No Characterss found");
        }
        else{
            System.out.println("Characterss found");
            for(Characters Characters : Characterss){
                System.out.println(Characters);
            }
        }
    }


    public void deleteCharacters(Characters Characters) {
        if (Characters == null){
            throw new IllegalArgumentException("Characters cannot be null");
        }

        try {
            Characters exists = charrepository.read(Characters.getId());
            if (exists != null){
                charrepository.delete(Characters.getId());
            }
        }catch (Exception ex){}
    }

    public void updateCharacters(Characters Characters) {
        Characters exists = charrepository.read(Characters.getId());
        if (exists == null){
            throw new IllegalArgumentException("Characters cannot be null");
        }
        charrepository.update(Characters.getId(), Characters);
    }

    public Characters getCharacters(int id) {
        return charrepository.read(id);
    }


    public void addProduct(Product product) {
        if (product.getName().isEmpty()){
            throw new IllegalArgumentException("Product name cannot be empty");
        }
        if (product.getPrice() < 0){
            throw new IllegalArgumentException("Product price cannot be negative");
        }
        prodrepository.create(product);
    }

    public List<Product> getProducts() {
        return prodrepository.getAll();
    }

    public void listAllProducts() {
        List<Product> pList = getProducts();

        if(pList.isEmpty()) {
            System.out.println("No product found");
        }
        else {
            System.out.println("Product list:");
            for(Product food : pList) {
                System.out.println(food);
            }
        }
    }



    public void deleteProduct(Product product) {
        if (product == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
        try {
            Product exists = prodrepository.read(product.getId());
            if (exists != null){
                prodrepository.delete(product.getId());
            }
        }catch (Exception ex){}

    }

    public void updateProduct(Product product) {
        Product exists = prodrepository.read(product.getId());
        if (exists == null){
            throw new IllegalArgumentException("Product cannot be null");
        }
        prodrepository.update(product.getId(), product);
    }

    public Product getProduct(int id) {
        return prodrepository.read(id);
    }

    public List<Characters> filterCharByPlace(String place) {
        List<Characters> characters = new ArrayList<>(getCharacterss());

        Iterator<Characters> iterator = characters.iterator();
        while(iterator.hasNext()){
            Characters characters1 = iterator.next();
            if(!characters1.getPlace().equals(place)){
                iterator.remove();
            }
        }
        if(characters.isEmpty()){
            System.out.println("No characters found");
        }
        return characters;
    }


    public List<Characters> sortCharactersByProductRegion(String region){
        return charrepository.getAll()
                .stream()
                .filter(c -> c.getProducts()
                        .stream()
                        .anyMatch(p -> p.getRegion().equals(region)))
                .collect(Collectors.toList());
    }

    public List<Product> getSortedProductsForCharacter(int characterId, boolean ascending) {
        Characters character = charrepository.read(characterId);

        if (character == null){
            throw new IllegalArgumentException("Character cannot be null");
        }

        return character.getProducts()
                .stream()
                .sorted(ascending ? Comparator.comparing(Product::getPrice) : Comparator.comparing(Product::getPrice).reversed())
                .collect(Collectors.toList());

    }





}
