package com.justyoga.util.dto;

import java.util.Date;
import java.util.UUID;
import lombok.Data;

@Data
public class BaseDTO {
    private UUID id;
    private Date createdAt;
    private Date updatedAt;
    private UUID createdBy;
    private UUID modifiedBy;
}
