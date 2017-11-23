package cn.itcast.qiniu;

import com.qiniu.util.Auth;
import java.io.IOException;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;

public class qiniuDemo {
	/** 基本配置-从七牛管理后台拿到 */
	// 设置好账号的ACCESS_KEY和SECRET_KEY
	String ACCESS_KEY = "JmoqGzFtsFDustbkwwOtSreYdWM7l33XWxFEJdpO";
	String SECRET_KEY = "rD_bBy1uFKk8Zl4TUa_zqyVUQMv9kxWrxVhsfHWk";
	// 要上传的空间--
	String bucketname = "jiazhiping";

	// 上传到七牛后保存的文件名---不指定的话，七牛随机产生-无后缀
	String key = "hi.jpg";
	// 上传文件的路径
	String FilePath = "E:\\11.jpg";

	// 密钥配置
	Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);

	String upToken = auth.uploadToken(bucketname);
	// 创建上传对象
	Configuration cfg = new Configuration(Zone.zone0());

	UploadManager uploadManager = new UploadManager(cfg);

	public void upload() throws IOException {
		try {
			// 调用put方法上传
			Response res = uploadManager.put(FilePath, key, upToken);
			// 打印返回的信息
			System.out.println(res.bodyString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) throws IOException {
		new qiniuDemo().upload();
	}

}
