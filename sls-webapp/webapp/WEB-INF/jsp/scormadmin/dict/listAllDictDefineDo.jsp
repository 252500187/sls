<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
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
    <title><spring:message code="dictDefine"/></title>
<body>
<div id="mainContent" class="easyui-panel" data-options="fit:true" style="padding: 10px">
    <form class="form-inline">
        <spring:message code="dictName"/>
        <input type="text" class="input-medium"
               name="dictName" id="dictName" value=""/>
        <spring:message code="dictDescription"/>
        <input type="text" class="input-medium"
               name="dictDescription" id="dictDescription" value=""/>
        <a class="btn btn-primary" onclick="query()"><spring:message code="query"/></a>
        <a class="btn btn-primary" onclick="addDictDefine()"><spring:message code="add"/></a>
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
        listOption.url = basePath + "admin/dict/listAllDictDefine";
        listOption.data = "dictName=" + $("#dictName").val() + "&dictDescription=" + $("#dictDescription").val();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: "<spring:message code="dictDefine"/>",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'dictName', title: '<spring:message code="dictName"/>', sortable: true, align: 'center', width: 200},
                    {field: 'dictDescription', title: '<spring:message code="dictDescription"/>', sortable: true, align: 'center', width: 200},
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
            case "dictName":
                sortColumn = "dict_name";
                break;
            case "dictDescription":
                sortColumn = "dict_description";
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
            temp[i].operate = "<a onclick='editDictDefine(\"" + temp[i].dictName + "\")'><spring:message code="edit"/></a>&nbsp;&nbsp;"
                    + "<a onclick='delDictDefine(\"" + temp[i].dictName + "\")'><spring:message code="delete"/></a>&nbsp;&nbsp;";
            temp[i].dictName = "<a onclick='listDictValuesDo(\"" + temp[i].dictName + "\")'>" + temp[i].dictName + "</a>"
        }
        return temp;
    }

    function addDictDefine() {
        var path = basePath + "admin/dict/addDictDefineDo";
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '<spring:message code="add"/>',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function editDictDefine(dictName) {
        var path = basePath + "admin/dict/editDictDefineDo?dictName=" + dictName;
        $("#contentList").attr("src", path);
        $('#dataEdit').dialog({
            title: '<spring:message code="edit"/>',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function delDictDefine(dictName) {
        $.messager.confirm("<spring:message code="prompt"/>", "<spring:message code="ensureDelete"/>？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/dict/delDictDefine?dictName=" + dictName,
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

    function listDictValuesDo(dictName) {
        location.href = basePath + "admin/dict/listAllDictValuesDo?dictName=" + dictName;
    }

    $(function () {
        initDataGrid();
        query();
    })
</script>