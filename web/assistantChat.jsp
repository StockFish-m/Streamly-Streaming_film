<%-- 
    Document   : assistantChat
    Created on : Jul 1, 2025, 5:38:06 PM
    Author     : DELL
--%>

<%@page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%
    // Khởi tạo lịch sử nếu chưa có
    List<String[]> chatHistory = (List<String[]>) session.getAttribute("chatHistory");
    if (chatHistory == null) {
        chatHistory = new ArrayList<>();
        session.setAttribute("chatHistory", chatHistory);
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Chat with Assistant</title>
        <style>
            body {
                font-family: Arial;
                background: #0d0d1a;
                margin: 0;
                padding: 0;
            }

            #chatToggle {
                position: fixed;
                bottom: 20px;
                right: 20px;
                width: 60px;
                height: 60px;
                background-color: #00bfff;
                border-radius: 50%;
                border: none;
                cursor: pointer;
                box-shadow: 0 0 15px rgba(0,0,0,0.3);
                z-index: 999;
            }

            #chatToggle::before {
                content: "💬";
                font-size: 24px;
            }

            .chat-popup {
                position: fixed;
                bottom: 90px;
                right: 20px;
                width: 360px;
                min-height: 300px;
                max-height: 80vh;
                background: #fff;
                border-radius: 10px;
                box-shadow: 0 0 20px rgba(0,0,0,0.5);
                display: none;
                flex-direction: column;
                z-index: 1000;
                overflow: hidden;
            }

            .chat-box {
                flex: 1;
                overflow-y: auto;
                display: flex;
                flex-direction: column;
                border-radius: 8px;
                padding: 5px;
                border: 1px solid #ddd;
                min-height: 150px;
                max-height: 400px;
            }


            .chat-container {
                padding: 10px;
                display: flex;
                flex-direction: column;
                height: 100%;
            }



            .bubble {
                padding: 10px 15px;
                margin: 8px;
                border-radius: 15px;
                max-width: 80%;
                word-wrap: break-word;
            }

            .user {
                background: #dcf8c6;
                align-self: flex-end;
                text-align: right;
            }

            .assistant {
                background: #eee;
                align-self: flex-start;
            }

            .typing {
                font-style: italic;
                color: #888;
                font-size: 14px;
                margin: 5px 10px;
            }

            .typing span {
                animation: blink 1.2s infinite;
            }

            .dot-1 {
                animation-delay: 0s;
            }
            .dot-2 {
                animation-delay: 0.2s;
            }
            .dot-3 {
                animation-delay: 0.4s;
            }

            @keyframes blink {
                0% {
                    opacity: 0.2;
                    transform: translateY(0);
                }
                50% {
                    opacity: 1;
                    transform: translateY(-5px);
                }
                100% {
                    opacity: 0.2;
                    transform: translateY(0);
                }
            }

            form {
                display: flex;
                margin-top: 10px;
            }

            input[type="text"] {
                flex-grow: 1;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 10px;
            }

            button.send-btn {
                padding: 10px 20px;
                border: none;
                background: #2196f3;
                color: white;
                border-radius: 10px;
                margin-left: 10px;
            }

            .chat-header {
                display: flex;
                justify-content: space-between;
                align-items: center;
                padding: 8px 12px;
                border-bottom: 1px solid #ccc;
            }

            .chat-header span {
                font-weight: bold;
            }

            .chat-close {
                cursor: pointer;
                font-size: 18px;
                color: #888;
            }
        </style>
    </head>
    <body>
        <!-- Nút bật chat -->
        <button id="chatToggle" title="Chat"></button>

        <!-- Khung chat popup -->
        <div class="chat-popup" id="chatPopup">
            <div class="chat-header">
                <span>🎬 Assistant</span>
                <span class="chat-close" onclick="toggleChat()">×</span>
            </div>

            <div class="chat-container">
                <div class="chat-box" id="chatBox">
                    <% for (String[] msg : chatHistory) {
                            String sender = msg[0];
                            String content = msg[1];
                    %>
                    <div class="bubble <%= sender.equals("user") ? "user" : "assistant"%>"><%= content%></div>
                    <% }%>
                </div>

                <!-- AI đang gõ -->
                <div id="typingIndicator" class="typing" style="display: none;">
                    AI đang trả lời<span class="dot-1">.</span><span class="dot-2">.</span><span class="dot-3">.</span>
                </div>

                <form id="chatForm" onsubmit="return sendMessage();">
                    <input type="text" id="userInput" placeholder="Nhập câu hỏi..." required>
                    <button type="submit" class="send-btn">Gửi</button>
                </form>
            </div>
        </div>

        <script>
            // Hiển thị/ẩn khung chat
            document.getElementById("chatToggle").addEventListener("click", toggleChat);
            function toggleChat() {
                const popup = document.getElementById("chatPopup");
                popup.style.display = (popup.style.display === "none" || popup.style.display === "") ? "flex" : "none";
                scrollToBottom();
            }

            // Cuộn xuống cuối khung chat
            function scrollToBottom() {
                const chatBox = document.getElementById('chatBox');
                chatBox.scrollTop = chatBox.scrollHeight;
            }

            // Gửi tin nhắn
            function sendMessage() {
                const input = document.getElementById('userInput');
                const message = input.value.trim();
                if (!message)
                    return false;

                const chatBox = document.getElementById('chatBox');
                const typing = document.getElementById('typingIndicator');

                const userBubble = document.createElement('div');
                userBubble.className = 'bubble user';
                userBubble.textContent = message;
                chatBox.appendChild(userBubble);

                scrollToBottom();

                input.value = '';
                typing.style.display = 'block';

                fetch('assistantChat', {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'},
                    body: 'message=' + encodeURIComponent(message)
                })
                        .then(res => res.text())
                        .then(html => {
                            const temp = document.createElement('div');
                            temp.innerHTML = html;

                            const newChatBox = temp.querySelector('#chatBox');
                            const chatBox = document.getElementById('chatBox');
                            chatBox.innerHTML = newChatBox.innerHTML;

                            document.getElementById('typingIndicator').style.display = 'none';
                            scrollToBottom();
                        });

                return false;
            }
        </script>
    </body>
</html>
