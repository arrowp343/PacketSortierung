public class Pallet {
    static int lastId;
    private final String id;
    private Box[][] boxes;      //[position][level]

    public Pallet(Box[][] boxes){
        id = String.valueOf(++lastId);
        this.boxes = boxes;
    }

    public Pallet(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
    public Box[][] getBoxes(){
        return boxes;
    }
    public void addBox(Box box){

    }
}
