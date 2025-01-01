package salesTaxPackage;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CalculateSalesTax {
    
    
    private static final double IMPORTED_TAX_RATE = 0.15; 
    private static final double NORMAL_TAX_RATE = 0.10; 
    private static final double EXEMPT_TAX_RATE = 0.0; 
    
    
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

   
    private static void processProduct(String input) {
        String regex = "(\\d+)\\s+(.*)\\sat\\s(\\d+\\.\\d{2})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            int quantity = Integer.parseInt(matcher.group(1));  
            String description = matcher.group(2).trim();  
            double price = Double.parseDouble(matcher.group(3));  

           
            double tax = 0;
            if (description.toLowerCase().contains("imported")) {
                tax = calculateImportedTax(quantity, price);
            } else if (description.toLowerCase().matches(".*(book|pill|chocolate).*")) {
                tax = calculateExemptTax(quantity, price); 
            } else {
                tax = calculateNormalTax(quantity, price); 
            }

            double totalPrice = (quantity * price) + tax;
            totalSalesTax += tax;
            totalAmount += totalPrice;

           
            System.out.println(quantity + " " + description + ": " + String.format("%.2f", totalPrice));
        } else {
            System.out.println("Invalid input format for: " + input);
        }
    }

    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

     
        System.out.println("Enter the products for calculating sales tax (press Enter for an empty line to finish):");

        while (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                break; 
            }
            processProduct(input); 
        }

        
        System.out.println("Sales Taxes: " + String.format("%.2f", totalSalesTax));
        System.out.println("Total: " + String.format("%.2f", totalAmount));

        scanner.close();
    }
}



