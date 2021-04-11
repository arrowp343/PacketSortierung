package packageSortingCenter.CentralContol;

import packageSortingCenter.Commands.ICommand;
import roles.Administrator;
import roles.Employee;
import roles.Operator;
import roles.SuperVisor;

public class CommandProxy {
    CentralControlUnit centralControlUnit;

    public CommandProxy(CentralControlUnit centralControlUnit) {
        this.centralControlUnit = centralControlUnit;
    }

    public boolean sendCommand(Employee employee, ICommand command) {
        if (employee != null) {
            Class aClass = employee.getClass();
            if (aClass == SuperVisor.class) {
                centralControlUnit.getEventBus().post(command);
                return true;
            } else {
                switch (command.getCommandType()) {
                    case next -> {
                        if (aClass == Operator.class) {
                            centralControlUnit.getEventBus().post(command);
                        } else return false;
                    }
                    case shutdown -> {
                        if (aClass == Administrator.class) {
                            centralControlUnit.getEventBus().post(command);
                        } else return false;
                    }
                    case show_statistics -> centralControlUnit.getEventBus().post(command);
                    default -> {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
