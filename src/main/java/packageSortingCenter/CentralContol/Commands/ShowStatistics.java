package packageSortingCenter.CentralContol.Commands;

public class ShowStatistics implements ICommand {
    CommandType commandType = CommandType.show_statistics;
    String[] params;

    public ShowStatistics() {
        this(null);
    }

    public ShowStatistics(String[] params) {
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
