package com.jlabarca.dialogflow_webhook_boilerplate.alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private int studentId;
    private String fname;
    private String lname;
}
