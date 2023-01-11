package com.densoft.portfolio.service.message;

import com.densoft.portfolio.dto.MessageDTO;
import com.densoft.portfolio.exceptions.ResourceNotFoundException;
import com.densoft.portfolio.model.Message;
import com.densoft.portfolio.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Value("${spring.mail.username}")
    String sender;
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
    public void createMessage(MessageDTO messageDTO) throws MessagingException {
        Message message = new Message(messageDTO.getName(), messageDTO.getEmail(), messageDTO.getMessage());
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        String emailFrom = message.getEmail();
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
//        helper.setSubject("Portfolio Contact");
//        helper.setFrom(emailFrom);
//        helper.setTo(sender);
//        helper.setText("<p>" + message.getMessage() + "</p>", true);
//        mailSender.send(mimeMessage);
        messageRepository.save(message);
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
