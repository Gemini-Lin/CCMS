package cn.geminiplanet.ccms.controller;


import cn.geminiplanet.ccms.common.dto.StudentLoginDto;
import cn.geminiplanet.ccms.common.lang.Result;
import cn.geminiplanet.ccms.entity.Student;
import cn.geminiplanet.ccms.service.StudentService;
import cn.geminiplanet.ccms.utils.JwtUtils;
import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

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
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    JwtUtils jwtUtils;

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
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return Result.success(null);
    }

}
