package packageSortingCenter.AutonomousCar;

import com.google.common.eventbus.Subscribe;
import csv.Pallet;
import packageSortingCenter.CentralContol.AutonomousCarStartEvent;
import packageSortingCenter.PackageSortingCenter;
import packageSortingCenter.TruckZone.TruckZone;

public class AutonomousCar {
    private final PackageSortingCenter packageSortingCenter;
    private boolean isParked;

    public AutonomousCar(PackageSortingCenter packageSortingCenter) {
        this.packageSortingCenter = packageSortingCenter;
        packageSortingCenter.getCentralControlUnit().getEventBus().register(this);
    }

    @Subscribe
    public void receive(AutonomousCarStartEvent autonomousCarStartEvent) {
        if (autonomousCarStartEvent.getAutonomousCar() == this) {
            unloadTruck(autonomousCarStartEvent.getTruckZone());
        }
    }

    private void unloadTruck(TruckZone truckZone) {
        isParked = false;
        for (Pallet pallet : truckZone.getTruck().getTrailer().getPalletsLeft()) {
            packageSortingCenter.getSortingMachine().getPalletArea().store(pallet);
        }
        for (Pallet pallet : truckZone.getTruck().getTrailer().getPalletsRight()) {
            packageSortingCenter.getSortingMachine().getPalletArea().store(pallet);
        }
        isParked = true;
        packageSortingCenter.getCentralControlUnit().getEventBus().post(new UnloadingFinishedEvent());
    }

    public boolean isParked() {
        return isParked;
    }
}
