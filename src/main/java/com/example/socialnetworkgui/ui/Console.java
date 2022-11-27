package com.example.socialnetworkgui.ui;


import com.example.socialnetworkgui.domain.friendship.Friendship;
import com.example.socialnetworkgui.domain.user.Address;
import com.example.socialnetworkgui.domain.user.User;
import com.example.socialnetworkgui.domain.user.UserParameter;
import com.example.socialnetworkgui.domain.validators.ValidationException;
import com.example.socialnetworkgui.service.Service;
import com.example.socialnetworkgui.service.ServiceException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Console {

    private final Scanner scanner;
    private Service service;

    public Console(Service service) {
        this.service = service;
        scanner = new Scanner(System.in);
    }

    public void run() {
        boolean stillRunning = true;
        while (stillRunning) {
            printOptions();
            String option = scanner.nextLine();
            switch (option) {
                case "1" -> addUser();
                case "2" -> updateUser();
                case "3" -> removeUser();
                case "4" -> printAllUsers();
                case "5" -> addFriendShip();
                case "6" -> updateFriendship();
                case "7" -> removeFriendship();
                case "8" -> printAllFriendships();
                case "9" -> printAllCommunities();
                case "10" -> printMostSociableCommunity();
                case "11" -> stillRunning = false;
                default -> System.out.println("Invalid option!");
            }
        }
    }

    private void addUser() {
        String firstName, lastName, email, userName, country, county, city, street, password;
        Long streetNumber;
        LocalDate date;
        System.out.print("Enter first name: ");
        firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        lastName = scanner.nextLine();
        System.out.print("Enter email: ");
        email = scanner.nextLine();
        System.out.print("Enter birth date: ");
        try {
            String stringDate = scanner.nextLine();
            date = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        } catch (DateTimeParseException exception) {
            date = null;
        }
        System.out.print("Enter country: ");
        country = scanner.nextLine();
        System.out.print("Enter county: ");
        county = scanner.nextLine();
        System.out.print("Enter city: ");
        city = scanner.nextLine();
        System.out.print("Enter street name: ");
        street = scanner.nextLine();
        System.out.print("Enter street number: ");
        try {
            streetNumber = Long.parseLong(scanner.nextLine());
        } catch (NumberFormatException exception) {
            streetNumber = -1L;
        }
        System.out.print("Enter user name: ");
        userName = scanner.nextLine();
        System.out.print("Enter password: ");
        password = scanner.next();
        Address address = new Address(country, county, city, street, streetNumber);
        try {
            service.addUser(userName, firstName, lastName, email, date, address, password);
            System.out.println("User added successfully!");
        } catch (ValidationException | ServiceException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println();
    }

    private void updateUser() {
        System.out.print("User Name: ");
        String userName = scanner.nextLine();
        Map<String, UserParameter> updateOptions = new HashMap<>();
        updateOptions.put("1", UserParameter.FIRST_NAME);
        updateOptions.put("2", UserParameter.LAST_NAME);
        updateOptions.put("3", UserParameter.EMAIL);
        updateOptions.put("4", UserParameter.COUNTRY);
        updateOptions.put("5", UserParameter.COUNTY);
        updateOptions.put("6", UserParameter.CITY);
        updateOptions.put("7", UserParameter.STREET);
        updateOptions.put("8", UserParameter.STREET_NUMBER);
        printUpdateUserOptions();
        String option = scanner.nextLine();
        System.out.print("Enter new value: ");
        String newValue = scanner.nextLine();
        if (!updateOptions.containsKey(option)) {
            System.out.println("Invalid option!\n");
        }
        try {
            service.updateUser(userName, newValue, updateOptions.get(option));
            System.out.println("User has been updated!");
        } catch (ServiceException | ValidationException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println();
    }

    private void removeUser() {
        String userName;
        System.out.print("Enter user name: ");
        userName = scanner.nextLine();
        try {
            service.removeUser(userName);
            System.out.println("User deleted successfully");
        } catch (ServiceException exception) {
            System.out.println(exception.getMessage());
        }
        System.out.println();
    }

    private void printAllUsers() {
        Iterable<User> users = service.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }
        System.out.println();
    }

    private void addFriendShip() {
        System.out.print("Enter the 2 user names: ");
        String lineArg = scanner.nextLine();
        String[] args = lineArg.split(" ");
        if (args.length != 2) {
            System.out.println("Invalid input!");
        } else {
            try {
                service.addFriendShip(args[0], args[1]);
                System.out.println("Friendship added successfully!");
            } catch (ServiceException | ValidationException exception) {
                System.out.println(exception.getMessage());
            }
        }
        System.out.println();
    }

    private void updateFriendship() {
        System.out.print("Enter the 2 user names: ");
        String lineArg = scanner.nextLine();
        String[] args = lineArg.split(" ");
        if (args.length != 2) {
            System.out.println("Invalid input!");
        } else {
            try {
                service.updateFriendship(args[0], args[1]);
                System.out.println("Friendship updated! Starting date restarted!");
            } catch (ServiceException exception) {
                System.out.println(exception.getMessage());
            }
        }
        System.out.println();
    }

    private void printAllFriendships() {
        Iterable<Friendship> friendships = service.getAllFriendships();
        for (Friendship friendship : friendships) {
            System.out.println(friendship);
        }
        System.out.println();
    }

    private void removeFriendship() {
        System.out.print("Enter the 2 user names: ");
        String lineArg = scanner.nextLine();
        String[] args = lineArg.split(" ");
        if (args.length != 2) {
            System.out.println("Invalid input!");
        } else {
            try {
                service.removeFriendship(args[0], args[1]);
                System.out.println("Friendship removed successfully");
            } catch (ServiceException exception) {
                System.out.println(exception.getMessage());
            }
        }
        System.out.println();
    }

    private void printAllCommunities() {
        List<List<User>> communities = service.getAllCommunities();
        System.out.printf("There are %d communities in our app:%n", communities.size());
        int index = 1;
        for (List<User> community : communities) {
            System.out.printf("Community #%d%n", index++);
            for (User user : community) {
                System.out.printf("%s ", user.getId());
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printMostSociableCommunity() {
        List<User> mostSociableCommunity = service.getMostSociableCommunity();
        if (mostSociableCommunity == null) {
            System.out.println("There is no community yet!");
        } else {
            System.out.print("Most sociable community: ");
            for (User user : mostSociableCommunity) {
                System.out.printf("%s ", user.getId());
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printOptions() {
        System.out.println("--- MENU OPTIONS ---");
        System.out.println("-> 1) Add new user");
        System.out.println("-> 2) Update user");
        System.out.println("-> 3) Remove user");
        System.out.println("-> 4) Get all users");
        System.out.println("-> 5) Add new friendship");
        System.out.println("-> 6) Update friendship");
        System.out.println("-> 7) Remove friendship");
        System.out.println("-> 8) Get all friendships");
        System.out.println("-> 9) Print all communities");
        System.out.println("-> 10) Find most sociable community");
        System.out.println("-> 11) Exit");
        System.out.println();
        System.out.print(">>>");
    }

    private void printUpdateUserOptions() {
        System.out.println("-> 1) Update first name");
        System.out.println("-> 2) Update last name");
        System.out.println("-> 3) Update email");
        System.out.println("-> 4) Update country");
        System.out.println("-> 5) Update county");
        System.out.println("-> 6) Update city");
        System.out.println("-> 7) Update street name");
        System.out.println("-> 8) Update street number");
        System.out.println();
        System.out.print(">>>");
    }

}
