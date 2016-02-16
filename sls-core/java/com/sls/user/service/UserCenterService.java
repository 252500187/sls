package com.sls.user.service;

import com.sls.user.entity.User;
import com.sls.user.entity.UserAttention;
import com.sls.user.entity.UserQuestion;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserCenterService {
    void toUserCenter(HttpServletRequest request);

    void selectModule(HttpServletRequest request, int module);

    void toUserInfo(HttpServletRequest request);

    void getAllRegisterScormInfo(HttpServletRequest request);

    void getAllCollectScormInfo(HttpServletRequest request);

    void getAllUpScormInfo(HttpServletRequest request);

    void getAskQuestions(HttpServletRequest request);

    void getUserQuestions(HttpServletRequest request);

    void getQuestionInfoAsk(HttpServletRequest request, HttpSession session, int questionId);

    void changeQuestionAskContent(UserQuestion userQuestion);

    void getQuestionInfoAnswer(HttpServletRequest request, HttpSession session, int questionId);

    void changeQuestionAnswerContent(UserQuestion userQuestion);

    void cancelNewAnswerByQuestionId(int questionId);

    void cancelNewAskByQuestionId(int questionId);

    List<UserAttention> getAttentionUsers();

    void getRecordInfo(HttpServletRequest request, int scormId);
}
