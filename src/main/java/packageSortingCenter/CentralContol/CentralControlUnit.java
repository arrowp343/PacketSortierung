package packageSortingCenter.CentralContol;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import packageSortingCenter.AutonomousCar.AutonomousCar;
import packageSortingCenter.AutonomousCar.UnloadingFinishedEvent;
import packageSortingCenter.CentralContol.Commands.ICommand;
import packageSortingCenter.PackageSortingCenter;
import packageSortingCenter.SortingMachine.SearchingAlgorithm;
import packageSortingCenter.SortingMachine.StartSortingEvent;
import packageSortingCenter.SortingMachine.StartStoringEvent;
import packageSortingCenter.SortingMachine.WareHouseTrackFullEvent;
import packageSortingCenter.Terminal.Terminal;
import packageSortingCenter.TruckZone.TruckDetectEvent;
import packageSortingCenter.TruckZone.TruckZone;
import packageSortingCenter.WaitingZone;

import java.util.LinkedList;

public class CentralControlUnit {
    private final EventBus eventBus;
    private final WaitingZone waitingZone;
    private final Terminal terminal;
    private final PackageSortingCenter packageSortingCenter;
    private final Log log;
    private int wareHouseTrackReports;

    public CentralControlUnit(PackageSortingCenter packageSortingCenter) {
        waitingZone = new WaitingZone();
        terminal = new Terminal(this);
        log = new Log();
        this.packageSortingCenter = packageSortingCenter;

        eventBus = new EventBus("PackageSorting");
        eventBus.register(this);
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    @Subscribe
    public void receive(ICommand command) {
        switch (command.getCommandType()) {
            case init -> init();
            case next -> next();
            case shutdown -> shutdown();
            case lock -> lock();
            case unlock -> unlock();
            case show_statistics -> showStatistics();
            case change_search_algorithm -> changeSearchAlgorithm(command.getParameters());
        }
    }

    @Subscribe
    public void receive(TruckDetectEvent truckDetectEvent) {
        LinkedList<AutonomousCar> availableCars = new LinkedList<>();
        for (AutonomousCar autonomousCar : packageSortingCenter.getParkingLot().getAutonomousCars())
            if (autonomousCar.isParked()) availableCars.add(autonomousCar);
        eventBus.post(new AutonomousCarStartEvent(truckDetectEvent.getTruckZone(), availableCars.get((int) Math.floor(Math.random() * availableCars.size()))));
    }

    @Subscribe
    public void receive(UnloadingFinishedEvent unloadingFinishedEvent) {
        log.addTruck();
        eventBus.post(new StartStoringEvent());
    }

    @Subscribe
    public void receive(WareHouseTrackFullEvent wareHouseTrackFullEvent) {
        wareHouseTrackReports++;
        if (wareHouseTrackReports == 8) {
            eventBus.post(new StartSortingEvent());
            wareHouseTrackReports = 0;
        }
    }

    private void init() {
        //TODO Truck from CSV
    }

    private void next() {
        packageSortingCenter.receiveTruck(waitingZone.leave());
    }

    private void shutdown() {
        for (TruckZone truckZone : packageSortingCenter.getTruckZones()) truckZone.getObserver().setActive(false);
    }

    private void lock() {
        packageSortingCenter.getSortingMachine().setActive(false);
    }

    private void unlock() {
        packageSortingCenter.getSortingMachine().setActive(true);
    }

    private void showStatistics() {
        log.generate();
    }

    private void changeSearchAlgorithm(String[] parameters) {
        switch (parameters[0].toLowerCase()) {
            case "boyermoore" -> packageSortingCenter.getSortingMachine().setSortingAlgorithm(SearchingAlgorithm.BoyerMoore);
            case "rabinkarp" -> packageSortingCenter.getSortingMachine().setSortingAlgorithm(SearchingAlgorithm.RabinKarp);
        }
    }

    public Log getLog() {
        return log;
    }
}
