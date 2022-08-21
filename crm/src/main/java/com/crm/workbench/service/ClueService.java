package com.crm.workbench.service;

import com.crm.model.Clue;

import java.util.Map;

public interface ClueService {
    Object saveCreatClue(Clue clue);
    Clue queryClueForDetailById(String id);
    Clue queryClueById(String id);
    Object deleteClueById(String id);
    Object saveConvertClue(Map<String,Object> map);
}
