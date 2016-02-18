<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | SLS ForgetChangePassword</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../includes/common.jsp" %>
</head>

<body class="page-header-fixed" style="background-color: #ffffff;overflow-x:hidden">
<%@include file="index/navigationMenu.jsp" %>
<div class="page-container">

    <div class="page-content">
        <div class="row">
            <div class="col-md-7 col-md-offset-3">
                <div class="portlet box blue">
                    <div class="portlet-title">
                        <div class="caption">
                            修改密码
                        </div>
                    </div>
                    <div class="portlet-body form">
                        <form class="form-horizontal" method="post" id="changePassword">
                            <div class="form-body">
                                <div class="tab-content">
                                    <div class="alert alert-info display-hide" id="changePasswordSuccess">
                                        <button class="close" data-close="alert"></button>
                                        <span>密码修改成功！</span>
                                    </div>
                                    <div class="alert alert-info display-hide" id="changePasswordError">
                                        <button class="close" data-close="alert"></button>
                                        <span>密码修改失败！可尝试重新发送重置密码邮件</span>
                                    </div>

                                    <br/>

                                    <div id="changePasswordTable" class="tab-pane active">
                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-md-offset-1">新密码:<span
                                                    class="required">
													* </span></label>


                                            <div class="col-md-3">
                                                <input class="form-control form-control-inline input-medium date-picker"
                                                       id="newPassword" name="newPassword" type="text" value=""
                                                       placeholder="输入新密码"/>
                                            </div>
                                        </div>
                                        <br/>

                                        <div class="form-group">
                                            <label class="control-label col-md-3 col-md-offset-1">再次输入:<span
                                                    class="required">
													* </span></label>

                                            <div class="col-md-3">
                                                <input class="form-control form-control-inline input-medium date-picker"
                                                       id="copyNewPassword" name="copyNewPassword" type="password"
                                                       value=""
                                                       placeholder="再次输入新密码"/>
                                            </div>
                                        </div>
                                        </br>
                                    </div>
                                    <div class="form-actions">
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="col-md-offset-3 col-md-9">
                                                    <button class="btn blue" type="submit"><i
                                                            class="fa fa-check"></i>
                                                        修改
                                                    </button>
                                                </div>
                                            </div>
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

</div>
</body>
</html>
<script>
    $(function () {
        Metronic.init();
        Layout.init();
    });

    $('#changePassword').validate({
                errorElement: 'span',
                errorClass: 'help-block',
                focusInvalid: false,
                rules: {
                    newPassword: {
                        required: true,
                        minlength: 6
                    },
                    copyNewPassword: {
                        required: true,
                        equalTo: '#newPassword'
                    }
                },

                messages: {
                    newPassword: {
                        required: "请输入新密码",
                        minlength: "密码长度过小(6位)"
                    },
                    copyNewPassword: {
                        required: "请再次输入新密码",
                        equalTo: "两次输入新密码不相同，请重新输入"
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
                    error.insertAfter(element);
                },
                submitHandler: function () {
                    $.ajax({
                        url: basePath + "forgetChangePassword",
                        data: {
                            password: $("#newPassword").val().md5(),
                            key: "${key}",
                            userId: "${userId}"
                        },
                        dataType: "json",
                        type: "POST",
                        success: function (result) {
                            if (result) {
                                $('#changePasswordSuccess').show();
                            } else {
                                $('#changePasswordError').show();
                            }
                        },
                        error: doError
                    })
                }
            }
    );
</script>