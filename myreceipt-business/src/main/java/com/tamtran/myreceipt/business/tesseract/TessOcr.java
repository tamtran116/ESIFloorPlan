package com.tamtran.myreceipt.business.tesseract;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.jpeg.JpegDirectory;
import com.tamtran.myreceipt.common.utils.OSValidator;
import net.coobird.thumbnailator.Thumbnails;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract.*;
import org.w3c.dom.Node;

import javax.imageio.ImageIO;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
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
        api.SetPageSegMode(0);
        System.out.println("PageSegMode = "+api.GetPageSegMode());
        api.ClearAdaptiveClassifier();

        if (OSValidator.isWindows() && api.Init("C:/Tesseract-OCR" , "eng") != 0) {
            throw new Exception("Could not initialize tesseract.");
        } else if (OSValidator.isUnix() && api.Init("/usr/local/share/" , "eng") != 0){
            throw new Exception("Could not initialize tesseract.");
        }
        lept.PIX image = null;
        BytePointer outText = null;
        int orientation = readImageInformation(new File(file));
        try {
            image = lept.pixRead(file);
            switch (orientation) {
                case 1: default: {
                    //do nothing
                    break;
                }
                case 8: {
                    System.out.println("rotate img to -90 degrees");
                    image = lept.pixRotate90(image, -1);
                    break;
                }
                case 6:
                {
                    System.out.println("rotate img to 90 degrees");
                    image = lept.pixRotate90(image, 1);
                    break;
                }
                case 3:
                {
                    System.out.println("rotate img to 180 deg");
                    image = lept.pixRotate180(null, image);
                    break;
                }
            }
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

    private int readImageInformation(File imageFile)  throws IOException, MetadataException, ImageProcessingException {
        Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
        Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
//        JpegDirectory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);

        int orientation = 1;
//        int width = 0;
//        int height = 0;
        try {
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
//            height = directory.getInt(ExifIFD0Directory.TAG_IMAGE_HEIGHT);
//            width = directory.getInt(ExifIFD0Directory.TAG_IMAGE_WIDTH);
        } catch (MetadataException me) {
            System.out.println("Could not get orientation");
        }
//        int width = jpegDirectory.getImageWidth();
//        int height = jpegDirectory.getImageHeight();

        System.out.println("Image orientation = "+orientation);

        return orientation;
    }
}
