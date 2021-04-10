package packageSortingCenter;

import csv.Truck;

public class WaitingZone {
    private final Truck[] waitingZone;

    public WaitingZone() {
        waitingZone = new Truck[5];
    }

    public boolean park(Truck truck) {
        for (int i = 0; i < 5; i++) {
            if (waitingZone[i] == null) {
                waitingZone[i] = truck;
                return true;
            }
        }
        return false;
    }

    public Truck leave() {
        for (int i = 0; i < 5; i++) {
            if (waitingZone[i] != null) {
                Truck truck = waitingZone[i];
                waitingZone[i] = null;
                return truck;
            }
        }
        return null;
    }
}
