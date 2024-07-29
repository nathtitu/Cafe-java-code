import java.util.Scanner;
import java.time.LocalDate;

public class Cafe {

    String table[][] = { { "0", "Kiran" }, { "1", "Sam" }, { "2", "Jill" }, { "3", "Jill" }, { "4", "Sam" },{ "5", "Jill" }, { "6", "Sam" }, { "7", "Sam" }, { "8", "Jill" } }; // Table number and staff name
    int tableOccupied[][] = { { 0, 0 }, { 1, 0 }, { 2, 0 }, { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 }, { 7, 0 },{ 8, 0 } };// Table number and status
    String order[][] = new String[200][7];
    int orderNumber = 100;

    private boolean order(int tableNumber, int Item, String Name, int Qty, float price, String comment) {
        boolean flag = false;
        // Check if the table is occupied
        for (int i = 0; i < 200; i++) {
            if (order[i][0] == null) {
                if (tableOccupied[tableNumber][1] == 0) {
                    // Place the order
                    orderNumber++;
                    order[i][0] = Integer.toString(orderNumber);
                    order[i][1] = Integer.toString(tableNumber);
                    order[i][2] = Integer.toString(Item);
                    order[i][3] = Name;
                    order[i][4] = Integer.toString(Qty);
                    order[i][5] = Float.toString(price);
                    order[i][6] = comment;
                    tableOccupied[tableNumber][1] = 1;
                    flag = true;
                    break;
                } else if (tableOccupied[tableNumber][1] == 1) {
                    // Add items to existing order
                    String existingOrderNumber = null;
                    for (int j = 0; j < 200; j++) {
                        // Find the existing order number
                        if (order[j][1] != null && order[j][1].equals(Integer.toString(tableNumber))) {
                            existingOrderNumber = order[j][0];
                            break;
                        }
                    }
                    if (existingOrderNumber != null) {
                        // Place the order
                        order[i][0] = existingOrderNumber;
                        order[i][1] = Integer.toString(tableNumber);
                        order[i][2] = Integer.toString(Item);
                        order[i][3] = Name;
                        order[i][4] = Integer.toString(Qty);
                        order[i][5] = Float.toString(price);
                        order[i][6] = comment;
                        flag = true;
                        break;
                    }
                }
            }
           
        }
        return flag;
    }

    private int searchTable(int tableNumber) {
        // Check if the table is occupied
        return tableOccupied[tableNumber][1];
    }

