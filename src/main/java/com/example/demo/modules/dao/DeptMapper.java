package com.example.demo.modules.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.modules.entity.Dept;
import org.apache.ibatis.annotations.Insert;

/**
 * @author Djh
 */
public interface DeptMapper extends BaseMapper<Dept> {

    @Override
    @Insert("INSERT INTO dept(d_name, db_source) VALUES (#{dName}, DATABASE())")
    int insert(Dept dept);
}
