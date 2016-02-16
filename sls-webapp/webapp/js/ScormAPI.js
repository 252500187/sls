var API = new API_functions();
//自定义1000到65535
var errorCode = "0";
var iniFlag = "false";
var totalTime = "false";
var passRaw = "";
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

    var ajaxResult = "";
    $.ajax({
        async: false,
        url: basePath + "user/scorm/getScoApiInfo?scoId=" + scoId,
        dataType: "json",
        type: "GET",
        success: function (info) {
            scoInfo['cmi.core.student_id'] = info[0].coreStudentId;       //Read
            scoInfo['cmi.core.student_name'] = info[0].coreStudentName;     //Read
            scoInfo['cmi.core.lesson_location'] = info[0].coreLessonLocation;
            scoInfo['cmi.core.credit'] = info[0].coreCredit;              //Read
            scoInfo['cmi.core.lesson_status'] = info[0].coreLessonStatus;
            scoInfo['cmi.core.entry'] = info[0].coreEntry;                 //Read
            scoInfo['cmi.core.score.raw'] = info[0].coreScoreRaw;
            scoInfo['cmi.core.total_time'] = info[0].coreTotalTime;      //Read
            scoInfo['cmi.core.lesson_mode'] = info[0].coreLessonMode;
            scoInfo['cmi.core.exit'] = info[0].coreExit;                        //Write
            scoInfo['cmi.core.session_time'] = info[0].coreSessionTime;                //Write
            scoInfo['cmi.suspend_data'] = info[0].suspendData;
            scoInfo['cmi.launch_data'] = info[0].launchData;          //Read
            passRaw = info[0].passRaw;
            iniFlag = "true";
            ajaxResult = "true";
        },
        error: function () {
            ajaxResult = "false";
        }
    });
    return ajaxResult;
}

function LMSSetValue(key, value) {
    key = key.toString();
    value = value.toString();
    if (iniFlag == "false") {
        errorCode = "301";
        return "false";
    }
    var test = key.substring(key.indexOf("._"), key.length);
    if ((test == "._version") || (test == "._children") || (test == "._count")) {
        errorCode = "403";
        return "false";
    }
    if (key == "cmi.core.student_id" || key == "cmi.core.student_name"
        || key == "cmi.core.credit" || key == "cmi.core.entry"
        || key == "cmi.core.total_time" || key == "cmi.launch_data"
        || key == "cmi.core.lesson_mode") {
        errorCode = "403";
        return "false";
    }
    if (key == "cmi.core.score.raw") {
        value = parseFloat(value);
        if (value == NaN || value < 0 || value > 100) {
            errorCode = "405"
            return "false";
        }
    }
    if (key == "cmi.core.lesson_status") {
        if (value != "passed" && value != "completed" && value != "browsed" && value != "incomplete" && value != "failed" && value != "not attempted") {
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
        errorCode = "301";
        return "";
    }
    if (key == "cmi.core.exit" || key == "cmi.core.session_time") {
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
        errorCode = "301";
        return "false";
    }
    var ajaxResult = "";
    $.ajax({
        url: basePath + "user/scorm/commitScoApiInfo",
        async: false,
        data: {
            scoId: scoId,
            scormId: scormId,
            coreLessonLocation: scoInfo['cmi.core.lesson_location'].trim(),
            coreLessonStatus: scoInfo['cmi.core.lesson_status'].trim(),
            coreScoreRaw: scoInfo['cmi.core.score.raw'].trim(),
            coreExit: scoInfo['cmi.core.exit'].trim(),
            coreSessionTime: totalTime == "false" ? "" : scoInfo['cmi.core.session_time'].trim(),
            suspendData: scoInfo['cmi.suspend_data'].trim(),
//            需要判断的信息
            passRaw: passRaw,
            coreCredit: scoInfo['cmi.core.credit'].trim()
        },
        dataType: "json",
        type: "POST",
        success: function () {
            ajaxResult = "true";
        },
        error: function () {
            ajaxResult = "false";
        }
    })
    return ajaxResult;
}

function LMSFinish(parameter) {
    totalTime = "true";
    if (iniFlag == "false") {
        errorCode = "301";
        return "false";
    }
    if (LMSCommit() == "false") {
        errorCode = "1112";
        totalTime = "false";
        return "false";
    }
    totalTime = "false";
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