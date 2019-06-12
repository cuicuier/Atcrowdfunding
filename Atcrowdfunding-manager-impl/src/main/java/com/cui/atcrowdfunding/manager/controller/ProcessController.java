package com.cui.atcrowdfunding.manager.controller;

import com.cui.atcrowdfunding.util.AjaxResult;
import com.cui.atcrowdfunding.util.Page;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/process")
public class ProcessController {

    @Autowired
    private RepositoryService repositoryService;
    @RequestMapping("/index")
    public String index(){
        return "process/index";
    }

    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno", required = false,defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize",required = false,defaultValue = "10") Integer pagesize){
        AjaxResult result=new AjaxResult();
        try {
            Page page=new Page(pageno,pagesize);
            ProcessDefinitionQuery processDefinitionQuery=repositoryService.createProcessDefinitionQuery();
            List<ProcessDefinition> list = processDefinitionQuery.listPage(page.getStartIndex(),pagesize);
            Long totalsize=processDefinitionQuery.count();//总记录数
            List<Map<String,Object>> mylistPage=new ArrayList<Map<String,Object>>();
            for (ProcessDefinition processDefinition:list){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("id",processDefinition.getId());
                map.put("name",processDefinition.getName());
                map.put("version",processDefinition.getVersion());
                map.put("key",processDefinition.getKey());
                mylistPage.add(map);
            }
            page.setData(mylistPage);
            page.setTotalsize(totalsize.intValue());
            result.setPage(page);
            result.setSuccess(true);

        }catch (Exception e){
            result.setSuccess(false);
            result.setMessage("查询流程定义失败");
            e.printStackTrace();
        }
        return result;

    }



}
