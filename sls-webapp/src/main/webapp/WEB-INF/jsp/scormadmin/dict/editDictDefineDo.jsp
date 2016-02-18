<%--@elvariable id="dictDefine" type="com.sls.system.entity.DictDefine"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | Dict</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
    <title><spring:message code="dictDefine"/></title>
<body>
<div id="mainContent">
    <form class="form-horizontal">
        <fieldset>
            <!-- Form Name -->
            <legend></legend>
            <div class="control-group">
                <label class="control-label" for="dictName"><spring:message code="dictName"/></label>

                <div class="controls">
                    <input id="dictName" name="dictName" type="text" value="${dictDefine.dictName}">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="dictDescription"><spring:message code="dictDescription"/></label>

                <div class="controls">
                    <textarea class="textarea" id="dictDescription"
                              name="dictDescription">${dictDefine.dictDescription}</textarea>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label"></label>

                <div class="controls">
                    <input type="button" id="saves" class="btn btn-primary" onclick="save()"
                           value="<spring:message code="save"/>"/>
                    <input type="button" onclick="quit()" class="btn btn-primary"
                           value="<spring:message code="cancel"/>"/>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
<script type="text/javascript">
    var rules = {
        objInfo: {
            dictName: {
                checkEmpty: ["dictName", "<spring:message code="dictName"/>"],
                checkRegExp: ["dictName", "", JCRegExp.letterNum],
                ajax: [basePath + "admin/dict/checkRepeatDictDefineName",
                    {oldDictName: "${dictDefine.dictName}"},
                    {dictName: "$('#dictName').val().trim()"},
                    backFunc, "text", "POST"]
            }
        }
    };

    function backFunc(data, returnObj) {
        returnObj.obj = $('#dictName');
        returnObj.errMsg = "<spring:message code="dictNameExist"/>！";
        if (data == "true") {
            returnObj.bool = true;
        }
        if (data == "false") {
            returnObj.bool = false;
        }
        return returnObj;
    }

    function save() {
        if (!JC.validate(rules)) return;
        $("#saves").button('loading');
        $.ajax({
            url: basePath + "admin/dict/editDictDefine?oldDictName=${dictDefine.dictName}",
            data: {
                dictName: $("#dictName").val(),
                dictDescription: $("#dictDescription").val()
            },
            dataType: "json",
            type: "POST",
            success: function () {
                $.messager.alert("<spring:message code="prompt"/>", "<spring:message code="edit"/><spring:message code="succeed"/>!", "", function () {
                    parent.$("#dataEdit").dialog('close');
                    parent.query();
                });
            },
            error: doError
        })
    }

    function quit() {
        parent.$("#dataEdit").dialog('close');
    }
</script>
