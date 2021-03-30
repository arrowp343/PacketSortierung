public class Pallet {
    static int lastId;
    private final int id;
    private Box[][][] boxes;

    public Pallet(Box[][][] boxes){
        id = ++lastId;
        this.boxes = boxes;
    }

    /*public Pallet(String csv){
        TODO einlesen aus csv
    }*/

    public int getId(){
        return id;
    }
    public Box[][][] getBoxes(){
        return boxes;
    }
}
