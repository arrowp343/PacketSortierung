import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCSV {

    final int amountPackages = 24000,
              amountBoxes = amountPackages / Configuration.maxPackagesInBox,
              amountPallet = amountBoxes / Configuration.maxBoxesOnPallet;

    @BeforeAll
    static void resetCSV(){
        CSV.reset();
    }

    @Test
    @Order(1)
    public void testPackageCSV() {
        //write packages
        int count = amountPackages;
        do {
            Package[] packages;
            if (count >= 1000) {
                count -= 1000;
                packages = new Package[1000];
            } else {
                packages = new Package[count];
            }
            for (int j = 0; j < packages.length; j++)
                packages[j] = new Package();
            try {
                CSV.writePackageInCSV(packages);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (count > 0);
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
            fail("Test failed because of Exception: " + e.getMessage());
        }
    }
    @Test
    @Order(3)
    public void testPalletCSV(){
        try{
            Box[] boxes = CSV.readBoxFromCSV();
            int bCount = 0;
            Pallet[] expect = new Pallet[amountPallet];
            for(int i = 0; i < expect.length; i++) {
                Box[][] b = new Box[Configuration.amountPositionsOnPallet][Configuration.amountLevelsOnPallet];
                for(int j = 0; j < b.length; j++) {
                    for(int k = 0; k < b[j].length; k++){
                        b[j][k] = boxes[bCount];
                        bCount++;
                    }
                }
                expect[i] = new Pallet(b);
            }
            CSV.writePalletInCSV(expect);
        } catch (Exception e){
            fail("Test failed because of Exception: " + e.getMessage());
        }
    }
}