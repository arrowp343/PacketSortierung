package packageSortingCenter.TruckZone;

public class TruckDetectEvent {
    private final TruckZone truckZone;

    public TruckDetectEvent(TruckZone truckZone) {
        this.truckZone = truckZone;
    }

    public TruckZone getTruckZone() {
        return truckZone;
    }
}
