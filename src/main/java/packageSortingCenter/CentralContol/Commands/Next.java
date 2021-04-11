package packageSortingCenter.CentralContol.Commands;

public class Next implements ICommand {
    CommandType commandType = CommandType.next;
    String[] params;

    public Next() {
        this(null);
    }

    public Next(String[] params) {
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
