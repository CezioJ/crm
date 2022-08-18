package com.crm.workbench.service;

import com.crm.model.ActivityRemark;

import java.util.List;

public interface ActivityRemarkService {
    List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId);

    Object saveCreateActivityRemark(ActivityRemark remark);

    Object deleteActivityRemarkById(String id);

    Object saveEditActivityRemark(ActivityRemark remark);
}
