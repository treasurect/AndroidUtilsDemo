package com.treasure.androidutils;

import android.content.ClipboardManager;
import android.content.Context;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ========================================
 * <p>
 * Created by treasure on 2018/5/2.
 * <p>
 * Android String 通用类
 * <p>
 * ========================================
 */

public class StringUtil {

    /**
     * 检查字符串是否为空
     */
    public static boolean isEmpty(CharSequence str) {
        int len;
        if (str == null || (len = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字符串是否全部有字母组成.
     */
    public static boolean isAllLetter(String str) {
        if (str == null) {
            return false;
        }
        for (int i = str.length(); i > 0; i--) {
            if (Character.isLetter(str.charAt(i - 1)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 检查字符串是否有数字[0~9]组成
     */
    public static boolean isAllNumber(String str) {
        if (StringUtil.isEmpty(str)) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符否是中文字符
     */
    public static boolean isChineseChar(char c) {
        return (c >= '\u4E00' && c <= '\u9FA5');
    }

    /**
     * 计算字符串出现次数
     */
    public static int hasStrCount(String str, String sub) {
        return hasStrCount(str, sub, 0);
    }

    /**
     * 计算字符串出现次数
     */
    public static int hasStrCount(String source, String sub, int start) {
        int count = 0;
        int j = start;
        int sublen = sub.length();
        if (sublen == 0) {
            return 0;
        }
        while (true) {
            int i = source.indexOf(sub, j);
            if (i == -1) {
                break;
            }
            count++;
            j = i + sublen;
        }
        return count;
    }

    /**
     * 计算字符出现次数
     */
    public static int hasCharCount(String source, char c) {
        return hasCharCount(source, c, 0);
    }

    /**
     * 计算字符出现次数
     */
    public static int hasCharCount(String source, char c, int start) {
        int count = 0;
        int j = start;
        while (true) {
            int i = source.indexOf(c, j);
            if (i == -1) {
                break;
            }
            count++;
            j = i + 1;
        }
        return count;
    }

    /**
     * 往左截断指定字符
     */
    public static String substringBefore(String str, String separator) {
        if (isEmpty(str) || separator == null) {
            return str;
        }
        if (separator.length() == 0) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(0, pos);
    }

    /**
     * 往右截断指定字符
     */
    public static String substringAfter(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return "";
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return "";
        }
        return str.substring(pos + separator.length());
    }

    /**
     * 截取中间字符串
     */
    public static String substringBetween(String str, String open, String close) {
        if (str == null || open == null || close == null) {
            return null;
        }
        int start = str.indexOf(open);
        if (start != -1) {
            int end = str.indexOf(close, start + open.length());
            if (end != -1) {
                return str.substring(start + open.length(), end);
            }
        }
        return null;
    }

    /**
     * 只替换一次relpace
     */
    public static String replace(String text, String searchString, String replacement) {
        return replace(text, searchString, replacement, 1);
    }

    public static String replace(String text, String searchString,
                                 String replacement, int max) {
        if (isEmpty(text) || isEmpty(searchString) || replacement == null
                || max == 0) {
            return text;
        }
        int start = 0;
        int end = text.indexOf(searchString, start);
        if (end == -1) {
            return text;
        }
        int replLength = searchString.length();
        int increase = replacement.length() - replLength;
        increase = (increase < 0 ? 0 : increase);
        increase *= (max < 0 ? 16 : (max > 64 ? 64 : max));
        StringBuilder buf = new StringBuilder(text.length() + increase);
        while (end != -1) {
            buf.append(text.substring(start, end)).append(replacement);
            start = end + replLength;
            if (--max == 0) {
                break;
            }
            end = text.indexOf(searchString, start);
        }
        buf.append(text.substring(start));
        return buf.toString();
    }

    /**
     * 把一个英文字符串的第一个字符变成大写字母
     */
    public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
                .append(Character.toTitleCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }

    /**
     * 把一个英文字符串的第一个字符变成小写字母
     */
    public static String uncapitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
                .append(Character.toLowerCase(str.charAt(0)))
                .append(str.substring(1)).toString();
    }

    /**
     * 检查一个字符串指定前缀开始,忽略大小写
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return startsWith(str, prefix, true);
    }

    /**
     * 检查一个字符串指定前缀开始
     */
    private static boolean startsWith(String str, String prefix,
                                      boolean ignoreCase) {
        if (str == null || prefix == null) {
            return (str == null && prefix == null);
        }
        return prefix.length() <= str.length() && str.regionMatches(ignoreCase, 0, prefix, 0, prefix.length());
    }

    /**
     * 检查一个字符串指定字符串结尾,忽略大小写
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return endsWith(str, suffix, true);
    }

    /**
     * 检查一个字符串指定字符串结尾
     */
    private static boolean endsWith(String str, String suffix,
                                    boolean ignoreCase) {
        if (str == null || suffix == null) {
            return (str == null && suffix == null);
        }
        if (suffix.length() > str.length()) {
            return false;
        }
        int strOffset = str.length() - suffix.length();
        return str.regionMatches(ignoreCase, strOffset, suffix, 0,
                suffix.length());
    }

    /**
     * url 添加参数
     */
    public static String appendUrlParams(String url, Map<String, String> params) {
        if (url == null)
            return null;

        if (params == null || params.size() == 0)
            return url;

        if (url.indexOf("?") == -1)
            url += "?";

        int i = 0;
        for (Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            if (StringUtil.isEmpty(key) && StringUtil.isEmpty(value)) {
                if (i > 0) {
                    url += "&";
                }

                url += StringUtil.urlEncode(key) + "="
                        + StringUtil.urlEncode(value);
                i++;
            }
        }
        return url;
    }

    /**
     * 实现文本复制功能
     */
    public static void textCopy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
    }


    /**
     * =======================================   正则 匹配   ========================================
     */

    /**
     * 匹配 密码
     */
    public static boolean matchPwd(String str) {
        Pattern pattern = Pattern.compile("^[0-9A-Za-z]{6,20}$");
        return pattern.matcher(str).matches();
    }

    /**
     * 获取手机号码
     */
    public static String getPhoneNum(String phoneStr) {
        if (StringUtil.isEmpty(phoneStr))
            return null;
        Pattern pattern = Pattern.compile("(1[0-9][0-9])\\d{8}");
        Matcher matcher = pattern.matcher(phoneStr);
        if (matcher.find()) {
            return matcher.group(0);
        }
        return null;
    }

    /**
     * 匹配 手机号
     */
    public static boolean isPhone(String phoneStr) {
        Pattern pattern = Pattern.compile("^(1[0-9][0-9])\\d{8}$");
        return pattern.matcher(phoneStr).matches();
    }

    /**
     * 校验身份证
     */
    public static boolean isIDCard(String s) {
        Pattern pattern = Pattern.compile("(\\d{15}$)|(\\d{18}$)|(\\d{17}(\\d|X|x)$)");
        return pattern.matcher(s).matches();
    }

    /**
     * 删除 HTML 标签
     */
    public static String deleteHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

    /**
     * =======================================   转换   ========================================
     */

    /**
     * URL 编码
     */
    public static String urlEncode(String url) {
        if (url == null)
            return null;

        try {
            url = URLEncoder.encode(url, "UTF-8");
            return url;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * URL 解码
     */
    public static String urlDecode(String str) {
        if (str == null)
            return null;

        try {
            str = URLDecoder.decode(str, "UTF-8");
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * base64 编码
     */
    public static String base64Encode(String str) {
        byte[] base64Token = Base64.encode(str.getBytes(), Base64.DEFAULT);
        return base64Token.toString();
    }

    /**
     * base64 解码
     */
    public static String base64Decode(String base64Data) {
        byte[] decode = Base64.decode(base64Data, Base64.DEFAULT);
        return decode.toString();
    }

    /**
     * String 转 byte[]
     */
    public static byte[] strToByte(String str) {
        if (str == null) {
            return null;
        }
        byte[] byteArray = str.getBytes();
        return byteArray;
    }

    /**
     * byte[] 转 String
     */
    public static String byteToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }
}
