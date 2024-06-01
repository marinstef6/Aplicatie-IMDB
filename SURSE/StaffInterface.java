package org.example;

//interfata Staff care are functii ce tin de actori si productii
public interface StaffInterface {
    void addProductionSystem(Production p);
    void addActorSystem(Actor a);
    void removeProductionSystem(String name);
    void removeActorSystem(String name);
    void updateProduction(Production p);
    void updateActor(Actor a);
    void resolveRequests();
}
