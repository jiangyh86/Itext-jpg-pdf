package com.jyh.condition;

import lombok.Data;

import java.util.List;

/**
 * Condition 用于接收JPG文本数据JSON格式
 *{
 * 		page:1,
 * 		height:100,
 * 		width:100,
 * 		content:[
 *                        {
 * 				text:"郭度声",
 * 				point:[
 * 					[
 * 						226.025,
 * 						38.25
 * 					],[
 * 						371.525,
 * 						38.25
 * 					],[
 * 						371.525,
 * 						72.75
 * 					],[
 * 						226.025,
 * 						72.75
 * 					]
 * 				]
 * 				confidence:0.8
 *            }
 * @author jiangyiheng
 * @version 2023/07/21 10:24
 **/
@Data
public class Condition {
    /**
     * 页数
     */
    Integer page;
    /**
     * 页面高度
     */
    Float height;
    /**
     * 页面宽度
     */
    Float width;
    /**
     * 内容
     */
    List<Points> content;
}

