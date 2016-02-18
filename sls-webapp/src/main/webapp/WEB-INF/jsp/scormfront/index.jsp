<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | Welcome to SLS</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../includes/common.jsp" %>
</head>
<body class="page-header-fixed" style="background-color: #ffffff;overflow-x:hidden">
<%@include file="index/navigationMenu.jsp" %>
<div class="page-container" style="margin-left: 30px;margin-right: 20px">
<div class="row">
<div class="col-md-9">
<a name="bestHot"></a>

<div class="row">
    <c:if test="${recommendIndexScorms[0]!=null}">
        <div class="col-md-5 col-md-offset-1">
            <div onclick="scormInfo('${recommendIndexScorms[0].scormId}')">
                <img src="${recommendIndexScorms[0].imgPath}" alt="${recommendIndexScorms[0].scormName}"
                     class="img-responsive"/>
            </div>
            <span>${recommendIndexScorms[0].scormName}</span>
        </div>
    </c:if>

    <div class="col-md-5">
        <div class="row">
            <c:if test="${recommendIndexScorms[1]!=null}">
                <div class="col-md-6">
                    <div onclick="scormInfo('${recommendIndexScorms[1].scormId}')">
                        <img src="${recommendIndexScorms[1].imgPath}" alt="${recommendIndexScorms[1].scormName}"
                             class="img-responsive"/>
                    </div>
                    <span>${recommendIndexScorms[1].scormName}</span>
                </div>
            </c:if>
            <c:if test="${recommendIndexScorms[2]!=null}">
                <div class="col-md-6">
                    <div onclick="scormInfo('${recommendIndexScorms[2].scormId}')">
                        <img src="${recommendIndexScorms[2].imgPath}" alt="${recommendIndexScorms[2].scormName}"
                             class="img-responsive"/>
                    </div>
                    <span>${recommendIndexScorms[2].scormName}</span>
                </div>
            </c:if>
        </div>
        <div class="row">
            <c:if test="${recommendIndexScorms[3]!=null}">
                <div class="col-md-6">
                    <div onclick="scormInfo('${recommendIndexScorms[3].scormId}')">
                        <img src="${recommendIndexScorms[3].imgPath}" alt="${recommendIndexScorms[3].scormName}"
                             class="img-responsive"/>
                    </div>
                    <span>${recommendIndexScorms[3].scormName}</span>
                </div>
            </c:if>
            <c:if test="${recommendIndexScorms[4]!=null}">
                <div class="col-md-6">
                    <div onclick="scormInfo('${recommendIndexScorms[4].scormId}')">
                        <img src="${recommendIndexScorms[4].imgPath}" alt="${recommendIndexScorms[4].scormName}"
                             class="img-responsive"/>
                    </div>
                    <span>${recommendIndexScorms[4].scormName}</span>
                </div>
            </c:if>
        </div>
    </div>
</div>
<br/>

<div class="row">
    <div class="col-md-10 col-md-offset-1">
        <div class="row">
            <c:if test="${recommendIndexScorms[5]!=null}">
                <div class="col-md-3">
                    <div onclick="scormInfo('${recommendIndexScorms[5].scormId}')">
                        <img src="${recommendIndexScorms[5].imgPath}" alt="${recommendIndexScorms[5].scormName}"
                             class="img-responsive"/>
                    </div>
                    <span>${recommendIndexScorms[5].scormName}</span>
                </div>
            </c:if>
            <c:if test="${recommendIndexScorms[6]!=null}">
                <div class="col-md-3">
                    <div onclick="scormInfo('${recommendIndexScorms[6].scormId}')">
                        <img src="${recommendIndexScorms[6].imgPath}" alt="${recommendIndexScorms[6].scormName}"
                             class="img-responsive"/>
                    </div>
                    <span>${recommendIndexScorms[6].scormName}</span>
                </div>
            </c:if>
            <c:if test="${recommendIndexScorms[7]!=null}">
                <div class="col-md-3">
                    <div onclick="scormInfo('${recommendIndexScorms[7].scormId}')">
                        <img src="${recommendIndexScorms[7].imgPath}" alt="${recommendIndexScorms[7].scormName}"
                             class="img-responsive"/>
                    </div>
                    <span>${recommendIndexScorms[7].scormName}</span>
                </div>
            </c:if>
            <c:if test="${recommendIndexScorms[8]!=null}">
                <div class="col-md-3">
                    <div onclick="scormInfo('${recommendIndexScorms[8].scormId}')">
                        <img src="${recommendIndexScorms[8].imgPath}" alt="${recommendIndexScorms[8].scormName}"
                             class="img-responsive"/>
                    </div>
                    <span>${recommendIndexScorms[8].scormName}</span>
                </div>
            </c:if>
        </div>
    </div>
