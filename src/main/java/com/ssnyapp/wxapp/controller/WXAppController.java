package com.ssnyapp.wxapp.controller;

import com.ssnyapp.common.utils.Query;
import com.ssnyapp.wxapp.domain.CompanyDO;
import com.ssnyapp.wxapp.domain.JobFairDO;
import com.ssnyapp.wxapp.domain.JobSeekerDO;
import com.ssnyapp.wxapp.service.CompanyService;
import com.ssnyapp.wxapp.service.JobFairService;
import com.ssnyapp.wxapp.service.JobSeekerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by clyao on 2017/10/24.
 */
@RequestMapping("/wxapp")
@Controller
public class WXAppController {

    @Autowired
    JobFairService jobFairService;

    @Autowired
    CompanyService companyService;

    @Autowired
    JobSeekerService jobSeekerService;

    //ssnyapp小程序首页接口数据
    @ResponseBody
    @GetMapping(value = {"/index/list"}, produces = "application/json;charset=UTF-8")
    public String indexList(@RequestParam Map<String, Object> params){

        params.put("startTime", getPastDate(7));
        params.put("endTime", getFetureDate(7));

        Query query = new Query(params);

        List<JobFairDO> jobFairDOList = jobFairService.listForWeek(query);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[{\"locJob\":");
        stringBuffer.append("[");

        for(int i=0; i<jobFairDOList.size();i++){
            JobFairDO jobFairList = jobFairDOList.get(i);
            //根据参会日期，计算招聘会状态
            String state = null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(jobFairList.getJoinDateStart());
            calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 1);
            int days= (int)(calendar.getTimeInMillis()-new Date().getTime())/(1000*3600*24);
            if(days<0){
                state = "0";
            }else if(days==0){
                state = "1";
            }else {
                state = "2";
            }
            stringBuffer.append("{\"id\":");
            stringBuffer.append("\""+jobFairList.getId()+"\",");
            stringBuffer.append("\"date\":");
            stringBuffer.append("\""+formatDateToString(jobFairList.getJoinDateStart())+"至"+formatDateToString(jobFairList.getJoinDateEnd())+"\",");
            stringBuffer.append("\"text\":");
            stringBuffer.append("\""+jobFairList.getTheme()+"\",");
            stringBuffer.append("\"location\":");
            stringBuffer.append("\""+jobFairList.getAddress()+"\",");
            stringBuffer.append("\"state\":");
            stringBuffer.append("\""+state+"\" }");
            if(i!=jobFairDOList.size()-1){
                stringBuffer.append(",");
            }
        }

        stringBuffer.append("]},");

        //热门职位
        stringBuffer.append("{\"hotjobList\":");
        stringBuffer.append("[");
        stringBuffer.append("\"会计\",");
        stringBuffer.append("\"文员\",");
        stringBuffer.append("\"司机\",");
        stringBuffer.append("\"业务员\",");
        stringBuffer.append("\"技术员\",");
        stringBuffer.append("\"跟单\",");
        stringBuffer.append("\"外贸\",");
        stringBuffer.append("\"送货员\",");
        stringBuffer.append("\"打磨工\",");
        stringBuffer.append("\"行政\",");
        stringBuffer.append("\"销售\",");
        stringBuffer.append("\"保安\",");
        stringBuffer.append("\"普工\",");
        stringBuffer.append("\"导游\",");
        stringBuffer.append("\"技工\",");
        stringBuffer.append("\"领班\"");
        stringBuffer.append("]}");

        stringBuffer.append("]");

        return stringBuffer.toString();
    }

