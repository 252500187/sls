<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | Message</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
</head>
<body>
<div id="mainContent">
    <form class="form-horizontal">
        <fieldset>
            <legend></legend>
            <div class="control-group">
                <label class="control-label">发送日期</label>

                <div class="controls">
                    ${message.date}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">接收用户</label>

                <div class="controls">
                    ${message.userName}
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">读取状态</label>

                <div class="controls">
                    <c:if test="${message.state==1}">
                        未读
                    </c:if>
                    <c:if test="${message.state!=1}">
                        已读
                    </c:if>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">消息内容</label>

                <div class="controls">
                    ${message.content}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"></label>

                <div class="controls">
                    <input id="saves" name="saves" type="button" onclick="transMessage()" class="btn btn-primary"
                           value="转发"/>
                    <input type="button" onclick="quit()" class="btn btn-primary" value="关闭"/>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
<script>
    function transMessage() {
        window.location.href = basePath + "admin/user/transMessageDo?messageId=${message.messageId}";
    }

    function quit() {
        parent.$("#dataEdit").dialog('close');
    }
</script>