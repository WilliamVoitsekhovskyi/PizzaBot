package com.example.springboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.*;

@SpringBootApplication
public class Application {

    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private MenuItemRepository menuItemRepository;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            orderPizza();
        };
    }

    private void orderPizza() {
        float totalPrice = 0;

        Map<String, Integer> orderMap = new LinkedHashMap<>();
        ArrayList<MenuItem> orderedPizza = new ArrayList<>();
        ArrayList<MenuItem> freePizza = new ArrayList<>();

        boolean isOrdered = false;
        while (!isOrdered) {
            isOrdered = true;
            for (String item : getOrder().split(" ")) {
                if (menuItemRepository.existsByName(item)) {
                    MenuItem menuItem = menuItemRepository.findByName(item);
                    totalPrice += menuItem.getPrice();
                    if(orderMap.containsKey(item)){
                        orderMap.put(item, orderMap.get(item)+1);
                    }
                    else
                        orderMap.put(item, 1);

                    if(menuItem.getName().contains("pizza")) {
                        orderedPizza.add(menuItem);
                    }

                    if(isFreePizza(orderedPizza.size()) == 0) {
                        orderedPizza.sort(getCompByName());
                        freePizza.add(orderedPizza.get(orderedPizza.size() - 1));
                        totalPrice -= orderedPizza.get(orderedPizza.size() - 1).getPrice();
                    }
                    else if(isFreePizza(orderedPizza.size()) == 1) {
                        System.out.println("We have a special offer \"Each third pizza is free\". You may order one more pizza free.\n Print \"no\" for discard");
                        Scanner scanner = new Scanner(System.in);
                        for (String additionalItem : scanner.next().split(" ")) {

                            if (menuItemRepository.existsByName(additionalItem)) {

                                if(additionalItem.contains("pizza"))
                                    orderedPizza.add(menuItemRepository.findByName(additionalItem));

                                if(orderMap.containsKey(additionalItem)){
                                    orderMap.put(additionalItem, orderMap.get(item)+1);
                                }
                                else
                                    orderMap.put(additionalItem, 1);
                            }
                            else
                                System.out.println("Please check spelling and repeat: " + additionalItem);
                        }
                    }
                } else {
                    System.out.println("Please check spelling and repeat: " + item);
                    isOrdered = false;
                    totalPrice = 0;
                    orderMap.clear();
                }
            }
        }
        printOrder(totalPrice, orderMap);
        System.out.println("You got for free: " + freePizza);
    }

    public static Comparator getCompByName()
    {
        Comparator comp = new Comparator<MenuItem>(){
            @Override
            public int compare(MenuItem o1, MenuItem o2)
            {
                return Float.compare(o1.getPrice(), o2.getPrice());
            }
        };
        return comp;
    }

    private int isFreePizza(int amount) {
        return amount % 3;
    }

    private String getOrder() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your order: ");
        return scanner.nextLine();
    }

    private void printOrder(float totalPrice, Map<String, Integer> orderMap) {
        System.out.println("Your order is: ");
        for (Map.Entry<String, Integer> entry : orderMap.entrySet()) {
            System.out.println(entry.getValue() + " " + entry.getKey());
        }
        System.out.println("Total price is: " + String.format("%.2f", totalPrice));
    }

}