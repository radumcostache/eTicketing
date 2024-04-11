package service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.io.File;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import model.Ticket;

public class QrGenerator {
//    Map for already generated qrCodes that keeps paths
    Map <String, String> map = new HashMap<String, String>();
    String defaultPath;

    public QrGenerator(String defaultPath) {
        this.defaultPath = defaultPath;
    }

    public String generateQr(Ticket ticket)
            throws WriterException, IOException
    {
        String ticketId = String.valueOf(ticket.getId());
        if (map.containsKey(ticketId)) {
            return map.get(ticketId);
        }
        else {
            BitMatrix matrix = new MultiFormatWriter().encode(
                    new String(ticketId.getBytes(StandardCharsets.US_ASCII), StandardCharsets.US_ASCII),
                    BarcodeFormat.QR_CODE, 10, 10);
            String path = defaultPath + File.separator + ticketId + ".png";
            MatrixToImageWriter.writeToFile(
                    matrix,
                    path.substring(path.lastIndexOf('.') + 1),
                    new File(path));

            map.put(ticketId, path);
            return path;
        }

    }
}
