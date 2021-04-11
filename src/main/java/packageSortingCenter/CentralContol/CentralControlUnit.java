package packageSortingCenter.CentralContol;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import csv.CSV;
import csv.Truck;
import packageSortingCenter.AutonomousCar.AutonomousCar;
import packageSortingCenter.Commands.ICommand;
import packageSortingCenter.Events.*;
import packageSortingCenter.PackageSortingCenter;
import packageSortingCenter.SortingMachine.SearchingAlgorithm;
import packageSortingCenter.Terminal.Terminal;
import packageSortingCenter.TruckZone.TruckZone;
import packageSortingCenter.WaitingZone;

import java.util.ArrayList;
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

    @Subscribe
    public void receive(LogEvent logEvent) {
        switch (logEvent.getLogEventType()) {
            case Package -> {
                log.addPackage(logEvent.getPackageType());
            }
            case Explosive -> log.addExplosive();
        }
    }

    private void init() {
        try {
            ArrayList<String> truckStrings = CSV.readFromCSV("csv/base_truck.csv");
            String truckID = null;
            ArrayList<String> oneTruckStrings = new ArrayList<>();
            for (String truckString : truckStrings) {
                if (truckID != null && !truckID.equals(truckString.split(",")[0])) {
                    waitingZone.park(new Truck(oneTruckStrings));
                    oneTruckStrings.clear();
                }
                truckID = truckString.split(",")[0];
                oneTruckStrings.add(truckString);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
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

    public Terminal getTerminal() {
        return terminal;
    }
}
