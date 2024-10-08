package com.xxxx.crm.mapper;

import com.xxxx.crm.base.BaseMapper;
import com.xxxx.crm.dto.TreeDto;
import com.xxxx.crm.vo.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {
    public List<TreeDto> queryAllModules();

    List<Module> queryModules();

    public Module queryModuleByGradeAndModuleName(@Param("grade") Integer grade,@Param("moduleName") String moduleName);
    public Module queryModuleByGradeAndUrl(@Param("grade") Integer grade, @Param("url") String url);

    public Module queryModuleByOptValue(String optValue);

    public int  countSubModuleByParentId(Integer parentId);
}
