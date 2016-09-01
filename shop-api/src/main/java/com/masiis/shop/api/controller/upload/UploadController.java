package com.masiis.shop.api.controller.upload;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import com.masiis.shop.api.bean.upload.UploadReq;
import com.masiis.shop.api.bean.upload.UploadRes;
import com.masiis.shop.api.constants.SignValid;
import com.masiis.shop.api.constants.SysResCodeCons;
import com.masiis.shop.common.util.OSSObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;


@Controller
@RequestMapping("/upload")
public class UploadController {

	private final static Log log = LogFactory.getLog(UploadController.class);

	@RequestMapping("/add2")
	public String add2(){
		return "product/add2";
	}

	
	@RequestMapping("/save"	)
	@ResponseBody
	@SignValid(paramType = UploadReq.class)
	public UploadRes upload(UploadReq uploadReq, MultipartFile file, HttpServletRequest request){
		UploadRes uploadRes = new UploadRes();

		try {
			if(file != null){
				String type   = request.getParameter("osspath");
				String suffix = StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
				if(StringUtils.isBlank(suffix)) {
					suffix = "png";
				}

				int              random           = (int)((Math.random()*9+1)*100000);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date             createTime       = new Date();
				String           saveName         = simpleDateFormat.format(createTime)+random+"."+suffix;
				String           osspath          = "static/user/organization/";
				                 osspath          = "1".equals(type) ? "static/user/organization/back_img/" : osspath;
				                 osspath          = "2".equals(type) ? "static/user/organization/logo/" : osspath;
				                 osspath          = "3".equals(type) ? "static/user/organization/wx_qr_code/" : osspath;

				OSSObjectUtils.uploadFile(saveName, file.getSize(), file.getInputStream(), osspath);

				uploadRes.getDataMap().put("originalName", StringUtils.substringAfterLast(file.getOriginalFilename(), File.separator));
				uploadRes.getDataMap().put("saveName",     saveName);
				uploadRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
				uploadRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
			}
		} catch (Exception e) {
			uploadRes.setResCode(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR);
			uploadRes.setResMsg(SysResCodeCons.RES_CODE_REQ_OPERATE_ERROR_MSG);

			log.error("上传文件失败![files="+file+"]"+e);
			e.printStackTrace();
		}

//		String uploadDirPath = request.getServletContext().getRealPath("/")+"static/images/upload";
//		File uploadDir = new File(uploadDirPath);
//		if(!uploadDir.exists()) uploadDir.mkdirs();
//
//		if(uploadReq.getFiles() != null){
//			MultipartFile[] files = uploadReq.getFiles();
//			String[] originalNames = new String[files.length];
//			String[] saveNames = new String[files.length];
//			for(int i = 0;i<files.length;i++){
//
//				if(!files[i].isEmpty()){
//					try {
//						int              random           = (int)((Math.random()*9+1)*100000);
//						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//						Date             createTime       = new Date();
//						String           saveName         = simpleDateFormat.format(createTime)+random+".png";
//
//						originalNames[i] = files[i].getName();
//						saveNames[i]     = saveName;
//
//						//拿到输出流，同时重命名上传的文件
//						FileOutputStream os = new FileOutputStream(uploadDirPath+File.separator+saveName);
//						//拿到上传文件的输入流
//						FileInputStream in = (FileInputStream) files[i].getInputStream();
//
//						//以写字节的方式写文件
//						int b = 0;
//						while((b=in.read()) != -1){
//							os.write(b);
//						}
//						os.flush();
//						os.close();
//						in.close();
//
//						//上传原图到oss
//						String osspath = request.getParameter("osspath");//oss上传路径
//						osspath = osspath==null ? "static/product/detail_img/" : osspath;
//						File imgFile = new File(saveName);
//						OSSObjectUtils.uploadFile(imgFile, osspath);
//						imgFile.delete();
//
//						uploadRes.getDataMap().put("originalNames", originalNames);
//						uploadRes.getDataMap().put("saveNames",     saveNames);
//						uploadRes.setResCode(SysResCodeCons.RES_CODE_SUCCESS);
//						uploadRes.setResMsg(SysResCodeCons.RES_CODE_SUCCESS_MSG);
//					} catch (Exception e) {
//						log.error("上传文件失败![files="+files+"]"+e);
//						e.printStackTrace();
//					}
//				}
//			}
//		}


		return uploadRes;
	}
	
	
	@RequestMapping("/multipartUpload"	)
	public String multipartUpload(HttpServletRequest request,HttpServletResponse response) throws IllegalStateException, IOException {
		String uploadDirPath = request.getServletContext().getRealPath("/")+"static/images/upload";
		File uploadDir = new File(uploadDirPath);
		if(!uploadDir.exists()) uploadDir.mkdirs();

		//创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//判断 request 是否有文件上传,即多部分请求
		if(multipartResolver.isMultipart(request)){
			//转换成多部分request  
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			//取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while(iter.hasNext()){
				//记录上传过程起始时的时间，用来计算上传时间
				int pre = (int) System.currentTimeMillis();
				//取得上传文件
				MultipartFile file = multiRequest.getFile(iter.next());
				if(file != null){
					//取得当前上传文件的文件名称
					String myFileName = file.getOriginalFilename();
					//如果名称不为“”,说明该文件存在，否则说明该文件不存在
					if(myFileName.trim() !=""){
						System.out.println(myFileName);
						//重命名上传后的文件名
						int              random           = (int)((Math.random()*9+1)*100000);
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
						Date             createTime       = new Date();
						String           saveName         = simpleDateFormat.format(createTime)+random+".png";

						String fileName = saveName;
						//定义上传路径
						String path = uploadDirPath+File.separator+ fileName;
						File localFile = new File(path);
						file.transferTo(localFile);
					}
				}
				//记录上传该文件后的时间
				int finaltime = (int) System.currentTimeMillis();
				System.out.println(finaltime - pre);
			}
			
		}
		return "/success";
	}
	
	@RequestMapping("/toUpload"	) 
	public String toUpload() {
		
		return "/upload";
	}
	
}
