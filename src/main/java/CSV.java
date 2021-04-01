import java.io.*;

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
        Package[] packages = null;
        int countLines = -1;        //skip first line
        try {
            BufferedReader reader = new BufferedReader(new FileReader("csv/base_package.csv"));
            while(reader.readLine() != null) countLines++;
            reader.close();
            reader = new BufferedReader(new FileReader("csv/base_package.csv"));      //reset reader
            packages = new Package[countLines];
            reader.readLine();      //skip first line
            String line;
            for(int i = 0; (line = reader.readLine()) != null; i++)
                packages[i] = new Package(line);
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
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
}
