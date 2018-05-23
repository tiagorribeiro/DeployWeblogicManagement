package br.com.trrsolution.model;

//@Setter
//@Getter
public class War {
	
	private String serviceName;
	private String lastModified;
	private Boolean checked;
	
	public War(){}	

	public War(String serviceName, Boolean checked, String lastModified) {
		super();
		this.lastModified = lastModified;
		this.serviceName = serviceName;
		this.checked = checked;
	}
		
	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	
}