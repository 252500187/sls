<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | GroupsScorm</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../../includes/common.jsp" %>
</head>
<body class="page-header-fixed" style="background-color: #ffffff;overflow-x:hidden">
<%@include file="../../index/navigationMenu.jsp" %>
<div class="page-container" style="margin-left: 30px;margin-right: 20px">
    <div class="row">
        <div class="col-md-9">
            <div class="row">
                <c:forEach var="group" items="${groupsScorm}">
                    <div class="col-md-4">
                        <div class="pricing pricing-active hover-effect">
                            <div class="pricing-head pricing-head-active">
                                <h3 style="height: 50px;overflow: hidden">${group.scormName}&nbsp;系列
                                </h3>
                                <h4><i>${group.groupScore}&nbsp;分</i>
                                    <span>系列平均分</span>
                                </h4>
                            </div>
                            <ul class="pricing-content list-unstyled">
                                <li>
                                    <img src="${group.imgPath}" class="img-responsive center" style="height: 150px">
                                </li>
                                <li>
                                    <i class="fa fa-folder-open"></i>课件数目:&nbsp;${group.groupNum}
                                </li>
                                <li>
                                    <i class="fa fa-upload"></i>发布时间;&nbsp;${group.passDate}
                                </li>
                            </ul>
                            <div class="pricing-footer">
                                <p style="height: 20px;overflow: hidden">
                                    简介:&nbsp;${group.description}
                                </p>
                                <a class="btn yellow-crusta" onclick="lookGroups(${group.groupId})">
                                    查看系列<i class="m-icon-swapright m-icon-white"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="col-md-3">
            <div class="row">
                <div class="col-md-11 col-md-offset-1">
                    <a name="recentUp"></a>

                    <div class="portlet-title">
                        <h1>
                            <div class="caption-sidebar" style="padding-bottom: 15px">最新上传</div>
                        </h1>
                    </div>
                    <div class="portlet-body">
                        <ul class="feeds">
                            <c:forEach var="latest" items="${latestScorms}">
                                <li>
                                    <div class="col1">
                                        <div class="cont">
                                            <div class="cont-col1">
                                                <img src="${latest.imgPath}" onclick="scormInfo('${latest.scormId}')"
                                                     class="img-rounded" style="width: 100px;height: 50px">
                                            </div>
                                            <div class="cont-col2">
                                                <div class="desc-sidebar"
                                                     onclick="scormInfo('${latest.scormId}')">${latest.scormName}</div>
                                                <div class="date-sidebar">${latest.uploadDate}</div>
                                            </div>
                                        </div>
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
<div id="groupScormModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="row" id="groupScormsPage" style="padding: 20px">
            </div>
        </div>
    </div>
</div>
<%@include file="../../index/footer.jsp" %>
</body>
</html>
<script type="text/javascript">
    $(function () {
        Metronic.init();
        Layout.init();
        Portfolio.init();
    });

    function lookGroups(groupId) {
        $("#groupScormsPage").children().remove();
        $.ajax({
            url: basePath + "tourist/getGroupScorms",
            dataType: "json",
            type: "post",
            data: {
                groupId: groupId
            },
            success: function (result) {
                for (var i in result) {
                    $("#groupScormsPage").append("<div class='col-md-4' style='margin-bottom: 20px'>" +
                            "<img src='" + result[i].imgPath + "' class='img-responsive' style='height:150px'/>" +
                            "<a onclick='scormInfo(" + result[i].scormId + ")'>" + result[i].scormName + "</a>" +
                            "</div>");
                }
                $("#groupScormModal").modal();
            },
            error: doError
        });
    }
</script>

