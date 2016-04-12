package com.tamtran.myreceipt.business.tesseract;


import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.tamtran.myreceipt.business.services.impl.ReceiptServiceImpl;
import com.tamtran.myreceipt.common.model.ReceiptItems;
import com.tamtran.myreceipt.common.utils.OSValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept;
import org.bytedeco.javacpp.tesseract.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@Scope("prototype")
public class TessOcr implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private ReceiptItems receiptItems;
    private String previewFile;
    @Autowired
    private ReceiptServiceImpl receiptService;

    public String processedData(String raw) {
        Pattern pattern = Pattern.compile("^([0-9a-zA-Z :./]+ )([$-]?[0-9]+\\.[0-9]+-?)(.*)$", Pattern.MULTILINE);
        Matcher matcher = pattern.matcher(raw);
        String processedData = "";
        while (matcher.find()) {
            processedData = processedData + "|" + (matcher.group(1) + ";" + matcher.group(2));
        }
        return processedData;
    }

    @Override
    public void run() {
        logger.info(Thread.currentThread().getName() + " is running.");
        try {
            if (StringUtils.isNotEmpty(receiptItems.getReceiptPath()) && null != receiptItems) {
                logger.info("Converting receipt {}", receiptItems.getReceiptPath());
                receiptItems.setReceiptItems(processRaw());
                receiptService.updateReceiptItems(receiptItems);
            } else {
                logger.error("Missing file path:{} or receiptItem: {}", receiptItems.getReceiptPath(), receiptItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String processRaw() throws Exception{
        logger.info("OCR-ing file: " + receiptItems.getReceiptPath());
        Integer cropO = receiptItems.getCropO();

        TessBaseAPI api = new TessBaseAPI();
        api.SetPageSegMode(0);
        api.SetVariable("tessedit_char_whitelist", "0123456789,/:-*$#%&;ABCDEFGHJKLMNPQRSTUVWXY");
        // Disable dictionary
        StringGenericVector pars = new StringGenericVector();
        pars.addPut(new STRING("load_system_dawg"));
        pars.addPut(new STRING("load_freq_dawg"));
        pars.addPut(new STRING("load_punc_dawg"));
        pars.addPut(new STRING("load_number_dawg"));
        pars.addPut(new STRING("load_unambig_dawg"));
        pars.addPut(new STRING("load_bigram_dawg"));
        pars.addPut(new STRING("load_fixed_length_dawgs"));
        StringGenericVector parsValues = new StringGenericVector();
        parsValues.addPut(new STRING("0"));
        parsValues.addPut(new STRING("0"));
        parsValues.addPut(new STRING("0"));
        parsValues.addPut(new STRING("0"));
        parsValues.addPut(new STRING("0"));
        parsValues.addPut(new STRING("0"));
        parsValues.addPut(new STRING("0"));


        logger.info("PageSegMode = "+api.GetPageSegMode());
        api.ClearAdaptiveClassifier();

        /*if (OSValidator.isWindows() && api.Init("C:/Tesseract-OCR" , "eng", 0,(ByteBuffer) null, 0, pars, parsValues, false) != 0) {
            throw new Exception("Could not initialize tesseract.");
        } else if (OSValidator.isUnix() && api.Init("/usr/local/share/" , "eng", 0,(ByteBuffer) null, 0, pars, parsValues, false) != 0){
            throw new Exception("Could not initialize tesseract.");
        }*/

        if (OSValidator.isWindows() && api.Init("C:/Tesseract-OCR" , "eng") != 0) {
            throw new Exception("Could not initialize tesseract.");
        } else if (OSValidator.isUnix() && api.Init("/usr/local/share/" , "eng") != 0){
            throw new Exception("Could not initialize tesseract.");
        }
        lept.PIX originalImage = null;
        BytePointer outText = null;
        int orientation = readImageInformation(new File(receiptItems.getReceiptPath()));
        if (cropO != orientation) {
            logger.error("different orientation");
            return "PROCESSED ERROR";
        } else {
            try {
                originalImage = lept.pixRead(receiptItems.getReceiptPath());
                switch (orientation) {
                    case 1: default: {
                        //do nothing
                        int left;
                        int top;
                        int height;
                        int width;
                        float wScale = (float) originalImage.w()/receiptItems.getImgW();
                        float hScale = (float) originalImage.h()/receiptItems.getImgH();
                        if (receiptItems.getCropW() == 0 && receiptItems.getCropH() == 0) {
                            originalImage = lept.pixBackgroundNormSimple(originalImage, null , null);
                            api.SetImage(originalImage);
                        } else {
                            logger.info("ios = {}, wScale = {}, hScale = {}", receiptItems.isIos(), wScale, hScale);
                            left = Math.round(receiptItems.getCropX() * wScale);
                            top = Math.round(receiptItems.getCropY() * hScale);
                            width = Math.round(receiptItems.getCropW() * wScale);
                            height = Math.round(receiptItems.getCropH() * hScale);
                            logger.debug("left = {}, top = {}, width = {}, height = {}", left, top, width, height);
                            lept.BOX box = lept.boxCreate(left,top,width,height);
                            lept.PIX crop = lept.pixClipRectangle(originalImage, box, (lept.BOX) null);
                            api.SetImage(crop);
                            lept.pixWrite(previewFile, api.GetInputImage(), lept.IFF_JFIF_JPEG);
                        }
                        break;
                    }
                    case 8: {
                        logger.info("rotate img to -90 degrees");
                        originalImage = lept.pixBackgroundNormSimple(originalImage, null , null);
                        originalImage = lept.pixRotate90(originalImage, -1);
                        api.SetImage(originalImage);
                        break;
                    }
                    case 6:
                    {
                        int left;
                        int top;
                        int height;
                        int width;
                        logger.info("rotate img to 90 degrees");
                        float wScale = (float) originalImage.w()/receiptItems.getImgW();
                        float hScale = (float) originalImage.h()/receiptItems.getImgH();
                        if (receiptItems.getCropW() == 0 && receiptItems.getCropH() == 0) {
                            originalImage = lept.pixBackgroundNormSimple(originalImage, null , null);
                            originalImage = lept.pixRotate90(originalImage, 1);
                            api.SetImage(originalImage);
                        } else if (receiptItems.isIos()) {
                            //imgW of ios will be imgH and vice versa because preview image is already rotated
                            wScale = (float) originalImage.w()/receiptItems.getImgH();
                            hScale = (float) originalImage.h()/receiptItems.getImgW();
                            logger.info("ios = {}, wScale = {}, hScale = {}", receiptItems.isIos(), wScale, hScale);
                            originalImage = lept.pixRotate90(originalImage, 1);
                            left = Math.round(receiptItems.getCropX() * wScale);
                            top = Math.round(receiptItems.getCropY() * hScale);
                            width = Math.round(receiptItems.getCropW() * wScale);
                            height = Math.round(receiptItems.getCropH() * hScale);
                            logger.debug("left = {}, top = {}, width = {}, height = {}", left, top, width, height);
                            lept.BOX box = lept.boxCreate(left,top,width,height);
                            lept.PIX crop = lept.pixClipRectangle(originalImage, box, (lept.BOX) null);
                            api.SetImage(crop);
                            lept.pixWrite(previewFile, api.GetInputImage(), lept.IFF_JFIF_JPEG);
                        } else {
                            logger.info("ios = {}, wScale = {}, hScale = {}", receiptItems.isIos(), wScale, hScale);
                            /*left = originalImage.w() - Math.round(receiptItems.getCropY2() * scale);
                            top = Math.round(receiptItems.getCropX() * scale);
                            width = Math.round(receiptItems.getCropH() * scale);
                            height = Math.round(receiptItems.getCropW() * scale);*/
                            left = Math.round(receiptItems.getCropX() * wScale);
                            top = Math.round(receiptItems.getCropY() * hScale);
                            width = Math.round(receiptItems.getCropW() * wScale);
                            height = Math.round(receiptItems.getCropH() * hScale);
                            logger.debug("left = {}, top = {}, width = {}, height = {}", left, top, width, height);
                            lept.BOX box = lept.boxCreate(left,top,width,height);
                            lept.PIX crop = lept.pixClipRectangle(originalImage, box, (lept.BOX) null);
                            crop = lept.pixRotate90(crop, 1);
                            api.SetImage(crop);
                            lept.pixWrite(previewFile, api.GetInputImage(), lept.IFF_JFIF_JPEG);
                        }
                        break;
                    }
                    case 3:
                    {
                        logger.info("rotate img to 180 deg");
                        originalImage = lept.pixBackgroundNormSimple(originalImage, null , null);
                        originalImage = lept.pixRotate180(null, originalImage);
                        api.SetImage(originalImage);
                        break;
                    }
                }
                outText = api.GetUTF8Text();
                logger.info("average confidence: " + api.MeanTextConf());
                String string = outText.getString("UTF-8");
                if (string != null) {
                    string = string.trim();
                    string = string.replaceAll("[^\\p{ASCII}+]", ""); // remove all characters which not in ascii range
//                string = string.replaceAll("[^\\w@$\\n]{3,6}", ""); // clean up character which is not a word and repeat from 3-6 times. exclude new line.
                }
                logger.info(string);
                return string;
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("charset", e);
            } finally {
                if (outText != null) {
                    outText.deallocate();
                }
                if (originalImage != null) {
                    lept.pixDestroy(originalImage);
                }
                if (api != null) {
                    logger.info("tesseract API end");
                    api.End();
                }
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
            if (null != directory) {
                orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
            } else {
                logger.info("Cannot get orientation of this image, defaulting to 1");
            }

//            height = directory.getInt(ExifIFD0Directory.TAG_IMAGE_HEIGHT);
//            width = directory.getInt(ExifIFD0Directory.TAG_IMAGE_WIDTH);
        } catch (MetadataException me) {
            logger.info("Could not get orientation");
        }
//        int width = jpegDirectory.getImageWidth();
//        int height = jpegDirectory.getImageHeight();

        logger.info("Image orientation = "+orientation);

        return orientation;
    }

    public void setReceiptItems(ReceiptItems receiptItems) {
        this.receiptItems = receiptItems;
    }

    public void setPreviewFile(String previewFile) {
        logger.info("Preview file path = {}", previewFile);
        this.previewFile = previewFile;
    }
}
