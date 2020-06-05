package com.merlin.mybatis.mapper;

import com.merlin.springcloud.entities.Dept;

import java.util.List;

@Deprecated
public interface DeptMapper {
    public List<Dept> getAllDept();

    public void deleteDept(int deptno);

    public void updateDept(Dept dept);

    public Dept getOneDeptById(int deptno);

    public void saveDept(Dept dept);
}
