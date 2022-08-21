package com.crm.workbench.web.controller;

import com.crm.common.constant.Constants;
import com.crm.common.message.ReturnMsg;
import com.crm.common.utils.DateUtils;
import com.crm.common.utils.UUIDUtils;
import com.crm.model.*;
import com.crm.settings.service.UserService;
import com.crm.workbench.mapper.ContactsActivityRelationMapper;
import com.crm.workbench.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {
    @Autowired
    private DicValueService dicValueService;
    @Autowired
    private UserService userService;
    @Autowired
    private ClueService clueService;
    @Autowired
    private ClueRemarkService clueRemarkService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService carService;

    @RequestMapping("/workbench/clue/index.do")
    public String index(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        List<DicValue> appellationList=dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList=dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        //把数据保存到request中
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("clueStateList",clueStateList);
        request.setAttribute("sourceList",sourceList);

        return "/workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/queryClueById.do")
    @ResponseBody
    public Object queryClueById(String id){
        return clueService.queryClueById(id);
    }

    @RequestMapping("/workbench/clue/queryClueForDetailById.do")
    public String detailClue(String id, HttpServletRequest request){
        Clue clue = clueService.queryClueForDetailById(id);
        List<ClueRemark> remarkList = clueRemarkService.queryClueRemarkForDetailByClueId(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        request.setAttribute("clue",clue);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("activityList",activityList);

        return "/workbench/clue/detail";
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    @ResponseBody
    public Object saveCreateClue(Clue clue, HttpSession session){
        User user=(User)session.getAttribute(Constants.SESSION_KEY);

        //封装参数
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));
        clue.setCreateBy(user.getId());
        return clueService.saveCreatClue(clue);
    }

    @RequestMapping("/workbench/clue/deleteClueById.do")
    @ResponseBody
    public Object deleteClueById(String id){
        return clueService.deleteClueById(id);
    }

    @RequestMapping("/workbench/clue/queryActivityForDetailByNameClueId.do")
    @ResponseBody
    public Object queryActivityForDetailByNameClueId(String activityName,String clueId){
        return activityService.queryActivityForDetailByNameClueId(activityName,clueId);
    }

    @RequestMapping("/workbench/clue/saveBind.do")
    @ResponseBody
    public Object saveBind(String[] activityId, String clueId){
        ArrayList<ClueActivityRelation> list = new ArrayList<>();
        for (String id : activityId) {
            ClueActivityRelation relation = new ClueActivityRelation();
            relation.setId(UUIDUtils.getUUID());
            relation.setActivityId(id);
            relation.setClueId(clueId);
            list.add(relation);
        }
        ReturnMsg msg = (ReturnMsg) carService.insertClueActivityRelationByList(list);
        List<Activity> activityList = activityService.queryActivityByIds(activityId);
        msg.setData(activityList);
        return msg;
    }

    @RequestMapping("/workbench/clue/saveUnbind.do")
    @ResponseBody
    public Object saveUnBind(String activityId, String clueId){
        ClueActivityRelation relation = new ClueActivityRelation();
        relation.setClueId(clueId);
        relation.setActivityId(activityId);
        return carService.deleteClueActivityRelationByClueId(relation);
    }

    @RequestMapping("/workbench/clue/toConvert.do")
    public String toConvert(String id,HttpServletRequest request){
        //调用service层方法，查询线索的明细信息
        Clue clue= clueService.queryClueForDetailById(id);
        List<DicValue> stageList = dicValueService.queryDicValueByTypeCode("stage");
        //把数据保存到request中
        request.setAttribute("clue",clue);
        request.setAttribute("stageList",stageList);
        //请求转发
        return "/workbench/clue/convert";
    }


    @RequestMapping("/workbench/clue/queryActivityForConvertByNameClueId.do")
    @ResponseBody
    public Object queryActivityForConvertByNameClueId(String activityName, String clueId){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("clueId",clueId);
        //调用service层方法，查询市场活动
        List<Activity> activityList = activityService.queryActivityForConvertByNameClueId(map);
        //根据查询结果，返回响应信息
        return activityList;
    }

    @RequestMapping("/workbench/clue/convertClue.do")
    public @ResponseBody Object convertClue(String clueId,String money,
                                            String name,String expectedDate,
                                            String stage,String activityId,
                                            String isCreateTran,HttpSession session){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("money",money);
        map.put("name",name);
        map.put("expectedDate",expectedDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("isCreateTran",isCreateTran);
        map.put(Constants.SESSION_KEY,session.getAttribute(Constants.SESSION_KEY));

        return clueService.saveConvertClue(map);
    }
}
