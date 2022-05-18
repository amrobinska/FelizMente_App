package com.example.felizmente.activities.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizModal {
    private int id;
    private String question,url, option1, option2, option3, answer;
}