</div>
<br/>

<div class="row">
    <a name="publicClass"></a>

    <div class="col-md-12">
        <hr/>
        <h2>
            <div class="caption-sidebar">正在进行的公开课，快来参加！</div>
        </h2>
        <br/><br/>

        <div class="portlet">
            <div class="portlet-body">
                <div class="row margin-bottom-40">
                    <c:forEach var="scorm" items="${publicScorms}">
                        <div class="col-md-3">
                            <div class="pricing hover-effect">
                                <div class="pricing-head">
                                    <h3>${scorm.scormName}
                                        <span style="height: 18px;overflow: hidden"> ${scorm.publicDescription} </span>
                                    </h3>
                                    <h4><i style="font-size: 15px">${scorm.startTime}</i>-<i
                                            style="font-size: 15px">${scorm.endTime}</i>
											<span>
											正在进行中... </span>
                                    </h4>
                                </div>
                                <ul class="pricing-content list-unstyled">
                                    <li>
                                        <img src="${scorm.imgPath}" class="img-responsive" style="height: 150px">
                                    </li>
                                    <li>
                                        <i class="fa fa-star"></i>课件分数:&nbsp;${scorm.score}分
                                    </li>
                                    <li>
                                        <i class="fa fa-signal"></i>推荐等级:&nbsp;${scorm.recommendLevel}级
                                    </li>
                                    <li>
                                        <i class="fa fa-users"></i>注册人数:&nbsp;${scorm.registerSum}人
                                    </li>
                                </ul>
                                <div class="pricing-footer">
                                    <p style="height: 20px;overflow: hidden">
                                            ${scorm.description}
                                    </p>
                                    <a onclick="scormInfo(${scorm.scormId})" class="btn yellow-crusta">
                                        查看 <i class="m-icon-swapright m-icon-white"></i>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<br/>

<a name="recommandUsers"></a>

<div class="row">
    <div class="col-md-12">
        <hr/>
        <h2>
            <div class="caption-sidebar">推荐用户</div>
        </h2>
    </div>
    <br/><br/>
    <c:forEach var="user" items="${recommendUsers}">
        <div class="col-md-2">
            <div class="meet-our-team">
                <h3 style="background-color: #b1c8d7">${user.userName}
                    <small>经验值:${user.score}</small>
                </h3>
                <div class="team-info" style="background-color: #b1c8d7">
                    <a onclick="userInfo(${user.userId})" class="pull-right btn grey">查看信息<i
                            class="m-icon-swapright m-icon-white"></i>
                    </a>
                    <img src="${user.imgUrl}" alt="${user.userName}" class="img-responsive"
                         style="height: 100px;padding: 5px;"/>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<a name="scormScore"></a>

