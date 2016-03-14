package com.masiis.shop.web.platform.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * 上传图片
 * @author ZhaoLiang
 * @date 2016/3/11 11:55
 */
public class UploadImage {

	/**
	 * 上传图片
	 * @author ZhaoLiang
	 * @date 2016/3/11 11:55
	 */
	public static String upload(MultipartFile upload, String imagesSavePath
			) throws IOException {
		String newFileName = "";
		File f = new File(imagesSavePath);
		if (!f.exists() && !f.isDirectory()) {
			f.mkdirs();
		}
		if (upload != null && !upload.isEmpty()) {
			String fileName = upload.getOriginalFilename();
			String extensionName = fileName
					.substring(fileName.lastIndexOf(".") + 1);
			/*newFileName = String.valueOf(System.currentTimeMillis()) + "."
			+ extensionName;*/
			newFileName = UUID.randomUUID().toString().replace("-", "") + "."
			+ extensionName;
			InputStream is = upload.getInputStream();
			FileOutputStream fos = new FileOutputStream(imagesSavePath + "/"
					+ newFileName);
			byte[] buffer = new byte[8192];
			int count = 0;
			while ((count = is.read(buffer)) > 0) {
				fos.write(buffer, 0, count);
			}
			fos.close();
			is.close();
		} else {
			newFileName = null;
		}
		return newFileName;
	}

	/**
	 * 
	 * 上传多个图片
	 * 
	 * @param upload
	 * @param imagesSavePath
	 * @return
	 */
	public static String[] MultiImages(MultipartFile upload[],
			String imagesSavePath) {
		String fileName[] = new String[10];
		String extensionName[] = new String[10];
		String newFileName[] = new String[10];

		try {
			for (int i = 0; i < upload.length; i++) {
				if (upload[i].isEmpty()) {
					continue;
				}
				fileName[i] = upload[i].getOriginalFilename();// 获取文件名
				extensionName[i] = fileName[i].substring(fileName[i]
						.lastIndexOf(".") + 1);// 获取图片扩展名
				newFileName[i] = String.valueOf(System.currentTimeMillis())
						+ "." + extensionName[i];

				InputStream is = upload[i].getInputStream();
				FileOutputStream fos = new FileOutputStream(imagesSavePath
						+ "\\" + newFileName[i]);
				byte[] buffer = new byte[8192]; // 每次读8K字节
				int count = 0;
				// 开始读取上传文件的字节，并将其输出到服务端的上传文件输出流中
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count); // 向服务端文件写入字节流
				}
				fos.close(); // 关闭FileOutputStream对象
				is.close(); // InputStream对象
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newFileName;
	}

	public static String[] MultiImages(MultipartFile upload[],
			HttpSession session) {
		String imagesSavePath = session.getServletContext().getRealPath("/")
				+ ("/static/images//");
		return MultiImages(upload, imagesSavePath);
	}

}
