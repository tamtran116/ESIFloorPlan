package edu.umsl.esi.floorplan.domain;

import javax.persistence.*;
import java.sql.Blob;
import java.util.Date;

/**
 * Created by Tam Tran on 10/29/2015.
 */
@Entity(name = "RECEIPT_REQUEST")
public class ReceiptDO {
    @Id
    @Column(name="RECEIPT_REQUEST_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long request_id;

    @Column(name = "EXT_RECEIPT_ID")
    private String extendReceiptId;

    @Column(name="USER_NAME", nullable = false)
    private String userName;

    @Column(name="PLACE_ID", nullable = false)
    private String placeId;

    @Column(name="PLACE_NAME", nullable = false)
    private String placeName;

    @Column(name="PLACE_LOCATION", nullable = false)
    private String placeLocation;

    @Column(name="RECEIPT_TOTAL", nullable = false)
    private String receiptTotal;

    @Column(name="RECEIPT_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date receiptDate;

    @Column(name = "RECEIPT_PATH")
    private String receiptPath;

    @Column(name = "RAW_RECEIPT_DATA", nullable = false, columnDefinition = "TEXT")
    private String rawReceiptData;

    @Column(name = "PROCESSED_RECEIPT_DATA", nullable = false, columnDefinition = "TEXT")
    private String processedReceiptData;

    @Column(name="ACTV_SW", nullable = false)
    private String activeSwitch;

    public Long getRequest_id() {
        return request_id;
    }

    public void setRequest_id(Long request_id) {
        this.request_id = request_id;
    }

    public String getExtendReceiptId() {
        return extendReceiptId;
    }

    public void setExtendReceiptId(String extendReceiptId) {
        this.extendReceiptId = extendReceiptId;
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

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getReceiptPath() {
        return receiptPath;
    }

    public void setReceiptPath(String receiptPath) {
        this.receiptPath = receiptPath;
    }

    public String getRawReceiptData() {
        return rawReceiptData;
    }

    public void setRawReceiptData(String rawReceiptData) {
        this.rawReceiptData = rawReceiptData;
    }

    public String getProcessedReceiptData() {
        return processedReceiptData;
    }

    public void setProcessedReceiptData(String processedReceiptData) {
        this.processedReceiptData = processedReceiptData;
    }

    public String getActiveSwitch() {
        return activeSwitch;
    }

    public void setActiveSwitch(String activeSwitch) {
        this.activeSwitch = activeSwitch;
    }
}
