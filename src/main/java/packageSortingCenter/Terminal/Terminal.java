package packageSortingCenter.Terminal;

import packageSortingCenter.CentralContol.CentralControlUnit;
import packageSortingCenter.CentralContol.CommandProxy;
import packageSortingCenter.Commands.ICommand;
import roles.Employee;

public class Terminal { //TODO GUI?
    private final IDCardReader idCardReader;
    private Employee employee;
    private final CommandProxy commandProxy;
    private final TouchPad touchPad;
    private final CentralControlUnit centralControlUnit;

    public Terminal(CentralControlUnit centralControlUnit) {
        idCardReader = new IDCardReader();
        commandProxy = new CommandProxy(centralControlUnit);
        touchPad = new TouchPad(this);
        this.centralControlUnit = centralControlUnit;
    }

    public void register(Employee employee, String pin) {
        if (idCardReader.read(employee.getIdCard(), pin)) this.employee = employee;
    }

    public void leave() {
        employee = null;
    }

    public boolean sendCommand(ICommand command) {
        return commandProxy.sendCommand(employee, command);
    }

    public TouchPad getTouchPad() {
        return touchPad;
    }
}
