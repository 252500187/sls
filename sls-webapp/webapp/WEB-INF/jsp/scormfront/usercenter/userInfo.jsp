<%--@elvariable id="labelList" type="java.util.List<com.sls.system.entity.Label>"--%>
<%--@elvariable id="myLabelList" type="java.util.List<com.sls.system.entity.Label>"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | UserInfo</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/common.jsp" %>
    <script src="<c:url value="/metronic/assets/global/plugins/pace/pace.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/metronic/assets/global/plugins/pace/themes/pace-theme-minimal.css"/>" rel="stylesheet"
          type="text/css"/>
    <style type="text/css">
        .visible-a {
            visibility: visible;
        }

        .hidden-a {
            visibility: hidden;
        }
    </style>
</head>
<body style="background-color: #ffffff">
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
                        <a>个人资料</a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="portlet box">
            <div class="portlet-body form">
                <div class="portlet-body form">
                    <form class="form-horizontal" id="userInfo" method="post" enctype="multipart/form-data">
                        <div class="form-body">
                            <div class="form-group ">
                                <label class="control-label col-md-2">头像</label>

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
                                                    <input type="file" name="upImg" id="upImg"/>
													</span>
                                            <a href="#" class="btn red fileinput-exists" data-dismiss="fileinput">
                                                移除 </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <hr/>
                                <label class="control-label col-md-2">昵称</label>

                                <div class="col-md-9">
                                    <input id="nickName"
                                           class="form-control form-control-inline input-medium date-picker"
                                           type="text"
                                           maxlength="20"
                                           value="${user.userName}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-2">性别</label>

                                <div class="col-md-9">
                                    <label class="radio-inline">
                                        <input type="radio" name="sex" id="sexMale" value="1">男
                                    </label>
                                    <label class="radio-inline">
                                        <input type="radio" name="sex" id="sexFemale" value="0">女
                                    </label>
                                </div>
                                </label>
                            </div>
                            <div class="form-group">
                                <div class="select2-container select2-container-multi form-control select2_sample3">
                                    <label class="control-label col-md-2">标签</label>

                                    <div class="col-md-9">
                                        <div>
                                            <ul class="select2-choices" style="border-width: 0;" id="myLabelList">
                                                <c:forEach var="label" items="${myLabelList}" varStatus="status">
                                                    <li class='select2-search-choice myLabel' style="border-width: 0;">
                                                        <div class="label label-success" style="cursor: pointer"
                                                             id="${label.labelId}">${label.labelName}</div>
                                                        <a class='select2-search-choice-close visible-a'
                                                           tabindex='-1'></a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                        <div>
                                            <ul class='select2-choices' style="border-width: 0;" id="labelList">
                                                <c:forEach var="label" items="${labelList}" varStatus="status">
                                                    <li class='select2-search-choice allLabels'
                                                        style="border-width: 0;">
                                                        <div class="label label-info" style="cursor: pointer"
                                                             id="${label.labelId}">${label.labelName}</div>
                                                        <a class='select2-search-choice-close hidden-a'
                                                           tabindex='-1'></a>
                                                    </li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-actions fluid">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="col-md-offset-3 col-md-9">
                                        <button class="btn purple" type="submit"><i
                                                class="fa fa-check"></i>
                                            修改
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
</div>
</body>
</html>
<script>
    $(function () {
        $("#sexMale").attr("checked", true);
        if ("${user.sex}" == "0") {
            $("#sexFemale").attr("checked", true);
        }
        if ("${result}" != "") {
            parent.$("#userNickName").html("${userName}");
            parent.$("#userTopName").html("${userName}");
            parent.$("#alertPromptMessage").html("${result}");
            parent.$(".modal-title").html("修改资料");
            parent.$("#alertPrompt").modal("show");
        }
        if ("${imgUrl}" != "") {
            parent.$("#userTopImg").attr("src", "${imgUrl}");
            parent.$("#userHeadPhoto").attr("src", "${imgUrl}");
        }
        jQuery.validator.addMethod("isImg", function (value, element, param) {
            if (param) {
                if (value == "") {
                    return true;
                }
                var imgType = value.substr(value.length - 3, 3);
                if ((imgType != "jpg") && (imgType != "png") && (imgType != "gif")) {
                    return false;
                }
                return true;
            }
        }, "请选择图片文件");
    });

    $('#userInfo').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                focusInvalid: false,
                rules: {
                    nickName: {
                        required: true
                    },
                    upImg: {
                        isImg: true
                    }
                },

                messages: {
                    nickName: {
                        required: "给自己选个昵称吧"
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
                    var myLabelList = "";
                    $("#myLabelList").find("div").each(function () {
                        myLabelList += $(this).attr("id") + ",";
                    });
                    var haveImg = "";
                    if ($("#upImg").val() != "") {
                        haveImg = "true";
                    }
                    $("#userInfo").attr("action",
                            basePath + "user/center/editUserInfo?userId=${user.userId}"
                                    + "&userName=" + $("#nickName").val().trim() + "&sex=" + $("input[name=sex]:checked").val()
                                    + "&myLabelList=" + myLabelList + "&haveImg=" + haveImg).submit();
                }
            }
    );

    $(".allLabels").live("click", function () {
        var addLabelObj = $(this);
        $("#myLabelList").append(addLabelObj);
        addLabelObj.attr("class", "select2-search-choice myLabel");
        addLabelObj.find("a").attr('class', 'select2-search-choice-close visible-a');
        addLabelObj.find("div").attr('class', "label label-success");
        addLabelObj.unbind("click");
    });

    $(".visible-a").live("click", function () {
        var removeLabelObj = $(this);
        $("#labelList").append(removeLabelObj.parent("li"));
        removeLabelObj.parent("li").attr("class", "select2-search-choice allLabels");
        removeLabelObj.parent("li").find("a").attr("class", "select2-search-choice-close hidden-a");
        removeLabelObj.parent("li").find("div").attr('class', "label label-info");
        removeLabelObj.unbind("click");
    })

</script>