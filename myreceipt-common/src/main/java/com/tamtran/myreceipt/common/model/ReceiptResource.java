package com.tamtran.myreceipt.common.model;

/**
 * Created by Tam Tran on 10/29/2015.
 */
public class ReceiptResource {
    private String receiptId;
    private String placeName;
    private String placeLocation;
    private String receiptTotal;
    private String receiptDateTime;
    private String receiptPath;
    private String receiptRaw;
    private String receiptProcessed;
    private String items;

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
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

    public String getReceiptDateTime() {
        return receiptDateTime;
    }

    public void setReceiptDateTime(String receiptDateTime) {
        this.receiptDateTime = receiptDateTime;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    public String getReceiptRaw() {
        return receiptRaw;
    }

    public void setReceiptRaw(String receiptRaw) {
        this.receiptRaw = receiptRaw;
    }

    public String getReceiptProcessed() {
        return receiptProcessed;
    }

    public void setReceiptProcessed(String receiptProcessed) {
        this.receiptProcessed = receiptProcessed;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }
}
