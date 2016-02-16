<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS |SortResult</title>
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
                <li>
                    <a onclick="sortByLabel('0')" style="font-size: 20px">
                        全部课件
                    </a>
                </li>
                <hr/>
                <c:forEach var="label" items="${sessionScope.labels}">
                    <li>
                        <a onclick="sortByLabel('${label.labelId}')">
                            <i class="fa fa-tags"></i>${label.labelName}
                        </a>
                    </li>
                </c:forEach>
            </ul>
            <hr/>
        </div>
        <div class="col-md-8">

            <div class="row">
                <div class="col-md-12">
                    <ul class="page-breadcrumb breadcrumb">
                        <li>
                            <i class="fa fa-home"></i>
                            <a href="">首页</a>
                            <i class="fa fa-angle-right"></i>
                        </li>
                        <li>
                            <a onclick="sortByLabel('0')">课件分类</a>
                            <i class="fa fa-angle-right"></i>
                        </li>
                        <li>
                            <a>${sortName}</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="row">
                <div class="col-md-12 col-sm-8 article-block">
                    <c:if test="${fn:length(sortLabelScorm)<=0}">
                        <h3 class="page-title">对不起</h3>

                        <h3 class="page-title">
                            没有相关标签课件
                            <small>...</small>
                        </h3>
                    </c:if>
                    <c:if test="${fn:length(sortLabelScorm)>0}">
                        <div class="row">
                            <c:forEach var="scorm" items="${sortLabelScorm}">
                                <div class="col-lg-4 col-md-4 col-sm-6 col-xs-12 margin-bottom-10">
                                    <div class="dashboard-stat blue-madison">
                                    </div>
                                    <img src="${scorm.imgPath}" alt="img" class="img-responsive"
                                         style="height:200px">
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
                                    <div class="dashboard-stat blue-madison">
                                        <div class="visual">

                                        </div>
                                        <div class="details">
                                            <div class="number">
                                                <small style="font-size: 20px"><c:if test="${scorm.showRecommendLevel!=''}">
                                                    <img src="${scorm.showRecommendLevel}"
                                                         style="width: 20px;height: 20px"/>&nbsp;
                                                </c:if>${scorm.scormName}</small>
                                            </div>

                                            <div class="desc">评分:${scorm.score}分</div>

                                        </div>
                                        <a class="more" onclick="scormInfo('${scorm.scormId}')">
                                            详细 <i class="m-icon-swapright m-icon-white"></i>
                                        </a>
                                    </div>
                                    <hr>
                                </div>
                            </c:forEach>
                        </div>
                    </c:if>
                </div>
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
<script src="<c:url value="/metronic/assets/global/plugins/bootstrap-sessiontimeout/jquery.sessionTimeout.js"/>"
        type="text/javascript"></script>
<script type="text/javascript">
    function findScorm() {
        if ($("#queryInfo").val() != "") {
            window.location.href = basePath + "tourist/findScorm?queryInfo=" + $("#queryInfo").val();
        }
    }

    function sortByLabel(labelId) {
        window.location.href = basePath + "tourist/sortScorm?labelId=" + labelId;
    }

    function study(scormId) {
        window.open(basePath + "user/scorm/studyScorm?scormId=" + scormId);
    }

    $(function () {
        Metronic.init();
        Layout.init();
    });
</script>