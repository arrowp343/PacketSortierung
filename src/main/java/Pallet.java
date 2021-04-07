public class Pallet {
    public static int lastId = 1;
    private final String id;
    private Box[][] boxes;      //[position][level]

    public Pallet(Box[][] boxes){
        id = String.valueOf(++lastId);
        this.boxes = boxes;
    }

    public Pallet(String id){
        this.id = id;
        boxes = new Box[Configuration.amountPositionsOnPallet][Configuration.amountLevelsOnPallet];
    }

    public String getId(){
        return id;
    }
    public Box[][] getBoxes(){
        return boxes;
    }
    public void addBox(Box box, int position, int level) throws ArrayIndexOutOfBoundsException{
        boxes[position][level] = box;
    }
}
