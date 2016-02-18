<%--@elvariable id="scorm" type="com.sls.scorm.entity.Scorm"--%>
<%--@elvariable id="noteList" type="java.util.List"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | ${scorm.scormName}</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-barber-shop.css"/>"
          rel="stylesheet" type="text/css"/>
    <script src="<c:url value="/js/ScormAPI.js"/>" type="text/javascript"></script>
    <style type="text/css">
        .protectEye {
            background-color: #C7EDCC;
        }

        .fa-star-o {
            color: #D7D31F;
        }

        .fa-star {
            color: #D7D31F;
        }
    </style>
</head>
<body class="page-header-fixed" style="background-color: #ffffff;overflow-x:hidden">
<%@include file="../index/navigationMenu.jsp" %>
<div class="page-container" style="margin-top: 80px">
<div class="row" name="protectEye">
    <div class="col-md-10">
        <iframe id="scormIframe" style="width:100%; height:800px;border:0px;margin-top: 5px;margin-left: 5px"
                allowfullscreen>
        </iframe>
    </div>
    <div class="col-md-2">
        <div class="portlet box blue" style="margin: 10px">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-sitemap"></i>课件信息及章节
                </div>
            </div>
            <div class="portlet-body">
                <div>
                    <a onclick="protectEye()" class="btn green" style="margin: 10px">
                        <i class="fa fa-eye"></i>
                                <span class="title">
                                    开/关灯
                                </span>
                    </a><br/>

                    <div class="caption-sidebar">${scorm.scormName}</div>
                </div>
                <img src="${scorm.imgPath}" class="img-responsive" style="height: 100px"/>
                <br/>
                <hr/>
                <iframe id="ztree" scrolling="no" style="width:100%; min-height:450px;border:0px;"
                        allowfullscreen>
                </iframe>
            </div>
        </div>
    </div>
