<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

<link rel="stylesheet" type="text/css" href="<c:url value="/js/common/bootstrap-2.3.2/css/bootstrap.min.css"/>"/>
<link rel="stylesheet" type="text/css"
      href="<c:url value="/js/common/bootstrap-2.3.2/css/bootstrap-datetimepicker.min.css"/>"/>
<link rel="stylesheet" type="text/css"
      href="<c:url value="/js/common/jquery-easyui-1.3.4/themes/bootstrap/easyui.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/common/jquery-easyui-1.3.4/themes/icon.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/common/cover/cover.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/common/ccjcJS/validate.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/common/autocomplete/autocomplete.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/common/alertMsg/alertMsg.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/js/common/bootstrap-2.3.2/css/bootstrap-responsive.min.css"/>"/>

<script src="<c:url value="/js/common/jquery/jquery-1.8.2.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/bootstrap-2.3.2/js/bootstrap.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/bootstrap-2.3.2/js/bootstrap-datetimepicker.min.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/js/common/autocomplete/autocomplete.min.js"/>" type=text/javascript></script>
<script src="<c:url value="/js/common/jquery-easyui-1.3.4/jquery.easyui.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/jquery-easyui-1.3.4/locale/easyui-lang-zh_CN.js"/>"
        type="text/javascript"></script>
<script src="<c:url value="/js/common/jquery.tools.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/common.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/ccjcJS/ccjcJS.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/cover/cover.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/alertMsg/alertMsg.js"/>" type="text/javascript"></script>

<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%><c:url value="/"/>"/>
<script>
    $(function () {
        JC.inputInit();
    })
</script>