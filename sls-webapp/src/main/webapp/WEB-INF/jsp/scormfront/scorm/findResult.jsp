<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | FindResult</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-barber-shop.css"/>"
          rel="stylesheet" type="text/css"/>
</head>
<body class="page-header-fixed" style="background-color: #ffffff;overflow-x:hidden">
<%@include file="../index/navigationMenu.jsp" %>
<div class="page-container" style="margin-top: 80px">
<div class="row">
<br/>

<div class="col-md-2">
    <hr/>
    <ul class="nav">
        <c:forEach var="label" items="${sessionScope.labels}">
            <li>
                <a onclick="sortByLabel(${label.labelId})">
                    <i class="fa fa-tags"></i>${label.labelName}
                </a>
            </li>
        </c:forEach>
    </ul>
    <hr/>
</div>
<div class="col-md-7">
    <div class="row">
        <div class="col-md-12">
            <h3 class="page-title">
                结果
                <small>"${info}"搜索结果</small>
            </h3>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <ul class="page-breadcrumb breadcrumb">
                <li>
                    <i class="fa fa-home"></i>
                    <a href="">首页</a>
                    <i class="fa fa-angle-right"></i>
                </li>
                <li>
                    <a>搜索结果</a>
                    <i class="fa fa-angle-right"></i>
                </li>
                <li>
                    <a>"${info}"</a>
                </li>
            </ul>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12 col-sm-8 article-block">
            <c:if test="${fn:length(findNameScorm)<=0&&fn:length(findScoreScorm)<=0&&fn:length(findUsers)<=0}">
                <h3 class="page-title">对不起</h3>

                <h3 class="page-title">
                    没有相关搜索结果
                    <small>...</small>
                </h3>
            </c:if>
            <c:if test="${fn:length(findNameScorm)>0}">
                <h1>按课件名称</h1>
                <c:forEach var="scorm" items="${findNameScorm}">
                    <div class="row">
                        <div class="col-md-4 blog-img blog-tag-data">
                            <img src="${scorm.imgPath}" alt="img" class="img-responsive"
                                 style="width: 300px">
                            <ul class="list-inline">
                                <li>
                                    <i class="fa fa-calendar"></i>
                                    <a>上传日期:${scorm.uploadDate}</a>
                                </li>
                                <li>
                                    <i class="fa fa-folder-open-o"></i>
                                    <a>章节数:${scorm.chapterNum}</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-md-8 blog-article">
                            <h3><a onclick="scormInfo('${scorm.scormId}')">${scorm.scormName}</a></h3>

                            <p>评分:${scorm.score}分</p>

                            <p>${scorm.description}</p>
                            <a class="btn blue" onclick="scormInfo('${scorm.scormId}')">
                                详细 <i class="m-icon-swapright m-icon-white"></i>
                            </a>
                        </div>
                    </div>
                    <hr>
                </c:forEach>
            </c:if>
            <c:if test="${fn:length(findScoreScorm)>0}">
                <h1>按课件评分</h1>
                <c:forEach var="scorm" items="${findScoreScorm}">
                    <div class="row">
                        <div class="col-md-4 blog-img blog-tag-data">
                            <img src="${scorm.imgPath}" alt="img" class="img-responsive"
                                 style="width: 300px">
                            <ul class="list-inline">
                                <li>
                                    <i class="fa fa-calendar"></i>
                                    <a>上传日期:${scorm.uploadDate}</a>
                                </li>
                                <li>
                                    <i class="fa fa-folder-open-o"></i>
                                    <a>章节数:${scorm.chapterNum}</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-md-8 blog-article">
                            <h3><a onclick="scormInfo('${scorm.scormId}')">${scorm.scormName}</a></h3>

                            <p>评分:${scorm.score}分</p>

                            <p>${scorm.description}</p>
                            <a class="btn blue" onclick="scormInfo('${scorm.scormId}')">
                                详细 <i class="m-icon-swapright m-icon-white"></i>
                            </a>
                        </div>
                    </div>
                    <hr>
                </c:forEach>
            </c:if>
            <c:if test="${fn:length(findUsers)>0}">
                <h1>搜索到的用户</h1>
                <c:forEach var="user" items="${findUsers}">
                    <div class="col-md-3 blog-img blog-tag-data">
                        <img src="${user.imgUrl}" alt="img" class="img-responsive"
                             style="width: 300px">
                    </div>
                    <div class="col-md-3 blog-article">
                        <h3><a onclick="userInfo('${user.userId}')">${user.userName}</a></h3>

                        <p>性别:&nbsp;&nbsp;
                            <c:if test="${user.sex==1}">男</c:if>
                            <c:if test="${user.sex!=1}">女</c:if>
                        </p>

                        <p>经验值:&nbsp;&nbsp;${user.score}分</p>
                        <a class="btn blue" onclick="userInfo('${user.userId}')">
                            查看 <i class="m-icon-swapright m-icon-white"></i>
                        </a>
                    </div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>