</div>
<div class="row" name="protectEye">
    <div class="col-md-4">
        <div class="portlet box green">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-file-text"></i>笔记本
                </div>
            </div>
            <div class="portlet-body">
                <iframe style="border: 0;display: none;" id="upImgIframe" name="upImgIframe"></iframe>
                <div class="chat-form">
                    传张图片？
                    <form target="upImgIframe" id="upImgForm" enctype="multipart/form-data">
                        <div class="form-group">
                            <div class="col-md-9">
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                       <span class="btn default btn-file">
                                           <span class="fileinput-new">
                                                选图
                                           </span>
                                           <span class="fileinput-exists">
                                                换一个
                                           </span>
                                           <input type="file" id="noteImg" name="noteImg">
                                       </span>
                                    <span class="row fileinput-filename"></span>
                                    <a href="#" class="close fileinput-exists" data-dismiss="fileinput"
                                       style="float: none">
                                    </a>
                                </div>
                            </div>
                        </div>
                        <div class="btn-cont">
                                <span class="arrow">
                                </span>
                            <a onclick="upStudyImg()" class="btn blue icn-only">
                                <i class="fa fa-check icon-white"></i>
                            </a>
                        </div>
                    </form>
                </div>
                <div class="chat-form">
                    <div class="input-cont">
                        <input class="form-control" type="text" placeholder="记点什么？" id="takeNotes"/>
                    </div>
                    <div class="btn-cont">
									<span class="arrow">
									</span>
                        <a onclick="takeNote()" class="btn blue icn-only">
                            <i class="fa fa-check icon-white"></i>
                        </a>
                    </div>
                </div>
                <div class="portlet-body" id="noteList">
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-5">
        <div class="portlet box green">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-comments"></i>评价
                </div>
            </div>
            <div class="portlet-body">
                <div class="alert alert-info display-hide col-md-4">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                    <strong>提示!</strong>

                    <p id="result"></p>
                </div>
                <div class="alert alert-danger display-hide col-md-4">
                    <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                    <strong>提示!</strong>

                    <p id="errorResult"></p>
                </div>
                <br/>

                <div class="text-center">
                    <ul id="stars" class="list-inline">
                        <li>评分:</li>
                        <li name="changeStar">
                            <i id="changeStar0" class="fa fa-star-o"></i>
                        </li>
                        <li name="changeStar">
                            <i id="changeStar1" class="fa fa-star-o"></i>
                        </li>
                        <li name="changeStar">
                            <i id="changeStar2" class="fa fa-star-o"></i>
                        </li>
                        <li name="changeStar">
                            <i id="changeStar3" class="fa fa-star-o"></i>
                        </li>
                        <li name="changeStar">
                            <i id="changeStar4" class="fa fa-star-o"></i>
                        </li>
                        <li>
                            <a id="showScore">${summarize.score}分</a>
                        </li>
                        <li>
                            <a class="btn blue" onclick="evaluateScorm()">
                                评分
                            </a>
                        </li>
                    </ul>
                </div>
                <div class="chat-form">
                    <div class="input-cont">
                        <input id="discuss" class="form-control" type="text" placeholder="说点什么？"/>
                    </div>
                    <div class="btn-cont">
									<span class="arrow">
									</span>
                        <a onclick="changeDiscuss()" class="btn blue icn-only">
                            <i class="fa fa-check icon-white"></i>
                        </a>
                    </div>
                </div>
                <br/>

                <div id="chats">
                    <ul class="chats">
                        <span id="appendDiscuss"></span>
                        <c:forEach var="comment" items="${allComments}">
                            <c:if test="${comment.userId!=user.userId}">
                                <li class="in">
                            </c:if>
                            <c:if test="${comment.userId==user.userId}">
                                <li class="out">
                            </c:if>
                            <img class="avatar img-responsive" alt=""
                                 src="${comment.imgUrl}"/>

                            <div class="message">
                                <span class="arrow"></span>
                                <a class="name">${comment.userName}</a>
                                <span class="datetime">${comment.discussDate}</span>
                                <span class="body">${comment.discuss}</span>
                            </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-3">
        <div class="portlet box blue" style="margin: 10px">
            <div class="portlet-title">
                <div class="caption">
                    <i class="fa fa-italic"></i>学习情况
                </div>
            </div>
            <div class="portlet-body">
                <ul class="list-inline">
                    <li>注册日期:</li>
                    <li>
                        ${summarize.registerDate}
                    </li>
                    <br/>
                    <li>完成时间:</li>
                    <li>
                        <c:if test="${summarize.completeDate!=''}">
                            ${summarize.completeDate}
                        </c:if>
                        <c:if test="${summarize.completeDate==''}">
                            未完成
                        </c:if>
                    </li>
                    <br/>
                    <li>课件成绩:</li>
                    <li>
                        <c:if test="${summarize.grade!=''}">
                            ${summarize.grade}
                        </c:if>
                        <c:if test="${summarize.grade==''}">
                            无成绩
                        </c:if>
                    </li>
                    <br/>
                    <li>学习总时间:</li>
                    <li>
                        <c:if test="${summarize.totalTime!=''}">
                            ${summarize.totalTime}
                        </c:if>
                        <c:if test="${summarize.totalTime==''}">
                            未记录
                        </c:if>
                    </li>
                    <br/>
                    <li>我的评分:</li>
                    <li>
                        <c:if test="${summarize.score!='0'}">
                            ${summarize.score}
                        </c:if>
                        <c:if test="${summarize.score=='0'}">
                            未评分
                        </c:if>
                    </li>
                    <br/>
                    <li>评论日期:</li>
                    <li>
                        <c:if test="${summarize.discussDate!=''}">
                            ${summarize.discussDate}
                        </c:if>
                        <c:if test="${summarize.discussDate==''}">
                            未评论
                        </c:if>
                    </li>
                    <br/>
                    <li>评论内容:</li>
                    <li>
                        <c:if test="${summarize.discuss!=''}">
                            ${summarize.discuss}
                        </c:if>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<div class="row" name="protectEye" style="padding: 20px">
    <div class="col-md-10">
        <c:if test="${fn:length(groupScorms)>0}">
            <div class="row">
                <div class="col-md-12">
                    <hr/>
                    <h2>
                        <div class="caption-sidebar">同系列课件
                            <small>(学习完本课程后，可继续学习这些课程)</small>
                        </div>
                        <br/>
                    </h2>
                </div>
                <div class="col-md-12">
                    <div class="portlet-body">
                        <div class="row">
                            <c:forEach var="scorm" items="${groupScorms}">
                                <a onclick="scormInfo('${scorm.scormId}')">
                                    <div class="col-md-3">
                                        <div class="news-blocks">
                                            <h3>
                                                <a onclick="scormInfo('${scorm.scormId}')">
                                                    <c:if test="${scorm.showRecommendLevel!=''}">
                                                        <img src="${scorm.showRecommendLevel}"
                                                             style="width: 30px;height: 30px"/>&nbsp;
                                                    </c:if>${scorm.scormName} </a>
                                            </h3>

                                            <div class="news-block-tags">
                                                <strong>${scorm.score}分</strong>
                                                <em>${scorm.registerSum}人注册</em>
                                                <hr/>
                                            </div>
                                            <p>
                                                <img class="news-block-img pull-right"
                                                     style="width: 200px;height: 150px"
                                                     src="${scorm.imgPath}"
                                                     alt="${scorm.scormName}">${scorm.description}
                                            </p>
                                            <a onclick="scormInfo('${scorm.scormId}')" class="news-block-btn blue">
                                                查看 <i class="m-icon-swapright m-icon-black"></i>
                                            </a>
                                        </div>
                                    </div>
                                </a>
                            </c:forEach>
                        </div>
                    </div>

                </div>
            </div>
        </c:if>
        <c:if test="${fn:length(otherScorms)>0}">
            <div class="row">
                <div class="col-md-12">
                    <hr/>
                    <h2>
                        <div class="caption-sidebar">作者课件</div>
                    </h2>
                </div>
                <c:forEach var="scorm" items="${otherScorms}">
                    <div class="col-md-3">
                        <div class="meet-our-team">
                            <h3 style="height: 35px;overflow: hidden">${scorm.scormName}
                            </h3>
                            <small>${scorm.passDate}</small>
                            <img src="${scorm.imgPath}" alt="${scorm.imgPath}" class="img-responsive"
                                 style="height: 150px"/>

                            <div class="team-info">
                                <p style="height: 20px;overflow: hidden">
                                        ${scorm.description}
                                </p>
                                <a onclick="scormInfo(${scorm.scormId})" class="btn blue pull-right">查看<i
                                        class="m-icon-swapright m-icon-white"></i></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
    <div class="col-md-2">
        <c:if test="${fn:length(registerUsers)>0}">
            <div class="row">
                <div class="col-md-12">
                    <hr/>
                    <h2>
                        <div class="caption-sidebar">注册用户
                        </div>
                        <br/>
                    </h2>
                </div>
                <c:forEach var="user" items="${registerUsers}">
                    <div class="col-md-6">
                        <a onclick="userInfo(${user.userId})">
                            <img src="${user.imgUrl}" style="height: 80px;" class="img-responsive">

                            <p>${user.userName}</p>
                        </a>
                    </div>
                </c:forEach>
            </div>
        </c:if>
    </div>
