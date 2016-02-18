<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | ${scorm.scormName}:${publicScorm.startTime}--${publicScorm.endTime}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/common.jsp" %>
</head>
<body class="page-header-fixed">
<div class="row">
    <div class="col-md-12">
        <p class="discussId" hidden="false">${discussId}</p>

        <div class="portlet-body">
            <div id="discusses" style="height: 310px;overflow-y:auto" data-always-visible="1" data-rail-visible1="1">
                <ul class="chats">
                    <br/>
                </ul>
            </div>
            <div class="chat-form">
                <div class="input-cont">
                    <input class="form-control" type="text" id="discuss" placeholder="说点什么..."/>
                </div>
                <div class="btn-cont">
									<span class="arrow">
									</span>
                    <a onclick="sendDiscuss()" class="btn blue icn-only">
                        <i class="fa fa-check icon-white"></i>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        Metronic.init();
        Layout.init();
        setInterval("getDiscusses()", 1000);
        $("#discuss").bind("keydown", function (e) {
            if (e.which == 13) {
                sendDiscuss();
            }
        });
    });

    function sendDiscuss() {
        if ($("#discuss").val().trim() == "") {
            return;
        }
        $.ajax({
            url: basePath + "user/scorm/sendDiscuss",
            type: "post",
            data: {
                publicId: "${publicScorm.publicId}",
                discuss: $("#discuss").val().trim()
            },
            success: function () {
                $("#discuss").val("");
            }
        });
    }

    function getDiscusses() {
        $.ajax({
            url: basePath + "user/scorm/getDiscuss",
            type: "post",
            data: {
                discussId: $("p.discussId").last().html(),
                publicId: "${publicScorm.publicId}"
            },
            success: function (discusses) {
                for (var i in discusses) {
                    if (discusses[i].userId == "${userId}") {
                        $("ul").append("<li class='out'><div class='message'><span class='arrow'></span>" +
                                "<a class='name'>我&nbsp;</a><span class='datetime'>" + discusses[i].sendTime +
                                "</span><p class='discussId' hidden='true'>" + discusses[i].discussId +
                                "</p><span class='body'>" + discusses[i].discuss + "</span></div></li>");
                    } else {
                        $("ul").append("<li class='in'><img class='avatar img-responsive' src='" + discusses[i].imgUrl + "'/><div class='message'><span class='arrow'></span>" +
                                "<a onclick='userInfo(" + discusses[i].userId + ")' class='name'>" + discusses[i].userName + "</a><span class='datetime'>" + discusses[i].sendTime +
                                "</span><span class='body'>" + discusses[i].discuss + "</span></div><p class='discussId' hidden='true'>" + discusses[i].discussId +
                                "</p></li>");
                    }
                    document.getElementById('discusses').scrollTop = document.getElementById('discusses').scrollHeight;
                }
            },
            error: doError
        });
    }

    function userInfo(userId) {
        top.window.open(basePath + "tourist/userInfo?userId=" + userId);
    }
</script>