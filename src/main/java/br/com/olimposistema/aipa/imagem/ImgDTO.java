package br.com.olimposistema.aipa.imagem;

import br.com.caelum.vraptor.observer.upload.UploadedFile;

public class ImgDTO {

	private UploadedFile[] files;
	private String classModel;
	private Integer idModel;
	private Integer idImage;
	private String propertyName;
	
	
	public UploadedFile[] getFiles() {
		return files;
	}

	public void setFiles(UploadedFile[] files) {
		this.files = files;
	}
	
	public String getClassModel() {
		return classModel;
	}
	public void setClassModel(String classModel) {
		this.classModel = classModel;
	}
	public Integer getIdModel() {
		return idModel;
	}
	public void setIdModel(Integer idModel) {
		this.idModel = idModel;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public Integer getIdImage() {
		return idImage;
	}
	public void setIdImage(Integer idImage) {
		this.idImage = idImage;
	}
	
}
