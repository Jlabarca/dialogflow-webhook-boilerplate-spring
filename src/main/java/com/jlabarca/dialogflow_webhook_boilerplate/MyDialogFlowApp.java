package com.jlabarca.dialogflow_webhook_boilerplate;

import com.google.actions.api.ActionRequest;
import com.google.actions.api.ActionResponse;
import com.google.actions.api.DialogflowApp;
import com.google.actions.api.ForIntent;
import com.google.actions.api.response.ResponseBuilder;
import com.jlabarca.dialogflow_webhook_boilerplate.service.StudentService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@CommonsLog
@Component
public class MyDialogFlowApp extends DialogflowApp {

    @Autowired
    private StudentService studentService;


    @ForIntent("sa.agent.students")
    public ActionResponse student(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request).add(studentService.getStudents().toString());
        ActionResponse actionResponse = responseBuilder.build();
        return actionResponse;
    }

}