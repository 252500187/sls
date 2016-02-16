<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | AskQuestions</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../../includes/common.jsp" %>
</head>
<body class="page-header-fixed" style="background-color: transparent">
<div class="row">
    <div class="col-md-12">
        <ul class="page-breadcrumb breadcrumb">
            <li>
                <i class="fa fa-home"></i>
                <a onclick="parent.window.location.href=''">首页</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a>个人中心</a>
                <i class="fa fa-angle-right"></i>
            </li>
            <li>
                <a>我的提问</a>
            </li>
        </ul>
    </div>
</div>
<div class="page-content" style="min-height:780px">
    <c:if test="${fn:length(questions)<1}">
        还未提问问题
    </c:if>
    <c:if test="${fn:length(questions)>0}">
        <div class="row mix-grid">
            <div class="col-md-12">
                <h3 class="form-section">未回答</h3>
                <hr/>
                <c:forEach var="question" items="${questions}">
                    <c:if test="${question.answerContent==''}">
                        <div class="col-md-2 col-sm-2 mix mix_all" style=" display: block;">
                            <a onclick="lookQuestion('${question.questionId}')">
                                <img src="${question.imgUrl}" class="img-responsive"
                                     alt="${question.userName}" style="height: 100px;">
                                    ${question.askDate}<br/>${question.userName}
                            </a>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <div class="row mix-grid">
            <div class="col-md-12">
                <h3 class="form-section">已回答</h3>
                <hr/>
                <c:forEach var="question" items="${questions}">
                    <c:if test="${question.answerContent!=''}">
                        <div class="col-md-2 col-sm-2 mix mix_all" style=" display: block;">
                            <a onclick="lookQuestion('${question.questionId}')">
                                <img src="${question.imgUrl}" class="img-responsive"
                                     alt="${question.userName}" style="height: 100px;">
                                    ${question.askDate}<br/>${question.userName}
                            </a>
                        </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
    </c:if>
</div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        Portfolio.init();
    })

    function lookQuestion(questionId) {
        parent.$(".modal-title").html("问题信息");
        parent.$('#alertIframe').modal('show');
        parent.$("#iframeInfo").attr("src", basePath + "user/center/lookQuestionInfoAsk?questionId=" + questionId);
    }
</script>