    private boolean searchOrderTable(int tableNumber) {
        boolean flag = false;
        for (int i = 0; i < 200; i++) {
            // Find the order number
            if (order[i][1] != null && order[i][1].equals(Integer.toString(tableNumber))) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    private void displayInvoice(int tableNumber) {
        String staff = null;
        int orderNumber = 0;
        double Total = 0;
        double vat = 0;

        for (int i = 0; i < 9; i++) {
            // Find the staff name
            if (table[i][0].equals(Integer.toString(tableNumber))) {
                staff = table[i][1];
                break;
            }
        }
        for (int i = 0; i < 200; i++) {
            // Find the order number
            if (order[i][1] != null && order[i][1].equals(Integer.toString(tableNumber))) {
                orderNumber = Integer.parseInt(order[i][0]);
                break;
            }
        }

        System.out.println("Order# " + orderNumber + "                                    " + " Table# " + tableNumber);
        System.out.println("Date: " + LocalDate.now() + "                                " + " Staff: " + staff);
        System.out.println();
        System.out.println("Item# \tName \t\t  Qty \tPrice \tTotal");
        for (int i = 0; i < 200; i++) {
            if (order[i][1] != null && order[i][1].equals(Integer.toString(tableNumber))) {
                // Display the order
                System.out.printf("%-6s %-20s %-6s %-7s %.2f%n", order[i][2], order[i][3] + "(" + order[i][6] + ")",
                        order[i][4], order[i][5], Float.parseFloat(order[i][4]) * Float.parseFloat(order[i][5]));
                Total += Float.parseFloat(order[i][4]) * Float.parseFloat(order[i][5]);
                // Delete that row
                order[i][0] = null;
                order[i][1] = null;
                order[i][2] = null;
                order[i][3] = null;
                order[i][4] = null;
                order[i][5] = null;
                order[i][6] = null;
               
            }
        }
        tableOccupied[tableNumber][1] = 0;

        vat = Total * (.2 / (1 + .2));
        double netTotal = Total - vat;

        System.out.println();
        System.out.printf("                                             Total: %.2f%n", Total);
        System.out.printf("                                             VAT (20%%): %.2f%n", vat);
        System.out.printf("                                             Net amount: %.2f%n", netTotal);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("-------------------------------------------");
        System.out.println("Welcome to Cafe Management System");
        System.out.println("-------------------------------------------");
        System.out.println();
        boolean repeat = true;
        Cafe cafe = new Cafe();
        while (repeat) {
            try {
                System.out.println("What do you want to do?");
                System.out.println("\t1. Place Order");
                System.out.println("\t2. Display Invoice");
                System.out.println("\t3. Exit");
                System.out.println();
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("-------------------------------------------");
                        System.out.println("You have chosen Place Order");
                        System.out.println("-------------------------------------------");
                        System.out.println();
                        System.out.println("You have the following options:");
                        System.out.println("\t1. New Order");
                        System.out.println("\t2. Add Items to existing order");
                        System.out.println("\t3. Exit");
                        System.out.println();
                        System.out.print("Enter your option: ");
                        int option1 = sc.nextInt();
                        switch (option1) {
                            case 1:
                                System.out.println("-------------------------------------------");
                                System.out.println("You have chosen New Order");
                                System.out.println("-------------------------------------------");
                                System.out.println();
                                System.out.print("Enter the table number: ");
                                int tableNumber = sc.nextInt();
                                if (tableNumber >= 0 && tableNumber <= 8) {
                                    System.out.print("Enter the Item number: ");
                                    int Item = sc.nextInt();
                                    while (Item < 0 || Item > 96) {
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                   System.out.println("Item number must be positive and less than eqal 96");
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.print("Enter the Item number: ");
                                        Item = sc.nextInt();
                                    }

                                    System.out.print("Enter the item Name: ");
                        sc.nextLine(); // Consume the newline character left after reading integer
                                    String Name = sc.nextLine();
                                    

                                    System.out.print("Enter the Quantity: ");
                                    int Qty = sc.nextInt();
                                    while (Qty < 0) {
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.println("Quantity must be positive");
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.print("Enter the Quantity: ");
                                        Qty = sc.nextInt();
                                    }

                                    System.out.print("Enter the price $: ");
                              
                                    float price = sc.nextFloat();
                                    while (price < 0) {
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.println("Price must be positive");
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.print("Enter the price $: ");
                                        price = sc.nextFloat();
                                    }

                                    System.out.print("Enter the comment: ");
                                sc.nextLine(); // Consume the newline character left after reading float
                                    String comment = sc.nextLine();
                                    if (cafe.order(tableNumber, Item, Name, Qty, price, comment)) {
                                        System.out.println("-------------------------------------------");
                                        System.out.println("Order placed successfully");
                                        System.out.println("-------------------------------------------");
                                        System.out.println();
                                    } else {
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.println("Order not placed");
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.println();
                                    }
                                } else {
                                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                    System.out.println("invalid table number");
                                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                    System.out.println();
                                }
                                break;
                            case 2:
                                System.out.println("-------------------------------------------");
                                System.out.println("You have chosen Add Items to existing order");
                                System.out.println("-------------------------------------------");
                                System.out.println();
                                System.out.print("Enter the table number: ");
                                int tableNum = sc.nextInt();
                                if (cafe.searchTable(tableNum) == 1) {
                                    System.out.print("Enter the Item number: ");
                                    int Item1 = sc.nextInt();
                                
                                    while (Item1 < 0 || Item1 > 96) {
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                System.out.println("Item number must be positive and less than eqal 96");
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.print("Enter the Item number: ");
                                        Item1 = sc.nextInt();
                                    }


                                    System.out.print("Enter the item Name: ");
                                    sc.nextLine(); // Consume the newline character
                                    String Name1 = sc.nextLine();
                                    System.out.print("Enter the Quantity: ");
                                    int Qty1 = sc.nextInt();
                                    while (Qty1 < 0) {
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.println("Quantity must be positive");
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.print("Enter the Quantity: ");
                                        Qty1 = sc.nextInt();
                                    }
                                    System.out.print("Enter the price $: ");
                                    float price1 = sc.nextFloat();
                                    while (price1 < 0) {
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.println("Price must be positive");
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.print("Enter the price $: ");
                                        price1 = sc.nextFloat();
                                    }
                                    System.out.print("Enter the comment: ");
                                    sc.nextLine(); // Consume the newline character
                                    String comment1 = sc.nextLine();
                                    if (cafe.order(tableNum, Item1, Name1, Qty1, price1, comment1)) {
                                        System.out.println("-------------------------------------------");
                                        System.out.println("Order placed successfully");
                                        System.out.println("-------------------------------------------");
                                        System.out.println();
                                    } else {
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.println("Order not placed");
                                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                        System.out.println();
                                    }
                                } else {
                                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                    System.out.println("Table not found");
                                    System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                    System.out.println();
                                }
                                break;
                            case 3:
                                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                System.out.println("You have chosen Exit");
                                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                System.out.println();
                                break;
                            default:
                                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                System.out.println("Invalid option");
                                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                                System.out.println();
                                break;
                        }
                        break;
                    case 2:
                        System.out.println("-------------------------------------------");
                        System.out.println("You have chosen Display Invoice");
                        System.out.println("-------------------------------------------");
                        System.out.println();
                        System.out.print("Enter the table number: ");
                        int tableNum = sc.nextInt();
                        if (cafe.searchOrderTable(tableNum)) {
                            cafe.displayInvoice(tableNum);
                        } else {
                            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                            System.out.println("Table not found");
                            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                            System.out.println();
                        }
                        break;
                    case 3:
                        System.out.println("-------------------------------------------");
                        System.out.println("You have chosen Exit");
                        System.out.println("-------------------------------------------");
                        System.out.println();
                        repeat = false;
                        break;
                    default:
                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                        System.out.println("Invalid option");
                        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                        System.out.println();
                        break;
                }
            } catch (Exception e) {
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");

                System.out.println("Invalid input, please try again.");
                System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
                sc.nextLine(); // Clear the scanner buffer
            }
        }
        sc.close();
        System.err.println();
        System.err.println(":)");
        System.err.println("Thank you for using Cafe Management System");
    }}


