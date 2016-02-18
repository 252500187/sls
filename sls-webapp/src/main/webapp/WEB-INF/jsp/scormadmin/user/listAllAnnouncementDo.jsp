<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | AnnouncementList</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
</head>
<body class="page-header-fixed ">
<div id="mainContent" class="easyui-panel" data-options="fit:true" style="padding: 10px">
    <form class="form-inline">
        发布日期
        <input type="text" class="input-medium"
               name="date" id="date" value=""/>
        公告标题
        <input type="text" class="input-medium"
               name="announcementTitle" id="announcementTitle" value=""/>
        <a class="btn btn-primary" onclick="query()">查询</a>
        <a class="btn btn-primary" onclick="addAnnouncement()">新增</a>
        <a class="btn btn-primary" onclick="delManyAnnouncement()">删除</a>
    </form>
    <table id="dataTable"></table>
    <div id="dataEdit" closed="true" modal="true" style="overflow: hidden" closable="true">
        <iframe style="width: 100%;height: 100%"
                id="contentList"
                name="contentList"
                marginheight="0"
                marginwidth="0"
                frameborder="0" src=""
                allowTransparency="true">
        </iframe>
    </div>
</div>
</body>
</html>
<script type="text/javascript">
    function query() {
        listOption.url = basePath + "admin/user/listAllAnnouncement";
        listOption.data = "date=" + $("#date").val().trim() + "&announcementTitle=" + $("#announcementTitle").val().trim();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: " 公告管理",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'check', title: "<input type='checkbox' id='selectAll' onclick='selectAllAnnouncement()'>全选", align: 'center', width: 50},
                    {field: 'date', title: '发布日期', sortable: true, align: 'center', width: 150 },
                    {field: 'announcementTitle', title: '公告标题', align: 'center', width: 150},
                    {field: 'announcementContent', title: '公告内容', align: 'center', width: 500 },
                    {field: 'state', title: '状态', align: 'center', width: 100 },
                    {field: 'operate', title: '操作', align: 'center', width: 150 }
                ]
            ],
            sortName: "",
            sortOrder: "asc",
            onSortColumn: onSortColumn
        });
    }

    function selectAllAnnouncement() {
        if ($("#selectAll").attr("checked")) {
            $("[name=announcement]").attr("checked", true);
        } else {
            $("[name=announcement]").attr("checked", false);
        }
    }

    function onSortColumn(sortColumn, sortDirection) {
        switch (sortColumn) {
            case "date":
                sortColumn = "date";
                break;
        }
        onSortColumnDefault(sortColumn, sortDirection);
    }

    function format(data) {
        data.resultList = queryFormat(data.resultList);
        return data;
    }

    function queryFormat(temp) {
        for (var i in temp) {
            var operate = "";
            if (temp[i].state == "1") {
                temp[i].state = "已发布";
                operate = "<a onclick='cancelSendAnnouncement(\"" + temp[i].announcementId + "\")'>取消</a>&nbsp;&nbsp;"
            } else {
                temp[i].state = "未发布";
                operate = "<a onclick='sendAnnouncement(\"" + temp[i].announcementId + "\")'>发布</a>&nbsp;&nbsp;"
            }
            temp[i].operate = "<a onclick='editAnnouncement(\"" + temp[i].announcementId + "\")'>编辑</a>&nbsp;&nbsp;"
                    + operate
                    + "<a onclick='delAnnouncement(\"" + temp[i].announcementId + "\")'>删除</a>&nbsp;&nbsp;";
            temp[i].check = "<input type='checkbox' name='announcement' value='" + temp[i].announcementId + "'>";

        }
        return temp;
    }

    function addAnnouncement() {
        var path = basePath + "admin/user/addAnnouncementDo";
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '新增',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function editAnnouncement(announcementId) {
        var path = basePath + "admin/user/editAnnouncementDo?announcementId=" + announcementId;
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '公告内容',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function delManyAnnouncement() {
        $("[name=announcement]").each(function () {
            if ($(this).attr("checked")) {
                delAnnouncement($(this).val());
            }
        })
    }

    function delAnnouncement(announcementId) {
        $.ajax({
            url: basePath + "admin/user/delAnnouncement?announcementId=" + announcementId,
            dataType: "json",
            type: "DELETE",
            success: function () {
                query();
            },
            error: doError
        })
    }

    function cancelSendAnnouncement(announcementId){
        $.ajax({
            url: basePath + "admin/user/cancelSendAnnouncement?announcementId=" + announcementId,
            dataType: "json",
            type: "POST",
            success: function () {
                query();
            },
            error: doError
        })
    }

    function sendAnnouncement(announcementId){
        $.ajax({
            url: basePath + "admin/user/sendAnnouncement?announcementId=" + announcementId,
            dataType: "json",
            type: "POST",
            success: function () {
                query();
            },
            error: doError
        })
    }

    $(function () {
        initDataGrid();
        query();
    })
</script>