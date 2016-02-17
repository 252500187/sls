package com.sls.util;

public class DictConstant {

    private DictConstant() {
    }

    //空值
    public static final int VOID_VALUE = -1;

    //用户初始化性别
    public static final int VOID_SEX = -1;

    //暂无记录
    public static final String NO_LOG = "暂无记录";

    //课件完成方式  浏览完成
    public static final int COMPLETE_WAY_1 = 1;

    //状态
    public static final String STATE = "state";

    //课件审核状态
    public static final String SCORM_STATE = "scormState";
    // 0 无效
    public static final int NO_USE = 0;
    // 1 有效
    public static final int IN_USE = 1;

    // 上传Scorm的地址
    public static final String TOP_SCORM_FILE_NAME = "scorms";
    // SCORM解压到的文件夹名
    public static final String SCORM_FILE_NAME = "scorm";
    // SCORM对应的图片名
    public static final String SCORM_IMG = "logo.jpg";
    // SCORM对应的课件名
    public static final String SCORM_NAME = "scorm.zip";
    // SCORM课件XML名
    public static final String IMSMANIFEST = "imsmanifest.xml";
    //上传用户头像的地址
    public static final String USER_PHOTO_NAME = "users/userphoto";
    //图片格式
    public static final String PHOTO_FORM = ".jpg";
    //笔记图片地址
    public static final String STUDY_IMG = "scorms/studyImg";
    //笔记类型图片
    public static final int IMG = 1;
    //笔记类型文字
    public static final int TEXT_TYPE = -1;

    //最后访问的SCO
    public static final int LAST_VISIT = 1;

    //SCO学习状态
    public static final String STUDY_STATE = "studyState";
    //未看
    public static final int STUDY_STATE_0 = 0;
    //已看
    public static final int STUDY_STATE_1 = 1;

    //scorm 数据模型对应值
    //通过
    public static final String LESSON_STATUS_PASS = "passed";
    //已完成
    public static final String LESSON_STATUS_COMPLETED = "completed";
    //浏览 （无lesson_mode，则不会出现浏览状态）
//    public static final String LESSON_STATUS_BROWSED = "browsed";
//非完成（课件赋值）
//    public static final String LESSON_STATUS_INCOMPLETE = "incomplete";
    //失败
    public static final String LESSON_STATUS_FAILED = "failed";
    //未尝试
    public static final String LESSON_STATUS_NOT_ATTEMPTED = "not attempted";
    //首次学习
    public static final String ENTRY_INI = "ab-initio";
    //再次学习
    public static final String ENTRY_RE = "resume";
    //重点
    public static final String CREDIT_IM = "credit";
    //非重点
    public static final String CREDIT_NO = "no-credit";
    //浏览
    public static final String LESSON_MODE_BROWSE = "browse";
    //正常模式
    public static final String LESSON_MODE_NORMAL = "normal";
    //再次阅读
    public static final String LESSON_MODE_REVIEW = "review";


    //管理员推荐级别
    public static final String RECOMMEND = "recommend";
    //无级别
    public static final int RECOMMEND_0 = 0;
    //级别1
    public static final int RECOMMEND_1 = 1;
    //级别2
    public static final int RECOMMEND_2 = 2;
    //级别3
    public static final int RECOMMEND_3 = 3;
    //级别4
    public static final int RECOMMEND_4 = 4;
    //级别5
    public static final int RECOMMEND_5 = 5;

    // 管理员角色,shiro配置权限
    public static final String ROLE_AUTHORITY_ADMIN = "admin";
    // 用户角色
    public static final String ROLE_AUTHORITY_USER = "user";

    //获得的经验值
    public static final int EXP_SCORE = 10;

    //用户默认头像
    public static final String DEFAULT_USER_PHOTO = "img/defaultImg/userDefaultImg.jpg";
    //用户默认名
    public static final String DEFAULT_USER_NAME = "地球人";


}