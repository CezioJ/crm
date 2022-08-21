package com.crm.workbench.service;

import com.crm.model.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {
    Object insertClueActivityRelationByList(List<ClueActivityRelation> list);
    Object deleteClueActivityRelationByClueId(ClueActivityRelation relation);
}
