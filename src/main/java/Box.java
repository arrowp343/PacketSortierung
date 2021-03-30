public class Box {
    private final String id;
    private final Package[] packages;

    private final char[] idCharPool;

    public Box(Package[] packages){
        idCharPool = Tools.connectCharPool(Tools.generateCharPool('a', 'z'), Tools.generateCharPool('0', '9'));
        id = generateId();
        this.packages = packages;
    }

    /*public Box(String csv){
        TODO einlesen aus csv
    }*/

    private String generateId(){
        char[] charId = new char[6];
        for(int i = 0; i < charId.length; i++)
            charId[i] = idCharPool[(int) (Math.random() * idCharPool.length)];
        //TODO check if already used
        return new String(charId);
    }

    public String getId(){
        return id;
    }
    public Package[] getPackages(){
        return packages;
    }
}
