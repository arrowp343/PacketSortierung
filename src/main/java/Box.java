public class Box {
    private final String id;
    private final Package[] packages;

    public Box(Package[] packages){
        id = generateId();
        this.packages = packages;
    }

   /* public Box(String csv){
        String[] split = csv.split(",");
        id = split[0];
        packages = new Package[Configuration.maxPackagesInBox];
        //CSV.readPackageFromCSV();
        for(int i = 1; i < split.length; i++){
            try {
                packages[i - 1] = new Package(CSV.readFromCSVById("package", split[i]));
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }*/

    public static String generateId(){
        char[] idCharPool = Tools.connectCharPool(Tools.generateCharPool('a', 'z'), Tools.generateCharPool('0', '9'));
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
