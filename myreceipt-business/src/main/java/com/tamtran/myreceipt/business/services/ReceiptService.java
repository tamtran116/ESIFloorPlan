package com.tamtran.myreceipt.business.services;

import com.tamtran.myreceipt.common.model.DeleteReceiptRequest;
import com.tamtran.myreceipt.common.model.ReceiptItems;
import com.tamtran.myreceipt.common.model.ReceiptResource;
import com.tamtran.myreceipt.common.model.SaveReceiptRequest;

import java.util.List;


public interface ReceiptService {

    void saveReceipt(SaveReceiptRequest saveReceiptRequest) ;

    List<ReceiptResource> getReceipts(String userName) ;

    ReceiptResource getReceipt(String receiptId);

    void deleteReceipts(DeleteReceiptRequest deleteReceiptRequest) ;

    void updateReceipt(ReceiptResource receiptResource) ;

    void updateReceiptItems(ReceiptItems receiptItems);
}
