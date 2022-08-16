package com.crm.common.message;
import lombok.Data;

@Data
public class ReturnMsg {
    private String status;
    private String message;
    private Object data;
}
