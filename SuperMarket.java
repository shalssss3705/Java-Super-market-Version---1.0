
import java.util.ArrayList;
import java.util.Scanner;

public class SuperMarket {

    Scanner input = new Scanner(System.in);
    DBManager dbManager = new DBManager();
    ArrayList<Product> cart = new ArrayList<>();
    double total = 0;

    public void run() {
        boolean flag = true;
        while (flag) {
            printMenu();
            System.out.print("Enter Option Number > ");
            String choice = input.nextLine();
            switch (choice) {
                case "1":
                    showAllProducts();
                    break;
                case "2":
                    showDiscountedProducts();
                    break;
                case "3":
                    if (cart.isEmpty()) {
                        System.out.println("Cart is empty!");
                    } else {
                        showCart();
                    }
                    break;
                case "4":
                    if (cart.isEmpty()) {
                        System.out.println("Cart is empty!");
                    } else {
                        billing();
                    }
                    break;
                case "5":
                    System.out.println("Thank you for using super market billing system");
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid Choice!");
                    break;

            }
        }
    }

    public void printMenu() {
        System.out.println("***** Super Market *****");
        System.out.println("1. Show All Products");
        System.out.println("2. Show Discounted Products");
        System.out.println("3. Show Cart");
        System.out.println("4. Billing");
        System.out.println("5. Exit");
    }

    public void showAllProducts() {
        ArrayList<Product> products = dbManager.getProducts();
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-15s |%n", "ID", "Name", "Price", "Discount");
        System.out.println("-------------------------------------------------------------------------------------");
        for (Product product : products) {
            System.out.printf("| %-10d | %-30s | %-15.2f | %-15s |%n", product.getProductId(), product.getProductName(), product.getPrice(), String.format("%.0f", product.getDiscountPercentage()) + "%");
        }
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println("1. Add to cart");
        System.out.println("2. Back");
        boolean flag = true;
        while (flag) {
            System.out.print("Enter Choice > ");
            String choice = input.nextLine();
            if (choice.equals("1")) {
                addToCart(products);
                flag = false;
            } else if (choice.equals("2")) {
                flag = false;
            } else {
                System.out.println("Invalid Choice!");
            }
        }
    }

    public void showDiscountedProducts() {
        ArrayList<Product> products = dbManager.getDiscountedProduct();
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-15s | %-20s |%n", "ID", "Name", "Price", "Discount", "Discounted Price");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (Product product : products) {
            System.out.printf("| %-10d | %-30s | %-15.2f | %-15s | %-20.2f |%n", product.getProductId(), product.getProductName(), product.getPrice(), String.format("%.0f", product.getDiscountPercentage()) + "%", product.getDiscountedPrice());
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("1. Add to cart");
        System.out.println("2. Back");
        boolean flag = true;
        while (flag) {
            System.out.print("Enter Choice > ");
            String choice = input.nextLine();
            if (choice.equals("1")) {
                addToCart(products);
                flag = false;
            } else if (choice.equals("2")) {
                flag = false;
            } else {
                System.out.println("Invalid Choice!");
            }
        }
    }

    public void showCart() {
        total = 0;
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-10s | %-30s | %-15s | %-15s | %-20s |%n", "ID", "Name", "Price", "Qunatity", "Total Price");
        System.out.println("----------------------------------------------------------------------------------------------------------");
        for (Product product : cart) {
            System.out.printf("| %-10d | %-30s | %-15.2f | %-15d | %-20.2f |%n", product.getProductId(), product.getProductName(), product.getDiscountedPrice(), product.getQuantity(), product.getTotalPrice());
            total += product.getTotalPrice();
        }
        System.out.println("----------------------------------------------------------------------------------------------------------");
        System.out.println("Total Price: " + total);
        System.out.println("----------------------------------------------------------------------------------------------------------");
    }

    public void addToCart(ArrayList<Product> products) {
        int id;
        int quantity;
        while (true) {
            System.out.print("Enter Product ID > ");
            String pid = input.nextLine();
            if (isDigit(pid)) {
                id = Integer.parseInt(pid);
                Product p = null;
                for (Product product : products) {
                    if (product.getProductId() == id) {
                        p = new Product(product, 0);
                        System.out.print("Enter Quantity: ");
                        String pQuantity = input.nextLine();
                        if (isDigit(pQuantity)) {
                            quantity = Integer.parseInt(pQuantity);
                            p.setQuantity(quantity);
                            boolean found = false;
                            for (Product pp : cart) {
                                if (pp.getProductId() == p.getProductId()) {
                                    pp.setQuantity(quantity + pp.getQuantity());
                                    found = true;
                                }
                            }
                            if (!found) {
                                cart.add(p);
                            }
                            break;
                        } else {
                            System.out.println("Invalid Quantity!");
                        }
                    }
                }
                if (p == null) {
                    System.out.println("Product not found!");
                }
            } else {
                System.out.println("Invalid Product ID!");
            }
            System.out.println("Do you want to add more? anykey for yes n for no:");
            String choice = input.nextLine();
            if (choice.equalsIgnoreCase("n")) {
                break;
            }
        }

    }

    public void billing() {
        showCart();
        String name;
        while (true) {
            System.out.print("Enter name: ");
            name = input.nextLine();
            if(name.length()<5){
                System.out.println("Name should be 5 or more letters");
            }else{
                break;
            }
        }
        String phone;
        while (true) {
            System.out.print("Enter phone: ");
            phone = input.nextLine();
            if (isValidPhone(phone)) {
                break;
            } else {
                System.out.println("Invalid Phone Number!");
            }
        }
        dbManager.addBill(name, phone, total);
        ArrayList<Product> products = dbManager.getProducts();
        for (Product c : cart) {
            for (Product p : products) {
                if (p.getProductId() == c.getProductId()) {
                    dbManager.updateProduct(p.getProductId(), p.getQuantity() - c.getQuantity());
                    break;
                }
            }
        }
        cart.clear();
        total = 0.0;
        
    }

    public boolean isDigit(String str) {
        return str.matches("\\d+");
    }

    public boolean isValidPhone(String phone) {
        return phone.matches("\\+91\\d{10}");
    }

}