    //ssnyapp小程序招聘会接口数据
    @ResponseBody
    @GetMapping(value = {"/locJob/list"}, produces = "application/json;charset=UTF-8")
    public String locJobList(Integer limit){

        List<JobFairDO> jobFairDOList = jobFairService.list(limit);

        Calendar now = Calendar.getInstance();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");

        stringBuffer.append("{\"jobLocTitle\":");
        stringBuffer.append("\"新南粤人才市场"+now.get(Calendar.YEAR)+"年"+(now.get(Calendar.MONTH)+1)+"月份现场招聘会排期表\"},");

        stringBuffer.append("{\"jobLocList\":");
        stringBuffer.append("[");

        for(int i=0; i<jobFairDOList.size();i++){
            JobFairDO jobFairList = jobFairDOList.get(i);
            stringBuffer.append("{ \"date\":");
            stringBuffer.append("\""+formatDateToString(jobFairList.getJoinDateStart())+"至"+formatDateToString(jobFairList.getJoinDateEnd())+"\",");
            stringBuffer.append("\"week\":");
            stringBuffer.append("\""+jobFairList.getJobWeek()+"\",");
            stringBuffer.append("\"floor\":");
            stringBuffer.append("\""+jobFairList.getFloor()+"\",");
            stringBuffer.append("\"theme\":");
            stringBuffer.append("\""+jobFairList.getTheme()+"\"}");
            if(i!=jobFairDOList.size()-1){
                stringBuffer.append(",");
            }
        }

        stringBuffer.append("]}");
        stringBuffer.append("]");

        return stringBuffer.toString();

    }

    //ssnyapp小程序招聘会展位接口数据
    @ResponseBody
    @GetMapping(value = {"/locJob/jobFair"}, produces = "application/json;charset=UTF-8")
    public String locJobFair(Integer id){

        JobFairDO jobFairDO = jobFairService.get(id);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("{\"locJobFair\":");
        stringBuffer.append("[");

        stringBuffer.append("{ \"date\":");
        stringBuffer.append("\""+formatDateToString(jobFairDO.getJoinDateStart())+"至"+formatDateToString(jobFairDO.getJoinDateEnd())+"\",");
        stringBuffer.append("\"week\":");
        stringBuffer.append("\""+jobFairDO.getJobWeek()+"\",");
        stringBuffer.append("\"floor\":");
        stringBuffer.append("\""+jobFairDO.getFloor()+"\",");
        stringBuffer.append("\"theme\":");
        stringBuffer.append("\""+jobFairDO.getTheme()+"\"}");

        stringBuffer.append("]");
        stringBuffer.append("}]");

        return stringBuffer.toString();

    }

    //ssnyapp小程序委托招聘接口数据
    @ResponseBody
    @GetMapping(value = {"/entrustJob/list"}, produces = "application/json;charset=UTF-8")
    public String entrustJobList(){

        Calendar now = Calendar.getInstance();

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");

        stringBuffer.append("{\"jobEntrustTitle\":");
        stringBuffer.append("\""+now.get(Calendar.YEAR)+"年委托招聘张贴海报服类会员类型及价格标准\"},");

        stringBuffer.append("{\"jobEntrustList\":");
        stringBuffer.append("[");

        stringBuffer.append("{\"time\":");
        stringBuffer.append("\"月会员\",");
        stringBuffer.append("\"size\":");
        stringBuffer.append("\"二楼（60CM*50CM）\",");
        stringBuffer.append("\"pirce\":");
        stringBuffer.append("\"500（元）\",");
        stringBuffer.append("\"moreservice\":");
        stringBuffer.append("\"A、免费制作宣传海报1张，张贴在本市场二楼指定墙面位置；B、服务期内免费提供1次现场求职人才简历查询30份\" },");

        stringBuffer.append("{\"time\":");
        stringBuffer.append("\"季度会员\",");
        stringBuffer.append("\"size\":");
        stringBuffer.append("\"二楼（60CM*50CM）\",");
        stringBuffer.append("\"pirce\":");
        stringBuffer.append("\"1280（元）\",");
        stringBuffer.append("\"moreservice\":");
        stringBuffer.append("\"A、免费制作宣传海报2张，张贴在本市场二楼指定玻璃版面位置；B、服务期内免费提供3次现场求职人才简历查询共60份\" },");

        stringBuffer.append("{\"time\":");
        stringBuffer.append("\"半年会员\",");
        stringBuffer.append("\"size\":");
        stringBuffer.append("\"二楼（60CM*50CM）\",");
        stringBuffer.append("\"pirce\":");
        stringBuffer.append("\"2200（元）\",");
        stringBuffer.append("\"moreservice\":");
        stringBuffer.append("\"A、免费制作宣传海报4张，张贴在本市场二楼指定玻璃版面位置；B、服务期内免费提供6次现场求职人才简历查询共120份\" },");

        stringBuffer.append("{\"time\":");
        stringBuffer.append("\"年会员\",");
        stringBuffer.append("\"size\":");
        stringBuffer.append("\"二楼（60CM*50CM）\",");
        stringBuffer.append("\"pirce\":");
        stringBuffer.append("\"3600（元）\",");
        stringBuffer.append("\"moreservice\":");
        stringBuffer.append("\"A、免费制作宣传海报6张，张贴在本市场二楼指定玻璃版面位置；B、服务期内免费提供10次现场求职人才简历查询共250份\" }");

        stringBuffer.append("]}");
        stringBuffer.append("]");

        return stringBuffer.toString();
    }

