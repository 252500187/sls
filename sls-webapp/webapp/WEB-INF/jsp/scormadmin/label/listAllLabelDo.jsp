<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | Label</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
</head>
<body class="page-header-fixed ">
<div id="mainContent" class="easyui-panel" data-options="fit:true" style="padding: 10px">
    <form class="form-inline">
        标签名
        <input type="text" class="input-medium"
               name="labelName" id="labelName" value=""/>
        <a class="btn btn-primary" onclick="query()"><spring:message code="query"/></a>
        <a class="btn btn-primary" onclick="addLabel()"><spring:message code="add"/></a>
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
        listOption.url = basePath + "admin/label/listAllLabel";
        listOption.data = "labelName=" + $("#labelName").val();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: "标签管理",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'labelName', title: '标签名', sortable: false, align: 'center', width: 200},
                    {field: 'operate', title: '操作', align: 'center', width: 200 }
                ]
            ],
            sortName: "",
            sortOrder: "asc",
            onSortColumn: onSortColumn
        });
    }

    function onSortColumn(sortColumn, sortDirection) {
        switch (sortColumn) {
        }
        onSortColumnDefault(sortColumn, sortDirection);
    }

    function format(data) {
        data.resultList = queryFormat(data.resultList);
        return data;
    }

    function queryFormat(temp) {
        for (var i in temp) {
            temp[i].operate = "<a onclick='editLabel(\"" + temp[i].labelId + "\")'>编辑</a>&nbsp;&nbsp;"
                    + "<a onclick='delLabel(\"" + temp[i].labelId + "\")'>删除</a>&nbsp;&nbsp;";
        }
        return temp;
    }

    function addLabel() {
        var path = basePath + "admin/label/addLabelDo";
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '<spring:message code="add"/>',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function editLabel(labelId) {
        var path = basePath + "admin/label/editLabelDo?labelId=" + labelId;
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '<spring:message code="edit"/>',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function delLabel(labelId) {
        $.messager.confirm("<spring:message code="prompt"/>", "<spring:message code="ensureDelete"/>？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/label/delLabel?labelId=" + labelId,
                    dataType: "json",
                    type: "DELETE",
                    success: function () {
                        $.messager.alert("<spring:message code="succeed"/>", "<spring:message code="deleteSucceed"/>！", "", function () {
                            query();
                        })
                    },
                    error: doError
                })
            }
        })
    }

    $(function () {
        initDataGrid();
        query();
    })
</script>