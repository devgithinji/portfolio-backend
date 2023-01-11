package com.densoft.portfolio.service.message;

import com.densoft.portfolio.dto.MessageDTO;
import com.densoft.portfolio.model.Message;

import javax.mail.MessagingException;
import java.util.List;

public interface MessageService {

    List<Message> getMessages();

    Message getMessage(Integer messageId);

    void createMessage(MessageDTO messageDTO) throws MessagingException;

    void updateReadStatus(Integer messageId);


    void deleteMessage(Integer messageId);
}
