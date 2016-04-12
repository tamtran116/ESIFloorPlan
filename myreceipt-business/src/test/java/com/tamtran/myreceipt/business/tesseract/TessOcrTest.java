package com.tamtran.myreceipt.business.tesseract;

import com.tamtran.myreceipt.common.model.ReceiptItems;
import org.junit.Test;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Iterator;

import org.w3c.dom.*;

import java.io.*;
import java.util.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

public class TessOcrTest {

//    @Test
    public void processRaw() throws Exception {
        TessOcr r = new TessOcr();
        ReceiptItems receiptItems = new ReceiptItems();
        receiptItems.setReceiptPath("C:\\tmp\\myreceipt\\test\\IMG_2203.JPG");
        receiptItems.setCropX(469);
        receiptItems.setCropY(645);
        receiptItems.setCropX2(0);
        receiptItems.setCropY2(0);
        receiptItems.setCropW(0);
        receiptItems.setCropH(0);
        receiptItems.setImgW(4032);
        receiptItems.setImgH(3024);
        receiptItems.setCropO(6);
        receiptItems.setIos(false);
        r.setReceiptItems(receiptItems);
//        r.setFilePath("C:\\tmp\\aa928b8e-8c73-4b97-a786-0957707479df.jpeg");
//        r.setFilePath("C:\\Users\\Tam\\Pictures\\IMG_2203.JPG");
        System.out.println(r.processRaw());

//        TessOcr tessOcr = new TessOcr("C:\\Users\\Tam\\Pictures\\myreceipt\\34a73e78-33f5-4dc4-a905-d2bd04f34b1b.jpeg");
//        TessOcr tessOcr1 = new TessOcr("C:\\Users\\Tam\\Pictures\\myreceipt\\34a73e78-33f5-4dc4-a905-d2bd04f34b1b_croped.jpg");
//        tessOcr.run();
//        tessOcr1.run();
//        System.out.println(tessOcr.processRaw("C:\\Users\\Tam\\Pictures\\myreceipt\\34a73e78-33f5-4dc4-a905-d2bd04f34b1b.jpeg"));
//        System.out.println(tessOcr.processRaw("C:\\Users\\Tam\\Pictures\\myreceipt\\34a73e78-33f5-4dc4-a905-d2bd04f34b1b_croped.jpg"));
//        System.out.println(tessOcr.processRaw("C:\\Users\\Tam\\Pictures\\myreceipt\\IMG_2220_rotated.JPG"));
//        System.out.println(tessOcr.processRaw("C:\\Users\\Tam\\Pictures\\myreceipt\\IMG_2220.JPG"));
//        System.out.println(tessOcr.processRaw("C:\\Users\\Tam\\Pictures\\myreceipt\\IMG_2220_ROTATE.JPG"));
//        File file = new File("C:\\Users\\Tam\\Pictures\\myreceipt\\IMG_2220_rotated.JPG");
//        TessOcr.readImageInformation(file);
    }

//    @Test
    public void name() throws Exception {
        float testFloat = (float) 4032/1130;
        System.out.println(testFloat);

    }
}