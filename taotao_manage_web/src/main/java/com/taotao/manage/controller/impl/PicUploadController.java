package com.taotao.manage.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taotao.manage.vo.PicUploadResult;
import org.apache.commons.lang3.StringUtils;
import org.csource.common.MyException;
import org.csource.fastdfs.*;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequestMapping("/pic")
@Controller
public class PicUploadController {

    private static final String[] IMAGE_TYPE = {".jpg", ".png", ".bmp", ".jpeg", ".gif"};
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 图片上传
     *  url:'/pic/upload',method:'port'
     * @param multipartFile 页面传来的图片信息
     * @return 返回封装好的json格式picUploadResult对象
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/upload",produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public String upload(
            @RequestParam("uploadFile") MultipartFile multipartFile) throws JsonProcessingException {
        PicUploadResult picUploadResult = new PicUploadResult();
        //返回结果
        picUploadResult.setError(1);

        //判断文件是否合法
        boolean isLegal = false;
        //循环判断
        for (String type : IMAGE_TYPE) {
            if (multipartFile.getOriginalFilename().lastIndexOf(type) > 0) {
                isLegal = true;
                break;
            }
        }

        if (isLegal) {
            //检验内容时候为图片
            try {
                //读取图片
                BufferedImage read = ImageIO.read(multipartFile.getInputStream());

                //上传图片
                String trackerConfig = this.getClass().getClassLoader().getResource("tracker.conf").getPath();
                //设置Tracker server 的地址
                ClientGlobal.init(trackerConfig);
                //创建追踪客户端 trackerClient
                TrackerClient trackerClient = new TrackerClient();
                //获取追踪服务器 trackerServer
                TrackerServer trackerServer = trackerClient.getConnection();
                //创建储存服务器
                StorageServer storageServer = null;
                //创建储存客户端 storageClient
                StorageClient storageClient = new StorageClient(trackerServer, null);

                //上传文件
                //获取文件后缀
                String excFileName = StringUtils.substringAfter(multipartFile.getOriginalFilename(), ".");
                //上传
                String[] fileInfos = storageClient.upload_file(multipartFile.getBytes(), excFileName, null);
                //拼接完整路径
                String url = "";
                if (fileInfos != null && fileInfos.length > 1) {
                    String groupName = fileInfos[0]; //组名
                    String fileName = fileInfos[1]; //文件名
                    //获取存储服务器信息
                    ServerInfo[] serverInfos = trackerClient.getFetchStorages(trackerServer, groupName, fileName);
                    url = "http://" + serverInfos[0].getIpAddr() + "/" + groupName + "/" + fileName;
                    //设置返回结果
                    picUploadResult.setError(0);
                    picUploadResult.setUrl(url);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return MAPPER.writeValueAsString(picUploadResult);
    }
}
