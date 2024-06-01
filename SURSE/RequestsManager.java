package org.example;

import java.util.ArrayList;
import java.util.List;

//interfata RequestManager pentru cereri
//functiile de creare si stergere cerere
public interface RequestsManager {
        void createRequest(Request r);
        void removeRequest(Request r);

}
