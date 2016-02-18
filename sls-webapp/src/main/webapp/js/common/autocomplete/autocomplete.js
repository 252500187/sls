function autoComplete(inputs,url) {
    var basePath = (function() {
        var url = window.location + "";
        var h = url.split("//");
        var x = h[1].split("/");
        return h[0] + "//" + window.location.host + "/" + x[1] + "/";
    })();
    var index = 0;              //当前选中项的行号，从1开始
    var maxId = 0;             //最大数据项的行号
    var $this;                      //当前google框的input
    var $showTextId;            //当前google框的hidden域
    var $showTipOri;            //当前google框的提示div
    var closeFlag = true;    //关闭的标志位。当鼠标在提示框的div上经过时，blur事件不关闭提示框
    var $oriObj;                   //上一个高亮的div
    var $desObj;                  //即将高亮的div
    var setTimeOutEvent;
    var delayTime =0;
    //向当页中的body添加提示的div
    $("#autoTipDiv").size() == 0 && $("body").append("<div id='autoTipDiv' class='autoTipDiv'>" +
            "<iframe style=\"position:absolute; z-index:-1;width:110px;\" frameborder=\"0\" src=\"about:blank\"></iframe>" +
            "<div id='autoTipImg'></div>" +
        "</div>");
    var $showTip = $("#autoTipDiv")
        .hover(
            function() {
                closeFlag = false
            },
            function() {
                closeFlag = true;
            })
        .bind("scroll", function() {
            $this&&$this.focus()
        });
    var $showTipImg = $("#autoTipImg");
    //参数重载，若没有参数则默认将整个页面符合条件的input都变成google框，若有参数，则将传入的input变为google框
    inputs = inputs == undefined ? $("input[autocomplete],textarea[autocomplete]").unbind(".autoComplete") : inputs;
    inputs.bind("keydown.autoComplete keyup.autoComplete blur.autoComplete", function(e) {
        //如果该input为readonly则不处理
        if ($(this).attr("readonly"))return;
        //若第一次使用google框或者使用不同的input则重新初始化选择的dom结点
        if ((!$this) || $this[0].id != this.id) {
            $this = $(this);
            $showTextId = $this.siblings("input[autoHidden=autoHidden]");
            $showTipOri = $this.siblings("div[tipDiv=tipDiv]");
        }
        //提示div
        var tempCssStr = $showTipOri.attr("class");
        $showTip.removeClass().addClass(tempCssStr==undefined?$this.attr("divClass")==undefined?"divauto":$this.attr("divClass"):tempCssStr)
            .css({left:$this.offset().left,top:$this.offset().top + $this.outerHeight(),height:"230px",overflowY:"auto",paddingLeft:0});
        $showTip.width()<$this.width()&&$showTip.width($this.width());
        var thisValue = this.value.trim();    //记录当前google框的keyword
        var $showText = $this;                     //当前google框的input
        var targetId = $showTextId[0].id;     //当前google框的input的id
        var _key = e.which;                       //按键的keycode
        var eType = e.type;                       //触发的事件类型
        var flag = $this.attr("flag");          //当前google框的类型标志位
        var sql = $this.attr("sql");            //当前google框的sql
        sql=sql==undefined?"":sql;
        //keydown事件且提示div显示的时候
        if (eType == "keydown" && $showTip[0].style.display != "none") {
            //若按下→，↓则调整选中的项目使其高亮
            if (_key == 39 || _key == 40) {//39:right,40:down
                //若index+1超过了maxId则将index置为1
                index = index + 1 > maxId ? 1 : index + 1;
                $desObj = $("#" + targetId + "_autoTip" + index);
                focusOP($oriObj, $desObj);
                //若显示全部带有滚动条时处理滚动条
                $showTip.scrollTop((index - 11) * 20)
            }
            //若按下←，↑则调整选中的项目使其高亮
            if (_key == 37 || _key == 38) {// 37left,38up
                //若index-1小于1，则将index置为maxId
                index = index - 1 < 1 ? maxId : index - 1;
                $desObj = $("#" + targetId + "_autoTip" + index);
                focusOP($oriObj, $desObj);
                //若显示全部带有滚动条时处理滚动条
                $showTip.scrollTop((index - 11) * 20)
            }
            // 回车或tab的处理
            if (_key == 13 || _key == 9) {
                $desObj = $oriObj;
                maxId != 0 && focusOP(undefined, $oriObj, true);
                $showTip.hide();
            }
            $oriObj = $desObj;
        }
        //keyup事件处理
        if (eType == "keyup") {
            //若按下了ctrl和shift则返回
            if (e.ctrlKey || e.shiftKey)return;
            //如果当前input不为空
            if (this.value != "") {
                //如果不是按下的上下左右、回车、ctrl则显示提示
                if (_key != 39 && _key != 40 && _key != 37 && _key != 38 && _key != 13 && _key != 17) {
                    $showTextId.val("");
                    $showTip[0].style.display == "none" && $showTip.show();
                    $showTipImg.html("<span class='auo_text_loading'>正在获取数据……</span>");
                    setTimeOutEvent != undefined && setTimeOutEvent != null && clearTimeout(setTimeOutEvent);
                    window.autoCompleteURL = url;
                    setTimeOutEvent = setTimeout('autoCompleteAjax(false,window.autoCompleteURL)', delayTime);
                }
            }
            else {
                $showTip.hide();
                $showTextId.val("")
            }
        }
        //blur事件时，若鼠标在提示div外时关闭。
        if (eType == "blur"&&closeFlag) {
            this.value == "" && $showTextId.val("");
            $("#autoTipDiv").hide();
        }
        //ajax请求主函数，参数isShowAll为是否显示所有提示的标志位
        window.autoCompleteAjax = function (isShowAll,ajaxUrl) {
            $.ajax({
                type:"POST",
                url:ajaxUrl == undefined ? basePath + "autoComplete" : ajaxUrl,
                dataType:"json",
                data:"keyword=" + encodeURI(thisValue) + "&sql=" + encodeURI(sql)+"&flag=" + flag+"&isShowAll="+isShowAll,
                error:function() {
                    $showTipImg.html("<span class='auo_text_warn'>数据返回错误！</span>");
                },
                success:function(resultArray) {
                    if (resultArray != "") {
                        // 返回结果的个数
                        maxId = resultArray.length;
                        var divStr = [];
                        if(maxId>10){
                            $showTip.css({height:$showTip.height(),overflowY:"scroll"})
                        }
                        for (var i = 0; i < maxId; i++) {
                            // 截取出显示的名称
                            var showResult = resultArray[i].name;
                            // 将名称中与关键字匹配的部分，变成红色
                            var formatStr = showResult.replace(RegExp(thisValue,"i"), "<b><span style='color:red'>$&</span></b>");
                            // 截取出显示的id
                            var formatStrId = resultArray[i].id;
                            //wangf begin 填写google框时如果手动输入 如果该名称存在自动填入id并截取前后空格.
                            $showText.val().trim() == showResult && $showTextId.val(formatStrId) && $showText.val(showResult);
                            //wangf end
                            divStr.push("<div id='" + targetId + "_autoTip" + (i + 1) + "' resultOptions='resultOptions' class='itemDiv' strId='" + formatStrId + "'>" + formatStr + "</div>");
                        }
                        //若当前google第一次打开只显示10条时加入显示全部按钮
                        if (!isShowAll) {
                            divStr.push("<div class='showAllDiv'><a id='isShowAll" + $showText.attr("id") + "' class='auto_text_btn'>显示全部</a></div>");
                        }
                        // 返回显示的内容
                        $showTipImg.html(divStr.join(""));
                        $showTip.show();
                        $this.focus();
                        //显示全部按钮点击事件
                        $("#isShowAll"+$showText.attr("id")).bind("mousedown",function(){
                            $showTipImg.html("<span class='auo_text_loading'>正在获取数据……</span>");
                            autoCompleteAjax(true,url);
                        });
                        //提示结果集的事件
                        $showTipImg.find("div[resultOptions=resultOptions]").hover(
                            function() {
                                $oriObj = $(this);
                                focusOP($("#"+targetId + "_autoTip" + index),$oriObj);
                                index = parseInt(this.id.replace(targetId + "_autoTip",""),10);
                            },
                            function() {
                                focusOP($(this));
                            })
                        .bind("mousedown",function() {
                            focusOP(undefined,$(this), true);
                            $showTip.hide();
                        });
                        //默认选中第一个
                        index = 1;
                        $oriObj = $("#"+targetId + "_autoTip1");
                        focusOP(undefined,$oriObj)
                    } else {
                        $showTipImg.html("<span class='auo_text_warn'>未找到匹配数据！</span>");
                        maxId=0;
                    }
                }
            })
        };
        //使选中的结果高亮，参数：需要去掉高亮的obj，需要高亮的obj，是否回填
        function focusOP($oriDiv,$focusObj, addFlag) {
            $oriDiv&&$oriDiv.css({"background": "#FFFFFF"});
            if ($focusObj !== undefined) {
                $focusObj.css({background:"#FBEC88"});
                if (addFlag) {
                    $showText.val($focusObj.text());
                    $showTextId.val($focusObj.attr("strId"));
                }
            }
        }
    })
}
$(function() {
    autoComplete();
});