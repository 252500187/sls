<%--@elvariable id="myLoginId" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | PublicDiscussList</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
    <title>评论管理</title>
</head>
<body>
<div id="mainContent" class="easyui-panel" data-options="fit:true" style="padding: 10px">
    <form class="form-inline">
        评论内容<input type="text" class="input-medium" name="discuss" id="discuss" value=""/>
        <a class="btn btn-primary" onclick="query()">查询</a>
    </form>
    <table id="dataTable"></table>
    <div id="dataEdit" closed="true" modal="true" style="overflow: hidden">
        <iframe style="width: 100%;height: 100%"
                id="contentFrame"
                name="contentFrame"
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
        listOption.url = basePath + "admin/user/listAllPublicDiscuss";
        listOption.data = "discuss=" + $("#discuss").val().trim();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    $(function () {
        initDataGrid();
        query();
    })

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: "讨论列表",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'userName', title: '用户名', align: 'center', width: 200},
                    {field: 'sendTime', title: '发送时间', sortable: true, align: 'center', width: 200},
                    {field: 'discuss', title: '讨论内容', align: 'center', width: 500},
                    {field: 'operate', title: '操作', align: 'center', width: 200}
                ]
            ],
            sortName: "",
            sortOrder: "",
            onSortColumn: onSortColumn
        });
    }

    function onSortColumn(sortColumn, sortDirection) {
        switch (sortColumn) {
            case "sendTime":
                sortColumn = "send_time";
                break;
        }
        onSortColumnDefault(sortColumn, sortDirection);
    }


    function format(data) {
        data.resultList = queryFormat(data.resultList);
        return data;
    }

    function queryFormat(temp) {
        var rowDataList = [];
        var operate = "";
        for (var i in temp) {
            operate = "";
            if (temp[i].inUse == "0") {
                operate = "取消";
            }
            temp[i].operate = "<a onclick=shieldUser(" + temp[i].userId + ",'" + operate + "')>" + operate + "屏蔽用户</a>&nbsp;&nbsp;" +
                    "<a onclick=delDiscuss('" + temp[i].discussId + "')>删除</a>";
            rowDataList.push(temp[i]);
        }
        return rowDataList;
    }

    function shieldUser(id, operate) {
        $.messager.confirm("提示", "确认" + operate + "屏蔽用户？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/user/shieldUser?id=" + id,
                    dataType: "json",
                    type: "POST",
                    success: function () {
                        $.messager.alert("提示", operate + "屏蔽成功", "", function () {
                            query();
                        })
                    },
                    error: doError
                })
            }
        })
    }

    function delDiscuss(discussId) {
        $.messager.confirm("提示", "确认删除讨论？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/user/delDiscuss",
                    data: {
                        discussId: discussId
                    },
                    dataType: "json",
                    type: "POST",
                    success: function () {
                        $.messager.alert("提示", "删除成功", "", function () {
                            query();
                        })
                    },
                    error: doError
                })
            }
        })
    }
</script>