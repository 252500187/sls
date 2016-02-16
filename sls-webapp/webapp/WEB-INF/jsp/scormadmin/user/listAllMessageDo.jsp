<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | MessageList</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
</head>
<body class="page-header-fixed ">
<div id="mainContent" class="easyui-panel" data-options="fit:true" style="padding: 10px">
    <form class="form-inline">
        上传日期
        <input type="text" class="input-medium"
               name="date" id="date" value=""/>
        消息内容
        <input type="text" class="input-medium"
               name="content" id="content" value=""/>
        <a class="btn btn-primary" onclick="query()">查询</a>
        <a class="btn btn-primary" onclick="sendMessage()">发送消息</a>
        <a class="btn btn-primary" onclick="delManyMessage()">删除</a>
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
        listOption.url = basePath + "admin/user/listAllMessage";
        listOption.data = "date=" + $("#date").val() + "&content=" + $("#content").val();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: "消息管理",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'check', title: "<input type='checkbox' id='selectAll' onclick='selectAllMessage()'>全选", align: 'center', width: 50},
                    {field: 'userName', title: '用户名', align: 'center', width: 100},
                    {field: 'date', title: '发送日期', sortable: true, align: 'center', width: 100 },
                    {field: 'content', title: '消息内容', align: 'center', width: 600 },
                    {field: 'state', title: '状态', align: 'center', width: 50 },
                    {field: 'operate', title: '操作', align: 'center', width: 100 }
                ]
            ],
            sortName: "",
            sortOrder: "asc",
            onSortColumn: onSortColumn
        });
    }

    function selectAllMessage() {
        if ($("#selectAll").attr("checked")) {
            $("[name=message]").attr("checked", true);
        } else {
            $("[name=message]").attr("checked", false);
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
            temp[i].operate = "<a onclick='lookMessage(\"" + temp[i].messageId + "\")'>查看</a>&nbsp;&nbsp;"
                    + "<a onclick='transMessage(\"" + temp[i].messageId + "\")'>转发</a>&nbsp;&nbsp;"
                    + "<a onclick='delMessage(\"" + temp[i].messageId + "\")'>删除</a>&nbsp;&nbsp;";
            temp[i].check = "<input type='checkbox' name='message' value='" + temp[i].messageId + "'>";
            if (temp[i].state == "1") {
                temp[i].state = "未读";
            } else {
                temp[i].state = "已读";
            }
        }
        return temp;
    }

    function sendMessage() {
        var path = basePath + "admin/user/sendMessageDo";
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '发送消息',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function lookMessage(messageId) {
        var path = basePath + "admin/user/lookMessageDo?messageId=" + messageId;
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '消息内容',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function transMessage(messageId) {
        var path = basePath + "admin/user/transMessageDo?messageId=" + messageId;
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '转发消息',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function delManyMessage() {
        $("[name=message]").each(function () {
            if ($(this).attr("checked")) {
                delMessage($(this).val());
            }
        })
    }

    function delMessage(messageId) {
        $.ajax({
            url: basePath + "admin/user/delMessage?messageId=" + messageId,
            dataType: "json",
            type: "DELETE",
            success: function () {
                query();
                $("#selectAll").attr("checked",false);
            }
        })
    }

    $(function () {
        initDataGrid();
        query();
    })
</script>