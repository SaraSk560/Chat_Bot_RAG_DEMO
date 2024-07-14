package org.sid.chatbot_rag_demo.services;

import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Vector;

//@Service
@BrowserCallable//tous les methodes qui se trouve dans cette classe je voudrais les appeler directement via front end
//pour acce nessacite auth
@AnonymousAllowed// ("forcément besoin de passser via l'aith")
public class ChatAiService {
    private ChatClient chatclient;
    private VectorStore vectorStore;

    @Value("classpath:/prompts/prompt-template.st")
    private Resource promptResource;

    public ChatAiService(ChatClient.Builder builder)
    {
        this.chatclient=builder.build();
    }


    public String ragChat(String question)
    {
        List<Document> documents = vectorStore.similaritySearch(question);
        List<String> context = documents.stream().map(Document::getContent).toList();
        PromptTemplate promptTemplate =new PromptTemplate(promptResource);
        Prompt prompt=promptTemplate.create(Map.of("context",context,"question",question));

        return chatclient.prompt(prompt)

                .call()
                .content();
    }

}
