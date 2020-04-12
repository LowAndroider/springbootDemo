package com.example.demo.modules.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.config.annotation.Master;
import com.example.demo.modules.dao.DeptMapper;
import com.example.demo.modules.entity.Dept;
import com.example.demo.modules.service.DeptService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Djh
 */
@Service
@AllArgsConstructor
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService {

    @Master
    @Override
    public List<Dept> list() {
        return super.list();
    }
}
