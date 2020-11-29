package com.example.springboot;

import com.example.springboot.Domain.MenuItem;
import com.example.springboot.Repo.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class OrderReceiver {

    @Autowired
    private MenuItemRepository menuItemRepository;

    private float totalPrice = 0;
    private float discount = 0;


    private final float DISCOUNT_PERCENT = 5;
    private final float deliveryPrice = 30;

    private final Scanner scanner = new Scanner(System.in);

    public String respond = "";

    Map<String, Integer> orderMap = new LinkedHashMap<>();
    ArrayList<MenuItem> orderedPizza = new ArrayList<>();
    ArrayList<MenuItem> freePizza = new ArrayList<>();


//    public void orderPizza(String order) {
//        recordOrder(order);
//        specialOfferProposal(false);
//        drinkPromotion(false);
//        discountProgram(false);
//        deliveryService(false);
//        respond = printOrder(totalPrice, orderMap);
//
//    }

    private void recordOrder(String order) {
        float orderPrice = 0;

            if(isOrderCorrect(order)) {
                for (String item : order.split(" ")) {
                    MenuItem menuItem = menuItemRepository.findByName(item);
                    orderPrice += menuItem.getPrice();

                    if (menuItem.getName().contains("pizza"))
                        orderedPizza.add(menuItem);

                    if (orderMap.containsKey(item))
                        orderMap.put(item, orderMap.get(item) + 1);
                    else
                        orderMap.put(item, 1);
                }
            }
        totalPrice += orderPrice;
    }

    private boolean isOrderCorrect(String order){
        boolean isCorrect = true;
        for (String item : order.split(" ")) {
            if (!menuItemRepository.existsByName(item)) {
                isCorrect = false;
                System.out.println("Please check spelling and repeat: " + item);
                break;
            }
        }
        return isCorrect;
    }

    private void specialOfferProposal(boolean isEnabled) {
        if(isEnabled) {
            if (isFreePizza(orderedPizza.size()) == 0) {
                System.out.println("We have a special offer \"Each third pizza is free\". You may order one more pizza free.\n Print \"_yes\" for accept.\n Print \"_no\" for discard");
                String answer = scanner.nextLine();
                if (!answer.contains("_no"))
                    recordOrder(""); //TODO
            }
            checkFreePizza();
        }
    }

    private void discountProgram(boolean isEnabled){
        if(isEnabled) {
            System.out.println("Your discount number: ");
            String answer = scanner.nextLine();
            if (answer.matches("\\d{6}"))
                discount = totalPrice * DISCOUNT_PERCENT / 100;
        }
    }

    private void deliveryService(boolean isEnabled){
        if(isEnabled) {
            if (isFreeDeliveryProposal()) {
                System.out.println("Your order cost " + totalPrice + " .Delivery will cost additional 30.00.\n" +
                        "You can order something to have free delivery (start after 300.00)\n" +
                        "\n Print \"_yes\" for accept.\n Print \"_no\" for discard");
                String answer = scanner.nextLine();
                if (!answer.contains("_no"))
                    recordOrder(""); //TODO
            }
        }
    }

    private boolean isFreeDeliveryProposal() {
        return totalPrice > 270 && totalPrice < 300;
    }

    private boolean isFreeDelivery(){
        return totalPrice >= 300;
    }

    private void drinkPromotion(boolean isEnabled) {
        if(isEnabled) {
            if (!isOrderContainsDrinks()) {
                System.out.println("Maybe you want to order some drinks?\n Print \"_yes\" for accept.\n Print \"_no\" for discard");
                String answer = scanner.nextLine();
                if (!answer.contains("_no"))
                    recordOrder(""); //TODO
            }
        }
    }

    private boolean isOrderContainsDrinks() {
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            if(menuItemRepository.findByName(entry.getKey()).getType().equals("drink"))
                return true;
        }
        return false;
    }

    private void checkFreePizza(){
        for (int i = 1; i <= orderedPizza.size(); i++) {
            if (isFreePizza(i) == 1) {
                orderedPizza.sort(getCompByName());
                freePizza.add(orderedPizza.get(i - 1));
                totalPrice -= orderedPizza.get(i - 1).getPrice();
            }
        }
    }

    public static Comparator getCompByName()
    {
        return (Comparator<MenuItem>) (o1, o2) -> Float.compare(o2.getPrice(), o1.getPrice());
    }

    private int isFreePizza(int amount) {
        if (amount % 3 == 0)
            return 1; // already free pizza
        else if((amount+1) % 3 == 0)
            return 0; // can order one more pizza for free
        else
            return -1;
    }

    public String printOrder(String order) {
        if(isOrderCorrect(order)) {
            recordOrder(order);
            String result = "";
            result += "Your order is: ";
            for (Map.Entry<String, Integer> entry : orderMap.entrySet())
                result += entry.getValue() + " " + entry.getKey();

            if (!isFreeDelivery())
                totalPrice += deliveryPrice;

            totalPrice -= discount;

            result += "Discount: " + discount +
                    "\nDelivery: " + deliveryPrice +
                    "\nTotal price is: " + String.format("%.2f", totalPrice);

            if (freePizza.size() > 0)
                result += "You got for free: " + freePizza;

            return result;
        }
        else return "incorrect";
    }

}
