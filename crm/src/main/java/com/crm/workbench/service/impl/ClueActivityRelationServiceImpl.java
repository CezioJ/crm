package com.crm.workbench.service.impl;

import com.crm.common.constant.Constants;
import com.crm.common.message.ReturnMsg;
import com.crm.model.ClueActivityRelation;
import com.crm.workbench.mapper.ClueActivityRelationMapper;
import com.crm.workbench.service.ClueActivityRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClueActivityRelationServiceImpl implements ClueActivityRelationService {
    @Autowired
    private ClueActivityRelationMapper carMapper;

    @Override
    public Object insertClueActivityRelationByList(List<ClueActivityRelation> list) {
        ReturnMsg msg = new ReturnMsg();
        msg.setStatus(Constants.STATUS_SUCCESS);
        if(carMapper.insertClueActivityRelationByList(list)!= list.size()){
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("失败。。。");
        }
        return msg;
    }

    @Override
    public Object deleteClueActivityRelationByClueId(ClueActivityRelation relation) {
        ReturnMsg msg = new ReturnMsg();
        msg.setStatus(Constants.STATUS_SUCCESS);
        if(carMapper.deleteClueActivityRelationByClueIdActivityId(relation)!=1){
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("失败。。。");
        }
        return msg;
    }


}
