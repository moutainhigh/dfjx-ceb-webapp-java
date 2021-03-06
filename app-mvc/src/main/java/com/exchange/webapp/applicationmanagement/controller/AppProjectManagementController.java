package com.exchange.webapp.applicationmanagement.controller;

import com.exchange.webapp.applicationmanagement.bean.AppProjectManagement;
import com.exchange.webapp.applicationmanagement.service.AppProjectManagementService;
import com.webapp.support.json.JsonSupport;
import com.webapp.support.jsonp.JsonResult;
import com.webapp.support.page.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
/**
 * tb_project管理Controller
 */
@Controller
@RequestMapping("/consumption")
public class AppProjectManagementController {

    @Autowired
    private AppProjectManagementService appProjectManagementService;

    //tb_project应用管理列表
    @RequestMapping("/apmanagementList")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String apmanagementList(
            @RequestParam("currPage") int currPage,
            @RequestParam("pageSize")int pageSize
    ){
        PageResult pageResult = null;
        try{
            pageResult = appProjectManagementService.apmanagementList(currPage,pageSize);
        }catch(Exception e){
            return   JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "查询应用管理列表有误", null, "error");
        }
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "查询应用管理列表成功", null, pageResult);
    }



    //查看
    @RequestMapping("/apmanagementselect")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String apmanagementselect(
            @RequestParam("prj_cd") String prj_cd){
        List<AppProjectManagement> contactPageDatas;
        try{
            contactPageDatas = appProjectManagementService.apmanagementselect(prj_cd);
        }catch(Exception e){
            return   JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "应用管理查看失败", null, "error");
        }
        return   JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "应用管理查看成功", null, contactPageDatas);
    }


    //数据集项目下拉
    @RequestMapping("/apmanagementselectlist")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String apmanagementselectlist(){
        List<AppProjectManagement> contactPageDatas;
        try{
            contactPageDatas = appProjectManagementService.apmanagementselectlist();
        }catch(Exception e){
            return    JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "数据集项目下拉失败", null, "error");
        }
        return   JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "数据集项目下拉成功", null, contactPageDatas);
    }





    //新增
    @RequestMapping("/insertapmanagement")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String insertAPManagement(
            @RequestParam("prj_cd") String prj_cd,
            @RequestParam("person_id")int person_id,
            @RequestParam("prj_nm")String prj_nm,
            @RequestParam("prj_desc")String prj_desc){
        if("0".equals(String.valueOf(person_id)) || "null".equals(person_id)  || person_id <= 0){
            return    JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "联系人输入类型有误", null, "error"); }
        int sd = 0;
        //查询项目标识是否已存在
        sd =   appProjectManagementService.selectprj_cd(prj_cd);
        if (sd != 0){
            return   JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "此应用项目标识已存在", null, "error");
        }
        if(!prj_cd.isEmpty() && !prj_nm.isEmpty()){
            try{
                appProjectManagementService.insertapmanagement(prj_cd,person_id,prj_nm,prj_desc);
            }catch(Exception e){
                return    JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "新增应用管理失败", null, "error");
            }
        }else{
            return   JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "请确认必填项是否填写内容", null, "error");
        }
        return  JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "新增应用管理成功", null, "success");
    }


    //修改
    @RequestMapping("/updateapmanagement")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String updateapmanagement(
            @RequestParam("prj_cd") String prj_cd,
            @RequestParam("person_id")int person_id,
            @RequestParam("prj_nm")String prj_nm,
            @RequestParam("prj_desc")String prj_desc){
        if("0".equals(String.valueOf(person_id)) || "null".equals(person_id)  || person_id <= 0){
            return   JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "联系人输入类型有误", null, "error"); }
        if( !prj_nm.isEmpty()  && !prj_cd.isEmpty()){
            try{
                appProjectManagementService.updateapmanagement(person_id,prj_nm,prj_desc,prj_cd);
            }catch(Exception e){
                return    JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "应用管理修改失败", null, "error");
            }

        }else{
            return    JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "请确认必填项是否填写内容", null, "error");
        }
        return JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "应用管理修改成功", null, "success");
    }



    //删除
    @RequestMapping("/delapmanagement")
    @ResponseBody
    @CrossOrigin(allowCredentials="true")
    public String delapmanagement(
            @RequestParam("prj_cd") String prj_cd){
        try{
            appProjectManagementService.delapmanagement(prj_cd);
        }catch(Exception e){

            return    JsonSupport.makeJsonResultStr(JsonResult.RESULT.FAILD, "删除失败", null, "error");
        }
        return  JsonSupport.makeJsonResultStr(JsonResult.RESULT.SUCCESS, "删除成功", null, "error");
    }
}
