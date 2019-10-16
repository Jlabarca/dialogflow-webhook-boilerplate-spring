package com.jlabarca.dialogflow_webhook_boilerplate.controller;

import com.jlabarca.dialogflow_webhook_boilerplate.MyDialogFlowApp;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

@RestController
@CommonsLog
public class DialogflowWebhookController {

    @Autowired
    private MyDialogFlowApp schoolAssistantApp;

    @RequestMapping(value = "/", method = RequestMethod.POST, produces = { "application/json" })
    String serveAction(@RequestBody String body, @RequestHeader Map<String, String> headers) {
        try {
            return schoolAssistantApp.handleRequest(body, headers).get();
        } catch (InterruptedException | ExecutionException e) {
            return handleError(e);
        }
    }

    private String handleError(Exception e) {
        e.printStackTrace();
        log.error("Error in App.handleRequest ", e);
        return "Error handling the intent - " + e.getMessage();
    }


}