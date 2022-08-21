package com.crm.workbench.service;

import com.crm.model.TranHistory;
import java.util.List;

public interface TranHistoryService {
    List<TranHistory> queryTranHistoryForDetailByTranId(String tranId);
}
