package com.crm.workbench.mapper;

import com.crm.model.ContactsActivityRelation;

public interface ContactsActivityRelationMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int insert(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int insertSelective(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    ContactsActivityRelation selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int updateByPrimaryKeySelective(ContactsActivityRelation record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_contacts_activity_relation
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int updateByPrimaryKey(ContactsActivityRelation record);
}