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
        int[] e = new int[Configuration.amountExplosives];
        for(int i = 0; i < e.length; i++){
            e[i] = (int) (Math.random() * amountPackages);
            for(int j = 0; j < i; j++)
                if (e[i] == e[j]) {
                    i = -1;
                    break;
                }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id,content,zip_code,type,weigth\n");
        int count = 0;
        while (count < amountPackages) {
            boolean explosive = false;
            for (int value : e) {
                if (count == value) {
                    explosive = true;
                    break;
                }
            }
            Package p = new Package(explosive);
            stringBuilder.append(p.getId()).append(",");
            char[][][] content = p.getContent();
            for (int i = 0; i < Configuration.h; i++)
                for (int j = 0; j < Configuration.w; j++)
                    for (int k = 0; k < Configuration.l; k++)
                        stringBuilder.append(content[k][j][i]);
            stringBuilder.append(",").append(p.getZip_code()).append(",")
                    .append(p.getType()).append(",")
                    .append(p.getWeight()).append("\n");
            if(++count % 1000 == 0) {
                writeInCSV("base_package.csv", stringBuilder.toString());
                stringBuilder = new StringBuilder();
            }
        }
        if(stringBuilder.length() != 0)
            writeInCSV("base_package.csv", stringBuilder.toString());
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
        BufferedReader reader = new BufferedReader(new FileReader("csv/base_package.csv"));
        reader.readLine();      //skip first line
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null) {
            if(count == 0)
                stringBuilder.append(Box.generateId()).append(",");
            stringBuilder.append(line.split(",")[0]);
            if (++count < Configuration.maxPackagesInBox)
                stringBuilder.append(",");
            else {
                stringBuilder.append("\n");
                count = 0;
            }
        }
        reader.close();
        writeInCSV("base_box.csv", stringBuilder.toString());
        System.out.println("\t\t[Done]");
    }
    public static void initPallets() throws IOException{
        System.out.print("Initializing Pallets...");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("pallet_id,position,level,box_id\n");

        BufferedReader reader = new BufferedReader(new FileReader("csv/base_box.csv"));
        reader.readLine();      //skip first line
        String line;
        int id = 1, pos = 0, level = 0;
        while ((line = reader.readLine()) != null){
            String pallet = id + "," +
                    pos + "," +
                    level + "," +
                    line.split(",")[0] + "\n";
            stringBuilder.append(pallet);
            if (++pos >= Configuration.amountPositionsOnPallet) {
                pos = 0;
                if (++level >= Configuration.amountLevelsOnPallet) {
                    level = 0;
                    id = ++Pallet.lastId;
                }
            }
        }
        writeInCSV("base_pallet.csv", stringBuilder.toString());
        System.out.println("\t\t[Done]");
    }
    public static void initTrucks() throws IOException{
        System.out.print("Initializing Trucks...");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("truck_id,side,position,pallet_id\n");

        BufferedReader reader = new BufferedReader(new FileReader("csv/base_pallet.csv"));
        reader.readLine();      //skip first line
        String line, truck_id = Truck.generateId(), pallet_id = "";
        int pos = 0;
        while ((line = reader.readLine()) != null){
            if(pallet_id.equals(line.split(",")[0])) continue;
            else pallet_id = line.split(",")[0];
            stringBuilder.append(truck_id).append(",")
                         .append(pos%2==0 ? "left" : "right").append(",")
                         .append(pos / 2).append(",")
                         .append(pallet_id).append("\n");
            if(++pos >= Configuration.maxPalletsInTruck){
                pos = 0;
                truck_id = Truck.generateId();
            }
        }
        reader.close();
        writeInCSV("base_truck.csv", stringBuilder.toString());
        //System.out.println("\t\t[Not implemented yet]");
        System.out.println("\t\t[Done]");
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