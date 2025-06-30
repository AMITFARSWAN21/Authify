package com.authify.service;

import com.authify.config.GeminiConfig;
import com.authify.entity.Marks;
import com.authify.repository.MarksRepository;
import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StudyPlanAIService {

    private final RestTemplate restTemplate;
    private final GeminiConfig geminiConfig;
    private final MarksRepository marksRepository;

    private static final String GEMINI_API_URL =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key=";

    public String generatePersonalizedStudyPlan(String studentName, Map<String, String> syllabusMap) {
        List<Marks> marksList = marksRepository.findAllByName(studentName);

        if (marksList.isEmpty()) {
            return "No marks found for student: " + studentName;
        }

        StringBuilder studentInfo = new StringBuilder();
        for (Marks mark : marksList) {
            int total = Integer.parseInt(mark.getMidTerm()) +
                    Integer.parseInt(mark.getAssignment()) +
                    Integer.parseInt(mark.getTutorials()) +
                    Integer.parseInt(mark.getPresentation()) +
                    Integer.parseInt(mark.getEndTerm());

            double percentage = (total / 200.0) * 100;

            studentInfo.append("- Subject: ").append(mark.getSubject())
                    .append(" | Score: ").append((int) percentage)
                    .append("%%\n"); // ✅ escape % properly
        }

        StringBuilder syllabusInfo = new StringBuilder();
        syllabusMap.forEach((subject, topics) -> syllabusInfo
                .append("- ").append(subject).append(": ").append(topics).append("\n"));

        String prompt = """
You are a world-class academic mentor and productivity expert.

Given the student's subject-wise scores and a list of topics to cover, design a **7-day intense and optimized study schedule**. The plan should not only cover syllabus topics but also include motivation, revision, and smart breaks to maximize retention.

🧠 Use these rules:
1. Subjects with < 60%%: allocate 3+ hours daily with detailed topic breakdown.
2. Subjects with 60%%–80%%: allocate 1.5–2.5 hours daily.
3. Subjects with >80%%: allocate 1–1.5 hours only for reinforcement.
4. Distribute topics wisely — easy to hard, spaced repetition, mix theory + problem-solving.
5. Add short breaks after 90 mins of focused study.
6. Keep total study time between 6–7 hours max per day.

📈 Include productivity hacks like Pomodoro, brain dump sessions, and active recall.

🎯 Goals:
- Build confidence in weak subjects.
- Master key topics with efficient effort.
- Stay mentally fresh and consistent.

📅 Format strictly as:
- Use emoji headers (🎯, 💡, 📅, ✅)
- Use bullet points: (•, ✅, ➤)
- Use section dividers like ━━━━━━━━━━━━━━━━━━━━━━━━
- Keep plan clean and readable for white-background UI

🎯 WEEKLY STUDY STRUCTURE
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
• Monday-Wednesday: New concept learning
• Thursday-Friday: Practice and application
• Saturday: Review and self-assessment
• Sunday: Light revision or rest

💡 STUDY TIPS FOR SUCCESS
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
✅ Use the Pomodoro Technique (25 min study + 5 min break)
✅ Practice active recall and self-quizzing
✅ Create mind maps and visuals for complex topics
✅ Take breaks after every 90 mins of focused study
✅ Avoid passive reading, use active techniques

👨‍🎓 Student Score Summary:
%s

📚 Syllabus Breakdown:
%s

📅 7-DAY STUDY PLAN
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
""".formatted(studentInfo.toString(), syllabusInfo.toString());


        return callGemini(prompt);
    }



    private String callGemini(String prompt) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

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
            JsonObject jsonResponse = JsonParser.parseString(response).getAsJsonObject();

            String rawPlan = jsonResponse.getAsJsonArray("candidates")
                    .get(0).getAsJsonObject()
                    .getAsJsonObject("content")
                    .getAsJsonArray("parts")
                    .get(0).getAsJsonObject()
                    .get("text").getAsString();

            // 🔧 Beautify output
            String beautifiedPlan = rawPlan
                    .replaceAll("(?m)^\\*\\s+", "• ")
                    .replaceAll("(?m)^-\\s+", "➤ ")
                    .replaceAll("(?m)^\\+\\s+", "✅ ")
                    .replaceAll("\\*\\*(.*?)\\*\\*", "🔹 $1");

            return beautifiedPlan;

        } catch (Exception e) {
            return "Error generating study plan: " + e.getMessage();
        }
    }
}