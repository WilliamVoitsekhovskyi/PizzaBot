package com.example.springboot.OrderReciever;

import com.example.springboot.Domain.MenuItem;
import com.example.springboot.Repo.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;

@SpringBootApplication
public class OrderReceiver {

    @Autowired
    private MenuItemRepository menuItemRepository;

    private final float DISCOUNT_PERCENT = 5;
    private final float DELIVERY_PRICE = 30;
    
    private float totalPrice = 0;
    private float discount = 0;

    private boolean isDiscountAlreadyEnabled = false;
    
    private String incorrectMessage;
    
    private final StringBuilder responseToCustomer = new StringBuilder();

    Map<String, Integer> orderedItemsByAmountMap = new LinkedHashMap<>(); //String - item name
    ArrayList<MenuItem> orderedPizza = new ArrayList<>();
    ArrayList<MenuItem> freePizza = new ArrayList<>();
    

    private void recordOrder(String order) {
        int amountOfThisItemInMap;
            if(isOrderCorrect(order)) {
                for (String item : order.split(" ")) {
                    MenuItem menuItem = menuItemRepository.findByName(item);
                    totalPrice += menuItem.getPrice();

                    if (menuItem.getName().contains("pizza"))
                        orderedPizza.add(menuItem);

                    if (orderedItemsByAmountMap.containsKey(item)) {
                        amountOfThisItemInMap = orderedItemsByAmountMap.get(item);
                        orderedItemsByAmountMap.put(item, amountOfThisItemInMap + 1);
                    }
                    else
                        orderedItemsByAmountMap.put(item, 1); //if the item wasn't in map before we set amount 1 of this item by default
                }
            }
    }

    private boolean isOrderCorrect(String order){
        boolean isCorrect = true;
        for (String item : order.split(" ")) {
            if (!menuItemRepository.existsByName(item)) {
                isCorrect = false;
                incorrectMessage = "Please check spelling and repeat: " + item;
                break;
            }
        }
        return isCorrect;
    }

    private String specialOfferProposal() {
            if (isFreePizza(orderedPizza.size()) == 0) {
                return "We have a special offer \"Each third pizza is free\". You may order one more pizza free." +
                        "\n Print \"/no\" for discard";
            }
            else
                return null;
    }

    private String deliveryService(){
            if (isFreeDeliveryProposal()) {
                return "Your order cost " + totalPrice + " .Delivery will cost additional 30.00.\n" +
                        "You can order something to have free delivery (start after 300.00)\n" +
                        "\n Print \"/no\" for discard";
            }
            return null;
    }

    private boolean isFreeDeliveryProposal() {
        return totalPrice > 270 && totalPrice < 300;
    }

    private boolean isFreeDelivery(){
        return totalPrice >= 300;
    }

    private String drinkPromotion() {
            if (!isOrderContainsDrinks())
                return "Maybe you want to order some drinks? " +
                        "\n Print \"/no\" for discard";
            else
                return null;
    }

    private boolean isOrderContainsDrinks() {
        for (Map.Entry<String, Integer> entry : orderedItemsByAmountMap.entrySet()) {
            if(menuItemRepository.findByName(entry.getKey()).getType().equals("drink"))
                return true;
        }
        return false;
    }

    private void checkIsOrderContainsFreePizza(){
        for (int i = 1; i <= orderedPizza.size(); i++) {
            if (isFreePizza(i) == 1) {
                orderedPizza.sort(getCompByName());
                freePizza.add(orderedPizza.get(i - 1));
                totalPrice -= orderedPizza.get(i - 1).getPrice();
            }
        }
    }

    private int isFreePizza(int amount) {
        if (amount % 3 == 0)
            return 1; // already free pizza
        else if((amount+1) % 3 == 0)
            return 0; // can order one more pizza for free
        else
            return -1;
    }

    public static Comparator<MenuItem> getCompByName() {
        return (o1, o2) -> Float.compare(o2.getPrice(), o1.getPrice());
    }

    public String getResponse(String order) {
        if(order.matches("\\d{6}") && !isDiscountAlreadyEnabled) {
            isDiscountAlreadyEnabled = true;
            return "Discount added!";
        }

        if (isOrderCorrect(order)) {
            recordOrder(order);

            if (deliveryService() != null) {
                return deliveryService();
            }
            else if(specialOfferProposal() != null){
                return specialOfferProposal();
            }
            else if(drinkPromotion() != null){
                return drinkPromotion();
            }
            else {
                return getOrder();
            }

        } else if(order.contains("/no"))
            return getOrder();

            return incorrectMessage;
    }

    private void addDiscount(){
        discount = totalPrice * DISCOUNT_PERCENT / 100;
    }

    private String getOrder(){
        float deliveryPrice;

        checkIsOrderContainsFreePizza();

        responseToCustomer.setLength(0);
        responseToCustomer.append("Your order is: ");

        for (Map.Entry<String, Integer> entry : orderedItemsByAmountMap.entrySet())
            responseToCustomer.append(entry.getValue()).append(" ").append(entry.getKey()).append(" ");

        if (isFreeDelivery())
            deliveryPrice = 0;
        else
            deliveryPrice = DELIVERY_PRICE;

        if(isDiscountAlreadyEnabled)
            addDiscount();

        totalPrice -= discount;
        totalPrice += deliveryPrice;

        responseToCustomer.append("\nDiscount: ").append(String.format("%.2f", discount))
                .append("\nDelivery: ").append(deliveryPrice)
                .append("\nTotal price is: ").append(String.format("%.2f", totalPrice));

        if (freePizza.size() > 0)
            responseToCustomer.append("\nYou got for free: ").append(freePizza);

        return responseToCustomer.toString();
    }
}
