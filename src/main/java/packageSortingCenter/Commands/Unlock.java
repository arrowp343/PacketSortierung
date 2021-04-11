package packageSortingCenter.Commands;

public class Unlock implements ICommand {
    CommandType commandType = CommandType.unlock;
    String[] params;

    public Unlock() {
        this(null);
    }

    public Unlock(String[] params) {
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
