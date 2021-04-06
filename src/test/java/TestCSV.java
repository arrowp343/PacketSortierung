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
        Package[] expect = new Package[amountPackages];
        for(int i = 0; i < expect.length; i++)
            expect[i] = new Package();
        try{
            CSV.writePackageInCSV(expect);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            //read packages
            Package[] actual = CSV.readPackageFromCSV();

            //compare package arrays
            for (int i = 0; i < actual.length; i++) {
                assertEquals(expect[i].getId(), actual[i].getId());
                char[][][] e = expect[i].getContent(), a = actual[i].getContent();
                for (int i1 = 0; i1 < Configuration.l; i1++)
                    for (int i2 = 0; i2 < Configuration.w; i2++)
                        for (int i3 = 0; i3 < Configuration.h; i3++)
                            assertEquals(e[i1][i2][i3], a[i1][i2][i3]);
                assertEquals(expect[i].getZip_code(), actual[i].getZip_code());
                assertEquals(expect[i].getType(), actual[i].getType());
                assertEquals(expect[i].getWeight(), actual[i].getWeight());
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
            Box[] expect = new Box[amountBoxes];
            for(int i = 0; i < expect.length; i++) {
                Package[] p = new Package[Configuration.maxPackagesInBox];
                for(int k = 0; k < p.length; k++) {
                    p[k] = packages[pCount];
                    pCount++;
                }
                expect[i] = new Box(p);
            }
            CSV.writeBoxInCSV(expect);

            Box[] actual = CSV.readBoxFromCSV();
            for(int i = 0; i < expect.length; i++){
                assertEquals(expect[i].getId(), actual[i].getId());
                Package[] expectPackages = expect[i].getPackages(), actualPackages = actual[i].getPackages();
                for (int j = 0; j < actualPackages.length; j++) {
                    assertEquals(expectPackages[j].getId(), actualPackages[j].getId());
                    char[][][] e = expectPackages[j].getContent(), a = actualPackages[j].getContent();
                    for (int i1 = 0; i1 < Configuration.l; i1++)
                        for (int i2 = 0; i2 < Configuration.w; i2++)
                            for (int i3 = 0; i3 < Configuration.h; i3++)
                                assertEquals(e[i1][i2][i3], a[i1][i2][i3]);
                    assertEquals(expectPackages[j].getZip_code(), actualPackages[j].getZip_code());
                    assertEquals(expectPackages[j].getType(), actualPackages[j].getType());
                    assertEquals(expectPackages[j].getWeight(), actualPackages[j].getWeight());
                }
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}