package edu.iu.habahram.coffeeorder.repository;

import edu.iu.habahram.coffeeorder.model.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {
    public Receipt add(OrderData order) throws Exception {
        Beverage beverage = null;
        switch (order.beverage().toLowerCase()) {
            case "dark roast":
                beverage = new DarkRoast();
                break;
        }
        if (beverage == null) {
            throw new Exception("Beverage type '%s' is not valid!".formatted(order.beverage()));
        }
        for (String condiment : order.condiments()) {
            switch (condiment.toLowerCase()) {
                case "milk":
                    beverage = new Milk(beverage);
                    break;
                case "mocha":
                    beverage = new Mocha(beverage);
                    break;
                default:
                    throw new Exception("Condiment type '%s' is not valid".formatted(condiment));
            }
        }
        // Generating  a unique ID for the receipt
        String receiptId = UUID.randomUUID().toString();
        Receipt receipt = new Receipt(receiptId, beverage.getDescription(), beverage.cost());

        // Writing order details to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter("db.txt", true))) {
            writer.println(receiptId + "," + receipt.cost() + "," + receipt.description());

        } catch (Exception e) {
            // Handling exceptions
            throw new Exception("Failed to write order to the database.");
        }

        return receipt;
    }
}
