<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | Welcome to Back</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../includes/adminCommon.jsp" %>
</head>
<body style="overflow: hidden;">
<div class="container-fluid" style="padding: 0">
    <div class="row-fluid">
        <div class="row" style="margin-left: 0">
            <div class="span12" style="background: #1E90FF ">
                <img src="img/logo/logo_white.png"/>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <ul class="nav nav-tabs">
                    <li class="active"><a data-toggle="tab" onclick="changeIframe('admin/user/adminIndexStatisticDo')">统计信息</a>
                    </li>

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" data-target="#">
                            用户相关
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                            <li><a data-toggle="tab" onclick="changeIframe('admin/role/listAllRoleDo')">角色管理</a></li>
                            <li><a data-toggle="tab" onclick="changeIframe('admin/user/listAllUserDo')">用户管理</a></li>
                            <li><a data-toggle="tab" onclick="changeIframe('admin/user/listAllMessageDo')">发送消息管理</a>
                            </li>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" data-target="#">
                            课件相关
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                            <li><a data-toggle="tab" onclick="changeIframe('admin/scorm/upScormDo')">上传课件</a>
                            </li>
                            <li><a data-toggle="tab"
                                   onclick="changeIframe('admin/scorm/listNotAuditScormInfoDo')">未审核课件</a>
                            </li>
                            <li><a data-toggle="tab" onclick="changeIframe('admin/scorm/listAuditScormInfoDo')">已审核课件管理</a>
                            </li>
                            <li><a data-toggle="tab" onclick="changeIframe('admin/scorm/listAllPublicScormDo')">公开课件</a>
                            </li>
                        </ul>
                    </li>

                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" data-target="#">
                            审查管理
                            <b class="caret"></b>
                        </a>
                        <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                            <li><a data-toggle="tab" onclick="changeIframe('admin/user/listAllDiscussDo')">课件评论管理</a></li>
                            <li><a data-toggle="tab"
                                   onclick="changeIframe('admin/user/listAllPublicDiscussDo')">公开课讨论管理</a></li>
                            <li><a data-toggle="tab" onclick="changeIframe('admin/user/listAllQuestionDo')">问题管理</a>
                            </li>
                        </ul>
                    </li>

                    <li><a data-toggle="tab" onclick="changeIframe('admin/label/listAllLabelDo')">标签管理</a></li>
                    <li><a data-toggle="tab" onclick="changeIframe('admin/dict/listAllDictDefineDo')">字典管理</a></li>
                    <li><a data-toggle="tab" onclick="changeIframe('admin/user/listAllAnnouncementDo')">公告管理</a></li>

                    <li class="pull-right"><a data-toggle="tab" onclick="window.location.href='logout'">退出</a></li>
                    <li class="pull-right"><a data-toggle="tab" onclick="changeIframe('admin/user/changePasswordDo')">修改密码</a></li>
                </ul>
            </div>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <iframe frameborder="no" id="iframe" style="width:100%;"></iframe>
            </div>
        </div>
        <div class="row-fluid">
            <div style="font-size:15px;width: 100%; height: 20px; text-align: center;">
                SCORM LEARNING OF SEEK
            </div>
        </div>
    </div>
</div>
<div id="dataEdit" closed="true" modal="true" style="overflow: hidden" closable="true">
    <iframe style="width: 100%;height: 100%"
            id="contentFrame"
            name="contentFrame"
            marginheight="0"
            marginwidth="0"
            frameborder="0" src=""
            allowTransparency="true">
    </iframe>
</div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        $('#iframe').height(document.documentElement.clientHeight - 120);
        changeIframe('admin/user/adminIndexStatisticDo');
    });

    function changeIframe(src) {
        $("#iframe").attr("src", src);
    }
</script>
