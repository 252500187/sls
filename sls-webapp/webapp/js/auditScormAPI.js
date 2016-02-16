var API = new API_functions();
//自定义1000到65535
var errorCode = "0";
var iniFlag = "false";
var scoInfo = {};

function API_functions() {
    this.LMSInitialize = LMSInitialize;
    this.LMSSetValue = LMSSetValue;
    this.LMSGetValue = LMSGetValue;
    this.LMSCommit = LMSCommit;
    this.LMSFinish = LMSFinish;
    this.LMSGetLastError = LMSGetLastError;
    this.LMSGetErrorString = LMSGetErrorString;
    this.LMSGetDiagnostic = LMSGetDiagnostic;
}

function LMSInitialize(parameter) {
    scoInfo['cmi._version'] = "1.2";
    scoInfo['cmi.core._children'] = "student_id,student_name,lesson_location,credit," +
        "lesson_status,entry,score,score.raw,total_time," +
        "lesson_mode,exit,session_time";
    scoInfo['cmi.suspend_data._children'] = "suspend_data";
    scoInfo['cmi.core.score._children'] = "raw";
    scoInfo['cmi.core._count'] = "12";
    scoInfo['cmi.suspend_data._count'] = "0";

    scoInfo['cmi.core.student_id'] = "";       //Read
    scoInfo['cmi.core.student_name'] = "";     //Read
    scoInfo['cmi.core.lesson_location'] = "";
    scoInfo['cmi.core.credit'] = "";              //Read
    scoInfo['cmi.core.lesson_status'] = "";
    scoInfo['cmi.core.entry'] = "";                 //Read
    scoInfo['cmi.core.score.raw'] = "";
    scoInfo['cmi.core.total_time'] = "";      //Read
    scoInfo['cmi.core.lesson_mode'] = "";
    scoInfo['cmi.core.exit'] = "";                        //Write
    scoInfo['cmi.core.session_time'] = "";                //Write
    scoInfo['cmi.suspend_data'] = "";
    scoInfo['cmi.launch_data'] = "";          //Read
    iniFlag = "true";
    return "true";
}

function LMSSetValue(key, value) {
    key = key.toString();
    value = value.toString();
    if (iniFlag == "false") {
        alert("课件错误:课件未初始化执行setValue");
        errorCode = "301";
        return "false";
    }
    var test = key.substring(key.indexOf("._"), key.length);
    if ((test == "._version") || (test == "._children") || (test == "._count")) {
        alert("课件错误:课件对只读数据执行setValue");
        errorCode = "403";
        return "false";
    }
    if (key == "cmi.core.student_id" || key == "cmi.core.student_name"
        || key == "cmi.core.credit" || key == "cmi.core.entry"
        || key == "cmi.core.total_time" || key == "cmi.launch_data"
        || key == "cmi.core.lesson_mode") {
        alert("课件错误:课件对只读数据执行setValue");
        errorCode = "403";
        return "false";
    }
    if (key == "cmi.core.score.raw") {
        value = parseFloat(value);
        if (value == NaN || value < 0 || value > 100) {
            alert("课件错误:课件对成绩执行setValue，数据范围错误");
            errorCode = "405"
            return "false";
        }
    }
    if (scoInfo[key] == null) {
        errorCode = "401";
        return "false";
    }
    scoInfo[key] = value;
    return "true";
}

function LMSGetValue(key) {
    key = key.toString();
    if (iniFlag == "false") {
        alert("课件错误:课件未初始化执行getValue");
        errorCode = "301";
        return "";
    }
    if (key == "cmi.core.exit" || key == "cmi.core.session_time") {
        alert("课件错误:课件对只写数据执行getValue");
        errorCode = "404";
        return "";
    }
    if (scoInfo[key] == null) {
        errorCode = "401";
        return "";
    }
    if (scoInfo[key] == "") {
        errorCode = "0";
    }
    return scoInfo[key];
}

function LMSCommit(parameter) {
    if (iniFlag == "false") {
        alert("课件错误:课件未初始化执行commit");
        errorCode = "301";
        return "false";
    }
    return "true";
}

function LMSFinish(parameter) {
    if (iniFlag == "false") {
        alert("课件错误:课件未初始化执行finish");
        errorCode = "301";
        return "false";
    }
    if (LMSCommit() == "false") {
        alert("平台错误:课件执行finish时，commit未成功");
        errorCode = "1112";
        return "false";
    }
    iniFlag = "false";
    return "true";
}

function LMSGetLastError() {
    return errorCode;
}

function LMSGetErrorString(error) {
    error = error.toString().trim();
    errorCode = "0";
    switch (error) {
        case "":
            errorCode = "201";
            return "";
        case "0":
            return "Sorry:我检查了一下自己，还没有发现错误啊。";
        case "101":
            return "一般错误";
        case "201":
            return "参数无效";
        case "202":
            return "元素属性无子节点(children属性)";
        case "203":
            return "属性非集合类型，无count属性";
        case "301":
            return "LMS未初始化";
        case "401":
            return "未实现的数据元素模型";
        case "402":
            return "无效的setvalue操作，传递的element是关键字";
        case "403":
            return "元素属性只读";
        case "404":
            return "元素属性只写";
        case "405":
            return "错误的数据类型";
        case "1111":
            return "Sorry:暂时没有学习过程对其赋值！";
        case "1112":
            return "Sorry:结束的时候信息没存进我的脑子里。亲！";
        case "1115":
            return "这是我不知道的错误CODE。";
        default:
            errorCode = "1115";
            return "";
    }
}

function LMSGetDiagnostic(error) {
    error = error.toString().trim();
    errorCode = "0";
    switch (error) {
        case "":
            errorCode = "201";
            return "";
        case "0":
            return "没有问题，继续。";
        case "101":
            return "一般错误";
        case "201":
            return "参数无效";
        case "202":
            return "元素属性无子节点(children属性)";
        case "203":
            return "属性非集合类型，无count属性";
        case "301":
            return "LMS未初始化";
        case "401":
            return "未实现的数据元素模型";
        case "402":
            return "无效的setvalue操作，传递的element是关键字";
        case "403":
            return "元素属性只读";
        case "404":
            return "元素属性只写";
        case "405":
            return "错误的数据类型";
        case "1111":
            return "下次再来学习或许就有值了。";
        case "1112":
            return "麻烦您再进行一次学习尝试，刚才我走神了，我这次努力记住。";
        case "1115":
            return "这是我不知道的错误CODE。";
        default:
            errorCode = "1115";
            return "";
    }
}