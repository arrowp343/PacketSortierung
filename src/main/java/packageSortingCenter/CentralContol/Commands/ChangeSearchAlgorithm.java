package packageSortingCenter.CentralContol.Commands;

public class ChangeSearchAlgorithm implements ICommand {
    CommandType commandType = CommandType.change_search_algorithm;
    String[] params;

    public ChangeSearchAlgorithm() {
        this(null);
    }

    public ChangeSearchAlgorithm(String[] params) {
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
