package com.jlabarca.dialogflow_webhook_boilerplate.service;

import com.jlabarca.dialogflow_webhook_boilerplate.alias.Student;
import lombok.extern.apachecommons.CommonsLog;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@CommonsLog
public class StudentService {

    @Autowired
    private SqlSession sqlSession;

    public List<Student> getStudents() {
        return sqlSession.selectList("getStudents");
    }

}
