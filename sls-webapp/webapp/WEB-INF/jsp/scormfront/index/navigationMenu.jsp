<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->

<div class="page-header navbar navbar-fixed-top" style="box-shadow: 1px 1px 10px #969696;">
<div class="page-header-inner" style="margin-left: 30px">
<div class="page-logo">
    <a href="">
        <img src="img/logo/index_logo.png" alt="logo" class="logo-default"
             style="margin-top: 2px"/>
    </a>
</div>

<div class="hor-menu hor-menu-light hidden-sm hidden-xs" style="margin-top: 2px">
    <ul class="nav navbar-nav">
        <li class="mega-menu-dropdown">
            <a data-toggle="dropdown" href="#" class="dropdown-toggle" data-toggle="dropdown"
               data-hover="dropdown"
               style="font-size: 20px;" data-close-others="true">
                课件分类<i class="fa fa-angle-down"></i>
            </a>
            <ul class="dropdown-menu">
                <li>
                    <div class="mega-menu-content">
                        <ul class="mega-menu-submenu">
                            <li>
                                <p onclick="sortByLabel('0')">
                                    全部课件&nbsp;<i class="m-icon-swapright"></i>
                                </p>
                            </li>
                            <li class="divider">
                            </li>
                            <c:forEach var="label" items="${sessionScope.labels}">
                                <li>
                                    <a onclick="sortByLabel('${label.labelId}')">
                                        <i class="fa fa-tags"></i>${label.labelName}
                                    </a>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </li>
            </ul>
        </li>
    </ul>
</div>

<div class="input-group" style="margin-top: 15px">
    <input type="text" class="form-control" placeholder="搜索您感兴趣的内容..." id="queryInfo" name="query"
           style="width: 400px;height: 50px;"/>
    <span class="input-group-btn" style="background-color: #4AA1FF;width: 80px;height: 50px;">
         <a style="color: #fff;margin-left: 18px;font-size: 20px;line-height: 50px;cursor: pointer;text-decoration: none;"
            onclick="findScorm()">搜索</a>
    </span>
</div>

<div class="top-menu" style="margin-top: -50px">
<ul class="nav navbar-nav pull-right">
<c:if test="${sessionScope.userId==null||sessionScope.userId==''}">
    <li class="dropdown dropdown-extended dropdown-notification" id="header_task_bar">
        <a href="login?page=register" style="font-size: 20px;" class="dropdown-toggle">
            <i class="fa fa-user"></i>注册&nbsp;&nbsp;&nbsp;
        </a>
    </li>
    <li class="dropdown dropdown-extended dropdown-tasks" id="header_task_bar">
        <a href="login" style="font-size: 20px;" class="dropdown-toggle">
            <i class="fa fa-sign-in"></i>登陆&nbsp;&nbsp;&nbsp;
        </a>
    </li>
