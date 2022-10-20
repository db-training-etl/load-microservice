package com.db.load.service;

import com.db.load.exception.NotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@AllArgsConstructor
public class FileLoaderService {

    ObjectMapper objectMapper;

    public <T> List<T> load(String fileRoute, Class<T> typeClass) throws IOException {

        File file = new File(fileRoute);

        if (!file.exists()) {
            throw new NotFoundException("No file exists at " + fileRoute);
        }

        return this.convertStringToEntityList(this.readFile(new FileInputStream(file)), typeClass);
    }

    public <T> List<T> convertStringToEntityList(String fileContent, Class<T> typeClass) throws JsonProcessingException {
        JavaType listTradeType = objectMapper.getTypeFactory().constructParametricType(List.class, typeClass);
        return objectMapper.readValue(fileContent, listTradeType);
    }

    public String readFile(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(inputStreamReader)) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }

        }
        return resultStringBuilder.toString();
    }
}
