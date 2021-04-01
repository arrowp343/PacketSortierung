import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestCSV {
    @Test
    public void testPackageCSV(){
        //write packages
        Package[] packages = new Package[1000];
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
}