<div class="row">
    <div class="col-md-10 col-md-offset-1">

        <div class="row">
            <div class="col-md-12">
                <hr/>
                <h2>
                    <div class="caption-sidebar">课件评分</div>
                </h2>
            </div>
            <div class="row thumbnails">
                <c:forEach var="scorm" items="${scormScore}">
                    <div class="col-md-3">
                        <div class="meet-our-team">
                            <h3 style="height: 35px;overflow: hidden">${scorm.scormName}
                            </h3>
                            <small>${scorm.passDate}</small>
                            <img src="${scorm.imgPath}" alt="${scorm.imgPath}" class="img-responsive"
                                 style="height: 150px"/>

                            <div class="team-info">
                                <p style="height: 20px;overflow: hidden">
                                        ${scorm.description}
                                </p>
                                <a onclick="scormInfo(${scorm.scormId})" class="btn blue pull-right">查看<i
                                        class="m-icon-swapright m-icon-white"></i></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <a name="recommandLevel"></a>

        <div class="row">
            <div class="col-md-12">
                <hr/>
                <h2>
                    <div class="caption-sidebar">推荐等级</div>
                </h2>
            </div>
            <div class="row thumbnails">
                <c:forEach var="scorm" items="${scormLevel}">
                    <div class="col-md-3">
                        <div class="meet-our-team">
                            <h3 style="height: 35px;overflow: hidden">${scorm.scormName}
                            </h3>
                            <small>${scorm.passDate}</small>
                            <img src="${scorm.imgPath}" alt="${scorm.imgPath}" class="img-responsive"
                                 style="height: 150px"/>

                            <div class="team-info">
                                <p style="height: 20px;overflow: hidden">
                                        ${scorm.description}
                                </p>
                                <a onclick="scormInfo(${scorm.scormId})" class="btn blue pull-right">查看<i
                                        class="m-icon-swapright m-icon-white"></i></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <a name="studyTime"></a>

        <div class="row">
            <div class="col-md-12">
                <hr/>
                <h2>
                    <div class="caption-sidebar">学习时间</div>
                </h2>
            </div>
            <div class="row thumbnails">
                <c:forEach var="scorm" items="${scormTime}">
                    <div class="col-md-3">
                        <div class="meet-our-team">
                            <h3 style="height: 35px;overflow: hidden">${scorm.scormName}
                            </h3>
                            <small>${scorm.passDate}</small>
                            <img src="${scorm.imgPath}" alt="${scorm.imgPath}" class="img-responsive"
                                 style="height: 150px"/>

                            <div class="team-info">
                                <p style="height: 20px;overflow: hidden">
                                        ${scorm.description}
                                </p>
                                <a onclick="scormInfo(${scorm.scormId})" class="btn blue pull-right">查看<i
                                        class="m-icon-swapright m-icon-white"></i></a>
                            </div>
                        </div>
                    </div>

                </c:forEach>
            </div>
        </div>

        <a name="registerNum"></a>

        <div class="row">
            <div class="col-md-12">
                <hr/>
                <h2>
                    <div class="caption-sidebar">注册人数</div>
                </h2>
            </div>
            <div class="row thumbnails">
                <c:forEach var="scorm" items="${scormSum}">
                    <div class="col-md-3">
                        <div class="meet-our-team">
                            <h3 style="height: 35px;overflow: hidden">${scorm.scormName}
                            </h3>
                            <small>${scorm.passDate}</small>
                            <img src="${scorm.imgPath}" alt="${scorm.imgPath}" class="img-responsive"
                                 style="height: 150px"/>

                            <div class="team-info">
                                <p style="height: 20px;overflow: hidden">
                                        ${scorm.description}
                                </p>
                                <a onclick="scormInfo(${scorm.scormId})" class="btn blue pull-right">查看<i
                                        class="m-icon-swapright m-icon-white"></i></a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

