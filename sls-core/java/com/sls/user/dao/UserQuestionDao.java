package com.sls.user.dao;

import com.core.page.entity.Page;
import com.core.page.entity.PageParameter;
import com.sls.user.entity.UserQuestion;

import java.util.List;

public interface UserQuestionDao {

    List<UserQuestion> getUserAnsWerQuestionsByAnswerUserId(int answerUserId);

    List<UserQuestion> getUserAskQuestionsByAnswerUserId(int answerUserId);

    List<UserQuestion> findNoAnswerQuestions(int answerUserId, int askUserId);

    void addUserQuestion(UserQuestion userQuestion);

    int getNewQuestionNumByUserId(int userId);

    int getNewAnswerNumByUserId(int userId);

    List<UserQuestion> getAskQuestionsByAskUserId(int askUserId);

    List<UserQuestion> getUserQuestionsByAskUserId(int answerUserId);

    UserQuestion getQuestionInfoByQuestionId(int questionId);

    void cancelNewAnswerByQuestionId(int questionId);

    void changeQuestionAskContentByQuestionId(UserQuestion userQuestion);

    void setNewAskByQuestionId(int questionId);

    void cancelNewAskByQuestionId(int questionId);

    void changeQuestionAnswerContentByQuestionId(UserQuestion userQuestion);

    void setNewAnswerByQuestionId(int questionId);

    Page<UserQuestion> listAllQuestion(PageParameter pageParameter, UserQuestion userQuestion);

    void delQuestionByQuestionId(int questionId);
}
