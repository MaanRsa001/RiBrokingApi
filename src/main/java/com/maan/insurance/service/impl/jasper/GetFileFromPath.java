package com.maan.insurance.service.impl.jasper;

import java.io.File;
import java.util.concurrent.Callable;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.web.multipart.MultipartFile;

public class GetFileFromPath implements Callable<Object> {
	private String path;
	private String refno=null;
	
	public GetFileFromPath(String path) {
		super();
		this.path = path;
	}
	public GetFileFromPath(String path,String refNo) {
		super();
		this.path = path;
		this.refno = refNo;
	}
	

	@Override
	public Document call() throws Exception {
		
		File file=new File(path);
		 if(StringUtils.isNotBlank(path) && new File(path).exists())  {
			 byte[] array = FileUtils.readFileToByteArray(new File(path));
			 MultipartFile baseM = new BASE64DecodedMultipartFile(array,file.getName());
			 String contenttype=baseM.getContentType();
			 String prefix = "data:"+contenttype+";base64,";
			 
			 Document doc= new Document();
			 String imgurlen=Base64Utils.encodeToString(array);
			 doc.setImgUrl(prefix+imgurlen);
			 doc.setDocumentId(this.refno);
			 return doc;
		 }else {
			 System.out.println("File Is Not Found");
		 }
		return null;
	}

}
