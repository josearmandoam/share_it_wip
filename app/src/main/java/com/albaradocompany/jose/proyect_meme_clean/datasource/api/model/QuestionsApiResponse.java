package com.albaradocompany.jose.proyect_meme_clean.datasource.api.model;

import com.albaradocompany.jose.proyect_meme_clean.global.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jose on 25/04/2017.
 */

public class QuestionsApiResponse {
    List<QuestionsApiEntry> preguntas;

    public List<Question> parseQuestions() {
        List<Question> list = new ArrayList<Question>();
        if (preguntas == null)
            return list;

        for (QuestionsApiEntry e : preguntas) {
            list.add(e.parseQuestion());
        }
        return list;
    }
}
