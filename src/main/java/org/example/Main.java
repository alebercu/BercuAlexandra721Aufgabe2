package org.example;

import org.example.Controller.Controller;
import org.example.Models.Characters;
import org.example.Models.Product;
import org.example.Repository.IRepository;
import org.example.Repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {


        IRepository<Characters> charRepo = new Repository<>();
        IRepository<Product> productRepo = new Repository<>();

        // Initialize the Controller
        Controller controller = new Controller(charRepo, productRepo);

        Product product1 = new Product(1,"sword",200,"Maramures");
        Product product2 = new Product(2,"helmet",200,"Satu Mare");
        Product product3 = new Product(3,"Armour",400,"Buc");



        Characters character1 = new Characters(1,"Dragon Mom","Baia Mare",new ArrayList<>(List.of(product1, product2)));
        Characters character2 = new Characters(2,"Ale","Baia Mare",new ArrayList<>(List.of(product1)));
        Characters character3 = new Characters(3,"Andra","Buc",new ArrayList<>(List.of(product1, product2, product3)));

        controller.addProduct(product1);
        controller.addProduct(product2);
        controller.addProduct(product3);

        controller.addCharacters(character1);
        controller.addCharacters(character2);
        controller.addCharacters(character3);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Display menu
            System.out.println("\nMenu:");
            System.out.println("1. List all characters");
            System.out.println("2. Add a character");
            System.out.println("3. Update a character");
            System.out.println("4. Delete a character");
            System.out.println("5. List all products");
            System.out.println("6. Add a product");
            System.out.println("7. Update a product");
            System.out.println("8. Delete a product");
            System.out.println("9. Filter Movies by genre");
            System.out.println("10. Show clients who have movies from a specific director");
            System.out.println("11. View movies ordered for a client");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    controller.listAllCharacterss();
                    break;
                case 2:
                    System.out.print("Enter character name: ");
                    String cName = scanner.nextLine();

                    System.out.print("Enter character place: ");
                    String cPlace = scanner.nextLine();

                    System.out.println("Enter the product IDs to assign to this character (comma-separated): ");
                    String prodIdsInput = scanner.nextLine();
                    List<Product> assignedproducts = new ArrayList<>();

                    for (String movieIdStr : prodIdsInput.split(",")) {
                        try {
                            int pId = Integer.parseInt(movieIdStr.trim());
                            Product product= controller.getProduct(pId);
                            if (product != null) {
                                assignedproducts.add(product);
                            } else {
                                System.out.println("Product with ID " + pId + " not found.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println(movieIdStr + " is not a valid product ID.");
                        }
                    }

                    Characters newC = new Characters(0, cName, cPlace, assignedproducts);
                    controller.addCharacters(newC);
                    System.out.println("Character added.");
                    break;
                case 3:
                    System.out.print("Enter character ID to update: ");
                    int updateCId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Characters cToUpdate = controller.getCharacters(updateCId);

                    if (cToUpdate != null) {
                        System.out.print("Enter new name (leave blank to keep current name): ");
                        String newName = scanner.nextLine();
                        if (!newName.isBlank()) {
                            cToUpdate.setName(newName);
                        }

                        System.out.println("Current products assigned to the client: ");
                        cToUpdate.getProducts().forEach(System.out::println);

                        System.out.println("Enter the product IDs to update for this character (comma-separated): ");
                        String pIdsInputt = scanner.nextLine();
                        List<Product> updatedproducts = new ArrayList<>();

                        for (String movieIdStr : pIdsInputt.split(",")) {
                            try {
                                int movieId = Integer.parseInt(movieIdStr.trim());
                                Product product = controller.getProduct(movieId);
                                if (product != null) {
                                    updatedproducts.add(product);
                                } else {
                                    System.out.println("Product with ID " + movieId + " not found.");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println(movieIdStr + " is not a valid ID.");
                            }
                        }

                        cToUpdate.setProducts(updatedproducts);
                        System.out.println("Character updated.");
                    } else {
                        System.out.println("Character not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter character ID to delete: ");
                    int deleteCId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Characters cToDelete = controller.getCharacters(deleteCId);
                    if (cToDelete != null) {
                        controller.deleteCharacters(cToDelete);
                        System.out.println("Character deleted.");
                    } else {
                        System.out.println("Character not found.");
                    }
                    break;
                case 5:
                    controller.listAllCharacterss();
                    break;
                case 6:
                    System.out.print("Enter product Id: ");
                    int productId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter product name: ");
                    String productName = scanner.nextLine();
                    System.out.print("Enter product price: ");
                    int productPrice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter product region: ");
                    String productSeason = scanner.nextLine();
                    Product newProduct = new Product(productId, productName, productPrice, productSeason);
                    controller.addProduct(newProduct);
                    System.out.println("Product added.");
                    break;

                case 7:
                    System.out.print("Enter product ID to update: ");
                    int updateProductId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Product productToUpdate = controller.getProduct(updateProductId);
                    if (productToUpdate != null) {
                        System.out.print("Enter new name: ");
                        productToUpdate.setName(scanner.nextLine());
                        System.out.print("Enter new price: ");
                        productToUpdate.setPrice(scanner.nextInt());
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new region: ");
                        productToUpdate.setRegion(scanner.nextLine());
                        controller.updateProduct(productToUpdate);
                        System.out.println("Product updated.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 8:
                    System.out.print("Enter product ID to delete: ");
                    int deleteProductId = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    Product productToDelete = controller.getProduct(deleteProductId);
                    if (productToDelete != null) {
                        controller.deleteProduct(productToDelete);
                        System.out.println("Product deleted.");
                    } else {
                        System.out.println("Product not found.");
                    }
                    break;
                case 9:
                    System.out.print("Enter place to filter by: ");
                    String place = scanner.nextLine();
                    controller.filterCharByPlace(place).forEach(System.out::println);
                    break;
                case 10:
                    System.out.print("Enter region to see characters that bought products from that region: ");
                    String region = scanner.nextLine();
                    List<Characters> ch = controller.sortCharactersByProductRegion(region);
                    if (ch.isEmpty()) {
                        System.out.println("No characters found who bought products from " + region);
                    } else {
                        System.out.println("Characters who bought products from " + region);
                        ch.forEach(System.out::println);
                    }
                    break;

                case 11:
                    displaySortedProductsForClient(scanner, controller);
                    break;



                case 12:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void displaySortedProductsForClient(Scanner scanner, Controller controller) {
        System.out.print("Enter the client ID: ");
        int clientId = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline

        System.out.print("Sort mode (asc/desc): ");
        String sortMode = scanner.nextLine().trim().toLowerCase();

        boolean ascending = sortMode.equals("asc");

        try {
            List<Movie> sortedMovies = controller.getSortedMoviesForClient(clientId, ascending);
            if (sortedMovies.isEmpty()) {
                System.out.println("The client has no movies.");
            } else {
                System.out.println("Sorted Movies:");
                sortedMovies.forEach(System.out::println);
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}


