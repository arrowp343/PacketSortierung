package csv;

import config.Configuration;

public class Package {
    private final String id;
    private final char[][][] content;
    private final String zip_code;
    private final PackageType type;
    private final double weight;

    private char[] idCharPool, contentCharPool;

    public Package(boolean explosive) {
        idCharPool = Tools.connectCharPool(Tools.generateCharPool('a', 'z'), Tools.generateCharPool('0', '9'));
        this.id = generateId();
        contentCharPool = Tools.appendCharPool(Tools.appendCharPool(Tools.appendCharPool(Tools.generateCharPool('a', 'z'), ':'), '-'), '!');
        content = generateContent(explosive);
        zip_code = generateZipCode();
        type = generateType();
        weight = 1 + Math.random() * 4;
    }

    public Package(String csv) {
        String[] split = csv.split(",");
        id = split[0];
        content = new char[Configuration.l][Configuration.w][Configuration.h];
        int count = 0;
        for (int i = 0; i < Configuration.l; i++)
            for (int j = 0; j < Configuration.w; j++)
                for (int k = 0; k < Configuration.h; k++)
                    content[i][j][k] = split[1].charAt(count++);
        zip_code = split[2];
        type = PackageType.valueOf(split[3]);
        weight = Double.parseDouble(split[4]);
    }

    private String generateId() {
        char[] charId = new char[6];
        for (int i = 0; i < charId.length; i++)
            charId[i] = idCharPool[(int) (Math.random() * idCharPool.length)];
        //TODO check if already used
        return new String(charId);
    }

    private char[][][] generateContent(boolean explosive) {
        char[][][] content = new char[Configuration.l][Configuration.w][Configuration.h];
        for (int i = 0; i < Configuration.l; i++)
            for (int j = 0; j < Configuration.w; j++)
                for (int k = 0; k < Configuration.h; k++)
                    content[i][j][k] = contentCharPool[(int) (Math.random() * contentCharPool.length)];
        if (explosive) {
            char[] explosiveContent = "exp!os:ve".toCharArray();
            int l = (int) (Math.random() * (Configuration.l - explosiveContent.length)),
                    w = (int) (Math.random() * Configuration.w),
                    h = (int) (Math.random() * Configuration.h);
            for (int i = 0; i < explosiveContent.length; i++)
                content[l + i][w][h] = explosiveContent[i];
        }
        return content;
    }

    private String generateZipCode() {
        int zipStart = 1067, zipEnd = 99998, zipInt = zipStart + (int) (Math.random() * (zipEnd - zipStart));
        return zipInt < 10000 ? "0" + zipInt : Integer.toString(zipInt);
    }

    private PackageType generateType() {
        double r = Math.random(), percentageNormal = 0.80, percentageExpress = 0.15;
        if (r <= percentageNormal)
            return PackageType.NORMAL;
        else if (r > percentageNormal && r <= percentageNormal + percentageExpress)
            return PackageType.EXPRESS;
        else
            return PackageType.VALUE;
    }

    public String getId() {
        return id;
    }

    public char[][][] getContent() {
        return content;
    }

    public String getZip_code() {
        return zip_code;
    }

    public PackageType getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }

    public PackageType getPackageType() {
        return type;
    }
}
