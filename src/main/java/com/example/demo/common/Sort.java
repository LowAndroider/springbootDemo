package com.example.demo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Djh
 */
@Data
public class Sort {

    /** 升序，0；降序，1 */
    @ApiModelProperty("升序，0；降序，1")
    private int desc;

    /** 排序字段 */
    @ApiModelProperty("排序字段")
    private String sortField;
}