</div>
</div>
<%@include file="../index/footer.jsp" %>
<div id="alertConfirm" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">提示</h4>
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
<div id="publicPrompt" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">提示</h4>
            </div>
            <div class="modal-body">
                <p>
                    为进行公开课，若浏览器屏蔽了弹出窗口，请点击允许，并重新进入课件学习页面。
                </p>
            </div>
            <div class="modal-footer">
                <button class="btn blue" data-dismiss="modal">确认</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script src="<c:url value="/metronic/assets/global/plugins/bootstrap-sessiontimeout/jquery.sessionTimeout.js"/>"
        type="text/javascript"></script>
<script>
var noteStyles = ["note note-success", "note note-note-info", "note note-danger", "note note-warning"];
var scormId = "${scorm.scormId}";
var scoId = "";
var nowScoId = "-1";
var score = ${summarize.score}-1;

$(function () {
    Metronic.init();
    Layout.init();
    $("#ztree").attr("src", basePath + "user/scorm/studyScormZtree?scormId=${scorm.scormId}");
    $("#scormIframe").attr("src", "img/studyscormdefaultimg/8.jpg");
    initScormNotes();
    initScormStar();
    <c:if test="${publicScorm}">
    initPublicScorm();
    </c:if>
});

function getRandom() {
    var noteIndex = Math.floor(Math.random() * 4);
    return noteStyles[noteIndex];
}

function initScormNotes() {
    <c:forEach var="note" items="${noteList}">
    $("#noteList").append('<div class="' + getRandom() + '">' +
            '<h4 class="caption" style="color: #6b7582">${note.date}</h4>' +
            '<small style="color: #6b7582">${note.time}</small><br/>'
            <c:if test="${note.noteType == -1 }">
            + '<p>${note.note}</p>'
            </c:if>
            <c:if test="${note.noteType == 1 }">
            + '<img style="max-height: 100px;max-width: 100px" src="${note.imgPath}"/>'
            </c:if>
            + '</div>');
    </c:forEach>
}

function initScormStar() {
    for (var i = 0; i < score + 1; i++) {
        $("#changeStar" + i).attr("class", "fa fa-star");
    }
    var stars = $("#stars > li[name='changeStar']>i");
    stars.each(function (i) {
        $(stars[i]).click(function () {
            var j = 1;
            for (; j <= i; j++) {
                $("#changeStar" + j).attr("class", "fa fa-star");
            }
            for (; j < 5; j++) {
                $("#changeStar" + j).attr("class", "fa fa-star-o");
            }
            score = i;
            $("#showScore").html(score + 1 + "分");
        });
        $(stars[i]).mouseover(function () {
            var j = score + 1;
            for (j; j <= i; j++) {
                $("#changeStar" + j).attr("class", "fa fa-star");
            }
            for (j; j < 5; j++) {
                $("#changeStar" + j).attr("class", "fa fa-star-o");
            }
        });
        $(stars[i]).mouseout(function () {
            var j = score + 1;
            for (j; j < 5; j++) {
                $("#changeStar" + j).attr("class", "fa fa-star-o");
            }
        });
    });
}

