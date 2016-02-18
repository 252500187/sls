package com.sls.user.dao;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.user.entity.BackMessage;

import java.util.List;

public interface BackMessageDao {

    void addBackMessage(BackMessage backMessage);

    List<BackMessage> getNewMessageByUserId(int userId);

    void cancelMessageByMessageId(int messageId);

    Page<BackMessage> getMessagePageList(PageParameter pageParameter, BackMessage backMessage);

    void delMessageByMessageId(int messageId);

    BackMessage getMessageInfoByMessageId(int messageId);
}
