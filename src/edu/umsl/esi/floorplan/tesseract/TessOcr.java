package edu.umsl.esi.floorplan.tesseract;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract.*;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TessOcr {

    public TessOcr(){}

    /*public static void main(String[] args) {
        TessOcr tessOcr = new TessOcr();
        String raw = tessOcr.processRaw("C:/Tesseract-OCR/FullSizeRender1.jpg");
        System.out.println(raw);
    }*/

    public String processedData(String raw) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z :./]+ )([$-]?[0-9]+\\.[0-9]+-?)(.*)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(raw);
        String processedData = "";
        while (matcher.find()) {
            processedData = processedData + "|" + (matcher.group(1) + ";" + matcher.group(2));
        }
        return processedData;
    }

    public String processRaw(String file) {
        TessBaseAPI api = new TessBaseAPI();
        api.SetVariable("tessedit_char_whitelist", "0123456789,/ABCDEFGHJKLMNPQRSTUVWXY");
//        if (api.Init("/usr/local/share/" , "eng") != 0) {
        if (api.Init("C:/Tesseract-OCR" , "eng") != 0) {
            throw new RuntimeException("Could not initialize tesseract.");
        }

        lept.PIX image = null;
        BytePointer outText = null;
        try {
            image = lept.pixRead(file);
            api.SetImage(image);
            outText = api.GetUTF8Text();
            String string = outText.getString("UTF-8");
            if (string != null) {
                string = string.trim();
            }
            return string;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset", e);
        } finally {
            if (outText != null) {
                outText.deallocate();
            }
            if (image != null) {
                lept.pixDestroy(image);
            }
            if (api != null) {
                api.End();
            }
        }
    }
}
