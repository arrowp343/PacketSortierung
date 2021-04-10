package packageSortingCenter.CentralContol.Commands;

public interface ICommand {
    CommandType getCommandType();

    String[] getParameters();
}
