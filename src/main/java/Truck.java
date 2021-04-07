public class Truck {
    private final String id;
    private Trailer trailer;

    public Truck(Trailer trailer){
        id = generateId();
        this.trailer = trailer;
    }

    /*public Package(String csv){
        TODO einlesen aus csv
    }*/

    public static String generateId() {
        char[] charId = new char[4];
        char[] idCharPool = Tools.connectCharPool(Tools.generateCharPool('a', 'z'), Tools.generateCharPool('0', '9'));
        for (int i = 0; i < charId.length; i++)
            charId[i] = idCharPool[(int) (Math.random() * idCharPool.length)];
        //TODO check if already used
        return new String(charId);
    }

    public String getId(){
        return id;
    }
    public Trailer getTrailer(){
        return trailer;
    }
    public void setTrailer(Trailer trailer){
        this.trailer = trailer;
    }
}
