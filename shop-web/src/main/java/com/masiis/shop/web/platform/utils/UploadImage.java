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
	 *
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

	/**
	 * 删除文件，可以是文件或文件夹
	 *
	 * @param fileName 要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			System.out.println("删除文件失败:" + fileName + "不存在！");
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(fileName);
			}
		}
		return false;
	}




	/**
	 * 删除单个文件
	 *
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				System.out.println("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				System.out.println("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			System.out.println("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * 删除目录及目录下的文件
	 *
	 * @param dir
	 *            要删除的目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println("删除目录失败：" + dir + "不存在！");
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = UploadImage.deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = UploadImage.deleteDirectory(files[i]
						.getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			System.out.println("删除目录失败！");
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println("删除目录" + dir + "成功！");
			return true;
		} else {
			return false;
		}
	}
}
