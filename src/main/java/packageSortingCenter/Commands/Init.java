package packageSortingCenter.Commands;

public class Init implements ICommand {
    CommandType commandType = CommandType.init;
    String[] params;

    public Init() {
        this(null);
    }

    public Init(String[] params) {
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
