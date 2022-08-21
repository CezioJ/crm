package com.crm.workbench.service;


import com.crm.model.FunnelVO;
import com.crm.model.Tran;

import java.util.List;
import java.util.Map;

public interface TranService {
    Object saveCreatTran(Map<String,Object> map);
    Tran queryTranForDetailById(String id);
    List<FunnelVO> queryCountOfTranGroupByStage();
}
