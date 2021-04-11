package utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientDemo {
    public static void main(String[] args) {
        String s = HttpClientDemo.doGet("http://localhost:8801");
        System.out.println(s);
    }

    public static String doGet(String url) {
        CloseableHttpClient aDefault = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = aDefault.execute(httpGet);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    return new String(EntityUtils.toByteArray(entity),"UTF-8");
                    //EntityUtils.toString(entity)之后会关闭流
//                    System.out.println("Response content: " + EntityUtils.toString(entity));
//                    return EntityUtils.toString(entity);

                }
            } finally {
                response.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                aDefault.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
