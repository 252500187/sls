<%--@elvariable id="dictName" type="java.lang.String"--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
    <title>SLS | Dict</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
    <title><spring:message code="dictValues"/></title>
<body>
<div id="mainContent" class="easyui-panel" data-options="fit:true" style="padding: 10px">
    <form class="form-inline">
        <spring:message code="dictValue"/><input type="text" name="dictValue" id="dictValue" value=""/>
        <a class="btn btn-primary" onclick="query()"><spring:message code="query"/></a>
        <a class="btn btn-primary" onclick="back()"><spring:message code="pageBack"/></a>
        <a class="btn btn-primary" onclick="addDictValues()"><spring:message code="add"/></a>
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
        listOption.url = basePath + "admin/dict/listAllDictValues?dictName=${dictName}";
        listOption.data = "dictValue=" + $("#dictValue").val();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function back() {
        location.href = basePath + "admin/dict/listAllDictDefineDo";
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: "<spring:message code="dictValues"/>",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'dictName', title: '<spring:message code="dictName"/>', sortable: true, align: 'center', width: 200},
                    {field: 'dictCode', title: '<spring:message code="dictCode"/>', sortable: true, align: 'center', width: 200},
                    {field: 'dictValue', title: '<spring:message code="dictValue"/>', align: 'center', width: 100},
                    {field: 'operate', title: '<spring:message code="operate"/>', align: 'center', width: 200 }
                ]
            ],
            sortName: "",
            sortOrder: "asc",
            onSortColumn: onSortColumn
        });
    }

    function onSortColumn(sortColumn, sortDirection) {
        switch (sortColumn) {
            case "dictCode":
                sortColumn = "dict_code";
                break;
            case "dictValue":
                sortColumn = "dict_value";
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
            temp[i].operate = "<a onclick='editDictValues(\"" + temp[i].dictCode + "\")'><spring:message code="edit"/></a>&nbsp;&nbsp;"
                    + "<a onclick='delDictValues(\"" + temp[i].dictCode + "\")'><spring:message code="delete"/></a>&nbsp;&nbsp;";
        }
        return temp;
    }

    function addDictValues() {
        var path = basePath + "admin/dict/addDictValuesDo?dictName=${dictName}";
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '<spring:message code="add"/>',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function editDictValues(dictCode) {
        var path = basePath + "admin/dict/editDictValuesDo?dictName=${dictName}&dictCode=" + dictCode;
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '<spring:message code="edit"/>',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function delDictValues(dictCode) {
        $.messager.confirm("<spring:message code="prompt"/>", "<spring:message code="ensureDelete"/>？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/dict/delDictValues?dictName=${dictName}" + "&dictCode=" + dictCode,
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