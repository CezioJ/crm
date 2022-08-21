package com.crm.workbench.service;

import com.crm.model.DicValue;

import java.util.List;

public interface DicValueService {
   List<DicValue> queryDicValueByTypeCode(String typeCode);
}
