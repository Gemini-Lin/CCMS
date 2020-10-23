package cn.geminiplanet.ccms.controller;

import cn.geminiplanet.ccms.common.dto.TeacherLoginDto;
import cn.geminiplanet.ccms.common.lang.Result;
import cn.geminiplanet.ccms.entity.Teacher;
import cn.geminiplanet.ccms.service.TeacherService;
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
@RequestMapping("/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public Result login(@Validated @RequestBody TeacherLoginDto loginDto, HttpServletResponse response) {

        Teacher teacher= teacherService.getOne(new QueryWrapper<Teacher>().eq("account",loginDto.getAccount()));
        Assert.notNull(teacher, "用户不存在");

        if(!teacher.getPassword().equals(SecureUtil.md5(loginDto.getPassword()))){
            return Result.fail("密码不正确");
        }

        String jwt = jwtUtils.generateToken(teacher.getAccount());

        response.setHeader("Authorization", jwt);
        response.setHeader("Access-control-Expose-Headers", "Authorization");

        return Result.success(MapUtil.builder()
                .put("account",teacher.getAccount())
                .put("password",teacher.getPassword())
                .put("phone",teacher.getPhone())
                .put("email",teacher.getJob())
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
