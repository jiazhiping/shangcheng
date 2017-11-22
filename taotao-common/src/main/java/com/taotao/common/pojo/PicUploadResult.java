package com.taotao.common.pojo;

public class PicUploadResult {

	private Integer error;// 0上传成功，1代表上传失败
	private String width;// 图片的宽
	private String height;// 图片的高
	private String url;// 图片的上传地址

	public Integer getError() {
		return error;
	}

	public void setError(Integer error) {
		this.error = error;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
