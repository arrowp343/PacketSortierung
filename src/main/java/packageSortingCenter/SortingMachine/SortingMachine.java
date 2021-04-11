package packageSortingCenter.SortingMachine;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import csv.Box;
import csv.Package;
import csv.PackageType;
import csv.Pallet;

import java.util.ArrayList;

public class SortingMachine {
    private boolean isActive;
    private final PalletArea palletArea;
    private final Robot robot;
    private final WarehouseTrack[] warehouseTracks;
    private final SortingTrack[] sortingTracks;
    private final EventBus eventBus;
    private final ArrayList<Box> emptyBoxes;
    private final ArrayList<Pallet> emptyPallets;

    private SearchingAlgorithm searchingAlgorithm;

    public SortingMachine(EventBus eventBus) {
        this.eventBus = eventBus;
        this.eventBus.register(this);
        isActive = true;
        palletArea = new PalletArea();
        robot = new Robot(eventBus, this);
        warehouseTracks = new WarehouseTrack[8];
        for (int i = 0; i < 8; i++) {
            warehouseTracks[i] = new WarehouseTrack(eventBus);
        }
        sortingTracks = new SortingTrack[3];
        for (int i = 0; i < 3; i++) {
            PackageType packageType = switch (i) {
                case 0 -> PackageType.EXPRESS;
                case 1 -> PackageType.VALUE;
                case 2 -> PackageType.NORMAL;
                default -> null;
            };
            sortingTracks[i] = new SortingTrack(packageType, this);
        }
        searchingAlgorithm = SearchingAlgorithm.BoyerMoore;
        emptyBoxes = new ArrayList<>();
        emptyPallets = new ArrayList<>();
    }

    @Subscribe
    public void receive(StartSortingEvent startSortingEvent) {
        sort();
    }

    private void sort() {
        for (WarehouseTrack warehouseTrack : warehouseTracks) {
            Package aPackage;
            while ((aPackage = warehouseTrack.takeFromTrack()) != null) {
                for (SortingTrack sortingTrack : sortingTracks) {
                    if (sortingTrack.getPackageType() == aPackage.getPackageType()) {
                        sortingTrack.setOnTrack(aPackage);
                    }
                }
            }
        }
        for (SortingTrack sortingTrack : sortingTracks) {
            sortingTrack.scan();
        }
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public PalletArea getPalletArea() {
        return palletArea;
    }

    public WarehouseTrack[] getWarehouseTracks() {
        return warehouseTracks;
    }

    public void setSortingAlgorithm(SearchingAlgorithm searchingAlgorithm) {
        this.searchingAlgorithm = searchingAlgorithm;
    }

    public SearchingAlgorithm getSortingAlgorithm() {
        return searchingAlgorithm;
    }

    public ArrayList<Pallet> getEmptyPallets() {
        return emptyPallets;
    }

    public ArrayList<Box> getEmptyBoxes() {
        return emptyBoxes;
    }
}
