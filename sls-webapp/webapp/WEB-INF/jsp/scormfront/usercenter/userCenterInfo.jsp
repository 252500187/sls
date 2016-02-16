<%--@elvariable id="finalScore" type="java.lang.Integer"--%>
<%--@elvariable id="user" type="com.sls.user.entity.User"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | CenterInfo</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-minimal.css"/>" rel="stylesheet"
          type="text/css"/>
</head>
<body>
<div class="page-container">
    <div class="page-content">
        <div class="row">
            <div class="col-md-9">
                <div class="row">
                    <div class="col-md-12">
                        <h3>
                            ${user.userName}&nbsp;个人中心
                        </h3>
                        <ul class="page-breadcrumb breadcrumb">
                            <li>
                                <i class="fa fa-home"></i>
                                <a onclick="parent.window.location.href=''">首页</a>
                                <i class="fa fa-angle-right"></i>
                            </li>
                            <li>
                                <a>个人中心</a>
                                <i class="fa fa-angle-right"></i>
                            </li>
                            <li>
                                <a>统计信息</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12">
                        <c:if test="${nextLevel==null}">
                            <h3>${user.score}分,恭喜,您已满级！</h3>
                        </c:if>
                        <c:if test="${nextLevel!=null}">
                            <h3>升至${nextLevel}等级:&nbsp;&nbsp;${user.score}分&nbsp;已完成${finalScore}%</h3>
                        </c:if>
                        <div class="progress progress-striped active">
                            <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40"
                                 aria-valuemin="0"
                                 aria-valuemax="100"
                                 style="width:${finalScore}%">
                            </div>
                        </div>
                        <hr>
                    </div>
                    <div class="col-md-7">
                        <div class="portlet blue box">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="fa fa-bar-chart-o"></i>学习分布图
                                </div>
                                <div class="tools">
                                    <a href="javascript:;" class="collapse">
                                    </a>
                                    <%--<a href="javascript:;" class="reload">--%>
                                    <%--</a>--%>
                                    <a href="javascript:;" class="remove">
                                    </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <c:if test="${fn:length(userPeiCharts)<1}">
                                    <h3 id="studyDistribute">您还没有注册过课程，试着去注册课程吧！</h3>
                                </c:if>
                                <div id="pie_chart_3" class="chart">
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <blockquote class="hero">
                                <p>
                                    学知不足，业精于勤。
                                </p>
                                <small>（唐）韩愈</small>
                            </blockquote>
                        </div>
                    </div>
                    <div class="col-md-5">
                        <div class="portlet green box">
                            <div class="portlet-title">
                                <div class="caption">
                                    <i class="fa fa-table"></i>最近学习
                                </div>
                                <div class="tools">
                                    <a href="javascript:;" class="collapse">
                                    </a>
                                    <a href="javascript:;" class="reload">
                                    </a>
                                    <a href="javascript:;" class="remove">
                                    </a>
                                </div>
                            </div>
                            <div class="portlet-body">
                                <c:if test="${fn:length(registerScorm)<1}">
                                    <h5>您最近没有正在学习的课程</h5>
                                </c:if>
                                <div class="blog-twitter">
                                    <c:forEach var="scorm" items="${registerScorm}">
                                        <div class="blog-twitter-block">
                                            <i class="fa fa-book blog-twiiter-icon"></i>
                                            <a onclick="top.scormInfo('${scorm.scormId}')">${scorm.scormName}</a>
                                            <a onclick="study('${scorm.scormId}')">
                                                <em>继续</em>
                                            </a>
                                            <c:if test="${scorm.lastVisitTime!=''}">
                                                <p>上次学习时间:${scorm.lastVisitTime}</p>
                                            </c:if>
                                            <c:if test="${scorm.lastVisitTime==''}">
                                                <p>还未学习！</p>
                                            </c:if>
                                        </div>
                                        <hr/>
                                    </c:forEach>
                                </div>
                            </div>
                        </div>
                    </div>
                    <hr>
                </div>
            </div>
            <div class="col-md-3">
                <br/>
                <h3>推荐给你</h3>
                <hr/>
                <c:if test="${fn:length(recommendScorm)<1}">
                    <h5>试着给自己添加标签</h5>

                    <h5>我们会推荐相关课程！</h5>
                </c:if>
                <div class="top-news">
                    <c:if test="${recommendScorm[0]!=null}">
                        <a onclick="top.scormInfo('${recommendScorm[0].scormId}')" class="btn red">
                                        <span>
                                            <c:if test="${recommendScorm[0].showRecommendLevel!=''}">
                                                <img src="${recommendScorm[0].showRecommendLevel}"
                                                     style="width: 25px;height: 25px"/>&nbsp;
                                            </c:if>
                                            ${recommendScorm[0].scormName}
                                        </span>
                            <em>评分：${recommendScorm[0].score}</em>
                            <em><i class="fa fa-tags"></i>${recommendScorm[0].labelName}</em>
                            <i class="fa fa-briefcase top-news-icon"></i>
                        </a>
                    </c:if>
                    <c:if test="${recommendScorm[1]!=null}">
                        <a onclick="top.scormInfo('${recommendScorm[1].scormId}')" class="btn green">
                                         <span>
                                            <c:if test="${recommendScorm[1].showRecommendLevel!=''}">
                                                <img src="${recommendScorm[1].showRecommendLevel}"
                                                     style="width: 25px;height: 25px"/>&nbsp;
                                            </c:if>
                                            ${recommendScorm[1].scormName}
                                        </span>
                            <em>评分：${recommendScorm[1].score}</em>
                            <em><i class="fa fa-tags"></i>${recommendScorm[1].labelName}</em>
                            <i class="fa fa-music top-news-icon"></i>
                        </a>
                    </c:if>
                    <c:if test="${recommendScorm[2]!=null}">
                        <a onclick="top.scormInfo('${recommendScorm[2].scormId}')" class="btn blue">
                                       <span>
                                            <c:if test="${recommendScorm[2].showRecommendLevel!=''}">
                                                <img src="${recommendScorm[2].showRecommendLevel}"
                                                     style="width: 25px;height: 25px"/>&nbsp;
                                            </c:if>
                                            ${recommendScorm[2].scormName}
                                        </span>
                            <em>评分：${recommendScorm[2].score}</em>
                            <em><i class="fa fa-tags"></i>${recommendScorm[2].labelName}</em>
                            <i class="fa fa-globe top-news-icon"></i>
                        </a>
                    </c:if>
                    <c:if test="${recommendScorm[3]!=null}">
                        <a onclick="top.scormInfo('${recommendScorm[3].scormId}')" class="btn yellow">
                                         <span>
                                            <c:if test="${recommendScorm[3].showRecommendLevel!=''}">
                                                <img src="${recommendScorm[3].showRecommendLevel}"
                                                     style="width: 25px;height: 25px"/>&nbsp;
                                            </c:if>
                                            ${recommendScorm[3].scormName}
                                        </span>
                            <em>评分：${recommendScorm[3].score}</em>
                            <em><i class="fa fa-tags"></i>${recommendScorm[3].labelName}</em>
                            <i class="fa fa-book top-news-icon"></i>
                        </a>
                    </c:if>
                    <c:if test="${recommendScorm[4]!=null}">
                        <a onclick="top.scormInfo('${recommendScorm[4].scormId}')" class="btn purple">
                                         <span>
                                            <c:if test="${recommendScorm[4].showRecommendLevel!=''}">
                                                <img src="${recommendScorm[4].showRecommendLevel}"
                                                     style="width: 25px;height: 25px"/>&nbsp;
                                            </c:if>
                                            ${recommendScorm[4].scormName}
                                        </span>
                            <em>评分：${recommendScorm[4].score}</em>
                            <em><i class="fa fa-tags"></i>${recommendScorm[4].labelName}</em>
                            <i class="fa fa-bolt top-news-icon"></i>
                        </a>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    jQuery(document).ready(function () {
        Metronic.init();
        Layout.init();
        var data = [];
        var i = 0;
        <c:forEach var="perItem" items="${userPeiCharts}">
        data[i++] = {
            label: '${perItem.labelName}',
            data:${perItem.number}
        }
        </c:forEach>
        <c:if test="${fn:length(userPeiCharts)>0}">
        Charts.initPieCharts(data);
        </c:if>
    });

    function findScorm() {
        if ($("#queryInfo").val() != "") {
            window.open(basePath + "tourist/findScorm?queryInfo=" + $("#queryInfo").val());
        }
    }

    function study(scormId) {
        window.open(basePath + "user/scorm/studyScorm?scormId=" + scormId);
    }
</script>