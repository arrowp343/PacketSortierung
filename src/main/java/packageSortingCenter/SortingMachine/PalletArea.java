package packageSortingCenter.SortingMachine;

import csv.Pallet;

public class PalletArea {
    private Pallet[][] palletArea;

    public PalletArea() {
        palletArea = new Pallet[5][2];
    }

    public void store(Pallet pallet) {
        for (int i = 0; i < 5; i++) {
            if (palletArea[i][0] == null) {
                palletArea[i][0] = pallet;
                return;
            } else if (palletArea[i][1] == null) {
                palletArea[i][1] = pallet;
                return;
            }
        }
    }

    public Pallet deliver() {
        for (int i = 0; i < 5; i++) {
            if (palletArea[i][0] != null) {
                return palletArea[i][0];
            } else if (palletArea[i][1] != null) {
                return palletArea[i][1];
            }
        }
        return null;
    }
}
