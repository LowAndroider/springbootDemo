package com.example.demo.modules.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Djh
 */
@Data
public class Address {

    public interface Type1 {}

    public interface Type2 {}

    @NotNull(message = "省份不能为空！", groups = {Type1.class})
    private String province;

    @NotNull(message = "城市不能为空！", groups = {Type1.class})
    private String city;

    @NotNull(message = "街道不能为空！", groups = {Type1.class})
    private String road;

    @NotNull(message = "门牌号不能为空！", groups = {Type2.class})
    private String number;
}
