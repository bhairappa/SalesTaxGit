package salesTaxPackage;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculateSalesTax {
    
    // Constants for tax rates
    private static final double IMPORTED_TAX_RATE = 0.15; // 15% tax for imported items
    private static final double NORMAL_TAX_RATE = 0.10; // 10% tax for normal items
    private static final double EXEMPT_TAX_RATE = 0.0; // No tax for exempt items (e.g., Books, Pills, Chocolates)
    
    // Variables to hold the overall total tax and total amount
    private static double totalSalesTax = 0;
    private static double totalAmount = 0;

    // Method to calculate tax for imported items
    private static double calculateImportedTax(int quantity, double price) {
        return (quantity * price) * IMPORTED_TAX_RATE;
    }

    // Method to calculate tax for normal items
    private static double calculateNormalTax(int quantity, double price) {
        return (quantity * price) * NORMAL_TAX_RATE;
    }

    // Method to calculate tax for exempt items (Books, Pills, Chocolates)
    private static double calculateExemptTax(int quantity, double price) {
        return quantity * price * EXEMPT_TAX_RATE;
    }

    // Method to process each product input and calculate the tax
    private static void processProduct(String input) {
        String regex = "(\\d+)\\s+(.*)\\sat\\s(\\d+\\.\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));  // Extract quantity
            String description = matcher.group(2).trim();  // Extract product description
            double price = Double.parseDouble(matcher.group(3));  // Extract product price

            // Determine the applicable tax based on whether the item is imported or exempt
            double tax = 0;
            if (description.toLowerCase().contains("imported")) {
                tax = calculateImportedTax(quantity, price);
            } else if (description.toLowerCase().matches(".*(book|pill|chocolate).*")) {
                tax = calculateExemptTax(quantity, price);  // Exempt items (Book, Pills, Chocolates)
            } else {
                tax = calculateNormalTax(quantity, price);  // Normal items
            }

            double totalPrice = (quantity * price) + tax;
            totalSalesTax += tax;
            totalAmount += totalPrice;

            // Output the result for the current item
            System.out.println(quantity + " " + description + ": " + String.format("%.2f", totalPrice));
        } else {
            System.out.println("Invalid input format for: " + input);
        }
    }

    // Main method to read user input and process each product
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Reading multiple lines of input until the user presses enter on an empty line
        System.out.println("Enter the products for calculating sales tax (press Enter for an empty line to finish):");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break; // Exit when an empty line is encountered
            }
            processProduct(input);  // Process each product
        }

        // Output the total sales tax and total amount
        System.out.println("Sales Taxes: " + String.format("%.2f", totalSalesTax));
        System.out.println("Total: " + String.format("%.2f", totalAmount));

        scanner.close();
    }
}



