package com.tamtran.myreceipt.business.tesseract;

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
        TessOcr tessOcr = new TessOcr();
        System.out.println(tessOcr.processRaw("C:\\Users\\Tam\\Pictures\\myreceipt\\IMG_2220_rotated.JPG"));
        System.out.println(tessOcr.processRaw("C:\\Users\\Tam\\Pictures\\myreceipt\\IMG_2220.JPG"));
        System.out.println(tessOcr.processRaw("C:\\Users\\Tam\\Pictures\\myreceipt\\IMG_2220_ROTATE.JPG"));
//        File file = new File("C:\\Users\\Tam\\Pictures\\myreceipt\\IMG_2220_rotated.JPG");
//        TessOcr.readImageInformation(file);
    }

}