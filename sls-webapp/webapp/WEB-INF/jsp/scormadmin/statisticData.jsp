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
    <title>SLS | AdminIndex</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-minimal.css"/>" rel="stylesheet"
          type="text/css"/>
</head>
<body>
<div class="page-container">
    <div class="page-content">
        <div class="row">
            <div class="col-md-6">
                <div class="portlet blue box">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="fa fa-bar-chart-o"></i>课件分布图
                        </div>
                        <div class="tools">
                            <a href="javascript:;" class="collapse">
                            </a>
                            <a href="javascript:;" class="remove">
                            </a>
                        </div>
                    </div>
                    <div class="portlet-body">
                        <h3 id="studyDistribute"></h3>

                        <div id="pie_chart" class="chart">
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-6">
                <div class="portlet box purple">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="fa fa-signal"></i>比例统计信息
                        </div>
                        <div class="tools">
                            <a href="javascript:;" class="collapse">
                            </a>
                            <a href="javascript:;" class="remove">
                            </a>
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div id="bar_chart" class="chart">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-3">
                <h3>注册人数排行</h3>
                <hr/>
                <c:forEach var="scorm" items="${scormSum}">
                    <div class="alert alert-block alert-info fade in">
                        <button type="button" class="close" data-dismiss="alert"></button>
                        <h4 class="alert-heading">${scorm.scormName}</h4>

                        <p>
                            注册数目:${scorm.registerSum}<br/><br/>
                            课件描述: ${scorm.description}
                        </p>

                        <p>
                            <a class="btn purple" onclick="scormInfo('${scorm.scormId}')">
                                详情 </a>
                        </p>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-3">
                <h3>学习时间排行</h3>
                <hr/>
                <c:forEach var="scorm" items="${scormTime}">
                    <div class="alert alert-block alert-info fade in">
                        <button type="button" class="close" data-dismiss="alert"></button>
                        <h4 class="alert-heading">${scorm.scormName}</h4>

                        <p>
                            学习时间:${scorm.totalTime}<br/><br/>
                            课件描述: ${scorm.description}
                        </p>

                        <p>
                            <a class="btn purple" onclick="scormInfo('${scorm.scormId}')">
                                详情 </a>
                        </p>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-3">
                <h3>评分排行</h3>
                <hr/>
                <c:forEach var="scorm" items="${scormScore}">
                    <div class="alert alert-block alert-info fade in">
                        <button type="button" class="close" data-dismiss="alert"></button>
                        <h4 class="alert-heading">${scorm.scormName}</h4>

                        <p>
                            评分:${scorm.score}<br/><br/>
                            课件描述: ${scorm.description}
                        </p>

                        <p>
                            <a class="btn purple" onclick="scormInfo('${scorm.scormId}')">
                                详情 </a>
                        </p>
                    </div>
                </c:forEach>
            </div>
            <div class="col-md-3">
                <h3>推荐等级排行</h3>
                <hr/>
                <c:forEach var="scorm" items="${scormLevel}">
                    <div class="alert alert-block alert-info fade in">
                        <button type="button" class="close" data-dismiss="alert"></button>
                        <h4 class="alert-heading">${scorm.scormName}</h4>

                        <p>
                            推荐等级:${scorm.recommendLevel}级<br/><br/>
                            课件描述: ${scorm.description}
                        </p>

                        <p>
                            <a class="btn purple" onclick="scormInfo('${scorm.scormId}')">
                                详情 </a>
                        </p>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    $(function () {
        Metronic.init();
        Layout.init();
        initPieChart();
        initBarChart();
    });

    function initPieChart() {
        var data = [];
        <% int i=0; %>
        <c:forEach var="label" items="${labels}">
        data[<%=i++%>] = {
            label: '${label.labelName}',
            data: ${label.labelId}
        };
        </c:forEach>
        $.plot($("#pie_chart"), data, {
            series: {
                pie: {
                    show: true
                }
            },
            legend: {
                show: false
            }
        });
    }

    function initBarChart() {
        var userNum = [
            [0, ${userNum}]
        ];
        var useUserNum = [
            [1, ${useUserNum}]
        ];
        var scormNum = [
            [2, ${scormNum}]
        ];
        var useScormNum = [
            [3, ${useScormNum}]
        ];
        $.plot($("#bar_chart"),
                [
                    {
                        label: "用户总数量",
                        data: userNum,
                        lines: {
                            lineWidth: 1
                        },
                        shadowSize: 0
                    },
                    {
                        label: "在用状态用户数量",
                        data: useUserNum,
                        lines: {
                            lineWidth: 1
                        },
                        shadowSize: 0
                    },
                    {
                        label: "课件总数量",
                        data: scormNum,
                        lines: {
                            lineWidth: 1
                        },
                        shadowSize: 0
                    },
                    {
                        label: "在用课件数量",
                        data: useScormNum,
                        lines: {
                            lineWidth: 1
                        },
                        shadowSize: 0
                    }
                ],
                {
                    series: {
                        stack: false,
                        bars: {
                            show: true,
                            barWidth: 0.5,
                            lineWidth: 0, // in pixels
                            shadowSize: 0,
                            align: 'center'
                        }
                    },
                    grid: {
                        tickColor: "#eee",
                        borderColor: "#eee",
                        borderWidth: 1
                    }
                }
        );
    }

    function scormInfo(scormId) {
        var contentFrame = parent.$("#contentFrame");
        var dataEdit = parent.$('#dataEdit');
        contentFrame[0].contentWindow.document.write("");
        contentFrame.attr("src", basePath + "admin/scorm/scormInfo?scormId=" + scormId);
        dataEdit.dialog({
            title: '课件信息',
            height: document.documentElement.clientHeight - 100,
            width: document.documentElement.clientWidth - 100
        });
        dataEdit.dialog('open');
    }
</script>