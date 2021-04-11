package packageSortingCenter.Commands;

public class Lock implements ICommand {
    CommandType commandType = CommandType.lock;
    String[] params;

    public Lock() {
        this(null);
    }

    public Lock(String[] params) {
        this.params = params;
    }

    @Override
    public CommandType getCommandType() {
        return commandType;
    }

    @Override
    public String[] getParameters() {
        return null;
    }
}
