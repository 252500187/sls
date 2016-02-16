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
                <label class="control-label">消息内容</label>

                <div class="controls">
                    ${message.content}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">接收用户</label>

                <div class="controls">
                    <input type="checkbox" id="selectAll" onclick="selectAllUser()">全选<br/>

                    <div style="max-height: 100px;width:250px;overflow-y: scroll">
                        <c:forEach var="user" items="${users}">
                            <input type="checkbox" name="user" value="${user.userId}">${user.userName}<br/>
                        </c:forEach>
                    </div>
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
        $("#saves").button('loading');
        var userIds = "";
        var haveSameUser = "";
        $("[name=user]").each(function () {
            if ($(this).attr("checked")) {
                if ($(this).val() != "${message.userId}") {
                    userIds += $(this).val() + ",";
                } else {
                    haveSameUser = true;
                }
            }
        })
        if (userIds == "") {
            if (haveSameUser) {
                parent.$("#dataEdit").dialog('close');
            }
            return;
        }
        $.ajax({
            url: basePath + "admin/user/transMessage",
            data: {
                userIds: userIds,
                messageId: "${message.messageId}"
            },
            dataType: "json",
            type: "POST",
            success: function () {
                parent.$("#dataEdit").dialog('close');
            },
            error: doError
        })
    }

    function selectAllUser() {
        if ($("#selectAll").attr("checked")) {
            $("[name=user]").attr("checked", true);
        } else {
            $("[name=user]").attr("checked", false);
        }
    }

    function quit() {
        parent.$("#dataEdit").dialog('close');
    }
</script>