package com.crm.workbench.service.impl;

import com.crm.common.constant.Constants;
import com.crm.common.message.ReturnMsg;
import com.crm.common.utils.DateUtils;
import com.crm.common.utils.UUIDUtils;
import com.crm.model.Activity;
import com.crm.workbench.mapper.ActivityMapper;
import com.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ActivityServiceImpl implements ActivityService {
    @Autowired
    private ActivityMapper activityMapper;

    @Override
    public Object saveActivity(Activity activity) {
        //封装参数
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));

        ReturnMsg msg = new ReturnMsg();
        try {
            int ret = activityMapper.insertActivity(activity);

            if(ret>0){
                msg.setStatus(Constants.STATUS_SUCCESS);
            }else{
                msg.setStatus(Constants.STATUS_FAIL);
                msg.setMessage("系统忙,请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("系统忙,请稍后重试....");
        }

        return msg;
    }
}