    //ssnyapp小程序现场职位接口数据
    @ResponseBody
    @GetMapping(value = {"/job/list"}, produces = "application/json;charset=UTF-8")
    public String jobList(@RequestParam Map<String, Object> params) {

        Query query = new Query(params);

        List<CompanyDO> companyDOList = companyService.list(query);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("{\"jobList\":");
        stringBuffer.append("[");

        for(int i=0; i<companyDOList.size(); i++){
            CompanyDO companyList = companyDOList.get(i);
            stringBuffer.append("{ \"id\":");
            stringBuffer.append("\""+companyList.getId()+"\",");
            stringBuffer.append("\"positionName\":");
            stringBuffer.append("\""+companyList.getPositionName()+"\",");
            stringBuffer.append("\"salary\":");
            stringBuffer.append("\""+companyList.getSalary()+"\",");
            stringBuffer.append("\"companyName\":");
            stringBuffer.append("\""+companyList.getCompanyName()+"\",");
            stringBuffer.append("\"updateTime\":");
            stringBuffer.append("\""+formatDateToString(companyList.getCreateDate())+"\" }");
            if(i!=companyDOList.size()-1){
                stringBuffer.append(",");
            }
        }

        stringBuffer.append("]");
        stringBuffer.append("}]");

        return stringBuffer.toString();

    }

    //ssnyapp小程序现场详细职位接口数据
    @ResponseBody
    @GetMapping(value = {"/jobDetail/list"}, produces = "application/json;charset=UTF-8")
    public String jobDetailList(Integer id) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("{\"jobDetailList\":");
        stringBuffer.append("[");

        CompanyDO companyDO = companyService.get(id);
        stringBuffer.append("{ \"positionName\":");
        stringBuffer.append("\""+companyDO.getPositionName()+"\",");
        stringBuffer.append("\"companyName\":");
        stringBuffer.append("\""+companyDO.getCompanyName()+"\",");
        stringBuffer.append("\"salary\":");
        stringBuffer.append("\""+companyDO.getSalary()+"\",");
        stringBuffer.append("\"workAddress\":");
        stringBuffer.append("\""+companyDO.getWorkAddress()+"\",");
        stringBuffer.append("\"linkMan\":");
        stringBuffer.append("\""+companyDO.getLinkMan()+"\",");
        stringBuffer.append("\"telephone\":");
        stringBuffer.append("\""+companyDO.getTelephone()+"\",");
        stringBuffer.append("\"description\":");
        stringBuffer.append("\""+companyDO.getDescription()+"\" }");

        stringBuffer.append("]");
        stringBuffer.append("}]");

