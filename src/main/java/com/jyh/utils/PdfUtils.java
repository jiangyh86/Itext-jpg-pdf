package com.jyh.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.jyh.condition.Condition;
import com.jyh.condition.Points;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PdfUtils 进行jpg转pdf
 *
 * @author jiangyiheng
 * @version 2023/07/21 11:47
 **/
public class PdfUtils {
    public static void main(String[] args) throws DocumentException, IOException {
        ArrayList<Condition> list = new ArrayList<>();
        ArrayList<Float[]> floats = new ArrayList<>();
        floats.add(new Float[]{244.675f, 96.11145833333333f});
        floats.add(new Float[]{547.2739583333333f, 96.11145833333333f});
        floats.add(new Float[]{547.2739583333333f, 136.67708333333334f});
        floats.add(new Float[]{244.675f, 136.67708333333334f});
        ArrayList<Float[]> float2 = new ArrayList<>();
        float2.add(new Float[]{142.903125f, 177.996875f});
        float2.add(new Float[]{681.8f, 177.996875f});
        float2.add(new Float[]{681.8f, 213.88333333333333f});
        float2.add(new Float[]{142.903125f, 213.88333333333333f});
        Condition condition = Condition.builder().width(793F).height(1123F).page(1).text("量子伟业员工请假制度").point(floats).build();
        Condition condition2 = Condition.builder().width(793F).height(1123F).page(2).text("为进一步完善管理制度，明确公司休假规定，保障员工合法权益，根据国家").point(float2).build();
        list.add(condition);
        list.add(condition2);
        condition2.setPage(1);
        list.add(condition2);
        ByteArrayOutputStream stream = jpgToPDF(new String[]{"http://43.143.20.115:9000/public/imags/2023/07/20/testpdf(1)_00.png","http://43.143.20.115:9000/public/imags/2023/07/20/testpdf(1)_00.png"}, list);
        try{
            FileOutputStream outputStream = new FileOutputStream("D:\\PDF\\123\\123.pdf");
            stream.writeTo(outputStream);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private static ByteArrayOutputStream jpgToPDF(String[] imagePaths, List<Condition> list) throws DocumentException, IOException {
        if (imagePaths.length == 0) {
            throw new RuntimeException("无图片转换或者json串不正确");
        }
        Map<Integer, List<Condition>> integerListMap = toMap(list);
        // 第一步：创建一个document对象。
        Document document = new Document();
        document.setMargins(0, 0, 0, 0);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        // 第二步：
        // 创建一个PdfWriter实例，
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        // 第三步：打开文档。
        document.open();
        //设置中文字体
        BaseFont baseFont = BaseFont.createFont("方正粗黑宋简体.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        //获取canvas对象
        PdfContentByte canvas = writer.getDirectContentUnder();
        writer.setCompressionLevel(0);
        //二号字体
        Font _30Font = new Font(baseFont, 30, Font.FontStyle.NORMAL.ordinal(), BaseColor.BLACK);
        Font _27Font = new Font(baseFont, 22, Font.FontStyle.NORMAL.ordinal(), BaseColor.BLACK);
        // 第四步：在文档中增加图片。
        Set<Integer> set = integerListMap.keySet();
        for (Integer integer : set) {
            Image img = Image.getInstance(imagePaths[integer-1]);
            img.setAlignment(Image.ALIGN_CENTER);
            //根据图片大小设置页面，一定要先设置页面，再newPage（），否则无效
            document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
            document.newPage();
            document.add(img);
            List<Condition> conditions = integerListMap.get(integer);
            for (int i = 0; i < conditions.size(); i++) {
                Condition condition = conditions.get(i);
                //添加文字定位信息
                Float[] floats = condition.getPoint().get(3);
                Font font = new Font(baseFont, fontSize(condition.getPoint(), condition.getText()), Font.FontStyle.NORMAL.ordinal(), BaseColor.BLACK);
                ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
                        new Paragraph(condition.getText(), font), floats[0],  img.getHeight() - floats[1], 0);
            }
        }
//        document.setPageSize(new Rectangle(793f, 1123f));
//        document.newPage();
//        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT,
//                new Paragraph("为进一步完善管理制度，明确公司休假规定，保障员工合法权益，根据国家", _27Font), 142.903125f,  1123f - 213.88333333333333f, 0);
        // 第五步：关闭文档。
        document.close();
        return outputStream;
    }

    public static Map<Integer, List<Condition>> toMap(List<Condition> list){
//        HashMap<Integer, List<Condition>> map = new HashMap<>(imagePaths.length);
        HashMap<Integer, List<Condition>> map = (HashMap<Integer, List<Condition>>) list.stream()
                .collect(Collectors.groupingBy(Condition::getPage, Collectors.toList()));
        for (Map.Entry<Integer, List<Condition>> integerListEntry : map.entrySet()) {
            System.out.println(integerListEntry);
        }
        return map;
    }

    public static Integer fontSize(List<Float[]> list,String text){
        //计算字体大小
        Float x;
        Float y;
        Float[] floats = list.get(0);
        Float[] floats1 = list.get(2);
        x=Math.abs(floats[0]-floats1[0]);
        y=Math.abs(floats[1]-floats1[1]);
        x= x/text.length();
        return x.intValue();
    }

}

