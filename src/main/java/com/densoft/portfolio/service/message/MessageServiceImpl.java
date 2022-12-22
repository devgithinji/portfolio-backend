package com.densoft.portfolio.service.message;

import com.densoft.portfolio.dto.MessageDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Message;
import com.densoft.portfolio.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public List<Message> getMessages() {
        return messageRepository.findAll();
    }

    @Override
    public Message getMessage(Integer messageId) {
        return getExistingMessage(messageId);
    }


    @Override
    public Message createMessage(MessageDTO messageDTO) {
        Message message = new Message(messageDTO.getEmail(), messageDTO.getMessage());
        return messageRepository.save(message);
    }

    @Override
    public void updateReadStatus(Integer messageId) {
        Message message = getExistingMessage(messageId);
        message.setRead(true);
        messageRepository.save(message);

    }

    @Override
    public void deleteMessage(Integer messageId) {
        messageRepository.deleteById(messageId);
    }

    private Message getExistingMessage(Integer messageId) {
        return messageRepository.findById(messageId).orElseThrow(() -> new ResourceNotFoundException("message", "Id", String.valueOf(messageId)));
    }
}
