import org.junit.jupiter.api.Test;
import packageSortingCenter.Commands.*;
import packageSortingCenter.PackageSortingCenter;
import packageSortingCenter.Terminal.Terminal;
import roles.SuperVisor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSorting {
    @Test
    public void testSorting() {
        PackageSortingCenter packageSortingCenter = new PackageSortingCenter();
        Terminal terminal = packageSortingCenter.getCentralControlUnit().getTerminal();
        terminal.register(new SuperVisor("Peter", "6355", "753464"), "6355");
        terminal.getTouchPad().inputCommand(new Init());
        //TODO assert

        terminal.getTouchPad().inputCommand(new Lock());
        assertFalse(packageSortingCenter.getSortingMachine().getActive());
        terminal.getTouchPad().inputCommand(new Unlock());
        assertTrue(packageSortingCenter.getSortingMachine().getActive());
        terminal.leave();

        //terminal.register(new Operator(), "test");//TODO real employee
        for (int i = 0; i < 5; i++) {
            terminal.getTouchPad().inputCommand(new Next());
        }
        terminal.leave();

        //terminal.register(new DataAnalyst(), "test");//TODO real employee
        terminal.getTouchPad().inputCommand(new ShowStatistics());
        terminal.leave();
        //TODO assert
    }
}