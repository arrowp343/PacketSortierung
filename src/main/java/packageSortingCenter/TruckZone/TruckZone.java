package packageSortingCenter.TruckZone;

import csv.Truck;
import packageSortingCenter.CentralContol.CentralControlUnit;

public class TruckZone {
    private Truck truck;
    private boolean isEmpty;
    private final TruckObserver truckObserver;
    private final CentralControlUnit centralControlUnit;

    public TruckZone(CentralControlUnit centralControlUnit) {
        this.centralControlUnit = centralControlUnit;
        truckObserver = new TruckObserver(this.centralControlUnit, this);
    }

    public void setTruck(Truck truck) {
        if (truck != null) {
            this.truck = truck;
            isEmpty = false;
            truckObserver.detect();
        } else {
            isEmpty = true;
        }
    }

    public Truck getTruck() {
        return truck;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public TruckObserver getObserver() {
        return truckObserver;
    }
}
