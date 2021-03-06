package cn.geminiplanet.ccms.controller;

import cn.geminiplanet.ccms.common.dto.TeacherLoginDto;
import cn.geminiplanet.ccms.common.lang.Result;
import cn.geminiplanet.ccms.entity.*;
import cn.geminiplanet.ccms.service.*;
import cn.geminiplanet.ccms.utils.JwtUtils;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LinYanling
 * @since 2020-10-24
 */
@RestController
@RequestMapping("/teacher")
@Api(tags = "API for teacher", description = "")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @Autowired
    SubjectService subjectService;

    @Autowired
    GroupService groupService;

    @Autowired
    TopicselectService topicselectService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    MessageService messageService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    @ApiOperation("登录")
    public Result login(@Validated @RequestBody TeacherLoginDto loginDto, HttpServletResponse response) {

        Teacher teacher= teacherService.getOne(new QueryWrapper<Teacher>().eq("account",loginDto.getAccount()));
        Assert.notNull(teacher, "用户不存在");

        if(!teacher.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            System.out.println("md5加密后的密码为："+SecureUtil.md5(loginDto.getPassword()));
            return Result.fail("密码不正确");
        }

        String jwt = jwtUtils.generateToken(teacher.getAccount());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("tId", teacher.getTId())
                .put("account",teacher.getAccount())
                .put("password",teacher.getPassword())
                .put("phone",teacher.getPhone())
                .put("email",teacher.getEmail())
                .put("job",teacher.getJob())
                .put("gender", teacher.getGender())
                .put("introduction", teacher.getIntroduction())
                .map()
        );
    }

//    @RequiresAuthentication
    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }



    /**
     *
     * * * 指导课题及小组 * * *
     *
     */
//    @RequiresAuthentication
    @GetMapping("/all_direct_subjects")
    @ApiOperation("查看指导的所有课题列表")
    public List<Subject> getAllDirectSubjects(@RequestParam("tId") Integer tId){
        List<Subject> subjectList = subjectService.list(new QueryWrapper<Subject>().eq("tId",tId));
        return subjectList;
    }

//    @RequiresAuthentication
    @GetMapping("/subject")
    @ApiOperation("查看指定课题详情")
    public Subject getSubjectById(@RequestParam("subId") Integer subId){
        Subject subject = subjectService.getOne(new QueryWrapper<Subject>().eq("subId",subId));

        return subject;
    }

//    @RequiresAuthentication
    @GetMapping("/all_directd_group")
    @ApiOperation("查看指导的所有小组列表(课题ID+课题名称+小组id+完成度)")
    public List<GroupModel> getAllDirectedGroups(@RequestParam("tId") Integer tId){
        List<Subject> subjectList = subjectService.list(new QueryWrapper<Subject>().eq("tId",tId));

        List<Integer> subIdList= new ArrayList<>() ;
        subjectList.forEach((o) -> {
            subIdList.add(o.getSubId());
        });

        List<GroupModel> results= new ArrayList<>() ;

        if(subIdList.size()!=0){
            Collection<Topicselect> topicselectCollection = topicselectService.listByIds(subIdList);

            topicselectCollection.forEach((o)-> {
                String topic = subjectService.getOne(new QueryWrapper<Subject>().eq("subId",o.getSubId())).getTopic();
                results.add(new GroupModel(o.getSubId(), topic, o.getGId(), o.getCompleted(), o.getFilePath()));
            });
        }
        return results;
    }


//    @RequiresAuthentication
    @GetMapping("/subject/all_chosen_group")
    @ApiOperation("查看选择某一课题的小组列表(小组Id+小组成员+完成度)")
    public Collection<Group> getChosenGroups(@RequestParam("subId") Integer subId){
        List<Topicselect> topicselectList = topicselectService.list(new QueryWrapper<Topicselect>().eq("subId",subId));

        List<Integer> groupIdList= new ArrayList<>() ;
        topicselectList.forEach((o) -> {
            groupIdList.add(o.getGId());
        });

        Collection<Group> groupList = new ArrayList<>();
        if(groupIdList.size()!=0){
            groupList = groupService.listByIds(groupIdList);
        }
        return groupList;
    }

//    @RequiresAuthentication
    @GetMapping("/group_detail")
    @ApiOperation("查看某一小组详情")
    public Group getGroupById(@RequestParam("gId") Integer gId){
        Group group = groupService.getOne(new QueryWrapper<Group>().eq("gId",gId));
        return group;
    }

