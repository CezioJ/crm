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
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Activity> queryActivityByConditionForPage(Map<String,Object> map) {
        return activityMapper.selectActivityByConditionForPage(map);
    }

    @Override
    public int queryCountOfActivityByCondition(Map<String,Object> map) {
        return activityMapper.selectCountOfActivityByCondition(map);
    }

    @Override
    public Object deleteActivityByIds(String[] id) {
        ReturnMsg msg = new ReturnMsg();
        msg.setStatus(Constants.STATUS_FAIL);
        msg.setMessage("系统繁忙，请重试。。。");
        try {
            int ret = activityMapper.deleteActivityByIds(id);
            if (ret == id.length){
                msg.setStatus(Constants.STATUS_SUCCESS);
                msg.setMessage(ret+"条数据被删除");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Override
    public Object detailActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public Object queryActivityById(String id) {
        return activityMapper.selectActivityById(id);
    }

    @Override
    public Object saveEditActivity(Activity ac) {
        ReturnMsg msg = new ReturnMsg();
        msg.setStatus(Constants.STATUS_SUCCESS);
        if(activityMapper.updateActivity(ac) != 1){
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("更新失败，请检查网络。。。");
        }
        return msg;
    }
}
