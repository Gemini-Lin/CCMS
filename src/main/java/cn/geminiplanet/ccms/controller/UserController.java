package cn.geminiplanet.ccms.controller;


import cn.geminiplanet.ccms.common.lang.Result;
import cn.geminiplanet.ccms.entity.User;
import cn.geminiplanet.ccms.service.UserService;
import cn.geminiplanet.ccms.utils.CreateImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author LinJun
 * @since 2020-10-08
 */
@RestController
@RequestMapping("/user")
@CrossOrigin // 允许跨域(前后端分离)
@Slf4j // 日志对象
public class UserController {
    /**
     * 生成验证码
     * @throws IOException
     */
    @GetMapping("/getImage")
    public Map<String, String> getImage(HttpServletRequest request) throws IOException {
        Map<String, String> result = new HashMap<>();
        CreateImageCode createImageCode = new CreateImageCode();
        // 获取验证码
        String securityCode = createImageCode.getCode();
        // 验证码存入session
        String key = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        request.getServletContext().setAttribute(key, securityCode);
        // 生成图片
        BufferedImage image = createImageCode.getBuffImg();
        //进行base64编码
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        String string = Base64Utils.encodeToString(bos.toByteArray());
        result.put("key", key);
        result.put("image", string);
        return result;
    }
}


