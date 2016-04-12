package com.tamtran.myreceipt.business.services.impl;

import com.tamtran.myreceipt.business.services.ReceiptService;
import com.tamtran.myreceipt.common.constant.ReceiptConstants;
import com.tamtran.myreceipt.common.model.DeleteReceiptRequest;
import com.tamtran.myreceipt.common.model.ReceiptItems;
import com.tamtran.myreceipt.common.model.ReceiptResource;
import com.tamtran.myreceipt.common.model.SaveReceiptRequest;
import com.tamtran.myreceipt.data.dao.ReceiptDao;
import com.tamtran.myreceipt.data.domain.ReceiptDO;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReceiptServiceImpl implements ReceiptService {
    private static final Logger logger = LogManager.getLogger();

    @Autowired
    private ReceiptDao receiptDao;

    public void saveReceipt(SaveReceiptRequest saveReceiptRequest) {
        ReceiptDO receiptDO = new ReceiptDO();
        receiptDO.setExtendReceiptId(saveReceiptRequest.getExtrlRequestId());
        receiptDO.setPlaceId(saveReceiptRequest.getPlaceId());
        receiptDO.setPlaceLocation(saveReceiptRequest.getPlaceLocation());
        receiptDO.setPlaceName(saveReceiptRequest.getPlaceName());
        try {
            receiptDO.setReceiptDate(DateUtils.parseDate(saveReceiptRequest.getDate(), "yyyy-MM-dd HH:mm"));
        } catch (ParseException e) {
            e.printStackTrace();
            receiptDO.setReceiptDate(new Date());
        }
        receiptDO.setReceiptTotal(saveReceiptRequest.getReceiptTotal());
        receiptDO.setUserName(saveReceiptRequest.getUserName());
//        receiptDO.setRawReceiptData(saveReceiptRequest.getRawData());
        receiptDO.setRawReceiptData("Unprocessed");
        receiptDO.setProcessedReceiptData("Unprocessed");
        receiptDO.setReceiptPath(saveReceiptRequest.getReceiptPath());
        receiptDO.setActiveSwitch(ReceiptConstants.ACTIVE_SW.Y.toString());
        System.out.println(ReflectionToStringBuilder.toString(receiptDO, ToStringStyle.MULTI_LINE_STYLE));
        receiptDao.addReceipt(receiptDO);
    }

    public List<ReceiptResource> getReceipts(String userName) {
        List<ReceiptResource> receiptResources = new ArrayList<ReceiptResource>();
        for(ReceiptDO receiptDO : receiptDao.getReceiptByUserName(userName)) {
            ReceiptResource receiptResource = mapReceiptDOToReceiptResource(receiptDO);
            receiptResources.add(receiptResource);
        }
        return receiptResources;
    }

    private ReceiptResource mapReceiptDOToReceiptResource(ReceiptDO receiptDO) {
        ReceiptResource receiptResource = new ReceiptResource();
        receiptResource.setReceiptId(receiptDO.getExtendReceiptId());
        receiptResource.setPlaceName(receiptDO.getPlaceName());
        receiptResource.setPlaceLocation(receiptDO.getPlaceLocation());
        receiptResource.setReceiptTotal(receiptDO.getReceiptTotal());
        receiptResource.setReceiptPath(receiptDO.getReceiptPath());
        File f = new File(receiptDO.getReceiptPath());
        if(!f.exists()){
//            logger.error("scanned receipt missing for receipt {} with path {}",receiptDO.getExtendReceiptId(),receiptDO.getReceiptPath());
            receiptResource.setReceiptPath("Not found");
        }
        if (null != receiptDO.getReceiptDate()) {
//                receiptResource.setReceiptDateTime(DateFormatUtils.ISO_DATETIME_FORMAT.format(receiptDO.getReceiptDate()));
            receiptResource.setReceiptDateTime(String.valueOf(receiptDO.getReceiptDate().getTime()));
        }
        receiptResource.setItems(receiptDO.getProcessedReceiptData());
        return receiptResource;
    }

    public void deleteReceipts(DeleteReceiptRequest deleteReceiptRequest) {
        receiptDao.deleteReceipts(deleteReceiptRequest.getReceiptIds());
    }

    public void updateReceipt(ReceiptResource receiptResource) {
        ReceiptDO receiptDO = receiptDao.getReceiptByExtId(receiptResource.getReceiptId());
        receiptDO.setRawReceiptData(receiptResource.getReceiptRaw());
        receiptDO.setProcessedReceiptData(receiptResource.getReceiptProcessed());
        receiptDao.updateReceipt(receiptDO);
    }

    public void updateReceiptItems(ReceiptItems receiptItems) {
        ReceiptDO receiptDo = receiptDao.getReceiptByExtId(receiptItems.getReceiptId());
        receiptDo.setProcessedReceiptData(receiptItems.getReceiptItems());
        receiptDao.updateReceipt(receiptDo);
    }

    @Override
    public ReceiptResource getReceipt(String receiptId) {
        ReceiptDO receiptDO = receiptDao.getReceiptByExtId(receiptId);
        return mapReceiptDOToReceiptResource(receiptDO);
    }
}
