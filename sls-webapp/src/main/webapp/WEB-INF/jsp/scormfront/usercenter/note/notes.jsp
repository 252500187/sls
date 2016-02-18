<%--@elvariable id="userName" type="java.lang.String"--%>
<%--@elvariable id="noteList" type="java.util.List<com.sls.scorm.entity.StudyNote>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | Notes</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-minimal.css"/>" rel="stylesheet"
          type="text/css"/>
    <link rel="stylesheet" type="text/css" href="booknote/css/default.css"/>
    <link rel="stylesheet" type="text/css" href="booknote/css/bookblock.css"/>
    <!-- custom demo style -->
    <link rel="stylesheet" type="text/css" href="booknote/css/demo4.css"/>
    <script src="booknote/js/modernizr.custom.js" type="text/javascript"></script>
</head>
<body style="background-color: #FFFFFF">
<div class="container">
    <div class="alert alert-success margin-bottom-10" style="text-align: center">
        <button class="close" aria-hidden="true" data-dismiss="alert" type="button"></button>
        <i class="fa fa-warning fa-lg"></i>
        试试按住鼠标后向左滑动吧
    </div>
    <div class="bb-custom-wrapper">
        <div id="bb-bookblock" class="bb-bookblock">
            <div class="bb-item">
                <div class="bb-custom-firstpage">
                    <nav>
                        <ul style="list-style-type:none">
                            <li><h1>我</h1></li>
                            <li><h1>的</h1></li>
                            <li><h1>笔</h1></li>
                            <li><h1>记</h1></li>
                            <li><h1>本</h1></li>
                        </ul>
                    </nav>
                </div>
                <div class="head-Info" style="text-align: right;padding-right: 5%;">
                    作者：${userName}
                </div>
                <%--<div class="bb-custom-side">--%>
                <%--&lt;%&ndash;<p class="page-title">${userName}</p>&ndash;%&gt;--%>
                <%--</div>--%>
            </div>
            <c:forEach begin="0" step="2" items="${noteList}" varStatus="status">
                <div class="bb-item">

                    <div class="head-Info" style="padding-left: 5%">
                            ${noteList[status.index].date}<br/>
                        <small>${noteList[status.index].time}</small>
                        <br/>

                        <h3 class="page-title">${noteList[status.index].scormName}</h3>
                        <a onclick="delNote('${noteList[status.index].noteId}')" style="font-size: 20px">撕&nbsp;掉</a>
                    </div>

                    <div class="head-Info" style="text-align: right;padding-right: 5%;">
                            ${noteList[status.index+1].date}<br/>
                        &nbsp;&nbsp;
                        <small>${noteList[status.index+1].time}</small>
                        <br/>

                        <h3 class="page-title">${noteList[status.index+1].scormName}</h3>
                        <a onclick="delNote('${noteList[status.index+1].noteId}')" style="font-size: 20px">撕&nbsp;掉</a><br/>
                    </div>

                    <div class="bb-custom-side">
                        <c:if test="${noteList[status.index].noteType == -1 }">
                            <p>${noteList[status.index].note}</p>
                        </c:if>
                        <c:if test="${noteList[status.index].noteType != -1 }">
                            <img style="max-height: 250px;max-width: 350px" src="${noteList[status.index].imgPath}"/>
                        </c:if>
                    </div>

                    <div class="bb-custom-side">
                        <c:if test="${noteList[status.index+1].noteType == -1 }">
                            <p>${noteList[status.index+1].note}</p>
                        </c:if>
                        <c:if test="${noteList[status.index+1].noteType != -1 }">
                            <img style="max-height: 250px;max-width: 350px" src="${noteList[status.index+1].imgPath}"/>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </div>
        <nav>
            <a id="bb-nav-first" href="#" class="bb-custom-icon bb-custom-icon-first">首页</a>
            <a id="bb-nav-prev" href="#" class="bb-custom-icon bb-custom-icon-arrow-left">前页</a>
            <a id="bb-nav-next" href="#" class="bb-custom-icon bb-custom-icon-arrow-right">后页</a>
            <a id="bb-nav-last" href="#" class="bb-custom-icon bb-custom-icon-last">尾页</a>
        </nav>
    </div>
</div>
</body>
</html>
<script src="booknote/js/jquerypp.custom.js"></script>
<script src="booknote/js/jquery.bookblock.js"></script>
<script type="text/javascript">
    $(function () {
        parent.$("#maskNotes").modal("hide");
        Page.init();
    })

    function delNote(noteId) {
        $.ajax({
            url: basePath + "user/center/delNote",
            data: {
                noteId: noteId
            },
            dataType: "json",
            type: "POST",
            success: function () {
                window.location.href = basePath + "user/center/notesDo?scormId=${scormId}";
            },
            error: doError
        })
    }

    var Page = (function () {
        var config = {
                    $bookBlock: $('#bb-bookblock'),
                    $navNext: $('#bb-nav-next'),
                    $navPrev: $('#bb-nav-prev'),
                    $navFirst: $('#bb-nav-first'),
                    $navLast: $('#bb-nav-last')
                },
                init = function () {
                    config.$bookBlock.bookblock({
                        speed: 1000,
                        shadowSides: 0.8,
                        shadowFlip: 0.4
                    });
                    initEvents();
                },
                initEvents = function () {

                    var $slides = config.$bookBlock.children();

                    // add navigation events
                    config.$navNext.on('click t' +
                            'ouchstart', function () {
                        config.$bookBlock.bookblock('next');
                        return false;
                    });

                    config.$navPrev.on('click touchstart', function () {
                        config.$bookBlock.bookblock('prev');
                        return false;
                    });

                    config.$navFirst.on('click touchstart', function () {
                        config.$bookBlock.bookblock('first');
                        return false;
                    });

                    config.$navLast.on('click touchstart', function () {
                        config.$bookBlock.bookblock('last');
                        return false;
                    });

                    // add swipe events
                    $slides.on({
                        'swipeleft': function (event) {
                            config.$bookBlock.bookblock('next');
                            return false;
                        },
                        'swiperight': function (event) {
                            config.$bookBlock.bookblock('prev');
                            return false;
                        }
                    });

                    // add keyboard events
                    $(document).keydown(function (e) {
                        var keyCode = e.keyCode || e.which,
                                arrow = {
                                    left: 37,
                                    up: 38,
                                    right: 39,
                                    down: 40
                                };

                        switch (keyCode) {
                            case arrow.left:
                                config.$bookBlock.bookblock('prev');
                                break;
                            case arrow.right:
                                config.$bookBlock.bookblock('next');
                                break;
                        }
                    });
                };

        return { init: init };
    })();
</script>