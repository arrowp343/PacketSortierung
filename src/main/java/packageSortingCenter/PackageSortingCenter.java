package packageSortingCenter;

import config.Configuration;

public class PackageSortingCenter {
    CentralControlUnit centralControlUnit;
    TruckZone[] truckZones = new TruckZone[Configuration.amountTruckZones];
    ParkingLot parkingLot;
    SortingMachine sortingMachine;

    public PackageSortingCenter(){
        centralControlUnit = new CentralControlUnit();
        for(int i = 0; i < truckZones.length; i++)
            truckZones[i] = new TruckZone();
        parkingLot = new ParkingLot();
        sortingMachine = new SortingMachine();
    }
}
