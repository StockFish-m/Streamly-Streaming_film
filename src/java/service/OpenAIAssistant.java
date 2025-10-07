/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import org.json.JSONArray;

public class OpenAIAssistant {

    private static final String API_KEY = "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";
    private static final String ASSISTANT_ID = "asst_rOs9KEJXB6RDUqfbAqQ0E3NZ";

    private static final OkHttpClient client = new OkHttpClient();
    private static final MediaType JSON = MediaType.get("application/json");

    public static void main(String[] args) throws IOException, InterruptedException {
        // thread
        String threadId = createThread();

        // send message
        sendMessage(threadId, "Can i what for free?");

        // run assistant?
        String runId = runAssistant(threadId);

        // ienumurator
        waitForCompletion(threadId, runId);

        // reply
        getMessages(threadId);
    }

    public static String askAssistant(String threadId, String userMessage) throws IOException, InterruptedException {

        sendMessage(threadId, userMessage);
        String runId = runAssistant(threadId);
        waitForCompletion(threadId, runId);
        return getMessages(threadId);
    }

    public static String createThread() throws IOException {
        RequestBody body = RequestBody.create(JSON, "{}");
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/threads")
                .post(body)
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body().string();  // Only call once!
            System.out.println("Response: " + responseBody); // You can print it

            JSONObject json = new JSONObject(responseBody);  // Reuse the stored string
            return json.getString("id");  // or whatever field you need
        }
    }

    static void sendMessage(String threadId, String message) throws IOException {
        JSONObject body = new JSONObject()
                .put("role", "user")
                .put("content", message);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/threads/" + threadId + "/messages")
                .post(RequestBody.create(JSON, body.toString()))
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .build();

        client.newCall(request).execute().close();
    }

    static String runAssistant(String threadId) throws IOException {
        JSONObject body = new JSONObject()
                .put("assistant_id", ASSISTANT_ID);

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/threads/" + threadId + "/runs")
                .post(RequestBody.create(JSON, body.toString()))
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .build();

        try (Response response = client.newCall(request).execute()) {
            JSONObject json = new JSONObject(response.body().string());
            return json.getString("id");
        }
    }

    static void waitForCompletion(String threadId, String runId) throws IOException, InterruptedException {
        while (true) {
            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/threads/" + threadId + "/runs/" + runId)
                    .header("Authorization", "Bearer " + API_KEY)
                    .header("OpenAI-Beta", "assistants=v2")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                JSONObject json = new JSONObject(response.body().string());
                String status = json.getString("status");
                if ("completed".equals(status)) {
                    break;
                }
            }

            Thread.sleep(1000);
        }
    }

    static String getMessages(String threadId) throws IOException {
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/threads/" + threadId + "/messages")
                .header("Authorization", "Bearer " + API_KEY)
                .header("OpenAI-Beta", "assistants=v2")
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseText = response.body().string();
            System.out.println("Assistant response:\n" + responseText);
            return extractAssistantReply(responseText);
        } catch (Exception e) {
            e.printStackTrace();
            return "Something went horribly wrong!";
        }
    }

    public static String extractAssistantReply(String jsonString) {
        JSONObject json = new JSONObject(jsonString);
        JSONArray messages = json.getJSONArray("data");

        for (int i = 0; i < messages.length(); i++) {
            JSONObject message = messages.getJSONObject(i);
            if ("assistant".equals(message.getString("role"))) {
                JSONArray contentArray = message.getJSONArray("content");
                for (int j = 0; j < contentArray.length(); j++) {
                    JSONObject content = contentArray.getJSONObject(j);
                    if ("text".equals(content.getString("type"))) {
                        JSONObject text = content.getJSONObject("text");
                        return text.getString("value");
                    }
                }
            }
        }

        return "No assistant reply found.";
    }

}
