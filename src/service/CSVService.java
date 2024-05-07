package service;

import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.sql.Timestamp;

public class CSVService {
    FileWriter csvOutput;
    public CSVService (String filePath) {
       File fileCsvOutput = new File(filePath);
        try {
            fileCsvOutput.createNewFile();
            this.csvOutput = new FileWriter(fileCsvOutput);


        } catch (IOException e) {
            System.out.println("Eroarea la deschiderea fisierului csv");
            e.printStackTrace();
        }
    }

    public void printAction(String action) {
        try {
            csvOutput.write(action + "," + new Timestamp(System.currentTimeMillis()) + "\n");
            csvOutput.flush();
        } catch (IOException e) {
            System.out.println("Eroarea la scrierea fisierului csv");
        }
    }
}
