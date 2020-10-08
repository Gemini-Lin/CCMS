package cn.geminiplanet.ccms.service.impl;

import cn.geminiplanet.ccms.entity.User;
import cn.geminiplanet.ccms.mapper.UserMapper;
import cn.geminiplanet.ccms.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LinJun
 * @since 2020-10-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
