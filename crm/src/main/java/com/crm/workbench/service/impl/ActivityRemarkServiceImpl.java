package com.crm.workbench.service.impl;
import com.crm.common.constant.Constants;
import com.crm.common.message.ReturnMsg;
import com.crm.model.ActivityRemark;
import com.crm.workbench.mapper.ActivityRemarkMapper;
import com.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("activityRemarkService")
public class ActivityRemarkServiceImpl implements ActivityRemarkService {

    @Autowired
    private ActivityRemarkMapper activityRemarkMapper;

    @Override
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId) {
        return activityRemarkMapper.selectActivityRemarkForDetailByActivityId(activityId);
    }

    @Override
    public Object saveCreateActivityRemark(ActivityRemark remark) {
        ReturnMsg msg = new ReturnMsg();
        try {
            //调用mapper层方法，保存创建的市场活动备注
            int ret = activityRemarkMapper.insertActivityRemark(remark);

            if(ret > 0){
                msg.setStatus(Constants.STATUS_SUCCESS);
                msg.setData(remark);
            }else{
                msg.setStatus(Constants.STATUS_FAIL);
                msg.setMessage("系统忙，请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("系统忙，请稍后重试....");
        }

        return msg;
    }

    @Override
    public Object deleteActivityRemarkById(String id) {
        ReturnMsg msg= new ReturnMsg();
        try {
            //调用mapper层方法，删除备注
            int ret = activityRemarkMapper.deleteActivityRemarkById(id);

            if(ret>0){
                msg.setStatus(Constants.STATUS_SUCCESS);
            }else{
                msg.setStatus(Constants.STATUS_FAIL);
                msg.setMessage("系统忙，请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("系统忙，请稍后重试....");
        }

        return msg;
    }

    @Override
    public Object saveEditActivityRemark(ActivityRemark remark) {
        ReturnMsg msg = new ReturnMsg();
        try {
            //保存修改的市场活动备注
            int ret = activityRemarkMapper.updateActivityRemark(remark);

            if(ret > 0){
                msg.setStatus(Constants.STATUS_SUCCESS);
                msg.setData(remark);
            }else{
                msg.setStatus(Constants.STATUS_FAIL);
                msg.setMessage("系统忙，请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("系统忙，请稍后重试....");
        }

        return msg;
    }
}
