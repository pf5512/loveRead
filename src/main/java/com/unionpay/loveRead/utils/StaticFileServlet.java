package com.unionpay.loveRead.utils;

import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * Simple servlet to solve virtual folder mapping problem in JBoss AS 7
 * 
 * @author Yaguo Zhou
 * 
 */
public class StaticFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static String basePath;

	static {
	    ClassPathResource resource = new ClassPathResource(
                "configuration.properties");
        try {
            String ppsPath = resource.getURL().getPath().replace("20%", "");
            String shareDir = getValueByKey(ppsPath, "shareDir");
            basePath = shareDir;
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String requestedFile = request.getPathInfo();

		File file = new File(basePath,
				URLDecoder.decode(requestedFile, "UTF-8"));
		if (!file.exists()) {
			// Throw 404, redirect to error page may is another selection
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// set response expire a querter
		response.setDateHeader("expires",
				System.currentTimeMillis() + 1000 * 900);

		// write via response's OutputStream
		FileInputStream inputStream = null;

		try {
			inputStream = new FileInputStream(file);
			byte[] buffer = new byte[1024];
			int bytesRead = 0;

			do {
				bytesRead = inputStream.read(buffer, 0, buffer.length);
				response.getOutputStream().write(buffer, 0, bytesRead);
			} while (bytesRead == buffer.length);

			response.getOutputStream().flush();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * 根据key读取properties文件内容
	 * 
	 * @param filePath
	 * @param key
	 * @return
	 */
	public static String getValueByKey(String filePath, String key) {
		Properties pps = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			pps.load(in);
			String value = pps.getProperty(key);
			System.out.println(key + " = " + value);
			return value;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}