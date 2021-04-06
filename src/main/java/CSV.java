import java.io.*;
import java.util.ArrayList;

public class CSV {
    private static ArrayList<String> packageList, boxList, palletList, trailerList;

    public static void writePackageInCSV(Package[] packages) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("id,content,zip_code,type,weigth\n");
        for(Package p: packages){
            stringBuilder.append(p.getId()).append(",");
            char[][][] content = p.getContent();
            for(int i = 0; i < Configuration.l; i++)
                for(int j = 0; j < Configuration.w; j++)
                    for(int k = 0; k < Configuration.h; k++)
                        stringBuilder.append(content[i][j][k]);
            stringBuilder.append(",").append(p.getZip_code()).append(",")
                    .append(p.getType()).append(",")
                    .append(p.getWeight()).append("\n");
        }
        writeInCSV("base_package.csv", stringBuilder.toString());
    }
    public static void writeBoxInCSV(Box[] boxes) throws IOException{
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
    public static void writePalletInCSV(Pallet[] pallets) throws IOException{
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

    public static Package[] readPackageFromCSV(){
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

    private static ArrayList<String> readFromCSV(String fileName){
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            reader.readLine();      //skip first line
            String line;
            while((line = reader.readLine()) != null)
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
}