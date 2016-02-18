<%--@elvariable id="allScorm" type="java.util.List"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../../includes/common.jsp" %>
    <title>SLS | ScormNotes</title>
    <%@include file="../../../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-minimal.css"/>" rel="stylesheet"
          type="text/css"/>
</head>
<body class="page-header-fixed" style="background-color: transparent">
<div class="page-content" style="min-height:780px">
    <div class="row">
        <div class="col-md-12">
            <div class="row">
                <div class="col-md-12">
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
                            <a>我的笔记本</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div class="tabbable tabbable-custom boxless">
                <div class="tab-pane active" id="tab_1">
                    <div class="margin-top">
                        <ul class="mix-filter">
                            <li class="filter" data-filter="category_0" id="allNotes">
                                全部笔记
                            </li>
                            <li class="filter" data-filter="category_2">
                                未完成课件笔记
                            </li>
                            <li class="filter" data-filter="category_3">
                                已完成课件笔记
                            </li>
                        </ul>
                        <div class="row mix-grid">
                            <div class="col-md-3 col-sm-4 mix mix_all category_0"
                                 style=" display: block; opacity: 1;">
                                <div class="mix-inner">
                                    <img src="img/note/1.jpg" class="img-responsive" alt="笔记本">

                                    <div class="mix-details">
                                        <br/>
                                        <h4 style="margin-top:0px;padding-top:10px;margin-bottom: 0px;padding-bottom: 0px">
                                            笔记本</h4>
                                        <h4 style="margin-top:0px;padding-top:10px;margin-bottom: 0px;padding-bottom: 0px">
                                            <a class="btn btn-sm blue"
                                               onclick="openNote('-1')">打开</a>&nbsp;
                                        </h4>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <hr/>
                        <div class="row mix-grid">
                            <c:forEach var="scormInfo" items="${allScorm}">
                                <c:if test="${scormInfo.completeDate == ''}">
                                    <div class="col-md-3 col-sm-4 mix mix_all category_2"
                                         style=" display: block; opacity: 1;">
                                        <div class="mix-inner">
                                            <img src="${scormInfo.imgPath}" class="img-responsive" style="height: 200px"
                                                 alt="${scormInfo.scormId}">

                                            <div class="mix-details">
                                                <br/>
                                                <h4 style="margin-top:0px;padding-top:10px;margin-bottom: 0px;padding-bottom: 0px">
                                                    《${scormInfo.scormName}》相关的笔记</h4>
                                                <h4 style="margin-top:0px;padding-top:10px;margin-bottom: 0px;padding-bottom: 0px">
                                                    <a class="btn btn-sm blue"
                                                       onclick="openNote('${scormInfo.scormId}')">打开</a>&nbsp;
                                                </h4>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                                <c:if test="${scormInfo.completeDate != ''}">
                                    <div class="col-md-3 col-sm-4 mix mix_all category_3"
                                         style=" display: block; opacity: 1;">
                                        <div class="mix-inner">
                                            <img src="${scormInfo.imgPath}" class="img-responsive" style="height: 200px"
                                                 alt="${scormInfo.scormId}">

                                            <div class="mix-details">
                                                <br/>
                                                <h4 style="margin-top:0px;padding-top:10px;margin-bottom: 0px;padding-bottom: 0px">
                                                    《${scormInfo.scormName}》相关的笔记</h4>
                                                <h4 style="margin-top:0px;padding-top:10px;margin-bottom: 0px;padding-bottom: 0px">
                                                    <a class="btn btn-sm blue"
                                                       onclick="openNote('${scormInfo.scormId}')">打开</a>&nbsp;
                                                </h4>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    $(function () {
        Portfolio.init();
    })

    function openNote(scormId) {
        parent.$("#maskNotes").modal("show");
        window.location.href = basePath + "user/center/notesDo?scormId=" + scormId;
    }
</script>