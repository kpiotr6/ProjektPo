package logic;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class CSVWritter {
    private File file;
    private BufferedWriter writer;
    private boolean opened=false;
    public CSVWritter(){
        file = new File("stats.csv");
        int i =1;
        while(file.exists() && !file.isDirectory()){
            file = new File("stats"+i+".csv");
            i++;
        }
    }
    public void open() throws IOException {
        writer = new BufferedWriter(new FileWriter(file));
        opened = true;
    }
    public void writeline(String[] list) throws IOException {
        if(!opened){
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String s:list) {
            builder.append(s);
            builder.append(";");
        }
        builder.deleteCharAt(builder.length()-1);
        builder.append("\n");
//        System.out.println(builder.toString());
        writer.write(builder.toString());
    }
    public void close() throws IOException {
        writer.close();
        opened = false;
    }
}
