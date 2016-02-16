/*
* Cn2Pinyin.java
* Created on  2013-10-26 下午4:39
* 版本       修改时间          作者      修改内容
* V1.0.1    2013-10-26       gaoxinyu    初始版本
*
*/
package com.core.util.pinyin;

//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 汉字转换位汉语拼音，英文字符不变
 *
 * @author gaoxinyu
 * @version 1.0.1
 */
public class Cn2Pinyin {

    public String converterToFirstSpell(String chines) {
        return getSpellForZh(chines, true);
    }

    public String converterToSpell(String chines) {
        return getSpellForZh(chines, false);
    }

    /**
     * 汉字转换位汉语拼音，英文字符不变
     *
     * @param chines           汉字
     * @param returnFirstSpell true返回拼音的首字母，false返回整个拼音
     * @return 拼音
     */
    private String getSpellForZh(String chines, boolean returnFirstSpell) {
        if (chines == null || "".equals(chines)) {
            return "";
        }
        StringBuilder pinyinName = new StringBuilder();
//        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
//        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] nameChar = chines.toCharArray();
        for (char zhChar : nameChar) {
            if (zhChar > 128) {
//                String spell = returnFirstSpell ? getFirstSpellForChar(zhChar, defaultFormat) : getSpellForChar(zhChar, defaultFormat);
//                pinyinName.append(spell);
            } else {
                pinyinName.append(zhChar);
            }
        }
        return pinyinName.toString();
    }

//    private String getSpellForChar(char zhChar, HanyuPinyinOutputFormat defaultFormat) {
//        try {
//            String[] pinyin = PinyinHelper.toHanyuPinyinStringArray(zhChar, defaultFormat);
//            if (pinyin != null && pinyin.length != 0) {
//                return pinyin[0];
//            } else {
//                return String.valueOf(zhChar);
//            }
//        } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
//            badHanyuPinyinOutputFormatCombination.printStackTrace();
//        }
//        return "";
//    }

//    private String getFirstSpellForChar(char zhChar, HanyuPinyinOutputFormat defaultFormat) {
//        return getSpellForChar(zhChar, defaultFormat).substring(0, 1);
//    }
}
