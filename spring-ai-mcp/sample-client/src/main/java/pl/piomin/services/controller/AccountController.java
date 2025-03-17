package pl.piomin.services.controller;

import io.modelcontextprotocol.client.McpSyncClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final static Logger LOG = LoggerFactory.getLogger(PersonController.class);
    private final ChatClient chatClient;

//    @Autowired
//    private List<McpSyncClient> mcpSyncClients;

    public AccountController(ChatClient.Builder chatClientBuilder,
                            ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultTools(tools)
//                .defaultAdvisors(
//                        new PromptChatMemoryAdvisor(chatMemory),
//                        new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/count-by-nationality/{nationality}")
    String countByPersonId(@PathVariable String personId) {
        PromptTemplate pt = new PromptTemplate("""
                How many accounts has person with {personId} ID ?
                """);
        Prompt p = pt.create(Map.of("personId", personId));
//        loadPromptByName("persons-by-nationality", nationality);
        return this.chatClient.prompt(p)
                .call()
                .content();
    }
}