<div class="col-md-3 col-sm-4 blog-sidebar">
    <h3 class="page-title">推荐给你</h3>
    <hr/>
    <c:if test="${fn:length(recommendScorm)<1}">
        <h5>试着给自己添加标签</h5>

        <h5>我们会推荐相关课程！</h5>
    </c:if>
    <div class="top-news">
        <c:if test="${fn:length(recommendScorm)>0}">
            <a onclick="scormInfo('${recommendScorm[0].scormId}')" class="btn red">
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
            <a onclick="scormInfo('${recommendScorm[1].scormId}')" class="btn green">
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
            <a onclick="scormInfo('${recommendScorm[2].scormId}')" class="btn blue">
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
            <a onclick="scormInfo('${recommendScorm[3].scormId}')" class="btn yellow">
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
            <a onclick="scormInfo('${recommendScorm[4].scormId}')" class="btn purple">
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
    <div class="space20">
    </div>
    <br/>

    <h3 class="page-title">名言名句</h3>
    <hr/>
    <div class="tabbable tabbable-custom">
        <ul class="nav nav-tabs">
            <li class="active">
                <a data-toggle="tab" href="#tab_1_1">
                    培 根</a>
            </li>
            <li>
                <a data-toggle="tab" href="#tab_1_2">
                    徐特立</a>
            </li>
        </ul>
        <div class="tab-content">
            <div id="tab_1_1" class="tab-pane active">

                <p>
                    把学问过于用作装饰是虚假；完全依学问上的规则而断事是书生的怪癖。
                </p>
            </div>
            <div id="tab_1_2" class="tab-pane">

                <p>
                    学习要抓住基本知识：即不好高骛远，而忽略基本的东西。喜马拉雅山是世界著名的高山，因为它是建立在喜马拉雅山之上，
                    盘基广大高原之上的一个高峰；假如把喜马拉雅山建立在河海平原上，八千公尺的高峰是难以存在的，犹如无源之水易于枯竭的。
                </p>
            </div>
        </div>
    </div>
    <div class="space20">
        <hr/>
    </div>
    <c:if test="${registerScorm!=null}">
        <h3>最近学习</h3>
        <hr/>
    </c:if>
    <div class="blog-twitter">
        <c:forEach var="scorm" items="${registerScorm}">
            <div class="blog-twitter-block">
                <i class="fa fa-book blog-twiiter-icon"></i>
                <a onclick="scormInfo('${scorm.scormId}')">${scorm.scormName}</a>
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
        </c:forEach>
    </div>
</div>
</div>
</div>
<%@include file="../index/footer.jsp" %>
<div id="alertPrompt" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
                <h4 class="modal-title">提示</h4>
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
</body>
</html>
<script type="text/javascript">
    function findScorm() {
        if ($("#queryInfo").val() != "") {
            window.location.href = basePath + "tourist/findScorm?queryInfo=" + $("#queryInfo").val();
        }
    }

    function study(scormId) {
        window.open(basePath + "user/scorm/studyScorm?scormId=" + scormId);
    }

    $(function () {
        Metronic.init();
        Layout.init();
    });
</script>