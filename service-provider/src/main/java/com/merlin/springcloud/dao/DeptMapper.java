package com.merlin.springcloud.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.merlin.springcloud.entities.Dept;
import org.springframework.stereotype.Repository;

@Mapper
@Repository // NOTE：仅用于Autowired可以识别到此'bean'.
public interface DeptMapper {
    public boolean addDept(Dept dept);

    public Dept findById(Long id);

    public List<Dept> findAll();
}