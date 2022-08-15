package com.crm.settings.web.controller;

import com.crm.common.constant.Constants;
import com.crm.common.message.ReturnMsg;
import com.crm.common.utils.DateUtils;
import com.crm.model.User;
import com.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;

/**
 *  用户相关controller
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 首页跳转
     * @return
     */
    @RequestMapping("/toLogin.do")
    public String toLogin(){
        return "settings/qx/user/login";
    }

    /**
     * 登录功能
     * @param loginAct
     * @param loginPwd
     * @param req
     * @return
     */
    @RequestMapping("/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd,
                        HttpServletRequest req, HttpSession session,
                        HttpServletResponse response){
        HashMap<String, Object> map = new HashMap<>();
        map.put("loginPwd",loginPwd);
        map.put("loginAct", loginAct);

        User user = userService.login(map);
        ReturnMsg msg = new ReturnMsg();
        msg.setStatus(Constants.LOGIN_FAIL);
        if(user == null){
            msg.setMessage("用户名或密码错误");
        }else {
            if("0".equals(user.getLockState())){
                msg.setMessage("状态被锁定");
            }else if (!user.getAllowIps().contains(req.getRemoteAddr())){
                msg.setMessage("IP受限制");
            }else if (DateUtils.formatDateTime(new Date()).compareTo(user.getExpireTime())>0){
                msg.setMessage("账号过期");
            }else {
                msg.setStatus(Constants.LOGIN_SUCCESS);
                session.setAttribute(Constants.SESSION_KEY,user);

                //如果需要记住密码，则往外写cookie
                if("true".equals(isRemPwd)){
                    Cookie c1=new Cookie("loginAct",user.getLoginAct());
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd",user.getLoginPwd());
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else{
                    //把没有过期cookie删除
                    Cookie c1=new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2=new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }

        return msg;
    }

    @RequestMapping("/logout.do")
    public String logout(HttpServletRequest req, HttpSession session,
                         HttpServletResponse response){

        Cookie c1=new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2=new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);

        session.invalidate();

        return "redirect:/";
    }
}
