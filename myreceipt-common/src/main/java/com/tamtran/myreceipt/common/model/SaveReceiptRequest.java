package com.tamtran.myreceipt.common.model;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class SaveReceiptRequest {
    private String extrlRequestId;
    private String userName;
    private String placeId;
    private String placeName;
    private String placeLocation;
    private String receiptTotal;
    private String date;
    private String receiptPath;
    private String rawData;
    private String processedData;
    private String ios;
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


    public String getIos() {
        return ios;
    }

    public void setIos(String ios) {
        this.ios = ios;
    }

    public String getExtrlRequestId() {
        return extrlRequestId;
    }

    public void setExtrlRequestId(String extrlRequestId) {
        this.extrlRequestId = extrlRequestId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceLocation() {
        return placeLocation;
    }

    public void setPlaceLocation(String placeLocation) {
        this.placeLocation = placeLocation;
    }

    public String getReceiptTotal() {
        return receiptTotal;
    }

    public void setReceiptTotal(String receiptTotal) {
        this.receiptTotal = receiptTotal;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getProcessedData() {
        return processedData;
    }

    public void setProcessedData(String processedData) {
        this.processedData = processedData;
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

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
