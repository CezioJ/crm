package com.crm.workbench.service;


import com.crm.model.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Object saveActivity(Activity activity);
    List<Activity> queryActivityForDetailByNameClueId(String activityName,String clueId);
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);
    List<Activity> queryActivityForDetailByClueId(String id);
    List<Activity> queryActivityByIds(String[] ids);
    List<Activity> queryAllActivityForDetail();
    List<Activity> queryActivityForConvertByNameClueId(Map<String,Object> map);
    int queryCountOfActivityByCondition(Map<String,Object> map);
    Object deleteActivityByIds(String[] id);
    Object importActivityList(List<Activity> activityList);
    Object queryActivityById(String id);
    Object queryActivityForDetailById(String id);
    Object saveEditActivity(Activity ac);

}
