package com.crm.workbench.service.impl;

import com.crm.common.constant.Constants;
import com.crm.common.message.ReturnMsg;
import com.crm.common.utils.DateUtils;
import com.crm.common.utils.UUIDUtils;
import com.crm.model.*;
import com.crm.workbench.mapper.*;
import com.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ClueServiceImpl implements ClueService {
    @Autowired
    private ClueMapper clueMapper;
    @Autowired
    private ClueRemarkMapper clueRemarkMapper;
    @Autowired
    private ClueActivityRelationMapper carMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ContactsMapper contactsMapper;
    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private CustomerRemarkMapper customerRemarkMapper;
    @Autowired
    private ContactsRemarkMapper contactsRemarkMapper;
    @Autowired
    private ContactsActivityRelationMapper CARMapper;
    @Autowired
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public Object saveCreatClue(Clue clue) {
        ReturnMsg msg = new ReturnMsg();
        msg.setStatus(Constants.STATUS_SUCCESS);

        if(clueMapper.insert(clue) != 1){
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("失败。。。");
        }

        return msg;
    }

    @Override
    public Clue queryClueForDetailById(String id) {
        return clueMapper.selectClueForDetailById(id);
    }

    @Override
    public Clue queryClueById(String id) {
        return clueMapper.selectClueById(id);
    }

    @Override
    public Object deleteClueById(String id) {
        ReturnMsg msg = new ReturnMsg();
        msg.setStatus(Constants.STATUS_SUCCESS);

        if(clueMapper.deleteClueById(id) != 1){
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("失败。。。");
        }

        return msg;
    }

    @Override
    public Object saveConvertClue(Map<String, Object> map) {
        ReturnMsg msg = new ReturnMsg();
        try {
            //保存线索转换
            User user = (User) map.get(Constants.SESSION_KEY);
            String clueId = (String) map.get("clueId");
            String money = (String) map.get("money");
            String name = (String) map.get("name");
            String expectedDate = (String) map.get("expectedDate");
            String stage = (String) map.get("stage");
            String activityId = (String) map.get("activityId");
            String isCreateTran = (String) map.get("isCreateTran");
            //根据id查询线索的信息
            Clue clue=clueMapper.selectClueById(clueId);
            //把该线索中有关公司的信息转换到客户表中
            Customer c = new Customer();
            c.setAddress(clue.getAddress());
            c.setContactSummary(clue.getContactSummary());
            c.setCreateBy(user.getId());
            c.setCreateTime(DateUtils.formatDateTime(new Date()));
            c.setDescription(clue.getDescription());
            c.setId(UUIDUtils.getUUID());
            c.setName(clue.getCompany());
            c.setNextContactTime(clue.getNextContactTime());
            c.setOwner(user.getId());
            c.setPhone(clue.getPhone());
            c.setWebsite(clue.getWebsite());
            customerMapper.insertCustomer(c);

            //把该线索中有关个人的信息转换到联系人表中
            Contacts co=new Contacts();
            co.setAddress(clue.getAddress());
            co.setAppellation(clue.getAppellation());
            co.setContactSummary(clue.getContactSummary());
            co.setCreateBy(user.getId());
            co.setCreateTime(DateUtils.formatDateTime(new Date()));
            co.setCustomerId(c.getId());
            co.setDescription(clue.getDescription());
            co.setEmail(clue.getEmail());
            co.setFullname(clue.getFullname());
            co.setId(UUIDUtils.getUUID());
            co.setJob(clue.getJob());
            co.setMphone(clue.getMphone());
            co.setNextContactTime(clue.getNextContactTime());
            co.setOwner(user.getId());
            co.setSource(clue.getSource());
            contactsMapper.insertContacts(co);

            //查询该线索备注信息
            List<ClueRemark> clueRemarkList = clueRemarkMapper.selectClueRemarkByClueId(clueId);
            //转移至客户、联系人备注表
            if(clueRemarkList!=null && clueRemarkList.size() > 0){
                CustomerRemark cur=null;
                ContactsRemark cor=null;

                for(ClueRemark cr: clueRemarkList){
                    cur=new CustomerRemark();
                    cur.setCreateBy(cr.getCreateBy());
                    cur.setCreateTime(cr.getCreateTime());
                    cur.setCustomerId(c.getId());
                    cur.setEditBy(cr.getEditBy());
                    cur.setEditFlag(cr.getEditFlag());
                    cur.setEditTime(cr.getEditTime());
                    cur.setId(UUIDUtils.getUUID());
                    cur.setNoteContent(cr.getNoteContent());
                    customerRemarkMapper.insert(cur);

                    cor=new ContactsRemark();
                    cor.setContactsId(co.getId());
                    cor.setCreateBy(cr.getCreateBy());
                    cor.setCreateTime(cr.getCreateTime());
                    cor.setEditBy(cr.getEditBy());
                    cor.setEditFlag(cr.getEditFlag());
                    cor.setEditTime(cr.getEditTime());
                    cor.setId(UUIDUtils.getUUID());
                    cor.setNoteContent(cr.getNoteContent());
                    contactsRemarkMapper.insert(cor);
                }

            }

            //线索与活动关联关系的转移
            List<ClueActivityRelation> carList = carMapper.selectClueActivityRelationByClueId(clueId);
            if(carList!=null && carList.size()>0){
                ContactsActivityRelation car = null;

                for (ClueActivityRelation relation : carList) {
                    car = new ContactsActivityRelation();
                    car.setId(UUIDUtils.getUUID());
                    car.setActivityId(relation.getActivityId());
                    car.setContactsId(co.getId());
                    CARMapper.insert(car);
                }
            }

            //如果创建交易
            if ("true".equals(isCreateTran)){
                //备注添加到交易备注表
                Tran tran=new Tran();
                tran.setActivityId((String) map.get("activityId"));
                tran.setContactsId(co.getId());
                tran.setCreateBy(user.getId());
                tran.setCreateTime(DateUtils.formatDateTime(new Date()));
                tran.setCustomerId(c.getId());
                tran.setExpectedDate((String) map.get("expectedDate"));
                tran.setId(UUIDUtils.getUUID());
                tran.setMoney((String) map.get("money"));
                tran.setName((String) map.get("name"));
                tran.setOwner(user.getId());
                tran.setStage((String) map.get("stage"));
                tranMapper.insertTran(tran);
                //备注转移至交易表
                TranRemark tranRemark = null;
                for(ClueRemark cr: clueRemarkList){
                    tranRemark = new TranRemark();
                    tranRemark.setCreateBy(cr.getCreateBy());
                    tranRemark.setCreateTime(cr.getCreateTime());
                    tranRemark.setEditBy(cr.getEditBy());
                    tranRemark.setEditFlag(cr.getEditFlag());
                    tranRemark.setEditTime(cr.getEditTime());
                    tranRemark.setId(UUIDUtils.getUUID());
                    tranRemark.setNoteContent(cr.getNoteContent());
                    tranRemark.setTranId(tran.getId());
                    tranRemarkMapper.insert(tranRemark);
                }
            }

            //删除线索备注
            clueRemarkMapper.deleteClueRemarkByClueId(clueId);
            //删除线索与活动关联关系
            carMapper.deleteClueActivityRelationByClueId(clueId);
            //删除线索
            clueMapper.deleteClueById(clueId);

            msg.setStatus(Constants.STATUS_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            msg.setStatus(Constants.STATUS_FAIL);
            msg.setMessage("系统忙，请稍后重试....");
        }

        return msg;
    }
}
