package packageSortingCenter.Terminal;

import packageSortingCenter.Commands.ICommand;

public class TouchPad {
    Terminal terminal;

    public TouchPad(Terminal terminal) {
        this.terminal = terminal;
    }

    public void inputCommand(ICommand command) {
        terminal.sendCommand(command);
    }
}