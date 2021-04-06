public class Pallet {
    static int lastId;
    private final String id;
    private Box[][][] boxes;

    public Pallet(Box[][][] boxes){
        id = String.valueOf(++lastId);
        this.boxes = boxes;
    }

    /*public Pallet(String csv){
        TODO einlesen aus csv
    }*/

    public String getId(){
        return id;
    }
    public Box[][][] getBoxes(){
        return boxes;
    }
}
