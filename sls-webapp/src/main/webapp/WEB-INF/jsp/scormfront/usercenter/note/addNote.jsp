<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | AddNote</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-minimal.css"/>" rel="stylesheet"
          type="text/css"/>
</head>
<body class="page-header-fixed" style="background-color: #ffffff">
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
                        <a>添加笔记</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="portlet box">
            <div class="portlet-body form">
                <form class="form-horizontal" id="fileGetUp" method="post"
                      enctype="multipart/form-data">
                    <div class="form-body">
                        <div class="form-group ">
                            <label class="control-label col-md-2">笔记图片</label>

                            <div class="col-md-10">
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview thumbnail" data-trigger="fileinput"
                                         style="width: 200px; height: 150px;">
                                    </div>
                                    <div>
													<span class="btn default btn-file">
													<span class="fileinput-new">
													选择图片 </span>
													<span class="fileinput-exists">
													换一张 </span>
                                                    <input type="file" name="noteImg" id="noteImg"/>
													</span>
                                        <a href="#" class="btn red fileinput-exists" data-dismiss="fileinput">
                                            移除 </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">笔记内容</label>

                            <div class="col-md-3">
                                <textarea class="form-control"
                                          id="note" name="note" value=""/></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <hr/>
                            <label class="control-label col-md-2">是否属于课件</label>

                            <div class="col-md-3">
                                <input id="isScorm" type="checkbox" class="make-switch" checked data-on-color="primary"
                                       data-off-color="info">
                            </div>
                        </div>
                        <div class="form-group" id="ScormList">
                            <label class="control-label col-md-2">课件</label>

                            <div class="col-md-3">
                                <select id="scormId" class="form-control input-medium select2me"
                                        data-placeholder="选择...">
                                    <option id="voidScorm" value=""></option>
                                    <c:forEach var="scorm" items="${allScorm}">
                                        <option value="${scorm.scormId}">${scorm.scormName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-offset-3 col-md-9">
                                    <button class="btn purple" type="submit"><i
                                            class="fa fa-check"></i>
                                        添加
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
<script>
    var isScorm = "true";
    $(function () {
        Metronic.init();
        Layout.init();
        if ("${result}" != "") {
            parent.$("#alertPromptMessage").html("添加成功");
            parent.$("#alertPrompt").modal("show");
        }
        $("#isScorm").click(function () {
            if ($("#isScorm").attr("checked")) {
                isScorm = "true";
                $("#ScormList").slideDown("slow");
            } else {
                isScorm = "";
                $("#ScormList").slideUp("slow");
            }
        });
        jQuery.validator.addMethod("isImg", function (value, element, param) {
            if (param) {
                var imgType = value.substr(value.length - 3, 3);
                if ((imgType != "jpg") && (imgType != "png") && (imgType != "gif")) {
                    return false;
                }
                return true;
            }
        }, "请选择图片文件");
    });

    $('#fileGetUp').validate({
        errorElement: 'span',
        errorClass: 'help-block',
        focusInvalid: false,
        rules: {
            upImg: {
                isImg: true
            }
        },
        highlight: function (element) {
            $(element).closest('.form-group').addClass('has-error');
        },
        success: function (label) {
            label.closest('.form-group').removeClass('has-error');
            label.remove();
        },
        errorPlacement: function (error, element) {
            if (element.attr("name") == "upImg" || element.attr("name") == "upScorm") {
                error.insertAfter(element.parent().parent());
            } else {
                error.insertAfter(element);
            }
        },
        submitHandler: function () {
            var scormId = $("#scormId").val();
            var haveImg = "";
            if (isScorm == "" || scormId == "") {
                scormId = "-1";
            }
            if ($("#noteImg").val() != "") {
                haveImg = "true";
            }
            $("#fileGetUp").attr("action",
                    basePath + "user/scorm/userCenterUpNote?scormId=" + scormId + "&note=" + $("#note").val().trim() + "&haveImg=" + haveImg).submit();
        }
    });
</script>