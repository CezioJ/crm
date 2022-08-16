package com.crm.workbench.web.controller;

import com.crm.common.constant.Constants;
import com.crm.common.message.ReturnMsg;
import com.crm.model.Activity;
import com.crm.model.User;
import com.crm.settings.service.UserService;
import com.crm.workbench.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ActivityController {
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

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
}
