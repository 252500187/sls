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
        问题内容<input type="text" class="input-medium" id="askContent" value=""/>
        回答内容<input type="text" class="input-medium" id="answerContent" value=""/>
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
        listOption.url = basePath + "admin/user/listAllQuestion";
        listOption.data = "askContent=" + $("#askContent").val().trim() + "&answerContent=" + $("#answerContent").val().trim();
        listOption.pageNumber = 1;
        loadData(listOption);
    }

    function initDataGrid() {
        $('#dataTable').datagrid({
            title: "问题列表",
            pagination: true,
            fitColumns: true,
            columns: [
                [
                    {field: 'askDate', title: '提问日期', sortable: true, align: 'center', width: 200},
                    {field: 'askContent', title: '问题内容', align: 'center', width: 500},
                    {field: 'answerContent', title: '回答内容', align: 'center', width: 500},
                    {field: 'state', title: '状态', sortable: true, align: 'center', width: 100},
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
            case "askDate":
                sortColumn = "ask_date";
                break;
            case "state":
                sortColumn = "new_ask,new_answer";
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
        for (var i in temp) {
            temp[i].state = "未完成";
            if (temp[i].newAsk != 1 && temp[i].newAnswer != 1) {
                temp[i].state = "已完成";
            }
            temp[i].operate = "<a onclick=lookQuestion('" + temp[i].questionId + "')>查看</a>&nbsp;&nbsp;" +
                    "<a onclick=delQuestion('" + temp[i].questionId + "')>删除</a>&nbsp;&nbsp;";
            rowDataList.push(temp[i]);
        }
        return rowDataList;
    }

    $(function () {
        initDataGrid();
        query();
    })

    function lookQuestion(questionId) {
        var path = basePath + "admin/user/lookQuestion?questionId=" + questionId;
        $("#contentFrame").attr("src", path);
        $('#dataEdit').dialog({
            title: '查看',
            height: 400,
            width: 600
        }).dialog('open');
    }

    function delQuestion(questionId) {
        $.messager.confirm("提示", "确认删除问题？", function (r) {
            if (r) {
                $.ajax({
                    url: basePath + "admin/user/delQuestion",
                    data: {
                        questionId: questionId
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