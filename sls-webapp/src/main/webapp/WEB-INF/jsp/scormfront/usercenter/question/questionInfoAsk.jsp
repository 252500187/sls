<%--@elvariable id="scormId" type="java.lang.String"--%>
<%--@elvariable id="myEvaluateScore" type="java.lang.Integer"--%>
<%--@elvariable id="AllComments" type="java.util.List<com.sls.scorm.entity.ScormSummarize>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | AskQuestionInfo</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../../includes/common.jsp" %>
</head>
<body class="page-header-fixed" style="background: #ffffff">
<div class="page-container">
    <div class="page-content-wrapper">
        <div class="page-content">
            <div class="portlet box">
                <div class="portlet-body form">
                    <div class="portlet-body form">
                        <div class="form-horizontal">
                            <div class="form-body">
                                <div class="form-group ">
                                    <label class="control-label col-md-2">提问日期</label>${question.askDate}
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">提问用户</label>${question.userName}
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">问题描述</label>

                                    <div class="col-md-5">
                                        <textarea class="form-control"
                                                  id="askContent">${question.askContent}</textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="control-label col-md-2">问题回答</label>

                                    <div class="col-md-5">
                                        ${question.answerContent}
                                    </div>
                                </div>
                            </div>
                            <div class="form-actions fluid">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="col-md-offset-3 col-md-9">
                                            <a class="btn purple" onclick="changeQuestionAskContent()">
                                                <i class="fa fa-check"></i>
                                                修改
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $.ajax({
        url: basePath + "user/center/cancelNewAnswerByQuestionId",
        data: {
            questionId: "${question.questionId}"
        },
        type: "POST"
    });

    function changeQuestionAskContent() {
        $.ajax({
            url: basePath + "user/center/changeQuestionAskContent",
            data: {
                questionId: "${question.questionId}",
                askContent: $("#askContent").val().trim()
            },
            type: "POST",
            success: function () {
                top.$('#alertIframe').modal("hide");
                top.$("#alertPromptMessage").html("修改成功");
                top.$(".modal-title").html("提示");
                top.$("#alertPrompt").modal("show");
            },
            error: doError
        })
    }
</script>