package cn.itcast.core.controller;

import cn.itcast.common.utils.FastDFSClient;
import entity.Result;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

    //获取配置文件中的属性
    @Value("${FILE_SERVER_URL}")
    private String url;

    //商品图片的上传
    //入参:  Springmvc 接收 form 表单中的图片
    //返回值:
    @RequestMapping("/uploadFile")
    public Result uploadFile(MultipartFile file){

        try {
            /*file.getOriginalFilename()*/
            //1:上传图片到分布式文件系统
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastDFS/fdfs_client.conf");

            //扩展名
            String ext = FilenameUtils.getExtension(file.getOriginalFilename());

            //上传图片
            String path = fastDFSClient.uploadFile(file.getBytes(), ext);

            return new Result(true,url + path);//硬编码问题
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"上传图片失败");
        }

    }
}
