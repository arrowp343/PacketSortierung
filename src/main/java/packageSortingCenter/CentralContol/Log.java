package packageSortingCenter.CentralContol;

import csv.PackageType;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;

public class Log {
    private int countTrucks;
    private HashMap<PackageType, Integer> countPackages;
    private int countExplosives;

    public Log() {
        countTrucks = 0;
        countPackages = new HashMap<>();
        countPackages.put(PackageType.NORMAL, 0);
        countPackages.put(PackageType.EXPRESS, 0);
        countPackages.put(PackageType.VALUE, 0);
        countExplosives = 0;
    }

    public void addTruck() {
        countTrucks++;
    }

    public void addPackage(PackageType type) {
        countPackages.put(type, countPackages.get(type) + 1);
    }

    public void addExplosive() {
        countExplosives++;
    }

    public void generate() {
        try {
            File outFile = new File("log/report.txt");
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            PrintWriter logWriter = new PrintWriter(new FileWriter(outFile.getAbsolutePath()));
            logWriter.println(new Timestamp(System.currentTimeMillis()));
            logWriter.println("     Finished Trucks: " + countTrucks);
            logWriter.println("     Scanned Packages: ");
            logWriter.println("         Normal:  " + countPackages.get(PackageType.NORMAL));
            logWriter.println("         Express: " + countPackages.get(PackageType.EXPRESS));
            logWriter.println("         Value:   " + countPackages.get(PackageType.VALUE));
            logWriter.println("     Packages with explosives: " + countExplosives);
            logWriter.close();
        } catch (IOException ioException) {
            System.out.println(ioException);
        }
    }
}
