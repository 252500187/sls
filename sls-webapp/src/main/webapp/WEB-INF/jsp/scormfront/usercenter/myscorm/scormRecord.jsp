<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | ScormRecord</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../../includes/common.jsp" %>
    <link rel="stylesheet" type="text/css" href="<c:url value="/metronic/assets/admin/pages/css/tasks.css"/>"/>
</head>
<body class="page-header-fixed">
<div class="page-container">
    <div class="page-content-wrapper">
        <div class="page-content">
            <div class="row">
                <div class="col-md-10">
                    <c:forEach var="record" items="${records}" varStatus="status">
                        <div class="note note-info">
                            <h4 class="block">${record.studyTime}&nbsp;&nbsp;学习章节${record.title}</h4>
                        </div>
                    </c:forEach>
                    <c:if test="${fn:length(records)<1}">
                        <h4>您还未学习该课件,暂无记录</h4>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
</script>