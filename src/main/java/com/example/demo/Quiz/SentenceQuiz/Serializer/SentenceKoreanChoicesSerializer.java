package com.example.demo.Quiz.SentenceQuiz.Serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.List;

public class SentenceKoreanChoicesSerializer extends JsonSerializer<List<String>> {

    @Override
    public void serialize(List<String> value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeObject(value.stream().map(List::of).toList());
    }
}