</div>
<div class="col-md-3" style="margin-top: -35px">
    <c:if test="${fn:length(announcements)>0}">
        <div class="row">
            <a name="announcement"></a>

            <div class="meet-our-team">
                <div class="team-info" style="margin-top:30px;padding-top:0px ">
                    <h3 style="margin-top:10px;padding-bottom: 2px;margin-bottom: 2px">
                        <i class="fa fa-bullhorn"></i>公告：
                    </h3>
                    <ul class="page-breadcrumb breadcrumb">
                        <c:forEach var="announcement" items="${announcements}">
                            <li>
                                <div class="col1">
                                    <div class="cont">
                                        <div class="cont-col1">标题：&nbsp;&nbsp;<a>${announcement.announcementTitle}</a>
                                        </div>
                                        <div class="cont-col1">内容：&nbsp;&nbsp;${announcement.announcementContent}
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
            <hr/>
        </div>
    </c:if>
    <div class="row">
        <div class="col-md-11 col-md-offset-1">
            <a name="groupScorms"></a>

            <h1 style="padding-bottom: 30px">
                <div class="caption-sidebar">课件系列</div>
                <div class="caption-sidebar pull-right"><a onclick="groupInfo('-1')">更多</a></div>
            </h1>
            <hr/>
            <c:forEach var="group" items="${groupScorms}">
                <div class="row" style="padding-bottom:10px">
                    <div class="col-md-5">
                        <img src="${group.imgPath}" onclick="lookGroups(${group.groupId})"
                             class="img-responsive" style="height: 80px;">
                    </div>
                    <div class="col-md-7">
                        <a onclick="lookGroups(${group.groupId})">
                                ${group.scormName}系列
                        </a><br/><br/>

                        <p>系列上传日期:<br/>${group.uploadDate}</p>
                    </div>
                </div>
                <hr/>
            </c:forEach>
        </div>
    </div>
    <div class="row">
        <div class="col-md-11 col-md-offset-1">
            <a name="recentUp"></a>

            <div class="portlet-title">
                <h1>
                    <div class="caption-sidebar" style="padding-bottom: 15px">最新上传</div>
                </h1>
            </div>
            <div class="portlet-body">
                <ul class="feeds">
                    <c:forEach var="latest" items="${latestScorms}">
                        <li>
                            <div class="col1">
                                <div class="cont">
                                    <div class="cont-col1">
                                        <img src="${latest.imgPath}" onclick="scormInfo('${latest.scormId}')"
                                             class="img-rounded" style="width: 100px;height: 50px">
                                    </div>
                                    <div class="cont-col2">
                                        <div class="desc-sidebar"
                                             onclick="scormInfo('${latest.scormId}')">${latest.scormName}</div>
                                        <div class="date-sidebar">${latest.uploadDate}</div>
                                    </div>
                                </div>
                            </div>
                        </li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </div>
    <hr/>
    <div class="row" style="margin-top: 20px">
        <a name="scormSort"></a>

        <div class="col-md-11 col-md-offset-1">
            <h2>
                <div class="caption-sidebar">课件分类</div>
            </h2>
        </div>
        <% String color[] = {"green", "red", "blue", "yellow"};%>
        <c:forEach var="label" items="${labels}">
            <div class="col-md-9 col-md-offset-1" style="margin-top: 5px">
                <div class="top-news">
                    <a onclick="sortByLabel(${label.labelId})" class="btn <%=color[(int)(Math.random()*100)%4]%>">
                        <span style="padding: 5px"><i class="fa fa-tags"></i> ${label.labelName}</span>
                        <i class="fa fa-tags top-news-icon"></i>
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</div>
<br/><br/>
<hr/>
<div class="row" style="box-shadow: 0px 0px 10px #888888 inset;">
    <div class="col-md-12" style="padding: 20px">
        <c:if test="${fn:length(announcements)>0}">
            <a class="icon-btn" href="#announcement">
                <i class="fa fa-bullhorn"></i>

                <div>公告</div>
                <span class="badge badge-success"></span>
            </a>
        </c:if>

        <a class="icon-btn" href="#groupScorms">
            <i class="fa fa-upload"></i>

            <div>课件系列</div>
            <span class="badge badge-success"></span>
        </a>

        <a class="icon-btn" href="#recentUp">
            <i class="fa fa-upload"></i>

            <div>最新上传</div>
            <span class="badge badge-success"></span>
        </a>

        <a class="icon-btn" href="#scormSort">
            <i class="fa fa-folder-open"></i>

            <div>课件分类</div>
            <span class="badge badge-important"></span>
        </a>

        <a class="icon-btn" href="#bestHot">
            <i class="fa fa-fire"></i>

            <div>最热课件</div>
            <span class="badge badge-important"></span>
        </a>

        <a class="icon-btn" href="#publicClass">
            <i class="fa fa-flag"></i>

            <div>公开课</div>
            <span class="badge badge-info"></span>
        </a>

        <a class="icon-btn" href="#recommandUsers">
            <i class="fa fa-group"></i>

            <div>推荐用户</div>
            <span class="badge badge-success"></span>
        </a>

        <a class="icon-btn" href="#scormScore">
            <i class="fa fa-edit"></i>

            <div>课件评分</div>
            <span class="badge badge-info"></span>
        </a>

        <a class="icon-btn" href="#recommandLevel">
            <i class="fa fa-level-up"></i>

            <div>推荐等级</div>
            <span class="badge badge-important"></span>
        </a>

        <a class="icon-btn" href="#studyTime">
            <i class="fa fa-clock-o"></i>

            <div>学习时间</div>
            <span class="badge badge-success"></span>
        </a>

        <a class="icon-btn" href="#registerNum">
            <i class="fa fa-male"></i>

            <div>注册人数</div>
            <span class="badge badge-info"></span>
        </a>
    </div>
</div>
<br/>

<div id="groupScormModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel1"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="row" id="groupScormsPage" style="padding: 20px">
            </div>
        </div>
    </div>
</div>
<%@include file="index/footer.jsp" %>
</body>
</html>
<script type="text/javascript">
    $(function () {
        Metronic.init();
        Layout.init();
        Portfolio.init();
    });

    function lookGroups(groupId) {
        $("#groupScormsPage").children().remove();
        $.ajax({
            url: basePath + "tourist/getGroupScorms",
            dataType: "json",
            type: "post",
            data: {
                groupId: groupId
            },
            success: function (result) {
                for (var i in result) {
                    $("#groupScormsPage").append("<div class='col-md-4'>" +
                            "<img src='" + result[i].imgPath + "' class='img-responsive' style='height:150px'/>" +
                            "<a onclick='scormInfo(" + result[i].scormId + ")'>" + result[i].scormName + "</a>" +
                            "</div>");
                }
                $("#groupScormModal").modal();
            },
            error: doError
        });
    }
</script>

