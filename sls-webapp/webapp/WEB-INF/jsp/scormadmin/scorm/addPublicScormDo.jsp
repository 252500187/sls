<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | AddPublicScorm</title>
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
                <label class="control-label" for="startTime">开始时间</label>

                <div class="controls">
                    <input type="text" id="startTime" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="endTime">结束时间</label>

                <div class="controls">
                    <input id="endTime" name="endTime" type="text" readonly="true"/>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="scormId">选择课件</label>

                <div class="controls">
                    <select multiple="multiple" id="scormId">
                        <c:forEach var="scorm" items="${scorms}">
                            <option value="${scorm.scormId}"
                                    <c:if test="${scorm.scormId==scorms[0].scormId}">
                                        selected
                                    </c:if>
                                    >${scorm.scormName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label" for="description">描述</label>

                <div class="controls">
                    <textarea id="description"></textarea>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"></label>

                <div class="controls">
                    <input id="saves" name="saves" type="button" onclick="save()" class="btn btn-primary" value="确认"/>
                    <input type="button" onclick="quit()" class="btn btn-primary" value="关闭"/>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
<script>
    $("#startTime,#endTime").datetimepicker({
        language: 'zh-CN',
        format: 'yyyy-mm-dd hh:ii',
        autoclose: true,
        todayBtn: true,
        todayHighlight: true
    });

    $("#startTime").click(function () {
        $("#startTime").datetimepicker({endDate: $("#endTime").val()});
    });

    $("#endTime").click(function () {
        $("#endTime").datetimepicker({startDate: $("#startTime").val()});
    });

    $("option").click(function () {
        $("option").removeAttr("selected");
        $(this).attr("selected", true);
    });

    var rule = {
        objInfo: {
            startTime: {
                checkEmpty: ["startTime", "开始时间"]
            },
            endTime: {
                checkEmpty: ["endTime", "结束时间"]
            }
        }
    };

    function save() {
        if (!JC.validate(rule)) return;
        $("#saves").button('loading');
        $.ajax({
            url: basePath + "admin/scorm/addPublicScorm",
            data: {
                scormId: $("#scormId").val()[0],
                startTime: $("#startTime").val(),
                endTime: $("#endTime").val(),
                description: $("#description").val().trim()
            },
            dataType: "json",
            type: "POST",
            success: function () {
                parent.query();
                parent.$("#dataEdit").dialog('close');
            },
            error: doError
        })
    }

    function quit() {
        parent.$("#dataEdit").dialog('close');
    }
</script>