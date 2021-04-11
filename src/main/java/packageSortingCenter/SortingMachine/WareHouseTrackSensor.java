package packageSortingCenter.SortingMachine;

import com.google.common.eventbus.EventBus;
import packageSortingCenter.Events.WareHouseTrackFullEvent;

public class WareHouseTrackSensor {
    private final EventBus eventBus;

    public WareHouseTrackSensor(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void trigger() {
        eventBus.post(new WareHouseTrackFullEvent());
    }
}
