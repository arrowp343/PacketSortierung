package packageSortingCenter.AutonomousCar;

import config.Configuration;
import packageSortingCenter.PackageSortingCenter;

public class ParkingLot {
    AutonomousCar[] autonomousCars = new AutonomousCar[Configuration.amountAutonomousCarsInParkingLot];

    public ParkingLot(PackageSortingCenter packageSortingCenter) {
        for (int i = 0; i < autonomousCars.length; i++)
            autonomousCars[i] = new AutonomousCar(packageSortingCenter);
    }

    public AutonomousCar[] getAutonomousCars() {
        return autonomousCars;
    }
}
