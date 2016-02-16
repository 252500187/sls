var basePath = (function() {
    var url = window.location + "";
    var h = url.split("//");
    var x = h[1].split("/");
    return h[0] + "//" + window.location.host + "/" + x[1] + "/";
})();
$(function(){
    $.ajaxSetup({
        cache: false,
//        timeout: 15000,
        error: doError,
        dataType: "json"
    })
});

function doError(data){
    if(data.status==404){
//        alert("对不起,出错了","未找到请求地址","error");
        return
    }
    if(data.statusText.indexOf("Failure")!=-1){
//        alert("对不起,出错了","操作超时，请检查网络连接","error");
        return
    }
//    if(data.statusText=="timeout"){
//        alert("对不起,出错了","操作超时，请检查网络连接","error");
//    }else{
//        alert("对不起,出错了","error","error");
//    }
}
var listOption = {
    pageSize:10,
    pageNumber:1,
    tableId:"dataTable",
    data:"",
    sortColumn:"",
    sortDirection:""
};
function onSortColumnDefault(sortColumn, sortDirection) {
    listOption.sortColumn = sortColumn;
    listOption.sortDirection = sortDirection;
    loadData(listOption);
}
function loadData(listOpt){
    var $table = $('#'+listOpt.tableId);
    $table.datagrid("loading");
    $.ajax({
        url:listOpt.url,
        data:"pageNumber="+(listOpt.pageNumber-1)+"&pageSize="+listOpt.pageSize+"&sortColumn="+listOpt.sortColumn+"&sortDirection="+listOpt.sortDirection+"&"+listOpt.data,
        dataType:"json",
        type:"post",
        success:function(data){
            if (window["format"]!=undefined) {
                data = format(eval(data));
            }
            $table.datagrid("loaded")
                .datagrid("loadData",data.resultList)
                .datagrid('getPager')
                .pagination({
                    total: data.totalCount,
                    pageSize: listOpt.pageSize,
                    pageNumber: listOpt.pageNumber,
                    pageList:[10,20,30,50,100],
                    onSelectPage: function (pageNumber, pageSize) {
                        listOpt.pageNumber = pageNumber;
                        listOpt.pageSize = pageSize;
                        loadData(listOpt);
                    }
                });
        }
    })
}
/*
 *表单提交方法
 *formSub($form,action)指定form跳转
 *formSub(action)跳转到指定url
 *formSub()不改变url跳转，相当于刷新
 */
function formSub(){
    var $form = $("form");
    if($form.size()==0){
        $("#mainContent").wrap('<form method="post"></form>');
        $form = $("form");
    }
    switch (arguments.length){
        case 0:
            $form.attr("method","post").submit();
            break;
        case 1:
            $form.attr("method","post").attr("action",arguments[0]).submit();
            break;
        case 2:
            arguments[0].attr("method","post").attr("action",arguments[1]).submit();
            break;
    }
}


