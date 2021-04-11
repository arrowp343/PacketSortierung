package packageSortingCenter.Events;

import packageSortingCenter.AutonomousCar.AutonomousCar;
import packageSortingCenter.TruckZone.TruckZone;

public class AutonomousCarStartEvent {
    private final TruckZone truckZone;
    private final AutonomousCar autonomousCar;

    public AutonomousCarStartEvent(TruckZone truckZone, AutonomousCar autonomousCar) {
        this.truckZone = truckZone;
        this.autonomousCar = autonomousCar;
    }

    public AutonomousCar getAutonomousCar() {
        return autonomousCar;
    }

    public TruckZone getTruckZone() {
        return truckZone;
    }
}
