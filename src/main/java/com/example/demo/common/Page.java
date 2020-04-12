package com.example.demo.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Djh
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Page extends Sort {

    /** 单页长度 */
    @ApiModelProperty("单页长度")
    private int pageSize;

    /** 页码 */
    @ApiModelProperty("页码")
    private int pageNum;
}
