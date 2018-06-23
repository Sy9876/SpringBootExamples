package cn.sy.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.sy.domain.SSystemLog;

@Mapper
public interface SSystemLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SSystemLog record);

    int insertSelective(SSystemLog record);

    SSystemLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SSystemLog record);

    int updateByPrimaryKeyWithBLOBs(SSystemLog record);

    int updateByPrimaryKey(SSystemLog record);
}