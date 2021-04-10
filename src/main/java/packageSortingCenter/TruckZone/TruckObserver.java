package packageSortingCenter.TruckZone;

import packageSortingCenter.CentralContol.CentralControlUnit;

public class TruckObserver {
    private final CentralControlUnit centralControlUnit;
    private final TruckZone truckZone;
    private boolean isActive;

    public TruckObserver(CentralControlUnit centralControlUnit, TruckZone truckZone) {
        this.centralControlUnit = centralControlUnit;
        this.truckZone = truckZone;
        isActive = true;
    }

    public void detect() {
        if (isActive)
            centralControlUnit.getEventBus().post(new TruckDetectEvent(truckZone));
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
