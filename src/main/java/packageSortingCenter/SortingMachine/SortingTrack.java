package packageSortingCenter.SortingMachine;

import csv.Package;
import csv.PackageType;

import java.util.PriorityQueue;
import java.util.Queue;

public class SortingTrack {
    private final PackageType packageType;
    private final Queue<Package> sortingTrack;
    private final Scanner scanner;
    private final SortingMachine sortingMachine;

    public SortingTrack(PackageType packageType, SortingMachine sortingMachine) {
        this.packageType = packageType;
        this.sortingMachine = sortingMachine;
        sortingTrack = new PriorityQueue<>();
        scanner = new Scanner();
    }

    public void setOnTrack(Package aPackage) {
        sortingTrack.add(aPackage);
    }

    public Package takeFromTrack() {
        return sortingTrack.poll();
    }

    public void scan() {
        Package aPackage;
        while ((aPackage = takeFromTrack()) != null) {
            if (scanner.scan(aPackage, sortingMachine.getSortingAlgorithm())) {
                sortingMachine.logExplosive();
            }
        }
    }

    public PackageType getPackageType() {
        return packageType;
    }
}
