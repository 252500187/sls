<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | PublicScormList</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
    <title>SLS | 公告课件列表</title>
</head>
<body>
<div id="mainContent" class="easyui-panel" data-options="fit:true" style="padding: 10px">
    <form class="form-inline">
        开始时间<input type="text" class="input-medium" name="startTime" id="startTime" value=""/>
        结束时间<input type="text" class="input-medium" name="endTime" id="endTime" value=""/>
        <a class="btn btn-primary" onclick="query()">查询</a>
        <a class="btn btn-primary" onclick="addPublicScorm()">新增</a>
    </form>
    <table id="dataTable"></table>
</div>
<div id="dataEdit" closed="true" modal="true" style="overflow: hidden" closable="true">
    <iframe style="width: 100%;height: 100%"
            id="contentFrame"
            name="contentFrame"
            marginheight="0"
            marginwidth="0"
            frameborder="0" src=""
            allowTransparency="true">
    </iframe>
</div>
</body>
</html>
<script type="text/javascript">
    function query() {
        listOption.url = basePath + "admin/scorm/listAllPublicScorm";
        listOption.data = "startTime=" + $("#startTime").val().trim() + "&endTime=" + $("#endTime").val().trim();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'scormName', title: '课件名称', sortable: true, align: 'center', width: 100},
                    {field: 'startTime', title: '开始时间', sortable: true, align: 'center', width: 200},
                    {field: 'endTime', title: '结束时间', sortable: true, align: 'center', width: 200},
                    {field: 'description', title: '描述', sortable: true, align: 'center', width: 200},
                    {field: 'operate', title: '操作', align: 'center', width: 100}
                ]
            ],
            sortName: "",
            sortOrder: "",
            onSortColumn: onSortColumn
        });
    }

    function onSortColumn(sortColumn, sortDirection) {
        switch (sortColumn) {
            case "startTime":
                sortColumn = "start_time";
                break;
            case "endTime":
                sortColumn = "end_time";
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
            temp[i].operate = "<a onclick='delPublicScorm(" + temp[i].publicId + ")'>取消</a>";
        }
        return temp;
    }

    function addPublicScorm() {
        var contentFrame = $("#contentFrame");
        var dataEdit = $('#dataEdit');
        contentFrame[0].contentWindow.document.write("");
        contentFrame.attr("src", basePath + "admin/scorm/addPublicScormDo");
        dataEdit.dialog({
            title: '新增公开课件',
            height: 400,
            width: 600
        });
        dataEdit.dialog('open');
    }

    function delPublicScorm(publicId) {
        $.messager.confirm("提示", "确认取消该公开课？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/scorm/delPublicScorm?publicId=" + publicId,
                    dataType: "json",
                    type: "DELETE",
                    success: function () {
                        query();
                    }
                })
            }
        })
    }

    $(function () {
        initDataGrid();
        query();
    })
</script>