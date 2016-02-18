<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | ScormTree</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/js/common/zTree-v3.5.14/css/zTreeStyle/zTreeStyle.css"/>"/>
    <script src="<c:url value="/metronic/assets/global/plugins/jquery-1.11.0.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/js/common/zTree-v3.5.14/js/jquery.ztree.all-3.5.min.js"/>"
            type="text/javascript"></script>
</head>
<body>
<div id="scoTree" class="ztree"
     style="width:100%; height:400px; border: 0px solid; float: left; overflow-x:auto; overflow-y:auto">
</div>
</body>
</html>
<script>
    var scoSrc = "";
    <c:forEach var="scoNode" items="${scoList}">
    <c:if test="${scoNode.lastVisit==isLast}">
    parent.scoId = "${scoNode.scoId}";
    scoSrc = "${scoNode.url}";
    </c:if>
    </c:forEach>

    var basePath = (function () {
        var url = window.location + "";
        var h = url.split("//");
        var x = h[1].split("/");
        return h[0] + "//" + window.location.host + "/" + x[1] + "/";
    })();

    var settingMenu = {
        view: {
            expandSpeed: "fast"
        },
        check: {
            enable: false
        },
        data: {
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId"
            }
        },
        callback: {
            onClick: zTreeOnClick
        }
    };

    var zNodes = [
        <c:forEach var="scoNode" items="${scoList}">
        {id: "${scoNode.treeId}",
            pId: "${scoNode.treeParentId}",
            name: "(${scoNode.showStudyState})${scoNode.title}",
            src: "${scoNode.url}",
            scoId: "${scoNode.scoId}"},
        </c:forEach>
    ];

    function zTreeOnClick(event, treeId, treeNode) {
        if (treeNode.id == "1") {
            $.fn.zTree.getZTreeObj("scoTree").expandAll(true);
        }
        if (treeNode.src.trim() == "") {
            return;
        }
        parent.scoId = treeNode.scoId;
        parent.nowScoId = treeNode.scoId;
        parent.$("#scormIframe").attr("src", treeNode.src);
        $.ajax({
            url: basePath + "user/scorm/treeScoClick?scormId=" + parent.scormId + "&scoId=" + parent.scoId,
            dataType: "json",
            type: "get"
        })
    }

    $(function () {
        $.fn.zTree.init($("#scoTree"), settingMenu, zNodes).expandAll(true);
        if (parent.scoId != "") {
            parent.$("#promptButton1").click(function(){
                parent.$("#scormIframe").attr("src", scoSrc);
            });
            parent.$("#alertConfirm").modal("show");
            parent.$("#alertConfirmMessage").html("是否继续上次学习进度");
        }
    });
</script>