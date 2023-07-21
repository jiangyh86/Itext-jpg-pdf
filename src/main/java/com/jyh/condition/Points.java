package com.jyh.condition;

import java.util.List;

/**
 * Points 坐标信息
 *
 * @author jiangyiheng
 * @version 2023/07/21 11:02
 **/
public class Points {
    /**
     * 文本内容
     */
    String text;

    /**
     * 坐标
     */
    List<point> point;

    Float confidence;
    class point{
        Float x;
        Float y;
    }
}

