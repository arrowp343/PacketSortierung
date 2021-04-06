import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCSV {

    final int amountPackages = 1000,
              amountBoxes = amountPackages / Configuration.maxPackagesInBox;

    @Test
    @Order(1)
    public void testPackageCSV(){
        //write packages
        Package[] packages = new Package[amountPackages];
        for(int i = 0; i < packages.length; i++)
            packages[i] = new Package();
        try{
            CSV.writePackageInCSV(packages);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            //read packages
            Package[] actual = CSV.readPackageFromCSV();

            //compare package arrays
            for (int i = 0; i < actual.length; i++) {
                assertEquals(packages[i].getId(), actual[i].getId());
                char[][][] e = packages[i].getContent(), a = actual[i].getContent();
                for (int i1 = 0; i1 < Configuration.l; i1++)
                    for (int i2 = 0; i2 < Configuration.w; i2++)
                        for (int i3 = 0; i3 < Configuration.h; i3++)
                            assertEquals(e[i1][i2][i3], a[i1][i2][i3]);
                assertEquals(packages[i].getZip_code(), actual[i].getZip_code());
                assertEquals(packages[i].getType(), actual[i].getType());
                assertEquals(packages[i].getWeight(), actual[i].getWeight());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Test
    @Order(2)
    public void testBoxCSV(){
        try{
            Package[] packages = CSV.readPackageFromCSV();
            int pCount = 0;
            Box[] boxes = new Box[amountBoxes];
            for(int i = 0; i < boxes.length; i++) {
                Package[] p = new Package[Configuration.maxPackagesInBox];
                for(int k = 0; k < p.length; k++) {
                    p[k] = packages[pCount];
                    pCount++;
                }
                boxes[i] = new Box(p);
            }
            CSV.writeBoxInCSV(boxes);
            //TODO readBoxFromCSV
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}