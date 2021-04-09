package packageSortingCenter;

import config.Configuration;

public class ParkingLot {
    AutonomousCar[] autonomousCars = new AutonomousCar[Configuration.amountAutonomousCarsInParkingLot];

    public ParkingLot(){
        for (int i = 0; i < autonomousCars.length; i++)
            autonomousCars[i] = new AutonomousCar();
    }
}
