<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

<script src="<c:url value="/metronic/assets/global/plugins/jquery-1.11.0.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/common.js"/>" type="text/javascript"></script>
<script src="<c:url value="/js/common/ccjcJS/ccjcJS.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/bootstrap-sessiontimeout/jquery.sessionTimeout.js"/>" type="text/javascript"></script>
<base href="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()%><c:url value="/"/>"/>
<link rel="icon" href="img/logo/top.jpg" type="image/x-icon"/>
<script>
    $(function () {
        JC.inputInit();
    })
</script>

<%--<!-- BEGIN GLOBAL MANDATORY STYLES -->--%>
<%--<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet" type="text/css"/>--%>
<link href="<c:url value="/metronic/assets/global/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/global/plugins/simple-line-icons/simple-line-icons.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/global/plugins/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/global/plugins/uniform/css/uniform.default.css"/>" rel="stylesheet" type="text/css"/>
<%--<!-- END GLOBAL MANDATORY STYLES -->--%>

<%--<!-- BEGIN PAGE LEVEL STYLES -->--%>
<link href="<c:url value="/metronic/assets/admin/pages/css/coming-soon.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/pages/css/news.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/pages/css/about-us.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/pages/css/pricing-table.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/global/plugins/select2/select2.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/pages/css/login-soft.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/pages/css/error.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/global/plugins/fancybox/source/jquery.fancybox.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/pages/css/portfolio.css"/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/metronic/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.css"/>"/>
<link href="<c:url value="/metronic/assets/admin/pages/css/profile.css"/>" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/metronic/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/metronic/assets/global/plugins/jquery-tags-input/jquery.tagsinput.css"/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/metronic/assets/global/plugins/bootstrap-markdown/css/bootstrap-markdown.min.css"/>">
<link rel="stylesheet" type="text/css" href="<c:url value="/metronic/assets/global/plugins/typeahead/typeahead.css"/>">
<%--<!-- END PAGE LEVEL SCRIPTS -->--%>

<%--<!-- BEGIN THEME STYLES -->--%>
<link href="<c:url value="/metronic/assets/global/css/components.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/global/css/plugins.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/layout/css/layout.css"/>" rel="stylesheet" type="text/css"/>
<link id="style_color" href="<c:url value="/metronic/assets/admin/layout/css/themes/default.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/layout/css/custom.css"/>" rel="stylesheet" type="text/css"/>
<link href="<c:url value="/metronic/assets/admin/layout/css/my.css"/>" rel="stylesheet" type="text/css"/>
<%--<!-- END THEME STYLES -->--%>

<%--<!-- BEGIN CORE PLUGINS -->--%>
<%--<!--[if lt IE 9]>--%>
<script src="<c:url value="/metronic/assets/global/plugins/respond.min.js"/>"></script>
<script src="<c:url value="/metronic/assets/global/plugins/excanvas.min.js"/>"></script>
<%--<![endif]-->--%>
<script src="<c:url value="/metronic/assets/global/plugins/jquery-migrate-1.2.1.min.js"/>" type="text/javascript"></script>
<!-- IMPORTANT! Load jquery-ui-1.10.3.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->
<script src="<c:url value="/metronic/assets/global/plugins/jquery-ui/jquery-ui-1.10.3.custom.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/bootstrap/js/bootstrap.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/bootstrap-hover-dropdown/bootstrap-hover-dropdown.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jquery.blockui.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jquery.cokie.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/uniform/jquery.uniform.min.js"/>" type="text/javascript"></script>
<%--<!-- END CORE PLUGINS -->--%>

<%--<!-- BEGIN PAGE LEVEL PLUGINS -->--%>
<script src="<c:url value="/metronic/assets/global/plugins/jquery-bootpag/jquery.bootpag.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/holder.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jqvmap/jqvmap/jquery.vmap.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.russia.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.world.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.europe.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.germany.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jqvmap/jqvmap/maps/jquery.vmap.usa.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jqvmap/jqvmap/data/jquery.vmap.sampledata.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.resize.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.categories.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jquery.pulsate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/bootstrap-daterangepicker/moment.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/bootstrap-daterangepicker/daterangepicker.js"/>" type="text/javascript"></script>
<!-- IMPORTANT! fullcalendar depends on jquery-ui-1.10.3.custom.min.js for drag & drop support -->
<script src="<c:url value="/metronic/assets/global/plugins/fullcalendar/fullcalendar/fullcalendar.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jquery-easypiechart/jquery.easypiechart.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jquery.sparkline.min.js"/>" type="text/javascript"></script>

<script src="<c:url value="/metronic/assets/global/plugins/countdown/jquery.countdown.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/global/plugins/backstretch/jquery.backstretch.min.js"/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/metronic/assets/global/plugins/select2/select2.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/metronic/assets/global/plugins/bootstrap-fileinput/bootstrap-fileinput.js"/>"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.min.js"/>"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.resize.min.js"/>"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.pie.min.js"/>"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.stack.min.js"/>"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.crosshair.min.js"/>"></script>
<script src="<c:url value="/metronic/assets/global/plugins/flot/jquery.flot.categories.min.js"/>" type="text/javascript"></script>
<script type="text/javascript" src="<c:url value="/metronic/assets/global/plugins/jquery-mixitup/jquery.mixitup.min.js"/>"></script>
<script type="text/javascript" src="<c:url value="/metronic/assets/global/plugins/fancybox/source/jquery.fancybox.pack.js"/>"></script>
<script src="<c:url value="/metronic/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"/>" type="text/javascript"></script>
<%--<!-- END PAGE LEVEL PLUGINS -->--%>

<%--<!-- BEGIN PAGE LEVEL SCRIPTS -->--%>
<script src="<c:url value="/metronic/assets/global/scripts/metronic.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/admin/layout/scripts/layout.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/admin/pages/scripts/ui-general.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/admin/pages/scripts/index.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/admin/pages/scripts/tasks.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/admin/pages/scripts/login-soft.js"/>" type="text/javascript"></script>
<script src="<c:url value="/metronic/assets/admin/pages/scripts/charts.js"/>"></script>
<script src="<c:url value="/metronic/assets/admin/pages/scripts/portfolio.js"/>"></script>
<script src="<c:url value="/metronic/assets/admin/pages/scripts/coming-soon.js"/>" type="text/javascript"></script>
<%--<!-- END PAGE LEVEL SCRIPTS -->--%>
