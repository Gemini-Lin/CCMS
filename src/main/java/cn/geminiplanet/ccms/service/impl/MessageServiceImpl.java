package cn.geminiplanet.ccms.service.impl;

import cn.geminiplanet.ccms.entity.Message;
import cn.geminiplanet.ccms.mapper.MessageMapper;
import cn.geminiplanet.ccms.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author LinYanling
 * @since 2020-10-25
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {
}
