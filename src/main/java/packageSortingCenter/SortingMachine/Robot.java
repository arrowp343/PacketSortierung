package packageSortingCenter.SortingMachine;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import csv.Box;
import csv.Package;
import csv.Pallet;

public class Robot {
    private final EventBus eventBus;
    private final SortingMachine sortingMachine;

    public Robot(EventBus eventBus, SortingMachine sortingMachine) {
        this.eventBus = eventBus;
        eventBus.register(this);
        this.sortingMachine = sortingMachine;
    }

    @Subscribe
    public void receive(StartStoringEvent startStoringEvent) {
        Pallet pallet;
        while ((pallet = sortingMachine.getPalletArea().deliver()) != null) {
            for (Box[] boxes : pallet.getBoxes()) {
                for (Box box : boxes) {
                    for (Package aPackage : box.getPackages()) {
                        for (WarehouseTrack warehouseTrack : sortingMachine.getWarehouseTracks()) {
                            if (!warehouseTrack.isFull()) {
                                warehouseTrack.setOnTrack(aPackage);
                                break;
                            }
                        }
                    }
                    sortingMachine.getEmptyBoxes().add(box);
                }
            }
            sortingMachine.getEmptyPallets().add(pallet);
        }
    }
}
