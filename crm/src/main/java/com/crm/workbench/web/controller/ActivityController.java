package com.crm.workbench.web.controller;

import com.crm.common.constant.Constants;
import com.crm.common.utils.DateUtils;
import com.crm.common.utils.HSSFUtils;
import com.crm.model.Activity;
import com.crm.model.ActivityRemark;
import com.crm.model.User;
import com.crm.settings.service.UserService;
import com.crm.workbench.service.ActivityRemarkService;
import com.crm.workbench.service.ActivityService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ActivityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/workbench/activity/index.do")
    public String index(HttpServletRequest req){
        List<User> userList = userService.selectAllUsers();
        req.setAttribute("userList",userList);
        return "workbench/activity/index";
    }

    @RequestMapping("/workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveActivity(Activity activity, HttpSession session){
        //进一步封装参数
        User user = (User) session.getAttribute(Constants.SESSION_KEY);
        activity.setCreateBy(user.getId());

        return activityService.saveActivity(activity);
    }

    @RequestMapping("/workbench/activity/queryActivityByConditionForPage.do")
    @ResponseBody
    public Object queryActivity(String name,String owner,String startDate,String endDate,
                                int pageNo,int pageSize){
        //封装参数
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //调用service层方法，查询数据
        List<Activity> activityList = activityService.queryActivityByConditionForPage(map);
        int totalRows = activityService.queryCountOfActivityByCondition(map);
        //根据查询结果结果，生成响应信息
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);

        return retMap;
    }

    @RequestMapping("/workbench/activity/deleteActivityByIds.do")
    @ResponseBody
    public Object deleteActivity(String[] id){
        return activityService.deleteActivityByIds(id);
    }

    @RequestMapping("/workbench/activity/detailActivity.do")
    public String detailActivity(String id, HttpServletRequest request){
        //调用service层方法，查询数据
        Activity activity = (Activity) activityService.queryActivityById(id);
        List<ActivityRemark> remarkList = activityRemarkService.queryActivityRemarkForDetailByActivityId(id);
        //把数据保存到request中
        request.setAttribute("activity",activity);
        request.setAttribute("remarkList",remarkList);
        //请求转发
        return "workbench/activity/detail";
    }

    @RequestMapping("/workbench/activity/queryActivityById.do")
    @ResponseBody
    public Object queryActivity(String id){
        return activityService.queryActivityById(id);
    }

    @RequestMapping("/workbench/activity/saveEditActivity.do")
    @ResponseBody
    public Object saveEditActivity(Activity ac, HttpSession session){
        User user = (User) session.getAttribute(Constants.SESSION_KEY);
        ac.setEditBy(user.getId());
        ac.setEditTime(DateUtils.formatDateTime(new Date()));
        return activityService.saveEditActivity(ac);
    }

    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public void exportActivity(HttpServletResponse response) throws IOException {
        //设置响应信息
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        OutputStream out = response.getOutputStream();

        //调用service层方法，查询所有的市场活动
        List<Activity> activityList = activityService.queryAllActivity();
        //创建excel文件，并且把activityList写入到excel文件中
        HSSFWorkbook wb = HSSFUtils.creatActivityExcel(activityList);
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/exportActivityByCondition.do")
    public void exportActivityByCondition(String[] id, HttpServletResponse response) throws IOException {
        //设置响应信息
        response.setContentType("application/octet-stream;charset=UTF-8");
        response.addHeader("Content-Disposition","attachment;filename=activityList.xls");
        OutputStream out = response.getOutputStream();

        //调用service层方法，查询指定的市场活动
        List<Activity> activityList = activityService.queryActivityByIds(id);
        //创建excel文件，并且把activityList写入到excel文件中
        HSSFWorkbook wb = HSSFUtils.creatActivityExcel(activityList);
        wb.write(out);
        wb.close();
        out.flush();
    }

    @RequestMapping("/workbench/activity/importActivity.do")
    @ResponseBody
    public Object importActivity(MultipartFile activityFile, String userName, HttpSession session)
            throws IOException {
        User user = (User) session.getAttribute(Constants.SESSION_KEY);
        HSSFWorkbook wb = new HSSFWorkbook(activityFile.getInputStream());
        List<Activity> activityList = HSSFUtils.getActivityFromExcel(wb, user);

        return activityService.importActivityList(activityList);
    }
}
