<%--@elvariable id="scormId" type="java.lang.String"--%>
<%--@elvariable id="myEvaluateScore" type="java.lang.Integer"--%>
<%--@elvariable id="AllComments" type="java.util.List<com.sls.scorm.entity.ScormSummarize>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | ReviewScorm</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/common.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/metronic/assets/admin/pages/css/tasks.css"/>"/>
    <style type="text/css">
        .fa-star-o {
            color: #D7D31F;
        }

        .fa-star {
            color: #D7D31F;
        }
    </style>
</head>
<body class="page-header-fixed">
<div class="page-container">
    <div class="page-content-wrapper">
        <div class="page-content">
            <div class="row">
                <div class="col-md-4">
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
                                <a id="showScore">${myEvaluateScore}分</a>
                            </li>
                            <li>
                                <a class="btn blue" onclick="evaluateScorm()">
                                    评分
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <hr/>
            <div class="row">
                <div class="col-md-8">
                    <div class="chat-form">
                        <div class="input-cont">
                            <input id="discussInput" class="form-control" type="text" placeholder="说点什么?"/>
                        </div>
                        <div class="btn-cont">
                            <span class="arrow"></span>
                            <a onclick="discussScorm()" class="btn blue icn-only">
                                <i class="fa fa-check icon-white"></i>
                            </a>
                        </div>
                    </div>
                    <br/>

                    <div class="scroller" style="height: 435px;" data-always-visible="1" data-rail-visible1="1">
                        <ul class="chats" id="chatList">
                            <c:forEach var="review" items="${AllComments}" varStatus="status">
                                <c:if test="${status.index == 0}">
                                    <li class="out"></c:if>
                                <c:if test="${status.index != 0}">
                                    <li class="in"></c:if>
                                <img class="avatar img-responsive" alt="" src="${review.imgUrl}"/>

                                <div class="message">
                                    <span class="arrow"></span>
                                    <a href="#" class="name">${review.userName} </a>
                                    <span class="datetime">${review.discussDate}</span>
                                    <span class="body">${review.discuss}</span>
                                </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    var score = ${myEvaluateScore}-1;
    $(function () {
        $("#showScore").html("${myEvaluateScore}分");
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
        })
    });

    function evaluateScorm() {
        var resultScore = score + 1;
        $.ajax({
            url: basePath + "user/dealScorm/evaluateScorm",
            data: {
                scormId: "${scormId}",
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

    function discussScorm() {
        var discuss = $("#discussInput").val().trim();
        if (discuss == "") return;
        $.ajax({
            url: basePath + "user/dealScorm/discussScorm?scormId=${scormId}",
            data: { discuss: discuss },
            type: "POST",
            success: function () {
                window.location.reload()
            },
            error: doError
        })
    }
</script>