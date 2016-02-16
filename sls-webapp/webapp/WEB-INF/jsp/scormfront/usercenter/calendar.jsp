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
    <title>SLS | Calendar</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/common.jsp" %>
    <link href="<c:url value="/metronic/assets/global/plugins/fullcalendar/fullcalendar/fullcalendar.css"/>"
          rel="stylesheet"/>
    <script src="<c:url value="/metronic/assets/global/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"/>"
            type="text/javascript"></script>
    <script src="<c:url value="/metronic/assets/admin/pages/scripts/calendar.js"/>" type="text/javascript"></script>
</head>
<body class="page-header-fixed ">
    <div class="page-content-wrapper">
        <div class="row">
            <div class="col-md-12">
                <div class="portlet box green-meadow calendar">
                    <div class="portlet-title">
                        <div class="caption">
                            <i class="fa fa-gift"></i>日程表
                        </div>
                    </div>
                    <div class="portlet-body">
                        <div class="row">
                            <div class="col-md-3 col-sm-12">
                                <h3 class="caption">添加学习日程</h3>

                                <div id="external-events">
                                    <div><h4>课件列表</h4></div>
                                    <div id="event_box">
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-9 col-sm-12">
                                <div id="calendar" class="has-toolbar">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(function () {
        var temp;
        Metronic.init(); // init metronic core componets
        Layout.init(); // init layout
        $.ajax({
            url: basePath + "user/center/getRegisterScorms",
            dataType: "json",
            type: "post",
            success: function (result) {
                Calendar.init(result);
            },
            error: doError
        });
    });
</script>
</body>
</html>