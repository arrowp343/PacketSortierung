import java.io.*;
import java.util.ArrayList;

public class CSV {

    private static final int amountPackages = 24000;
    private static ArrayList<String> packageList, boxList, palletList, trailerList;

    public static void writePackageCSV(Package p) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(p.getId()).append(",");
        char[][][] content = p.getContent();
        for (int i = 0; i < Configuration.l; i++)
            for (int j = 0; j < Configuration.w; j++)
                for (int k = 0; k < Configuration.h; k++)
                    stringBuilder.append(content[i][j][k]);
        stringBuilder.append(",").append(p.getZip_code()).append(",")
                .append(p.getType()).append(",")
                .append(p.getWeight()).append("\n");
        writeInCSV("base_package.csv", stringBuilder.toString());
    }
    public static void writeBoxCSV(Box[] boxes) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("box_id,");
        for(int i = 0; i < Configuration.maxPackagesInBox; i++){
            stringBuilder.append("p_id").append(i);
            if(i != Configuration.maxPackagesInBox - 1)
                stringBuilder.append(",");
            else
                stringBuilder.append("\n");
        }
        for(Box b: boxes){
            stringBuilder.append(b.getId()).append(",");
            Package[] p = b.getPackages();
            for(int i = 0; i < p.length; i++) {
                stringBuilder.append(p[i].getId());
                if (i != Configuration.maxPackagesInBox - 1)
                    stringBuilder.append(",");
                else
                    stringBuilder.append("\n");
            }
        }
        writeInCSV("base_box.csv", stringBuilder.toString());
    }
    public static void writePalletCSV(Pallet[] pallets) throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("pallet_id,position,level,box_id\n");
        for (Pallet pallet : pallets) {            //for each pallet
            Box[][] boxes = pallet.getBoxes();
            for (int j = 0; j < boxes.length; j++) {          //for each position
                for (int k = 0; k < boxes[j].length; k++) {   //for each level
                    String pallet_id = pallet.getId(),
                            position = String.valueOf(j),
                            level = String.valueOf(k),
                            box_id = boxes[j][k].getId();
                    stringBuilder.append(pallet_id).append(",");
                    stringBuilder.append(position).append(",");
                    stringBuilder.append(level).append(",");
                    stringBuilder.append(box_id).append("\n");
                }
            }
        }
        writeInCSV("base_pallet.csv", stringBuilder.toString());
    }
    private static void writeInCSV(String fileName, String line) throws IOException {
        String directoryName = "csv";
        File directory = new File(directoryName);
        if (!directory.exists())
            directory.mkdir();
        String filePath = directoryName + System.getProperty("file.separator") + fileName;
        File file = new File(filePath);
        FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(line);
        bw.close();
    }

    /*public static Package[] readPackageFromCSV(){
        packageList = readFromCSV("csv/base_package.csv");
        Package[] packages = new Package[packageList.size()];
        for(int i = 0; i < packages.length; i++)
            packages[i] = new Package(packageList.get(i));
        return packages;
    }
    public static Box[] readBoxFromCSV() throws IOException{
        ArrayList<String> csv = readFromCSV("csv/base_box.csv");
        Box[] boxes = new Box[csv.size()];
        for(int i = 0; i < boxes.length; i++)
            boxes[i] = new Box(csv.get(i));
        return boxes;
    }
*/  // evtl überflüssig, weil nicht 24k Pakete gleichzeitig geladen werden können

    private static ArrayList<String> readFromCSV(String fileName) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        reader.readLine();      //skip first line
        String line;
        while ((line = reader.readLine()) != null)
            lines.add(line);
        reader.close();

        return lines;
    }
    public static ArrayList<String> readFromCSV(String fileName, int from, int to) throws IOException{
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        reader.readLine();      //skip first line
        for(int i = 0; i < from; i++)
            reader.readLine(); //skip lines from 0 to start
        String line;
        for(int i = 0; i <= to-from; i++)
            if((line = reader.readLine()) != null)
                lines.add(line);
        reader.close();
        return lines;
    }
    public static ArrayList<String> readNth1000FromCSV(String fileName, int n){
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();      //skip first line
            for(int i = 0; i < 1000 * (n+1); i++)
                reader.readLine();      //skip first 1000 * (n+1) lines
            String line;
            for(int i = 0; i < 1000; i++)
                if((line = reader.readLine()) != null)
                    lines.add(line);
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return lines;
    }
    public static String readFromCSVById(String obj, String id) throws Exception{        // TODO evtl andere lösung als string zu übergeben
        ArrayList<String> lines = switch (obj) {
            case "package" -> packageList;
            case "box" -> boxList;
            case "pallet" -> palletList;
            default -> throw new Exception("readFromCSVbyId: Object " + obj + " not valid;");
        };
        if(lines != null)
            for (String line : lines)
                if (line.split(",")[0].equals(id))
                    return line;
        throw new Exception("readFromCSVbyId: id " + id + " not found in CSV");
    }

    public static void reset(){
        System.out.println("-- CSV Reset --");
        File folder = new File("csv");
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                f.delete();
            }
        }
        folder.delete();
    }

    public static void initPackages() throws IOException{
        System.out.print("Initializing Packages...");
        writeInCSV("base_package.csv", "id,content,zip_code,type,weigth\n");
        int count = amountPackages;
        while (count > 0) {
            Package p;
            p = new Package();
            CSV.writePackageCSV(p);
            count--;
        }
        System.out.println("\t[Done]");
    }
    public static void initBoxes() throws IOException{
        System.out.print("Initializing Boxes...");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("box_id,");
        for(int i = 0; i < Configuration.maxPackagesInBox; i++){
            stringBuilder.append("p_id").append(i);
            if(i != Configuration.maxPackagesInBox - 1)
                stringBuilder.append(",");
            else
                stringBuilder.append("\n");
        }
        int count = 0;
        ArrayList<String> lines;
        do {
            int from = Configuration.maxPackagesInBox * count;
            int to = from + Configuration.maxPackagesInBox - 1;
            lines = readFromCSV("csv/base_package.csv", from, to);      //TODO evtl alle 24.000 zeichen lesen?
            if(lines.size() == 0) break;
            stringBuilder.append(Box.generateId()).append(",");
            for (String line : lines) {
                stringBuilder.append(line.split(",")[0]).append(",");   //append package_id
            }
            stringBuilder.delete(stringBuilder.length()-1, stringBuilder.length()); //delete last ","
            stringBuilder.append("\n");
            count++;
        } while(lines.size() != 0);
        writeInCSV("base_box.csv", stringBuilder.toString());   // header
        System.out.println("\t\t[Done]");
    }
    public static void initPallets() throws IOException{
        System.out.print("Initializing Pallets...");
        writeInCSV("base_pallet.csv", "pallet_id,position,level,box_id\n");
        ArrayList<String> boxes = readFromCSV("csv/base_box.csv");
        StringBuilder pallets = new StringBuilder();
        int id = 1, pos = 0, level = 0;
        for (String box : boxes) {
            String pallet = id + "," +
                    pos + "," +
                    level + "," +
                    box.split(",")[0] + "\n";
            pallets.append(pallet);
            if (++pos >= Configuration.amountPositionsOnPallet) {
                pos = 0;
                if (++level >= Configuration.amountLevelsOnPallet) {
                    level = 0;
                    id = ++Pallet.lastId;
                }
            }
        }
        writeInCSV("base_pallet.csv", pallets.toString());
        System.out.println("\t\t[Done]");
    }
    public static void initTrucks(){
        System.out.print("Initializing Trucks...");

        System.out.println("\t\t[Not implemented yet]");


        //        System.out.println("\t\t[Done]");
    }

    public static void main(String[] args) {
        try {
            reset();
            initPackages();
            initBoxes();
            initPallets();
            initTrucks();
        } catch (Exception e){
            System.out.println("\t[Error] at CSV.main: \n" + e.getMessage());
        }
    }
}