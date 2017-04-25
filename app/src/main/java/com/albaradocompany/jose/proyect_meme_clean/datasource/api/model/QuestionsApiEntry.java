package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;

/**
 * Created by jose on 25/04/2017.
 */

public class QuestionsApiEntry {
    String idQuestion = "";
    String question = "";

    public Question parseQuestion() {
        Question obj = new Question();
        obj.setIdQuestion(idQuestion);
        obj.setQuestion(question);
        return obj;
    }
}
