package cn.geminiplanet.ccms.controller;


import cn.geminiplanet.ccms.common.dto.StudentLoginDto;
import cn.geminiplanet.ccms.common.lang.Result;
import cn.geminiplanet.ccms.entity.*;
import cn.geminiplanet.ccms.service.*;
import cn.geminiplanet.ccms.utils.JwtUtils;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LinJun
 * @since 2020-10-23
 */
@RestController
@RequestMapping("/student")
@Api(tags = "API for student", description = "")
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    SubjectService subjectService;

    @Autowired
    GroupService groupService;

    @Autowired
    TopicselectService topicselectService;

    @Autowired
    ScoreService scoreService;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody StudentLoginDto loginDto, HttpServletResponse response) {

        Student student = studentService.getOne(new QueryWrapper<Student>().eq("sId", loginDto.getSId()));
        Assert.notNull(student, "用户不存在");

        if(!student.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }

        String jwt = jwtUtils.generateToken(student.getSId());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("gender",student.getGender())
                .put("contact",student.getContact())
                .put("sId", student.getSId())
                .map()
        );
    }

    @RequiresAuthentication
    @GetMapping("/logout")
    @ApiOperation("退出登录")
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

    /**
     *
     * * * 个人信息管理 * * *
     *
     */

    @RequiresAuthentication
    @PutMapping("/update_student_information")
    @ApiOperation("根据学号，修改个人信息")
    // 根据用户的学号 sid， 更新用户的其他信息
    // 测试成功
    public Result updateStudentInformation(@Validated @RequestBody Student studentById){

        Student student = studentService.getOne(new QueryWrapper<Student>().eq("sId", studentById.getSId()));

        student.setContact(studentById.getContact());
        student.setGender(studentById.getGender());
        student.setPassword(SecureUtil.md5(studentById.getPassword()));

        studentService.updateById(student);

        return Result.success(MapUtil.builder()
                .put("gender",student.getGender())
                .put("contact",student.getContact())
                .put("sId", student.getSId())
                .put("password",student.getPassword())
                .map()
        );
    }

    /**
     *
     * * * 组队管理 * * *
     *
     */
    @RequiresAuthentication
    @PutMapping("/group_manage")
    @ApiOperation("根据组号，管理组队信息")
    public Result groupManage(@Validated @RequestBody Group groupByGId){

        Group group= groupService.getOne(new QueryWrapper<Group>().eq("gId", groupByGId.getGId()));

        group.setSId_1(groupByGId.getSId_1());
        group.setSId_2(groupByGId.getSId_2());
        group.setSId_3(groupByGId.getSId_3());
        group.setSId_4(groupByGId.getSId_4());
        group.setSId_5(groupByGId.getSId_5());
        group.setSId_6(groupByGId.getSId_6());

        groupService.updateById(group);

        return Result.success(MapUtil.builder()
                .put("gId",group.getGId())
                .put("sId_1",group.getSId_1())
                .put("sId_2",group.getSId_2())
                .put("sId_3",group.getSId_3())
                .put("sId_4",group.getSId_4())
                .put("sId_5",group.getSId_5())
                .put("sId_6",group.getSId_6())
                .map()
        );
    }

    /**
     *
     * * * 选择课题项目 * * *
     *
     */
    @RequiresAuthentication
    @PutMapping("/topic_select")
    @ApiOperation("根据选题编号，选择课题项目")
    public Result topicSelect(@Validated @RequestBody Topicselect topicselectByTsId){

        Topicselect topicselect= topicselectService.getOne(new QueryWrapper<Topicselect>().eq("tsId", topicselectByTsId.getTsId()));

        topicselect.setSubId(topicselectByTsId.getSubId());
        topicselect.setGId(topicselectByTsId.getGId());
        if(topicselectByTsId.getCompleted() == null){
            topicselect.setCompleted(0);
        }else {
            topicselect.setCompleted(topicselectByTsId.getCompleted());
        }

        topicselectService.updateById(topicselect);

        return Result.success(MapUtil.builder()
                .put("tsId",topicselect.getTsId())
                .put("subId",topicselect.getSubId())
                .put("gId",topicselect.getGId())
                .put("completed", topicselect.getCompleted())
                .map()
        );
    }

    /**
     *
     * * * 查看课题信息 * * *
     *
     */
    @RequiresAuthentication
    @GetMapping("/subject")
    @ApiOperation("查看指定课题详情")
    public Subject getSubjectById(@RequestParam("subId") Integer subId){
        Subject subject = subjectService.getOne(new QueryWrapper<Subject>().eq("subId",subId));

        return subject;
    }

    /**
     *
     * * * 查看所有课题信息 * * *
     *
     */
    @RequiresAuthentication
    @GetMapping("/all_subject")
    @ApiOperation("查看指定课题详情")
    public List<Student> getAllSubject(){
        return studentService.list();
    }

    /**
     *
     * * * 上传实验报告* * *
     *
     */
    @RequiresAuthentication
    @PostMapping("/upload_experiment_report")
    @ApiOperation("上传实验报告")
    // 测试成功
    public Result uploadExperimentReport(@RequestParam("file") MultipartFile file)  throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IOException("未选择需上传的日志文件");
        }
        String filePath = new File("experiment_reports").getAbsolutePath();
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }

        fileUpload = new File(filePath + file.getOriginalFilename());

        try {
            file.transferTo(fileUpload);

            return Result.success(200);
        } catch (IOException e) {
            throw new IOException("上传日志文件到服务器失败：" + e.toString());
        }
    }

    /**
     *
     * * * 更新实验报告* * *
     *
     */
    @RequiresAuthentication
    @PostMapping("/modify_experiment_report")
    @ApiOperation("更新实验报告")
    // 测试成功
    public Result modifyExperimentReport(@RequestParam("file") MultipartFile file)  throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IOException("未选择需上传的日志文件");
        }
        String filePath = new File("experiment_reports").getAbsolutePath();
        File fileUpload = new File(filePath);
        if (!fileUpload.exists()) {
            fileUpload.mkdirs();
        }

        fileUpload = new File(filePath + file.getOriginalFilename());

        try {
            file.transferTo(fileUpload);

            return Result.success(200);
        } catch (IOException e) {
            throw new IOException("上传日志文件到服务器失败：" + e.toString());
        }
    }


    /**
     *
     * * * 成绩查询* * *
     *
     */
    @RequiresAuthentication
    @GetMapping("/group_score")
    @ApiOperation("查看小组成绩")
    public Integer getScoreByGId(@RequestParam("gId") Integer gId){
        Integer tsId = topicselectService.getOne(new QueryWrapper<Topicselect>().eq("gId",gId)).getTsId();
        Integer score = scoreService.getOne(new QueryWrapper<Score>().eq("tsId",tsId)).getScore();
        return score;
    }

    /**
     *
     * * * 成绩有误反馈* * *
     *
     */
    @RequiresAuthentication
    @PostMapping("/send_message")
    @ApiOperation("给老师发送信息，反馈成绩有误")
    public void sendMessage(@RequestParam("tId") Integer tId, @RequestParam("sId") Integer gId, @RequestParam("content") String content){
        Group group = groupService.getOne(new QueryWrapper<Group>().eq("gId",gId));

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date());

        Message message1 = new Message();
        message1.setTId(tId);
        message1.setSId(group.getSId_1());
        message1.setContent(content);
        message1.setTime(time);
    }
}
