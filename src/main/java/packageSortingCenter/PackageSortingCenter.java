package packageSortingCenter;

import config.Configuration;
import csv.Truck;
import packageSortingCenter.AutonomousCar.ParkingLot;
import packageSortingCenter.CentralContol.CentralControlUnit;
import packageSortingCenter.SortingMachine.SortingMachine;
import packageSortingCenter.TruckZone.TruckZone;

import java.util.LinkedList;

public class PackageSortingCenter {
    CentralControlUnit centralControlUnit;
    TruckZone[] truckZones = new TruckZone[Configuration.amountTruckZones];
    ParkingLot parkingLot;
    SortingMachine sortingMachine;

    public PackageSortingCenter() {
        centralControlUnit = new CentralControlUnit(this);
        for (int i = 0; i < truckZones.length; i++)
            truckZones[i] = new TruckZone(centralControlUnit);
        parkingLot = new ParkingLot(this);
        sortingMachine = new SortingMachine(centralControlUnit.getEventBus());
    }

    public boolean receiveTruck(Truck truck) {
        LinkedList<Integer> openZones = new LinkedList<>();
        for (int i = 0; i < Configuration.amountTruckZones; i++)
            if (truckZones[i].isEmpty()) openZones.add(i);
        if (openZones.size() > 0) {
            truckZones[openZones.get((int) Math.floor(Math.random()) * openZones.size())].setTruck(truck);
            return true;
        } else return false;
    }

    public TruckZone[] getTruckZones() {
        return truckZones;
    }

    public SortingMachine getSortingMachine() {
        return sortingMachine;
    }

    public ParkingLot getParkingLot() {
        return parkingLot;
    }

    public CentralControlUnit getCentralControlUnit() {
        return centralControlUnit;
    }
}
