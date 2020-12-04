package cn.geminiplanet.ccms;

import cn.geminiplanet.ccms.entity.*;
import cn.geminiplanet.ccms.service.*;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class teacherTests {

    @Autowired
    SubjectService subjectService;

    @Autowired
    TeacherService teacherService;

    @Autowired
    TopicselectService topicselectService;

    @Autowired
    GroupService groupService;

    @Autowired
    ScoreService scoreService;

    @Autowired
    MessageService messageService;

    @Test
    void testGetAllDirectSubjects(){
        List<Subject> subjectList = subjectService.list(new QueryWrapper<Subject>().eq("tId",2018001));
        System.out.println(subjectList);
    }

    @Test
    void testGetSubjectById(){
        Subject subject = subjectService.getOne(new QueryWrapper<Subject>().eq("subId",2));
        System.out.println(subject);
    }


    @Test
    void testGetAllDirectedGroups(){
        List<Subject> subjectList = subjectService.list(new QueryWrapper<Subject>().eq("tId",2018001));

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
        System.out.println(results);
    }

    @Test
    void testGetChosenGroups(){
        List<Topicselect> topicselectList = topicselectService.list(new QueryWrapper<Topicselect>().eq("subId",1));

        List<Integer> groupIdList= new ArrayList<>() ;
        topicselectList.forEach((o) -> {
            groupIdList.add(o.getGId());
        });

        Collection<Group> groupList = new ArrayList<>();
        if(groupIdList.size()!=0){
            groupList = groupService.listByIds(groupIdList);
        }
        System.out.println(groupList);
    }


    @Test
    void testGetGroupById(){
        Group group = groupService.getOne(new QueryWrapper<Group>().eq("gId",1));
        System.out.println(group);
    }

    @Test
    void testGetScoreList(){
        List<Subject> subjectList = subjectService.list(new QueryWrapper<Subject>().eq("tId",2018001));

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
        System.out.println(results);
    }


    @Test
    void testGetScoreByGId(){
        Integer tsId = topicselectService.getOne(new QueryWrapper<Topicselect>().eq("gId",1)).getTsId();
        Integer score = scoreService.getOne(new QueryWrapper<Score>().eq("tsId",tsId)).getScore();
        System.out.println(score);
    }

    @Test
    void testCommitScore(){
        Integer tsId = topicselectService.getOne(new QueryWrapper<Topicselect>().eq("gId",1)).getTsId();
        Score score = scoreService.getOne(new QueryWrapper<Score>().eq("tsId",tsId));
        if(score != null){
            score.setScore(90);
            scoreService.updateById(score);
        }
        else {
            score.setTsId(tsId);
            score.setScore(90);
            scoreService.save(score);
        }
    }

    @Test
    void testSendMessage(){
        Integer tId = 2018001;
        String content = "催进度！";
        Integer gId = 1;

//        Date dNow = new Date( );
//        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");

        String time = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss").format(new Date());
//        System.out.println("当前时间为: " + time);

//        String time = "2020-10-25 21:55:11";

        Group group = groupService.getOne(new QueryWrapper<Group>().eq("gId",1));

        Message message1 = new Message();
        message1.setTId(tId);
        message1.setSId(group.getSId_1());
        message1.setContent(content);
        message1.setTime(time);

        Message message2 = new Message();
        message2.setTId(tId);
        message2.setSId(group.getSId_1());
        message2.setContent(content);
        message2.setTime(time);

        Message message3 = new Message();
        message3.setTId(tId);
        message3.setSId(group.getSId_1());
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

//    @Test
//    void testGetAllDirectSubjects(){
//        System.out.println(groupList);
//
//    }
}
