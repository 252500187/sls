<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | UserInfo</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
</head>
<body>
<div class="container">
    <form class="form-horizontal">
        <fieldset>
            <legend></legend>
            <div class="control-group">
                <label class="control-label">头像</label>

                <div class="controls">
                    <img src="${user.imgUrl}" style="width: 150px;height: 150px"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">用户名</label>

                <div class="controls">
                    ${user.userName}
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">注册日期</label>

                <div class="controls">
                    ${user.registerDate}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">性别</label>

                <div class="controls">
                    <c:if test="${user.sex==1}">
                        男
                    </c:if>
                    <c:if test="${user.sex==0}">
                        女
                    </c:if>
                </div>
            </div>


            <div class="control-group">
                <label class="control-label">经验值</label>

                <div class="controls">
                    ${user.sex}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">标签</label>

                <div class="controls">
                    <c:if test="${fn:length(labels)<1}">
                        该用户未选择标签
                    </c:if>
                    <c:forEach var="label" items="${labels}">
                        ${label.labelName}、
                    </c:forEach>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">问题提问数</label>

                <div class="controls">
                    ${fn:length(askQuestion)}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">问题回答数</label>

                <div class="controls">
                    ${fn:length(answerQuestion)}
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">上传课件</label>

                <div class="controls">
                    <c:if test="${fn:length(upScorms)<1}">
                        该用户未上传课件
                    </c:if>
                    <c:forEach var="scorm" items="${upScorms}">
                        <a onclick="scormInfo( ${scorm.scormId})">${scorm.scormName}</a>、
                    </c:forEach>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">注册课件</label>

                <div class="controls">
                    <c:if test="${fn:length(registerScorms)<1}">
                        该用户未注册课件
                    </c:if>
                    <c:forEach var="scorm" items="${registerScorms}">
                        <a onclick="scormInfo( ${scorm.scormId})">${scorm.scormName}</a>、
                    </c:forEach>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"></label>

                <div class="controls">
                    <input type="button" onclick="quit()" class="btn btn-primary" value="关闭"/>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
<script>
    function scormInfo(scormId) {
        var contentFrame = top.$("#contentFrame");
        var dataEdit = top.$('#dataEdit');
        contentFrame[0].contentWindow.document.write("");
        contentFrame.attr("src", basePath + "admin/scorm/scormInfo?scormId=" + scormId);
        dataEdit.dialog({
            title: '课件信息',
            height: top.document.documentElement.clientHeight - 100,
            width: top.document.documentElement.clientWidth - 100
        });
        dataEdit.dialog('open');
    }

    function quit() {
        parent.$("#dataEdit").dialog('close');
    }
</script>