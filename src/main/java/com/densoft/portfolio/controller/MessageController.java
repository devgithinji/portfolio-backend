package com.densoft.portfolio.controller;

import com.densoft.portfolio.dto.MessageDTO;
import com.densoft.portfolio.model.Message;
import com.densoft.portfolio.service.message.MessageService;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public List<Message> getMessages() {
        return messageService.getMessages();
    }

    @GetMapping("{messageId}")
    public Message getMessage(@PathVariable("messageId") Integer messageId) {
        return messageService.getMessage(messageId);
    }

    @PostMapping
    private String createMessage(@Valid @RequestBody MessageDTO messageDTO) throws MessagingException {
        messageService.createMessage(messageDTO);
        return "message sent successfully!";
    }

    @DeleteMapping("{messageId}")
    private String deleteMessage(@PathVariable("messageId") Integer messageId) {
        messageService.deleteMessage(messageId);
        return "message deleted successfully";
    }
}
