package org.example;

import java.util.ArrayList;
import java.util.List;

//interfata observer
interface Observer {
    void update(String message);
}

// Concrete Observers
class RegularUser implements Observer {
    private String name;

    public RegularUser(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received notification: " + message);
    }
}

class AdminUser implements Observer {
    private String name;

    public AdminUser(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received admin notification: " + message);
    }
}

interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyUpdate(String message);
}

class NotificationService implements Subject {
    private List<Observer> observers = new ArrayList<>();

    @Override
    public void attach(Observer o) {
        observers.add(o);
    }

    @Override
    public void detach(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyUpdate(String message) {
        for(Observer o: observers) {
            o.update(message);
        }
    }
}

public class ObserverPatternDemo {
    public static void main(String[] args) {
        NotificationService service = new NotificationService();

        Observer user1 = new RegularUser("User1");
        Observer admin1 = new AdminUser("Admin1");

        service.attach(user1);
        service.attach(admin1);

        service.notifyUpdate("A new product review is added!");

        service.detach(user1);
        service.notifyUpdate("A request has been resolved!");
    }
}
