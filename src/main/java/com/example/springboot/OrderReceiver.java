package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class OrderReceiver {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MenuItemRepository menuItemRepository;

    private float totalPrice = 0;

    private final Scanner scanner = new Scanner(System.in);

    Map<String, Integer> orderMap = new LinkedHashMap<>();
    ArrayList<MenuItem> orderedPizza = new ArrayList<>();
    ArrayList<MenuItem> freePizza = new ArrayList<>();


    @Bean
    public void orderPizza() {
        recordOrder();

        checkIsSpecialOffer();

        checkIsFreePizza();

        printOrder(totalPrice, orderMap);

    }

    private void recordOrder() {
        float orderPrice = 0;
        boolean isOrdered = false;

        while (!isOrdered) {
            System.out.println("Enter your order: ");
            String order = scanner.nextLine();

            isOrdered = true;

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
            else {
                isOrdered = false;
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

    private void checkIsSpecialOffer() {
        if(isFreePizza(orderedPizza.size()) == 0) {
            System.out.println("We have a special offer \"Each third pizza is free\". You may order one more pizza free.\n Print \"_yes\" for accept.\n Print \"_no\" for discard");
            String answer = scanner.nextLine();
            if (!answer.contains("_no"))
                recordOrder();
        }
    }

    private void checkIsFreePizza(){
        for (int i = 1; i <= orderedPizza.size(); i++) {
            if(isFreePizza(i) == 1){
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


    private void printOrder(float totalPrice, Map<String, Integer> orderMap) {
        System.out.println("Your order is: ");
        for (Map.Entry<String, Integer> entry : orderMap.entrySet())
            System.out.println(entry.getValue() + " " + entry.getKey());

        System.out.println("Total price is: " + String.format("%.2f", totalPrice));

        if(freePizza.size() > 0)
            System.out.println("You got for free: " + freePizza);
    }

}