        return stringBuffer.toString();

    }

    //ssnyapp小程序现场简历接口数据
    @ResponseBody
    @GetMapping(value = {"/resume/list"}, produces = "application/json;charset=UTF-8")
    public String resumeList(@RequestParam Map<String, Object> params) {

        Query query = new Query(params);

        List<JobSeekerDO> jobSeekerDOList = jobSeekerService.list(query);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("{\"resumeList\":");
        stringBuffer.append("[");

        for(int i=0; i<jobSeekerDOList.size(); i++){
            JobSeekerDO jobSeekerList = jobSeekerDOList.get(i);
            stringBuffer.append("{ \"id\":");
            stringBuffer.append("\""+jobSeekerList.getId()+"\",");
            stringBuffer.append("\"userName\":");
            stringBuffer.append("\""+jobSeekerList.getUserName()+"\",");
            stringBuffer.append("\"education\":");
            stringBuffer.append("\""+jobSeekerList.getDegree()+"\",");
            stringBuffer.append("\"position\":");
            stringBuffer.append("\""+jobSeekerList.getPosition()+"\",");
            stringBuffer.append("\"updateTime\":");
            stringBuffer.append("\""+formatDateToString(jobSeekerList.getCreateDate())+"\" }");
            if(i!=jobSeekerDOList.size()-1){
                stringBuffer.append(",");
            }
        }

        stringBuffer.append("]");
        stringBuffer.append("}]");

        return stringBuffer.toString();
    }

    //ssnyapp小程序现场简历详细信息接口数据
    @ResponseBody
    @GetMapping(value = {"/resumeDetail/list"}, produces = "application/json;charset=UTF-8")
    public String resumeDetailList(Integer id) {

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("{\"resumeDetailList\":");
        stringBuffer.append("[");

        JobSeekerDO jobSeekerDO = jobSeekerService.get(id);
        stringBuffer.append("{ \"userName\":");
        stringBuffer.append("\""+jobSeekerDO.getUserName()+"\",");
        stringBuffer.append("\"sex\":");
        stringBuffer.append("\""+jobSeekerDO.getSex()+"\",");
        stringBuffer.append("\"brthday\":");
        stringBuffer.append("\""+getAge(jobSeekerDO.getBrthday())+"\",");
        stringBuffer.append("\"nation\":");
        stringBuffer.append("\""+jobSeekerDO.getNation()+"\",");
        stringBuffer.append("\"marriage\":");
        stringBuffer.append("\""+jobSeekerDO.getMarriage()+"\",");
        stringBuffer.append("\"school\":");
        stringBuffer.append("\""+jobSeekerDO.getSchool()+"\",");
        stringBuffer.append("\"speciality\":");
        stringBuffer.append("\""+jobSeekerDO.getSpeciality()+"\",");
        stringBuffer.append("\"degree\":");
        stringBuffer.append("\""+jobSeekerDO.getDegree()+"\",");
        stringBuffer.append("\"address\":");
        stringBuffer.append("\""+jobSeekerDO.getAddress()+"\",");
        stringBuffer.append("\"telephone\":");
        stringBuffer.append("\""+jobSeekerDO.getTelephone()+"\",");
        stringBuffer.append("\"position\":");
        stringBuffer.append("\""+jobSeekerDO.getPosition()+"\" }");

        stringBuffer.append("]");
        stringBuffer.append("}]");

        return stringBuffer.toString();
    }

    //ssnyapp小程序现场职位发布接口
    @ResponseBody
    @GetMapping(value = {"/job/save"}, produces = "application/json;charset=UTF-8")
    public String jobSave(HttpServletRequest request) {

        String companyName = request.getParameter("companyName");
        String positionName = request.getParameter("positionName");
        String workAddress = request.getParameter("workAddress");
        String salary = request.getParameter("salary");
        String description = request.getParameter("description");
        String linkMan = request.getParameter("linkMan");
        String telephone = request.getParameter("telephone");

        CompanyDO companyDO = new CompanyDO();
        companyDO.setCompanyName(companyName);
        companyDO.setPositionName(positionName);
        companyDO.setWorkAddress(workAddress);
        companyDO.setSalary(salary);
        companyDO.setDescription(replaceText(description));
        companyDO.setLinkMan(linkMan);
        companyDO.setTelephone(telephone);
        companyDO.setCreateDate(new Date());
        companyDO.setState(0);

        int count = companyService.save(companyDO);
        if(count>0){
            return "职位发布成功,等待审核";
        }else{
            return "职位发布失败";
        }
    }

    //ssnyapp小程序现场简历发布接口
    @ResponseBody
    @GetMapping(value = {"/resume/save"}, produces = "application/json;charset=UTF-8")
    public String resumeSave(HttpServletRequest request) {

        String userName = request.getParameter("userName");
        String nation = request.getParameter("nation");
        String sex = request.getParameter("sex");
        String marriage = request.getParameter("marriage");
        String school = request.getParameter("school");
        String speciality = request.getParameter("speciality");
        String position = request.getParameter("position");
        String address = request.getParameter("address");
        String telephone = request.getParameter("telephone");
        String email = request.getParameter("email");
        String degree = request.getParameter("degree");
        String brthday = request.getParameter("brthday");

        JobSeekerDO jobSeekerDO = new JobSeekerDO();
        jobSeekerDO.setUserName(userName);
        jobSeekerDO.setNation(nation);
        jobSeekerDO.setSex(sex);
        jobSeekerDO.setMarriage(marriage);
        jobSeekerDO.setSchool(school);
        jobSeekerDO.setSpeciality(speciality);
        jobSeekerDO.setPosition(position);
        jobSeekerDO.setAddress(address);
        jobSeekerDO.setTelephone(telephone);
        jobSeekerDO.setEmail(email);
        jobSeekerDO.setDegree(degree);
        jobSeekerDO.setBrthday(formatDateToDate(brthday));
        jobSeekerDO.setCreateDate(new Date());
        jobSeekerDO.setState(0);

        int count = jobSeekerService.save(jobSeekerDO);
        if(count>0){
            return "简历发布成功,等待审核";
        }else{
            return "简历发布失败";
        }
    }

    //ssnyapp小程序现场文件下载接口数据
    @ResponseBody
    @GetMapping(value = {"/file/download"}, produces = "application/json;charset=UTF-8")
    public String fileDownloadList() {

        //word文件图标：/images/home/word.png
        //excel文件图标：/images/home/excel.png
        //pdf文件图标：/images/home/pdf.png
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[");
        stringBuffer.append("{\"fileList\":");
        stringBuffer.append("[");
        stringBuffer.append("{ \"icon\":");
        stringBuffer.append("\"/images/home/word.png\",");
        stringBuffer.append("\"fileName\":");
        stringBuffer.append("\"2018年新春招聘会2-3月函件\",");
        stringBuffer.append("\"fileUrl\":");
        stringBuffer.append("\"https://www.ssny98.com/img/wxapp/2018new2.doc\"");
        stringBuffer.append("},");
        stringBuffer.append("{ \"icon\":");
        stringBuffer.append("\"/images/home/word.png\",");
        stringBuffer.append("\"fileName\":");
        stringBuffer.append("\"2018年新春招聘会2-3月函件\",");
        stringBuffer.append("\"fileUrl\":");
        stringBuffer.append("\"https://www.ssny98.com/img/wxapp/2018new2.doc\"");
        stringBuffer.append("},");
        stringBuffer.append("{ \"icon\":");
        stringBuffer.append("\"/images/home/word.png\",");
        stringBuffer.append("\"fileName\":");
        stringBuffer.append("\"2018年新春招聘会2-3月函件\",");
        stringBuffer.append("\"fileUrl\":");
        stringBuffer.append("\"https://www.ssny98.com/img/wxapp/2018new2.doc\"");
        stringBuffer.append("}");

        stringBuffer.append("]");
        stringBuffer.append("}]");

        return stringBuffer.toString();
    }

    //将日期类型格式化为字符串，例如：1990-01-01
    public String formatDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }

    //将字符串类型格式化为日期
    public Date formatDateToDate(String date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate;
    }

    //根据生日计算年龄
    public int getAge(Date birthday){
        int age = 18;
        try {
            Calendar now = Calendar.getInstance();
            now.setTime(new Date());
            Calendar birth = Calendar.getInstance();
            birth.setTime(birthday);
            if (birth.after(now)) {
                age = 18;
            } else {
                age = now.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
                if (now.get(Calendar.DAY_OF_YEAR) > birth.get(Calendar.DAY_OF_YEAR)) {
                    age += 1;
                }
                if(age<18){
                    age = 18;
                }
            }
            return age;
        } catch (Exception e) {
            return age;
        }
    }

    //根据当前日期，计算前几天的时间
    public String getPastDate(int past){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - 7);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(today);
    }

    //根据当前日期，计算未来几天的时间
    public String getFetureDate(int past){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        calendar.set(Calendar.HOUR, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(today);
    }

    public String replaceText(String text){
        String result = "";
        if(text!=null){
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(text);
            result = m.replaceAll("");
        }
        return result;
    }

}
