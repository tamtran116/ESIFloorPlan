package com.tamtran.myreceipt.business.tesseract;


import com.tamtran.myreceipt.common.utils.OSValidator;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract.*;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TessOcr {

    public String processedData(String raw) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z :./]+ )([$-]?[0-9]+\\.[0-9]+-?)(.*)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(raw);
        String processedData = "";
        while (matcher.find()) {
            processedData = processedData + "|" + (matcher.group(1) + ";" + matcher.group(2));
        }
        return processedData;
    }

    public String processRaw(String file) throws Exception{
        TessBaseAPI api = new TessBaseAPI();
        api.SetVariable("tessedit_char_whitelist", "0123456789,/ABCDEFGHJKLMNPQRSTUVWXY");
        if (OSValidator.isWindows() && api.Init("C:/Tesseract-OCR" , "eng") != 0) {
            throw new Exception("Could not initialize tesseract.");
        } else if (OSValidator.isUnix() && api.Init("/usr/local/share/" , "eng") != 0){
            throw new Exception("Could not initialize tesseract.");
        }
        lept.PIX image = null;
        BytePointer outText = null;
        try {
            image = lept.pixRead(file);
            api.SetImage(image);
            outText = api.GetUTF8Text();
            String string = outText.getString("UTF-8");
//            String stringResult = string.replaceAll("[^\\w\n\\.\\*$;: ]","");
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
