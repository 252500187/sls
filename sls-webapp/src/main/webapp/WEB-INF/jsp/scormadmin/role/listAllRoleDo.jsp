<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html lang="en" class="no-js">
<!--<![endif]-->
<head>
    <meta charset="utf-8"/>
        <title>SLS | RoleList</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1.0" name="viewport"/>
    <%@include file="../../includes/adminCommon.jsp" %>
    <title>><spring:message code="role"/></title>
</head>
<body>
<div id="mainContent" class="easyui-panel" data-options="fit:true" style="padding: 10px">
    <form class="form-inline" action="">
        角色名<input type="text" name="roleName" id="roleName" value="" class="input-medium"/>
        <a class="btn btn-primary" onclick="query()"><spring:message code="query"/></a>
    </form>
    <table id="dataTable"></table>
    <div id="dataEdit" closed="true" modal="true" style="overflow: hidden">
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
        listOption.url = basePath + "admin/role/listAllRole";
        listOption.data = "roleName=" + $("#roleName").val();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: "角色列表",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'roleName', title: '角色名', sortable: true, align: 'center', width: 200},
                    {field: 'authority', title: '权限', align: 'center', width: 200}
                ]
            ],
            sortName: "",
            sortOrder: "",
            onSortColumn: onSortColumn
        });
    }

    function onSortColumn(sortColumn, sortDirection) {
        switch (sortColumn) {
            case "roleName":
                sortColumn = "role_name";
                break;
        }
        onSortColumnDefault(sortColumn, sortDirection);
    }

    $(function () {
        initDataGrid();
        query();
    })
</script>