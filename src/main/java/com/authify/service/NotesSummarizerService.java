package com.authify.service;


import com.authify.config.GeminiConfig;
import com.authify.entity.NotesSummarizer;
import com.authify.repository.NotesSummarizerRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NotesSummarizerService {

    private final NotesSummarizerRepository notesSummarizerRepository;
    private final RestTemplate restTemplate;
    private final GeminiConfig geminiConfig;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public NotesSummarizer processAndSaveReport(MultipartFile file) throws IOException {
        String text = extractTextFromPDF(file);
        String summary = callGeminiAPI(
                "You are a smart note summarizer. Read the following academic or medical notes carefully and summarize the key points in clean, well-structured bullet points.\n\n" +
                        "Notes:\n" + text + "\n\n" +
                        "Instructions:\n" +
                        "• Format the summary only using bullet points (use '-' or '•').\n" +
                        "• Keep each bullet point concise and meaningful.\n" +
                        "• Avoid paragraphs, introductions, or conclusions.\n" +
                        "• Focus on key terms, findings, definitions, and actionable items.\n" +
                        "• Do not repeat lines or add unnecessary filler.\n" +
                        "• Final output should be clean, short, and to-the-point.\n"
        );

        NotesSummarizer notes = NotesSummarizer.builder()
                .pdfText(text)
                .summary(summary)
                .build();

        return notesSummarizerRepository.save(notes);
    }


    private String extractTextFromPDF(MultipartFile file) throws IOException {
        try (PDDocument document = PDDocument.load(file.getInputStream())) {
            return new PDFTextStripper().getText(document);
        }
    }

    private String callGeminiAPI(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build the correct request body
        JsonObject textPart = new JsonObject();
        textPart.addProperty("text", prompt);

        JsonArray parts = new JsonArray();
        parts.add(textPart);

        JsonObject content = new JsonObject();
        content.add("parts", parts);

        JsonArray contents = new JsonArray();
        contents.add(content);

        JsonObject requestBody = new JsonObject();
        requestBody.add("contents", contents);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        String url = GEMINI_API_URL + geminiConfig.getApiKey();

        try {
            String response = restTemplate.postForObject(url, request, String.class);

            JsonObject jsonResponse = com.google.gson.JsonParser.parseString(response).getAsJsonObject();

            return jsonResponse.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();

        } catch (Exception e) {
            return "Error calling Gemini API: " + e.getMessage();
        }
    }
}
