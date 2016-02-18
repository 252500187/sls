<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | AddQuestion</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-minimal.css"/>" rel="stylesheet"
          type="text/css"/>
</head>
<body class="page-header-fixed" style="background-color: #ffffff">
<div class="row">
    <div class="col-md-12">
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
                        <a>提交问题</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="portlet box">
            <div class="portlet-body form">
                <form class="form-horizontal" method="post" id="addQuestion">
                    <div class="form-body">
                        <div class="form-group">
                            <label class="control-label col-md-2">问题描述</label>

                            <div class="col-md-3">
                                <textarea class="form-control"
                                          id="questionDescribe" name="questionDescribe" value=""/></textarea>
                            </div>
                        </div>
                        <%--<div class="form-group">--%>
                        <%--<hr/>--%>
                        <%--<label class="control-label col-md-2">选择回答人</label>--%>

                        <%--<div class="col-md-3">--%>
                        <%--<input id="isScorm" type="checkbox" class="make-switch" checked data-on-color="primary"--%>
                        <%--data-off-color="info">--%>
                        <%--</div>--%>
                        <%--</div>--%>
                        <div class="form-group" id="attentionUsers">
                            <label class="control-label col-md-2">回答用户</label>

                            <div class="col-md-3">
                                <select id="answerUserId" class="form-control input-medium select2me"
                                        data-placeholder="选择...">
                                    <%--<option id="voidUser"></option>--%>
                                    <c:forEach var="user" items="${attentionUsers}">
                                        <option value="${user.userId}">${user.userName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-offset-3 col-md-9">
                                    <button class="btn purple" type="submit"><i
                                            class="fa fa-check"></i>
                                        提交
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    $(function () {
        Metronic.init();
        Layout.init();
    });

    $('#addQuestion').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        rules: {
            questionDescribe: {
                required: true
            },
            answerUserId: {
                required: true
            }
        },
        messages: {
            questionDescribe: {
                required: "请输入问题描述"
            },
            answerUserId: {
                required: "请选择回答用户"
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function (error, element) {
//                error.insertAfter(element.parent().parent());
            error.insertAfter(element);
        },
        submitHandler: function () {
            submitQuestion();
        }
    });

    function submitQuestion() {
        $.ajax({
            url: basePath + "user/info/addUserQuestion",
            dataType: "json",
            type: "post",
            data: {
                answerUserId: $("#answerUserId").val(),
                questionDescribe: $("#questionDescribe").val()
            },
            success: function (result) {
                if (result) {
                    parent.$("#alertPromptMessage").html("提交成功");
                } else {
                    parent.$("#alertPromptMessage").html("提交失败<p>1.请查看您是否已关注此用户。<br/>2.请查看您是否已经向该用户提交问题且该问题尚被未回答。</p>");
                }
                parent.$("#alertPrompt").modal("show");
            }
        });
    }
</script>