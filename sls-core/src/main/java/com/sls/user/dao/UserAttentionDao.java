package com.sls.user.dao;

import com.sls.user.entity.User;
import com.sls.user.entity.UserAttention;

import java.util.List;

public interface UserAttentionDao {

    List<UserAttention> findAttention(int userId, int userAttentionId);

    List<UserAttention> findInUseAttention(int userId, int userAttentionId);

    void addUserAttention(UserAttention userAttention);

    void changeUserAttentionState(UserAttention userAttention);

    List<UserAttention> getAttentionUsersByUserId(int userId);

    void clearAllNewMessageByUserIdAndAttentionUserId(int userId, int attentionUserId);

    void countNewMessageByAttentionUserId(int attentionUserId);

    List<User> getAttentionUserUsersByUserId(int attentionUserId);
}
