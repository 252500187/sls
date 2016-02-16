<%--@elvariable id="user" type="com.sls.util.LoginUserUtil"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | UserCenter</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/common.jsp" %>
</head>
<body class="page-header-fixed" style="overflow-x:hidden">
<%@include file="../index/navigationMenu.jsp" %>
<div class="page-container" style="margin-top: 80px">
    <div class="page-sidebar-wrapper">
        <div class="page-sidebar navbar-collapse collapse">
            <ul class="page-sidebar-menu" data-auto-scroll="false" data-auto-speed="200">
                <li class="sidebar-toggler-wrapper">
                    <div class="sidebar-toggler"></div>
                </li>
                <li class="start">
                    <a id="userCenterIndex">
                        <table>
                            <tr>
                                <td>
                                    <i class="fa fa-user"></i><br/>
                                    <img id="userHeadPhoto" src="${user.imgUrl}" class="img-rounded" alt="用户头像"
                                         style="max-width:100px;max-height:100px;padding-right: 10px"/>
                                </td>
                                <td>
                                    <span>昵称:</span><br/><span
                                        id="userNickName">${user.userName}</span><br/>
                                    <span>分数</span>:&nbsp;&nbsp;${user.score}分<br/>
                                    <span>等级</span>:&nbsp;&nbsp;${user.levelName}<br/>
                                </td>
                            </tr>
                        </table>
                    </a>
                </li>
                <li>
                    <a id="statistic" onclick="changeIframe('user/center/userCenterInfo')">
                        <i class="fa fa-signal"></i>
						<span class="title">
							统计信息
						</span>
                    </a>
                </li>
                <li>
                    <a id="userInfo" onclick="changeIframe('user/center/userInfoDo')">
                        <i class="fa fa-italic"></i>
						<span class="title">
							个人资料
						</span>
                    </a>
                </li>
                <li>
                    <a>
                        <i class="fa fa-folder-open"></i>
                        <span class="title">我的课件</span>
                        <span class="selected"></span>
                        <span class="arrow open"></span>
                    </a>
                    <ul class="sub-menu">
                        <li>
                            <a onclick="changeIframe('user/center/registerScormDo')">
                                <i class="fa fa-briefcase"></i>
                                <span class="title">
                                    注册的课件
                                </span>
                            </a>
                        </li>
                        <li>
                            <a onclick="changeIframe('user/center/collectScormDo')">
                                <i class="fa fa-folder-open-o"></i>
                                <span class="title">
                                    收藏的课件
                                </span>
                            </a>
                        </li>
                        <li>
                            <a onclick="changeIframe('user/center/myUpScormsDo')">
                                <i class="fa fa-level-up"></i>
                                <span class="title">
                                    上传的课件
                                </span>
                            </a>
                        </li>
                    </ul>
                </li>
                <li>
                    <a onclick="changeIframe('user/center/upScormDo')">
                        <i class="fa fa-upload"></i>
                                <span class="title">
                                    上传课件
                                </span>
                    </a>
                </li>
                <li>
                    <a onclick="changeIframe('user/center/evaluateScormDo')">
                        <i class="fa fa-star"></i>
                                <span class="title">
                                    评价课件
                                </span>
                    </a>
                </li>
                <li>
                    <a onclick="changeIframe('user/center/userAttentionDo')">
                        <i class="fa fa-users"></i>
						<span class="title">
							关注列表
						</span>
                        <c:if test="${sessionScope.messageNum>0}">
                            <span class="badge">${sessionScope.messageNum}</span>
                        </c:if>
                    </a>
                </li>
                <li class="last ">
                    <a onclick="changeIframe('user/center/addQuestionDo')">
                        <i class="fa fa-plus"></i>
						<span class="title">
                            提交问题
						</span>
                    </a>
                </li>
                <li>
                    <a onclick="changeIframe('user/center/askQuestionsDo')">
                        <i class="fa fa-comment-o"></i>
						<span class="title">
							我的提问
						</span>
                        <c:if test="${sessionScope.answerNum>0}">
                            <span class="badge">${sessionScope.answerNum}</span>
                        </c:if>
                    </a>
                </li>
                <li>
                    <a onclick="changeIframe('user/center/askUserQuestionsDo')">
                        <i class="fa fa-question"></i>
						<span class="title">
							提问我的
						</span>
                        <c:if test="${sessionScope.questionNum>0}">
                            <span class="badge">${sessionScope.questionNum}</span>
                        </c:if>
                    </a>
                </li>
                <li class="last ">
                    <a onclick="changeIframe('user/center/addNoteDo')">
                        <i class="fa fa-plus"></i>
						<span class="title">
							添加笔记
						</span>
                    </a>
                </li>
                <li class="last">
                    <a onclick="changeIframe('user/center/scormNotesDo')">
                        <i class="fa fa-book"></i>
						<span class="title">
							我的笔记本
						</span>
                    </a>
                </li>
                <li>
                    <a onclick="changeIframe('user/center/calendarDo')">
                        <i class="fa fa-calendar"></i>
						<span class="title">
							日程表
						</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <div class="page-content-wrapper">
        <div class="page-content">
            <iframe id="iframe" style="min-height: 1200px;width:100%;"
                    frameborder=no
                    scrolling="no"
                    allowfullscreen>
            </iframe>
        </div>
    </div>
</div>
<%@include file="../index/footer.jsp" %>
<div id="alertPrompt" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <p id="alertPromptMessage">
                </p>
            </div>
            <div class="modal-footer">
                <button id="promptButton" class="btn blue" data-dismiss="modal">确认</button>
            </div>
        </div>
    </div>
</div>
<div id="alertConfirm" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"></h4>
            </div>
            <div class="modal-body">
                <p id="alertConfirmMessage">
                </p>
            </div>
            <div class="modal-footer">
                <button id="promptButton2" class="btn default" data-dismiss="modal">取消</button>
                <button id="promptButton1" class="btn blue" data-dismiss="modal">确认</button>
            </div>
        </div>
    </div>
</div>
<div id="alertIframe" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title"></h4>
            </div>
            <div>
                <iframe id="iframeInfo" style="width:100%; height:500px;border:1px;" frameborder=no allowfullscreen>
                </iframe>
            </div>
            <div class="modal-footer">
            </div>
        </div>
    </div>
</div>
<div id="maskNotes" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
        </div>
    </div>
</div>
</body>
</html>
<script>
    $(function () {
        Metronic.init(); // init metronic core componets
        Layout.init(); // init layout
        Tasks.initDashboardWidget();
        $("#iframe").attr("src", "${centerUrl}");
        $("#userCenterIndex").click();
    })

    function changeIframe(src) {
        $("#iframe").attr("src", src);
    }

    $('ul.page-sidebar-menu ').on('click', ' li > a', function (e) {
        var menuContainer = $('ul.page-sidebar-menu ');
        menuContainer.children('li.active').removeClass('active');
        menuContainer.children('arrow.open').removeClass('open');
        $(this).parents('li').each(function () {
            $(this).addClass('active');
            $(this).children('a > span.arrow').addClass('open');
        });
        $(this).parents('li').addClass('active');
        $('.selected').remove();
        $(this).parents('li').find("a").append('<span class="selected" ></span>');
    });
</script>