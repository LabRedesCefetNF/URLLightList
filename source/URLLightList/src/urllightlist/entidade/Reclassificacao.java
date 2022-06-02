package urllightlist.entidade;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reclassificacao")
public class Reclassificacao {

	@Id
	@GeneratedValue
	@Column(name = "reclassificacao_id")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "administrador_id", nullable = false)
	private Administrador administrador;
	
	@OneToOne(fetch = FetchType.EAGER, cascade={CascadeType.REMOVE})
	@JoinColumn(name = "categoria_id", nullable = false)
	private Categoria categoria;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "reclassificacacao_categoria", joinColumns = { @JoinColumn(name = "reclassificacao_id", nullable = false) }, inverseJoinColumns = { @JoinColumn(name = "categoria_id", nullable = false) })
	private List<Categoria> listaCategoria;

	public List<Categoria> getListaCategoria() {
		return listaCategoria;
	}

	public void setListaCategoria(List<Categoria> listaCategoria) {
		this.listaCategoria = listaCategoria;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}


}
