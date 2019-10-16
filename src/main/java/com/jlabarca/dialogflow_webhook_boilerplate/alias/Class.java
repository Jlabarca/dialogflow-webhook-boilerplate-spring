package com.jlabarca.dialogflow_webhook_boilerplate.alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Class {
    private int id;
    private String name;
    private List<Student> students;
}