</c:if>
<c:if test="${sessionScope.userId!=null&&sessionScope.userId!=''}">
    <c:if test="${fn:length(sessionScope.messages)+sessionScope.attentionMessageNum+sessionScope.questionNum+sessionScope.answerNum>0}">
        <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"
               data-close-others="true">
                <span style="font-size: 20px;">新消息</span>
                <span class="badge badge-success">${fn:length(sessionScope.messages)+sessionScope.attentionMessageNum+sessionScope.questionNum+sessionScope.answerNum+fn:length(sessionScope.calendarEvents)}</span>
                <i class="fa fa-angle-down"></i>
            </a>
            <ul class="dropdown-menu">
                <li style="margin-top: 5px"></li>
                <c:if test="${fn:length(sessionScope.messages)>0}">
                    <li class="dropdown-submenu">
                        <a>
                                <span class="label label-sm label-icon label-success">
                                    <i class="fa fa-envelope-o"></i>
                                </span>信件
                        </a>
                        <ul class="dropdown-menu">
                            <c:forEach var="message" items="${sessionScope.messages}">
                                <li style="padding: 15px;padding-bottom: 0px;"
                                    name="${message.messageId}">
                                        ${message.content}
                                    <a onclick="cancelMessage('${message.messageId}')" style="color: #0000ff">已&nbsp;&nbsp;读</a>
                                </li>
                                <li class="divider" name="${message.messageId}">
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                    <li class="divider">
                    </li>
                </c:if>
                <c:if test="${sessionScope.answerNum>0}">
                    <li>
                        <a href="user/center/userCenterDo?module=9">
                                    <span class="label label-sm label-icon label-success">
                                        <i class="fa fa-comment-o"></i>
                                    </span>您的问题有${sessionScope.answerNum}个回答&nbsp;&nbsp;
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.questionNum>0}">
                    <li>
                        <a href="user/center/userCenterDo?module=10">
                                    <span class="label label-sm label-icon label-success">
                                        <i class="fa fa-question"></i>
                                    </span>有${sessionScope.questionNum}位用户向您提问&nbsp;&nbsp;
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.questionNum>0||sessionScope.answerNum>0}">
                    <li class="divider">
                    </li>
                </c:if>
                <c:if test="${fn:length(sessionScope.calendarEvents)>0}">
                    <li class="dropdown-submenu">
                        <a href="user/center/userCenterDo?module=14">
                                <span class="label label-sm label-icon label-success">
                                    <i class="fa fa-envelope-o"></i>
                                </span>日程
                        </a>
                        <ul class="dropdown-menu">
                            <c:forEach var="calendarEvent" items="${sessionScope.calendarEvents}">
                                <li style="padding: 15px;padding-bottom: 0px;">
                                    <a onclick="scormInfo(${calendarEvent.scormId})">
                                            ${calendarEvent.scormName}
                                    </a>
                                </li>
                            </c:forEach>
                            <br/>
                        </ul>
                    </li>
                    <li class="divider">
                    </li>
                </c:if>

                <c:forEach var="attentionUser" items="${sessionScope.attentionUsers}">
                    <c:if test="${attentionUser.newMessage>0}">
                        <li>
                            <a onclick="userInfo('${attentionUser.userAttentionId}')">
                                                <span class="label label-sm label-icon label-success">
                                                    <i class="fa fa-user"></i>
                                                </span>${attentionUser.userName}
                                <c:if test="${attentionUser.newMessage>0}">
                                    <span style="color: red">${attentionUser.newMessage}</span>
                                </c:if>
                            </a>
                        </li>
                    </c:if>
                </c:forEach>
                <li style="height: 10px"></li>
            </ul>
        </li>
    </c:if>
    <li class="dropdown dropdown-user">
        <a class="dropdown-toggle" data-toggle="dropdown" onclick="toUserCenter()"
           data-hover="dropdown" data-close-others="true">
            <img id="userTopImg" alt="${sessionScope.userName}" style="height: 45px;width: 45px"
                 src="${sessionScope.userImg}" class="img-circle"/>
                            <span class="username" id="userTopName"
                                  style="font-size: 20px;">${sessionScope.userName}</span>
            <i class="fa fa-angle-down"></i>
        </a>
        <ul class="dropdown-menu">
            <li>
                <a href="" style="margin-top: 5px">
                    <i class="fa fa-bookmark-o"></i>&nbsp;&nbsp;首&nbsp;&nbsp;&nbsp;&nbsp;页
                </a>
            </li>
            <li class="divider">
            </li>
            <li class="dropdown-submenu">

                <a href="user/center/userCenterDo?module=1">
                    <i class="fa fa-user"></i>&nbsp;&nbsp;个人中心</a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="user/center/userCenterDo?module=1">
                            <i class="fa fa-signal"></i>&nbsp;&nbsp;统计信息</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=2">
                            <i class="fa fa-italic"></i>&nbsp;&nbsp;个人资料</a>
                    </li>
                    <li class="dropdown-submenu">
                        <a href="user/center/userCenterDo?module=3">
                            <i class="fa fa-folder-open"></i>&nbsp;&nbsp;我的课件</a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="user/center/userCenterDo?module=3">
                                    <i class="fa fa-briefcase"></i>&nbsp;&nbsp;注册的课件</a>
                            </li>
                            <li>
                                <a href="user/center/userCenterDo?module=4">
                                    <i class="fa fa-folder-open-o"></i>&nbsp;&nbsp;收藏的课件</a>
                            </li>
                            <li>
                                <a href="user/center/userCenterDo?module=5">
                                    <i class="fa fa-level-up"></i>&nbsp;&nbsp;上传的课件</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=6">
                            <i class="fa fa-upload"></i>&nbsp;&nbsp;上传课件</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=7">
                            <i class="fa fa-star"></i>&nbsp;&nbsp;评价课件</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=8">
                            <i class="fa fa-users"></i>&nbsp;&nbsp;关注列表</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=13">
                            <i class="fa fa-plus"></i>&nbsp;&nbsp;提交问题</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=9">
                            <i class="fa fa-comment-o"></i>&nbsp;&nbsp;我的提问</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=10">
                            <i class="fa fa-question"></i>&nbsp;&nbsp;提问我的</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=11">
                            <i class="fa fa-plus"></i>&nbsp;&nbsp;添加笔记</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=12">
                            <i class="fa fa-book"></i>&nbsp;&nbsp;我的笔记本</a>
                    </li>
                    <li>
                        <a href="user/center/userCenterDo?module=14">
                            <i class="fa fa-book"></i>&nbsp;&nbsp;日程表</a>
                    </li>
                </ul>
            </li>
            <li>
                <a onclick="changePassword()">
                    <i class="fa fa-key"></i>&nbsp;&nbsp;修改密码</a>
            </li>
            <li class="divider">
            </li>
            <li>
                <a href="logout" style="margin-bottom: 5px">
                    <i class="fa fa-sign-out"></i>&nbsp;&nbsp;退&nbsp;&nbsp;&nbsp;&nbsp;出</a>
            </li>
        </ul>
    </li>
