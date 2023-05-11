package cat.prateducacio.bdoo.domain;

public class Estudiante extends Persona {
	
	private int curso;

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return "Estudiante [curso=" + curso + ", getId()=" + getId() + ", getNombre()=" + getNombre()
				+ ", getApellido()=" + getApellido() + "]";
	}
	

}