function initPublicScorm() {
    top.window.open(basePath + "user/scorm/publicScormDiscusses?scormId=${scorm.scormId}", "${scorm.scormName}&nbsp;nbsp:公开课",
            'height=400, width=500, top=300, left=800, toolbar=no, menubar=no,location=no, status=no');
    setTimeout('checkShowPublic()', 5000);
}

function checkShowPublic() {
    $.ajax({
        url: basePath + "user/scorm/isShowPublic",
        dataType: "json",
        type: "get",
        success: function (showPublic) {
            if (!showPublic) {
                $("#publicPrompt").modal("show");
            }
        }
    });
}

function getNowDate() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    month = month < 10 ? ("0" + month) : month;
    var day = date.getDate();
    day = day < 10 ? ("0" + day) : day;
    date = year + "-" + month + "-" + day;
    return date;
}

function getNowTime() {
    var date = new Date();
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    month = month < 10 ? ("0" + month) : month;
    var day = date.getDate();
    day = day < 10 ? ("0" + day) : day;
    var hour = date.getHours();
    hour = hour < 10 ? ("0" + hour) : hour;
    var minutes = date.getMinutes();
    minutes = minutes < 10 ? ("0" + minutes) : minutes;
    var seconds = date.getSeconds();
    seconds = seconds < 10 ? ("0" + seconds) : seconds;
    date = year + "-" + month + "-" + day + " " + hour + ":" + minutes + ":" + seconds;
    return date;
}

function takeNote() {
    if ("" == ($("#takeNotes").val().trim())) {
        return;
    }
    $.ajax({
        url: basePath + "user/scorm/takeNote",
        data: {
            note: $("#takeNotes").val().trim(),
            scormId: "${scorm.scormId}",
            scoId: nowScoId
        },
        dataType: "json",
        type: "POST",
        success: function () {
            $("#noteList").prepend("<div class='" + getRandom() + "'>" +
                    " <h4 class='block'>" + getNowDate() + "</h4><p>" + $("#takeNotes").val().trim() + "</p></div>"
            )
            $("#takeNotes").attr("value", "");
        },
        error: doError
    })
}

function upStudyImg() {
    var imgType = $("#noteImg").val().substr($("#noteImg").val().length - 3, 3);
    if ((imgType != "jpg") && (imgType != "png") && (imgType != "gif")) {
        return;
    }
    $("#upImgForm").attr("method", "post").attr("action",
            basePath + "user/scorm/upStudyImg?scormId=" + scormId + "&scoId=" + nowScoId).submit();
    $("#noteList").prepend("<div class='note note-success'>" +
            " <h4 class='block'>" + getNowDate() + "</h4><p>图片" + $("#noteImg").val() + "上传成功！</p></div>"
    )
}

function protectEye() {
    $("div[name='protectEye']").toggleClass("protectEye");
}

function evaluateScorm() {
    var resultScore = score + 1;
    $.ajax({
        url: basePath + "user/dealScorm/evaluateScorm",
        data: {
            scormId: "${scorm.scormId}",
            score: resultScore
        },
        dataType: "json",
        type: "POST",
        success: function (result) {
            if (result) {
                $("#result").html("评分成功！您给了" + resultScore + "分！");
                $('.alert-info').show();
            } else {
                $("#errorResult").html("您还没完成学习，暂时无法评分，先评论吧？");
                $('.alert-danger').show();
            }
        },
        error: doError
    })
}

function changeDiscuss() {
    if ($("#discuss").val().trim() == "") {
        return;
    }
    $.ajax({
        url: basePath + "user/dealScorm/discussScorm",
        type: "post",
        dataType: "json",
        data: {
            scormId: "${scorm.scormId}",
            discuss: $("#discuss").val()
        },
        success: function () {
            var discuss = "<li class='out'><img class='avatar img-responsive' src='${user.imgUrl}'/><div class='message'><span class='arrow'></span>"
                    + "<a class='name'>${user.userName}</a><span class='datetime'>&nbsp;" + getNowTime() + "</span>"
                    + "<span class='body'>" + $("#discuss").val() + "</span></div></li>";
            $(".out").remove();
            $("#appendDiscuss").append(discuss);
        },
        error: doError
    })
}
</script>