package edu.umsl.esi.floorplan.model;

import java.util.List;

/**
 * Created by Tam Tran on 10/31/2015.
 */
public class DeleteReceiptRequest {
    private List<String> receiptIds;

    public List<String> getReceiptIds() {
        return receiptIds;
    }

    public void setReceiptIds(List<String> receiptIds) {
        this.receiptIds = receiptIds;
    }
}
