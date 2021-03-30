public class Package {
    private final String id;
    private final char[][][] content;
    private final String zip_code;
    private final PackageType type;
    private final double weight;

    private final char[] idCharPool, contentCharPool;

    public Package(){
        idCharPool = Tools.connectCharPool(Tools.generateCharPool('a', 'z'), Tools.generateCharPool('0', '9'));
        this.id = generateId();
        contentCharPool = Tools.appendCharPool(Tools.appendCharPool(Tools.appendCharPool(Tools.generateCharPool('a', 'z'), ':'), '-'), '!');
        content = generateContent();
        zip_code = generateZipCode();
        type = generateType();
        weight = 1 + Math.random() * 4;
    }

    /*public Package(String csv){
        TODO einlesen aus csv
    }*/

    private String generateId(){
        char[] charId = new char[6];
        for(int i = 0; i < charId.length; i++)
            charId[i] = idCharPool[(int) (Math.random() * idCharPool.length)];
        //TODO check if already used
        return new String(charId);
    }
    private char[][][] generateContent(){
        int l = 25, w = 10, h = 10;
        char[][][] content = new char[l][w][h];
        for(int i = 0; i < l; i++)
            for(int j = 0; j < w; j++)
                for(int k = 0; k < h; k++)
                    content[i][j][k] = contentCharPool[(int) (Math.random() * contentCharPool.length)];
        return content;
    }
    private String generateZipCode(){
        int zipStart = 1067, zipEnd = 99998, zipInt = zipStart + (int) (Math.random() * (zipEnd-zipStart));
        return zipInt < 100000 ? "0" + zipInt : Integer.toString(zipInt);
    }
    private PackageType generateType(){
        double r = Math.random(), percentageNormal = 0.80, percentageExpress = 0.15;
        if(r <= percentageNormal)
            return PackageType.NORMAL;
        else if(r > percentageNormal && r <= percentageNormal + percentageExpress)
            return PackageType.EXPRESS;
        else
            return PackageType.VALUE;
    }

    public String getId(){
        return id;
    }
    public char[][][] getContent() {
        return content;
    }
    public String getZip_code(){
        return zip_code;
    }
    public PackageType getType() {
        return type;
    }
    public double getWeight() {
        return weight;
    }
}
