package br.com.mylocation.comunication;

public class Son extends People {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7528141436288734798L;
	private String parent;
	
	public Son(String name, int age) {
		super(name, age);
		parent = "Mamae";
	}
	
	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String toString(){
		return "Son "+parent+" - "+super.toString();
	}
}
