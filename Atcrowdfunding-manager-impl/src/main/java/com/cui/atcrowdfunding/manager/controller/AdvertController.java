package com.cui.atcrowdfunding.manager.controller;

import com.cui.atcrowdfunding.bean.Advertisement;
import com.cui.atcrowdfunding.bean.User;
import com.cui.atcrowdfunding.manager.service.AdvertService;
import com.cui.atcrowdfunding.util.AjaxResult;
import com.cui.atcrowdfunding.util.Const;
import com.cui.atcrowdfunding.util.Page;
import com.cui.atcrowdfunding.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/advert")
public class AdvertController {

    @Autowired
    private AdvertService advertService;

    @RequestMapping("/index")
    public String index(){
        return "advert/index";
    }
    @ResponseBody
    @RequestMapping("/doIndex")
    public Object doIndex(@RequestParam(value = "pageno",required = false,defaultValue = "1") Integer pageno,
                          @RequestParam(value = "pagesize",required = false,defaultValue = "5") Integer pagesize,
                          String queryText){
        AjaxResult result=new AjaxResult();
        try{
            Map<String,Object> paramMap = new HashMap();
            paramMap.put("pageno",pageno);
            paramMap.put("pagesize",pagesize);
            if (StringUtil.isNotEmpty(queryText)){
                if (queryText.contains("%")){
                    queryText=queryText.replaceAll("%","\\\\%");

                }
                paramMap.put("queryText",queryText);
            }


            Page page=advertService.queryPage(paramMap);
            System.out.println("查到广告列表了");
            result.setSuccess(true);
            result.setPage(page);

        }catch (Exception e){
            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("查询广告数据失败！");
        }
        return result;
    }

    @RequestMapping("/add")
    public String add(){
        return "advert/add";
    }

    @ResponseBody
    @RequestMapping("/doAdd")
    public Object doAdd(HttpServletRequest request, Advertisement advertisement, HttpSession session){
        AjaxResult result=new AjaxResult();
        try{
            MultipartHttpServletRequest mreq = (MultipartHttpServletRequest) request;
            MultipartFile mFile=mreq.getFile("advimage");

            String name=mFile.getOriginalFilename();//java.jpg
            String extname=name.substring(name.lastIndexOf("."));//.jpg  后缀名
            String iconpath=UUID.randomUUID().toString()+extname; //随机生成文件名

            ServletContext servletContext=session.getServletContext();
            String realpath=servletContext.getRealPath("/pics");

            String path=realpath+"\\adv\\"+iconpath;  //保存上传的图片的位置路径

            mFile.transferTo(new File(path)); //保存当前文件

            User user= (User) session.getAttribute(Const.LOGIN_USER);
            advertisement.setUserid(user.getId());
            advertisement.setStatus("1");
            advertisement.setIconpath(iconpath);
            System.out.println("新增广告名称为："+advertisement.getName());

            int count=advertService.insertAdvert(advertisement);
            result.setSuccess(count==1);
            result.setMessage("新增广告成功！");

        }catch (Exception e){

            e.printStackTrace();
            result.setSuccess(false);
            result.setMessage("新增广告失败！");
        }
        return result;
    }

}
