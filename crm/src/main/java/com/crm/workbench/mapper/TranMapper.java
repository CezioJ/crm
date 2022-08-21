package com.crm.workbench.mapper;

import com.crm.model.FunnelVO;
import com.crm.model.Tran;

import java.util.List;

public interface TranMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int insert(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    Tran selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int updateByPrimaryKeySelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Sat Aug 20 01:09:34 CST 2022
     */
    int updateByPrimaryKey(Tran record);

    /**
     * 保存交易信息
     * @param record
     * @return
     */
    int insertTran(Tran record);

    /**
     * 根据id查询交易的详细信息
     * @param id
     * @return
     */
    Tran selectTranForDetailById(String id);

    /**
     * 查询交易表各个阶段数据量
     * @return
     */
    List<FunnelVO> selectCountOfTranGroupByStage();

}
