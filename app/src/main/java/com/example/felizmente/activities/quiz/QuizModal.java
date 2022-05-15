package com.example.felizmente.activities.quiz;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class QuizModal {
    private int id;
    private String question,url, option1, option2, option3, answer;
}
