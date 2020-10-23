package cn.geminiplanet.ccms.service.impl;

import cn.geminiplanet.ccms.entity.Student;
import cn.geminiplanet.ccms.entity.User;
import cn.geminiplanet.ccms.mapper.StudentMapper;
import cn.geminiplanet.ccms.service.StudentService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LinJun
 * @since 2020-10-23
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {


}