//    @RequiresAuthentication
    @PostMapping("/send_message")
    @ApiOperation("给指定小组的所有学生发送进度提醒消息")
    public void sendMessage(@RequestParam("tId") Integer tId, @RequestParam("gId") Integer gId, @RequestParam("content") String content){
        Group group = groupService.getOne(new QueryWrapper<Group>().eq("gId",gId));

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

        Message message1 = new Message();
        message1.setTId(tId);
        message1.setSId(group.getSId_1());
        message1.setContent(content);
        message1.setTime(time);

        Message message2 = new Message();
        message2.setTId(tId);
        message2.setSId(group.getSId_2());
        message2.setContent(content);
        message2.setTime(time);

        Message message3 = new Message();
        message3.setTId(tId);
        message3.setSId(group.getSId_3());
        message3.setContent(content);
        message3.setTime(time);

        //批量插入数据
        messageService.saveBatch(Arrays.asList(message1, message2, message3));
        if (group.getSId_4() != null){
            Message message4 = new Message();
            message4.setTId(tId);
            message4.setSId(group.getSId_1());
            message4.setContent(content);
            message4.setTime(time);
            messageService.save(message4);
        }
        if (group.getSId_5() != null){
            Message message5 = new Message();
            message5.setTId(tId);
            message5.setSId(group.getSId_1());
            message5.setContent(content);
            message5.setTime(time);
            messageService.save(message5);
        }
        if (group.getSId_6() != null){
            Message message6 = new Message();
            message6.setTId(tId);
            message6.setSId(group.getSId_1());
            message6.setContent(content);
            message6.setTime(time);
            messageService.save(message6);
        }
    }


    /**
     *
     * * * 成绩管理 * * *
     *
     */
//    @RequiresAuthentication
    @PutMapping("/commit_score")
    @ApiOperation("给指定小组评分")
    public void commitScore(@RequestParam("gId") Integer gId, @RequestParam("grade") Integer grade){
        Integer tsId = topicselectService.getOne(new QueryWrapper<Topicselect>().eq("gId",gId)).getTsId();
        Score score = scoreService.getOne(new QueryWrapper<Score>().eq("tsId",tsId));
        if(score != null){
            score.setScore(grade);
            scoreService.updateById(score);
        }
        else {
            score.setTsId(tsId);
            score.setScore(grade);
            scoreService.save(score);
        }
    }



    /**
     *
     * * * 导出成绩 * * *
     *
     */
//    @RequiresAuthentication
    @GetMapping("/scoreList")
    @ApiOperation("查看指导的所有小组的成绩(课题ID+课题名称+小组id+成绩)")
    public List<ScoreModel> getScoreList(@RequestParam("tId") Integer tId){
        List<Subject> subjectList = subjectService.list(new QueryWrapper<Subject>().eq("tId",tId));

        List<Integer> subIdList= new ArrayList<>() ;
        subjectList.forEach((o) -> {
            subIdList.add(o.getSubId());
        });

        List<ScoreModel> results= new ArrayList<>() ;

        if(subIdList.size()!=0){
            Collection<Topicselect> topicselectCollection = topicselectService.listByIds(subIdList);

            topicselectCollection.forEach((o)-> {
                String topic = subjectService.getOne(new QueryWrapper<Subject>().eq("subId",o.getSubId())).getTopic();
                Integer score = scoreService.getOne(new QueryWrapper<Score>().eq("tsId",o.getTsId())).getScore();
                results.add(new ScoreModel(o.getSubId(), topic, o.getGId(), score));
            });
        }
        return results;
    }


//    @RequiresAuthentication
    @GetMapping("/group_score")
    @ApiOperation("获取某一小组成绩")
    public Integer getScoreByGId(@RequestParam("gId") Integer gId){
        Integer tsId = topicselectService.getOne(new QueryWrapper<Topicselect>().eq("gId",gId)).getTsId();
        Integer score = scoreService.getOne(new QueryWrapper<Score>().eq("tsId",tsId)).getScore();
        return score;
    }


    /**
     *
     * * * 文件下载 * * *
     * @param fileName 文件名,如 data.xls
     * @param response
     *
     */
    @GetMapping("/download")
    @ApiOperation("下载文件")
    public Object downloadFile(@RequestParam String fileName, HttpServletResponse response){
        OutputStream os = null;
        InputStream is= null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=utf-8");
            //Content-Disposition中指定的类型是文件的扩展名，并且弹出的下载对话框中的文件类型图片是按照文件的扩展名显示的，点保存后，文件以filename的值命名，
            // 保存类型以Content中设置的为准。注意：在设置Content-Disposition头字段之前，一定要设置Content-Type头字段。
            //把文件名按UTF-8取出，并按ISO8859-1编码，保证弹出窗口中的文件名中文不乱码，中文不要太多，最多支持17个中文，因为header有150个字节限制。
            response.setHeader("Content-Disposition", "attachment;filename="+ new String(fileName.getBytes("utf-8"),"ISO8859-1"));
            //读取流
            File f = new File("C:\\"+fileName);
            is = new FileInputStream(f);
            if (is == null) {
                System.out.println("下载附件失败，请检查文件“" + fileName + "”是否存在");
                return Result.fail("下载附件失败，请检查文件“" + fileName + "”是否存在");
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return Result.fail("下载附件失败,error:"+e.getMessage());
        }
        //文件的关闭放在finally中
        finally
        {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //返回成功消息
        return Result.success(200,"下载成功",is);
    }
}
