package com.wxw.poiUtil;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.data.PictureRenderData;
import com.deepoove.poi.data.PictureType;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fengqian
 * @since <pre>2019/08/30</pre>
 */
public class Demo {
    public static void main(String[] args) throws Exception{

        List<String> list = new ArrayList<>();
        list.add("https://fileimg.makalu.cc/a077e3b9-eb6e-4f28-b4ba-2432fe6716ab");
        list.add("https://fileimg.makalu.cc/8152bec7-84c5-45ae-aaa3-18638ac382d3");
        String path = "C:\\Users\\wxw\\Desktop\\aaa.docx";
        InputStream templateFile = new FileInputStream(path);
        Map map = new HashMap(16);
        for(int i = 0;i<list.size();i++){
            URL urlConet = new URL(list.get(i));
            HttpURLConnection con = (HttpURLConnection)urlConet.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            con.setRequestMethod("GET");
            con.setConnectTimeout(4 * 1000);
            InputStream is = con.getInputStream();
            map.put("pic" + i, new PictureRenderData(80, 80, PictureType.suggestFileType(".png"), is));
        }
        // 将数据整合到模板中去
        ConfigureBuilder builder = Configure.builder();
        builder.addPlugin('@',new MyPictureRenderPolicy());
        XWPFTemplate template = XWPFTemplate.compile(templateFile,builder.build()).render(map);
        String docPath = "C:\\Users\\wxw\\Desktop\\out.docx";
        FileOutputStream outputStream1 = new FileOutputStream(docPath);
        template.write(outputStream1);
        outputStream1.flush();
        outputStream1.close();
    }
}
