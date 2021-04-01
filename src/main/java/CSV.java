import java.io.*;
import java.util.ArrayList;

public class CSV {
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
    public static Package[] readPackageFromCSV(){
        ArrayList<String> csv = readFromCSV("csv/base_package.csv");
        Package[] packages = new Package[csv.size()];
        for(int i = 0; i < packages.length; i++)
            packages[i] = new Package(csv.get(i));
        return packages;
    }
    private static void writeInCSV(String fileName, String line) throws IOException {
        String directoryName = "csv";
        File directory = new File(directoryName);
        if (!directory.exists())
            directory.mkdir();
        String filePath = directoryName + System.getProperty("file.separator") + fileName;
        File file = new File(filePath);
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
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
}