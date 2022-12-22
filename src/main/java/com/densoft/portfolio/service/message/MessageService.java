package com.densoft.portfolio.service.message;

import com.densoft.portfolio.dto.MessageDTO;
import com.densoft.portfolio.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> getMessages();

    Message getMessage(Integer messageId);

    Message createMessage(MessageDTO messageDTO);

    void updateReadStatus(Integer messageId);


    void deleteMessage(Integer messageId);
}