</c:if>
</ul>
</div>
</div>
</div>

<div class="clearfix">
</div>
<script>
    function findScorm() {
        if ($("#queryInfo").val() != "") {
            top.window.open(basePath + "tourist/findScorm?queryInfo=" + $("#queryInfo").val());
        }
    }

    $(function () {
        $.sessionTimeout({
            title: '提示',
            message: '您已经学习很久了（20分钟），请注意保护眼睛。',
            keepAliveUrl: '',
            redirUrl: 'logout',
            logoutUrl: 'logout',
            warnAfter: 1200000,
            redirAfter: 1210000
        });

        $("#queryInfo").bind("keydown", function (e) {
            if (e.which == 13) {
                findScorm();
            }
        });
    })

    function scormInfo(scormId) {
        top.window.open(basePath + "tourist/scormInfo?scormId=" + scormId);
    }

    function sortByLabel(labelId) {
        top.window.open(basePath + "tourist/sortScorm?labelId=" + labelId);
    }

    function changePassword() {
        top.window.open(basePath + "user/info/changePasswordDo");
    }

    function userInfo(userId) {
        top.window.open(basePath + "tourist/userInfo?userId=" + userId);
    }

    function cancelMessage(messageId) {
        $("li [name='" + messageId + "']").hide();
        $.ajax({
            url: basePath + "user/info/cancelMessage",
            dataType: "json",
            type: "post",
            data: {
                messageId: messageId
            },
            success: function () {
                window.location.href = "";
            }
        });
    }

    function toUserCenter() {
        window.location.href = "user/center/userCenterDo?module=1";
    }

    function groupInfo() {
        top.window.open(basePath + "tourist/groupsScorm");
    }
</script>