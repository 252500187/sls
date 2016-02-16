<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | ScormInfo</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
    <title>SLS | 课件信息</title>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/js/common/zTree-v3.5.14/css/zTreeStyle/zTreeStyle.css"/>"/>
    <script src="<c:url value="/js/common/zTree-v3.5.14/js/jquery.ztree.all-3.5.min.js"/>"
            type="text/javascript"></script>
    <script src="<c:url value="/js/auditScormAPI.js"/>" type="text/javascript"></script>
</head>

<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span2">

            <table class="table">
                <tr>
                    <td>
                        <button class="btn btn-danger" id="forbiddenScorm" onclick="forbiddenScorm()">禁用课件</button>
                        <button class="btn btn-primary" id="approveScorm" onclick="approveScorm()">审核通过</button>
                        <button class="btn" onclick="quit()">关闭</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        课件完成率:${completeRate*100}%<br/>
                        设置课件完成方式:<br/>
                        <button class="btn btn-success" onclick="changeCompleteWay('1')">浏览完成</button>
                        <button class="btn btn-success" onclick="changeCompleteWay('-1')">默认</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="btn btn-danger" onclick="changLevel('6')">首页推荐</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="btn btn-primary" onclick="changLevel('1')">1级推荐</button>
                        <button class="btn btn-info" onclick="changLevel('2')">2级推荐</button>
                        <br/>
                        <button class="btn btn-success" onclick="changLevel('3')">3级推荐</button>
                        <button class="btn btn-warning" onclick="changLevel('4')">4级推荐</button>
                        <br/>
                        <button class="btn btn-danger" onclick="changLevel('5')">5级推荐</button>
                    </td>
                </tr>
                <tr>
                    <td>
                        <div id="menuTree" class="ztree"
                             style="max-width:200px; min-height:300px; border: 0px solid; float: left; overflow-x:auto; overflow-y:auto ">
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="span8">
            <iframe id="scormIframe" style="width:95%;border:0px;"
                    allowfullscreen>
            </iframe>
        </div>
        <div class="span2">
            <div class="row">
                <img id="scormImg" src="${scorm.imgPath}" class="img-polaroid">
                <table class="table table-hover">
                    <tr>
                        <td>
                            <nobr>课件名称</nobr>
                        </td>
                        <td>${scorm.scormName}</td>
                    </tr>

                    <tr>
                        <td>推荐等级</td>
                        <td>
                            <c:if test="${scorm.showRecommendLevel!=''}">
                                <img id="recommend" src="${scorm.showRecommendLevel}" width="25px" height="25px"/>
                                ${scorm.recommendLevel}级
                            </c:if>
                        </td>
                    </tr>
                    <tr>
                        <td>总评分</td>
                        <td>
                            ${scorm.score}分
                        </td>
                    </tr>
                    <tr>
                        <td>上传用户</td>
                        <td>${scorm.showUploadUserId}</td>
                    </tr>
                    <tr>
                        <td>上传日期</td>
                        <td>${scorm.uploadDate}</td>
                    </tr>
                    <c:if test="${scorm.inUse==inUse}">
                        <tr>
                            <td>注册人数</td>
                            <td>${scorm.registerSum}</td>
                        </tr>
                        <tr>
                            <td>学习时间</td>
                            <td>${scorm.totalTime}</td>
                        </tr>
                    </c:if>
                    <tr>
                        <td>课件描述</td>
                        <td>${scorm.description}</td>
                    </tr>
                    <tr>
                        <td>使用状态</td>
                        <c:if test="${scorm.inUse==inUse}">
                            <td>在用</td>
                        </c:if>
                        <c:if test="${scorm.inUse!=inUse}">
                            <td>不在用</td>
                        </c:if>
                    </tr>
                    <tr>
                        <td>注册用户</td>
                        <td>
                            <c:forEach var="user" items="${registerUsers}">
                                <a onclick="">${user.userName}</a><br/>
                            </c:forEach>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
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
            },
            key: {
                name: "name"
            },
            keep: {
                parent: true
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
        if (treeNode.src.trim() == "") {
            return;
        }
        $("#scormIframe").attr("src", treeNode.src);
    }


    $(function () {
        <c:if test="${scorm.inUse==inUse}">
        $("#approveScorm").hide();
        </c:if>
        <c:if test="${scorm.inUse!=inUse}">
        $("#forbiddenScorm").hide();
        </c:if>
        $.fn.zTree.init($("#menuTree"), settingMenu, zNodes);
        $.fn.zTree.getZTreeObj("menuTree").expandAll(true);
        $("#scormIframe").attr("src", basePath + "img/studyscormdefaultimg/5.jpg");
        $("#scormIframe").css("height", document.documentElement.clientHeight-20);
    });

    function approveScorm() {
        if (confirm("确认审核通过该课件？")) {
            $.ajax({
                url: basePath + "admin/scorm/approveScorm",
                data: {
                    scormId: "${scorm.scormId}"
                },
                dataType: "json",
                type: "POST",
                success: function () {
                    $("#forbiddenScorm").show();
                    $("#approveScorm").hide();
                    parent.document.getElementById("iframe").contentWindow.query();
                    alert("操作成功!");
                },
                error: doError
            })
        }
    }

    function forbiddenScorm() {
        $.ajax({
            url: basePath + "admin/scorm/forbiddenScorm",
            data: {
                scormId: "${scorm.scormId}"
            },
            dataType: "json",
            type: "POST",
            success: function () {
                $("#approveScorm").show();
                $("#forbiddenScorm").hide();
                parent.document.getElementById("iframe").contentWindow.query();
                alert("操作成功!");
            },
            error: doError
        })
    }

    function changLevel(level) {
        $.ajax({
            url: basePath + "admin/scorm/changScormRecommend",
            data: {
                scormId: "${scorm.scormId}",
                level: level
            },
            dataType: "json",
            type: "POST",
            success: function (src) {
                $("#recommend").attr("src", basePath + src);
                alert("操作成功!");
            },
            error: doError
        })
    }

    function changeCompleteWay(way) {
        $.ajax({
            url: basePath + "admin/scorm/changScormCompleteWay",
            data: {
                scormId: "${scorm.scormId}",
                completeWay: way
            },
            dataType: "json",
            type: "POST",
            success: function () {
                alert("操作成功!");
            },
            error: doError
        })
    }

    function quit() {
        parent.$("#dataEdit").dialog('close');
    }
</script>