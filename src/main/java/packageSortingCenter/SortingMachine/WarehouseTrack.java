package packageSortingCenter.SortingMachine;

import com.google.common.eventbus.EventBus;
import csv.Package;

import java.util.PriorityQueue;
import java.util.Queue;

public class WarehouseTrack {
    private final WareHouseTrackSensor wareHouseTrackSensor;
    private final Queue<Package> wareHouseTrack;
    private boolean isFull;

    public WarehouseTrack(EventBus eventBus) {
        wareHouseTrack = new PriorityQueue<>();
        wareHouseTrackSensor = new WareHouseTrackSensor(eventBus);
    }

    public boolean setOnTrack(Package aPackage) {
        if (wareHouseTrack.size() < 600) {
            wareHouseTrack.add(aPackage);
            return true;
        } else {
            isFull = true;
            wareHouseTrackSensor.trigger();
            return false;
        }
    }

    public Package takeFromTrack() {
        isFull = false;
        return wareHouseTrack.poll();
    }

    public boolean isFull() {
        return isFull;
    }
}
