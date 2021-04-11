package packageSortingCenter.Commands;

public class Shutdown implements ICommand {
    CommandType commandType = CommandType.shutdown;
    String[] params;

    public Shutdown() {
        this(null);
    }

    public Shutdown(String[] params) {
        this.params = params;
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }

    @Override
    public String[] getParameters() {
        return params;
    }
}
