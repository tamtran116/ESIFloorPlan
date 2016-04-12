package com.tamtran.myreceipt.common.model;

/**
 * Created by Tam on 3/15/2016.
 */
public class ReceiptItems {
    private String receiptId;
    private String receiptItems;
    private String receiptPath;

    /**
     * Crop information
     */
    private Integer cropX;
    private Integer cropY;
    private Integer cropX2;
    private Integer cropY2;
    private Integer cropH;
    private Integer cropW;
    // Orientation of the image
    private Integer cropO;
    private Integer imgH;
    private Integer imgW;

    private boolean ios;

    public ReceiptItems() {
    }

    public ReceiptItems(String receiptId, String receiptPath) {
        this.receiptId = receiptId;
        this.receiptPath = receiptPath;
    }

    public boolean isIos() {
        return ios;
    }

    public void setIos(boolean ios) {
        this.ios = ios;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public String getReceiptItems() {
        return receiptItems;
    }

    public void setReceiptItems(String receiptItems) {
        this.receiptItems = receiptItems;
    }

    public Integer getCropX() {
        return cropX;
    }

    public void setCropX(Integer cropX) {
        this.cropX = cropX;
    }

    public Integer getCropY() {
        return cropY;
    }

    public void setCropY(Integer cropY) {
        this.cropY = cropY;
    }

    public Integer getCropX2() {
        return cropX2;
    }

    public void setCropX2(Integer cropX2) {
        this.cropX2 = cropX2;
    }

    public Integer getCropY2() {
        return cropY2;
    }

    public void setCropY2(Integer cropY2) {
        this.cropY2 = cropY2;
    }

    public Integer getCropH() {
        return cropH;
    }

    public void setCropH(Integer cropH) {
        this.cropH = cropH;
    }

    public Integer getCropW() {
        return cropW;
    }

    public void setCropW(Integer cropW) {
        this.cropW = cropW;
    }

    public Integer getCropO() {
        return cropO;
    }

    public void setCropO(Integer cropO) {
        this.cropO = cropO;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    public Integer getImgH() {
        return imgH;
    }

    public void setImgH(Integer imgH) {
        this.imgH = imgH;
    }

    public Integer getImgW() {
        return imgW;
    }

    public void setImgW(Integer imgW) {
        this.imgW = imgW;
    }
}
