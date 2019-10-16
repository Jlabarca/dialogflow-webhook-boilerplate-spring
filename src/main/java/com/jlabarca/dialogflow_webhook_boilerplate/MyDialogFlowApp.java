package com.jlabarca.dialogflow_webhook_boilerplate;

import com.google.actions.api.*;
import com.google.actions.api.response.ResponseBuilder;
import com.google.api.services.actions_fulfillment.v2.model.TableCard;
import com.google.api.services.actions_fulfillment.v2.model.TableCardCell;
import com.google.api.services.actions_fulfillment.v2.model.TableCardColumnProperties;
import com.google.api.services.actions_fulfillment.v2.model.TableCardRow;
import com.jlabarca.dialogflow_webhook_boilerplate.alias.Student;
import com.jlabarca.dialogflow_webhook_boilerplate.service.StudentService;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@CommonsLog
@Component
public class MyDialogFlowApp extends DialogflowApp {

    @Autowired
    private StudentService studentService;

    private Student currentStudent;

    private static final String[] SUGGESTIONS =
            new String[]{"Asistencia", "Rendimiento", "Anotaciones"};


    @ForIntent("sa.agent.student")
    public ActionResponse student(ActionRequest request) {

        ResponseBuilder responseBuilder = getResponseBuilder(request).add("Test answer");
        ActionResponse actionResponse = responseBuilder.build();
        log.info(actionResponse.toString());
        return actionResponse;
    }


    DecimalFormat df = new DecimalFormat("#,#");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    Random rn = new Random();

    @ForIntent("sa.agent.student - confirmar")
    public ActionResponse studentConfirmar(ActionRequest request) {
        ResponseBuilder responseBuilder = getResponseBuilder(request);
        log.info(request.getIntent());
        String answer = "";
        switch (request.getWebhookRequest().getQueryResult().getQueryText()){
            case "sí":
            case "si":
            case "afirmativo":

                String nota1 = (rn.nextInt(4) + 1)+","+(rn.nextInt(4) + 1);
                String nota2 = (rn.nextInt(4) + 1)+","+(rn.nextInt(4) + 1);
                String nota3 = (rn.nextInt(4) + 1)+","+(rn.nextInt(4) + 1);
                String nota4 = (rn.nextInt(4) + 1)+","+(rn.nextInt(4) + 1);

                if (!request.hasCapability(Capability.SCREEN_OUTPUT.getValue())) {
                    String notas = MessageFormat.format("Las últimas notas de {0} son: ", currentStudent.getName());
                    notas += nota1+" en Matemáticas ";
                    notas += nota2+" en Lenguaje ";
                    notas += nota3+" en Química ";
                    notas += nota4+" en Historia.";
                    answer = notas;
                    return responseBuilder.add(answer).build();
                }

                List<TableCardColumnProperties> columnProperties = new ArrayList<>();
                columnProperties.add(new TableCardColumnProperties().setHeader("Fecha"));
                columnProperties.add(new TableCardColumnProperties().setHeader("Asignatura"));
                columnProperties.add(new TableCardColumnProperties().setHeader("Nota"));

                String[] notas = {nota1,nota2,nota3,nota4};
                List<TableCardRow> rows = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    log.info(notas[i]);
                    List<TableCardCell> cells = new ArrayList<>();
                    cells.add(new TableCardCell().setText(LocalDateTime.now().format(formatter)));
                    cells.add(new TableCardCell().setText("Matemáticas"));
                    cells.add(new TableCardCell().setText(notas[i]));
                    rows.add(new TableCardRow().setCells(cells));
                }

                TableCard table =
                        new TableCard()
                                .setTitle("Notas Matemáticas")
                                .setSubtitle(currentStudent.getName())
                                .setColumnProperties(columnProperties)
                                .setRows(rows);

                return responseBuilder
                        .add("Últimas notas de "+currentStudent.getName()).add(table).addSuggestions(SUGGESTIONS).build();
            case "no":
                answer = "Lo siento, no sé a que alumno te refieres";
                break;
            default:
                answer = "Necesito que me confirmes con un si o un no";
                break;
        }

        return responseBuilder
                .add(answer)
                .add("Necesitas saber algo más?")
                .addSuggestions(SUGGESTIONS).build();

    }

}