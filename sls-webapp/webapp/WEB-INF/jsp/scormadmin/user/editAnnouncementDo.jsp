<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | Announcement</title>
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
                <label class="control-label">公告标题</label>

                <div class="controls">
                    <textarea id="announcementTitle">${announcement.announcementTitle}</textarea>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label">公告内容</label>

                <div class="controls">
                    <textarea id="announcementContent">${announcement.announcementContent}</textarea>
                </div>
            </div>

            <div class="control-group">
                <label class="control-label"></label>
                <div class="controls">
                    <input id="edit" name="edit" type="button" onclick="editAnnouncement()" class="btn btn-primary"
                           value="修改"/>
                    <input type="button" onclick="quit()" class="btn btn-primary" value="关闭"/>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
<script>
    var ruleLabel = {
        objInfo: {
            announcementTitle: {
                checkEmpty: ["announcementTitle", "公告主题"]
            },
            announcementContent: {
                checkEmpty: ["announcementContent", "公告内容"]
            }
        }
    };

    function editAnnouncement() {
        if (!JC.validate(ruleLabel)) return;
        $("#edit").button('loading');
        $.ajax({
            url: basePath + "admin/user/editAnnouncement",
            data: {
                announcementId: ${announcement.announcementId},
                announcementTitle: $("#announcementTitle").val().trim(),
                announcementContent: $("#announcementContent").val().trim()
            },
            dataType: "json",
            type: "POST",
            success: function () {
                parent.$("#dataEdit").dialog('close');
                window.parent.query();
            },
            error: doError
        })
    }

    function quit() {
        parent.$("#dataEdit").dialog('close');
    }
</script>