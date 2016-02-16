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
    <title>SLS | DiscussList</title>
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
        listOption.url = basePath + "admin/user/listAllDiscuss";
        listOption.data = "discuss=" + $("#discuss").val().trim();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: "评论列表",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'loginName', title: '帐号', sortable: true, align: 'center', width: 200},
                    {field: 'scormName', title: '课件名', sortable: true, align: 'center', width: 200},
                    {field: 'discussDate', title: '评论日期', align: 'center', width: 200},
                    {field: 'discuss', title: '评论内容', align: 'center', width: 500},
                    {field: 'edit', title: '操作', align: 'center', width: 200}
                ]
            ],
            sortName: "",
            sortOrder: "",
            onSortColumn: onSortColumn
        });
    }

    function onSortColumn(sortColumn, sortDirection) {
        switch (sortColumn) {
            case "discussDate":
                sortColumn = "discuss_date";
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
            temp[i].edit = "&nbsp;<a onclick=shieldDiscuss('" + temp[i].userId + "','" + temp[i].scormId + "')>屏蔽评论</a>&nbsp;";
            rowDataList.push(temp[i]);
        }
        return rowDataList;
    }

    $(function () {
        initDataGrid();
        query();
    })

    function shieldDiscuss(userId, scormId) {
        $.messager.confirm("提示", "确认屏蔽评论？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/user/shieldDiscuss",
                    data: {
                        userId: userId,
                        scormId: scormId
                    },
                    dataType: "json",
                    type: "POST",
                    success: function () {
                        $.messager.alert("成功", "屏蔽成功！", "", function () {
                            query();
                        })
                    },
                    error: doError
                })
            }
        })
    }
</script>