package com.albaradocompany.jose.proyect_meme_clean.global.model;

/**
 * Created by jose on 25/04/2017.
 */

public class Question {
    private String idQuestion;
    private String question;

    public Question() {
        this.idQuestion = "";
        this.question = "";
    }

    public Question(String idQuestion, String question) {
        this.idQuestion = idQuestion;
        this.question = question;
    }

    public String getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(String idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
