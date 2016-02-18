<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | QuestionInfo</title>
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
                <label class="control-label">提问用户</label>

                <div class="controls">
                    <a class="btn" id="askUserButton"
                       onclick="shieldUser('${askUser.userId}','askUserButton')">屏蔽用户</a>&nbsp;${askUser.userName}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">回答用户</label>

                <div class="controls">
                    <a class="btn" id="answerUserButton"
                       onclick="shieldUser('${answerUser.userId}','answerUserButton')">屏蔽用户</a>&nbsp;${answerUser.userName}
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">提问日期</label>

                <div class="controls">
                    ${question.askDate}
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">问题状态</label>

                <div class="controls">
                    未完成
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">问题内容</label>

                <div class="controls">
                    ${question.askContent}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">回答内容</label>

                <div class="controls">
                    ${question.answerContent}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"></label>

                <div class="controls">
                    <input id="saves" name="saves" type="button" onclick="delQuestion('${question.questionId}')"
                           class="btn btn-primary"
                           value="删除问题"/>
                    <input type="button" onclick="quit()" class="btn btn-primary" value="关闭"/>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
<script>
    $(function () {
        <c:if test="${question.newAsk!=1&&question.newAnswer!=1}">
        $("#state").html("已完成");
        </c:if>
        <c:if test="${answerUser.inUse==0}">
        $("#answerUserButton").html("取消屏蔽");
        </c:if>
        <c:if test="${askUser.inUse==0}">
        $("#askUserButton").html("取消屏蔽");
        </c:if>
    })

    function shieldUser(id, buttonId) {
        var operate = "";
        if ($("#" + buttonId).html() == "取消屏蔽") {
            operate = "取消";
        }
        $.messager.confirm("提示", "确认" + operate + "屏蔽用户？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/user/shieldUser?id=" + id,
                    dataType: "json",
                    type: "POST",
                    success: function () {
                        $.messager.alert("提示", operate + "屏蔽成功", "", function () {
                            if (operate == "取消") {
                                $("#" + buttonId).html("屏蔽用户");
                            } else {
                                $("#" + buttonId).html("取消屏蔽");
                            }
                        })
                    },
                    error: doError
                })
            }
        })
    }

    function delQuestion(questionId) {
        $.messager.confirm("提示", "确认删除问题？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/user/delQuestion",
                    data: {
                        questionId: questionId
                    },
                    dataType: "json",
                    type: "POST",
                    success: function () {
                        $.messager.alert("提示", "删除成功", "", function () {
                            parent.query();
                            quit();
                        })
                    },
                    error: doError
                })
            }
        })
    }

    function quit() {
        parent.$("#dataEdit").dialog('close');
    }
</script>