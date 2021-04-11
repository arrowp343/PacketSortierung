package packageSortingCenter.Commands;

public interface ICommand {
    CommandType getCommandType();

    String[] getParameters();
}
