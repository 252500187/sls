<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | AdminUpScorm</title>
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
<body class="page-header-fixed" style="background-color: #ffffff">
<div class="row">
    <div class="col-md-12">
        <div class="portlet box">
            <div class="portlet-body form">
                <form class="form-horizontal" id="fileGetUp" method="post"
                      enctype="multipart/form-data">
                    <div class="form-body">
                        <div class="form-group display-hide" id="upInfo">
                            <div class="col-md-1"></div>
                            <div class="alert alert-success alert-dismissable col-md-6">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true"></button>
                                ${result}
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">课件名称</label>

                            <div class="col-md-3">
                                <input class="form-control form-control-inline input-medium date-picker"
                                       id="scormName" name="scormName" type="text" value=""/>
                            </div>
                        </div>
                        <div class="form-group ">
                            <label class="control-label col-md-2">选择图片</label>

                            <div class="col-md-10">
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="fileinput-preview thumbnail" data-trigger="fileinput"
                                         style="width: 200px; height: 150px;">
                                    </div>
                                    <div>
                                        <span class="btn default btn-file">
                                            <span class="fileinput-new">选择图片 </span>
                                            <span class="fileinput-exists">换一张</span>
                                            <input type="file" name="upImg" id="upImg"/>
                                        </span>
                                        <a href="#" class="btn red fileinput-exists" data-dismiss="fileinput">移除</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">选择SCORM课件</label>

                            <div class="col-md-10">
                                <div class="fileinput fileinput-new" data-provides="fileinput">
                                    <div class="input-group input-large">
                                        <div class="form-control uneditable-input span3" data-trigger="fileinput">
                                            <i class="fa fa-file fileinput-exists"></i>&nbsp;
                                            <span class="fileinput-filename"></span>
                                        </div>
                                        <span class="input-group-addon btn default btn-file">
                                            <span class="fileinput-new">选择课件</span>
                                            <span class="fileinput-exists">换一个</span>
                                            <input type="file" name="upScorm" id="upScorm"/>
                                        </span>
                                        <a href="#" class="input-group-addon btn red fileinput-exists"
                                           data-dismiss="fileinput"> 移除 </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-2">课件简介</label>

                            <div class="col-md-3">
                                <textarea class="form-control"
                                          name="description" value=""/></textarea>
                            </div>
                        </div>
                        <div class="form-group">
                            <hr/>
                            <div class="select2-container select2-container-multi form-control select2_sample3">
                                <label class="control-label col-md-2">标签</label>

                                <div class="col-md-9">
                                    <div>
                                        <ul class="select2-choices" style="border-width: 0;" id="myLabelList"></ul>
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
                        <div class="form-group">
                            <hr/>
                            <label class="control-label col-md-2">添加到系列</label>

                            <div class="col-md-3">
                                <input id="isGroup" type="checkbox" class="make-switch" checked data-on-color="primary"
                                       data-off-color="info">
                            </div>
                        </div>
                        <div class="form-group" id="scormGroup" hidden="true">
                            <label class="control-label col-md-2">同系列课件</label>

                            <div class="col-md-3">
                                <select id="groupId" class="form-control input-medium select2me"
                                        data-placeholder="选择...">
                                    <option id="voidScorm" value=""></option>
                                    <c:forEach var="scorm" items="${groupsScorm}">
                                        <option value="${scorm.groupId}">${scorm.scormName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <hr/>
                            <label class="control-label col-md-2">推荐等级</label>

                            <div class="col-md-6">
                                <a class="btn btn-primary" onclick="changeLevel('1')">1级推荐</a>
                                <a class="btn btn-info" onclick="changeLevel('2')">2级推荐</a>
                                <a class="btn btn-success" onclick="changeLevel('3')">3级推荐</a>
                                <a class="btn btn-warning" onclick="changeLevel('4')">4级推荐</a>
                                <a class="btn btn-danger" onclick="changeLevel('5')">5级推荐</a>
                                <a class="btn btn-danger" onclick="changeLevel('6')">首页推荐</a>
                            </div>
                        </div>
                    </div>
                    <div class="form-actions">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="col-md-offset-3 col-md-9">
                                    <button class="btn purple" type="submit"><i
                                            class="fa fa-check"></i>
                                        上传
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
    var haveGroup = "";
    var level = "1";
    $(function () {
        Metronic.init();
        Layout.init();
        $("#isGroup").click();
        $("#isGroup").click(function () {
            if ($("#isGroup").attr("checked")) {
                haveGroup = "true";
                $("#scormGroup").slideDown("slow");
            } else {
                haveGroup = "";
                $("#scormGroup").slideUp("slow");
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

        jQuery.validator.addMethod("isZip", function (value, element, param) {
            if (param) {
                if (value.substr(value.length - 3, 3) != "zip") {
                    return false;
                }
                return true;
            }
        }, "请选择Zip文件");
        if ("${result}" != "") {
            $("#upInfo").show();
        }
    });

    function changeLevel(levelNum) {
        level = levelNum;
    }

    $('#fileGetUp').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                focusInvalid: false,
                rules: {
                    scormName: {
                        required: true
                    },
                    upImg: {
                        required: true,
                        isImg: true
                    },
                    upScorm: {
                        required: true,
                        isZip: true
                    },
                    description: {
                        required: true
                    }
                },

                messages: {
                    scormName: {
                        required: "请输入课件名称"
                    },
                    upImg: {
                        required: "请选择图片"
                    },
                    upScorm: {
                        required: "请选择课件"
                    },
                    description: {
                        required: "请输入课件简介"
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
                    var scormLabelList = "";
                    $("#myLabelList").find("div").each(function () {
                        scormLabelList += $(this).attr("id") + ",";
                    });
                    var groupId = $("#groupId").val();
                    if (groupId == null || groupId == "" || haveGroup == "") {
                        groupId = "-1";
                    }
                    $("#fileGetUp").attr("action",
                            basePath + "admin/scorm/upScorm?scormName=" + $("#scormName").val() + "&scormLabelList=" + scormLabelList + "&groupId=" + groupId + "&recommendLevel=" + level).submit();
                }
            }
    );

    $(".allLabels").live("click", function () {
        $("#myLabelList").append($(this));
        $(this).attr("class", "select2-search-choice myLabel");
        $(this).find("a").attr('class', 'select2-search-choice-close visible-a');
        $(this).find("div").attr('class', "label label-success");
        $(this).unbind("click");
    });

    $(".visible-a").live("click", function () {
        $("#labelList").append($(this).parent("li"));
        $(this).parent("li").attr("class", "select2-search-choice allLabels");
        $(this).parent("li").find("a").attr("class", "select2-search-choice-close hidden-a");
        $(this).parent("li").find("div").attr('class', "label label-info");
        $(this).unbind("click");
    })
</script>