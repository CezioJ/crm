package com.crm.workbench.service;


import com.crm.model.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    Object saveActivity(Activity activity);
    List<Activity> queryActivityByConditionForPage(Map<String,Object> map);
    int queryCountOfActivityByCondition(Map<String,Object> map);
    Object deleteActivityByIds(String[] id);
    Object detailActivityById(String id);
    Object queryActivityById(String id);
    Object saveEditActivity(Activity ac);
}
