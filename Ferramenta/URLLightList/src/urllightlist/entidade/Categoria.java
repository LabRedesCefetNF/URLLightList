package urllightlist.entidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categoria")
public class Categoria {
			
		public Categoria(){}
		
		@Id
		@GeneratedValue
		@Column(name="categoria_id")
		private Long id;
		
		@Column(nullable=false, unique=true)
		private String nome;
		
		@Column(nullable=false)
		private int grauPerversidade;
		
		@Column(nullable=true)
		private String checado;
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getChecado() {
			return checado;
		}

		public String getNome() {
			return nome;
		}

		public void setNome(String nome) {
			this.nome = nome;
		}

		public void setChecado(String checado) {
			this.checado = checado;
		}

		
		public void setGrauPerversidade(int grauPerversidade) {
			this.grauPerversidade = grauPerversidade;
		}

		public int getGrauPerversidade() {
			return grauPerversidade;
		}
		
}
