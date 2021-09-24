package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@Table(name="cliente")
public class Cliente implements Serializable {
	
   


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message="no puede ser vacío")
	@Size(min = 4, max = 12, message="debe tener entre 4 y 12 caracteres")
	@Column(nullable=false)
	private String nombre;
	
	@NotEmpty(message="no puede ser vacío")
	@Column(nullable=false)
	private String apellido;
	
	@NotEmpty(message="no puede ser vacío")
	@Email(message="tiene que tener formato de e-mail")
	@Column(nullable=false, unique=true)
	private String email;
	
	@Temporal(TemporalType.DATE)
	@Column(name="create_at")
	@NotNull(message= "no puede estar vacío")
	private Date createAt;
	
	private String foto;
	
	@ManyToOne(fetch= FetchType.LAZY) //CON LAZY SE GENERA UN PROXY, Y GENERA ATRIBUTOS EN EL JSON POR LO QUE LOS OMITO MAS ABAJO, SINO TIRA ERROR
	@JoinColumn(name="region_id") //no hace falta toma region y id que es el id de region como region_id, pero lo puedo poner explicito
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true) //Omitimos atributos de generar JSON
	@NotNull(message="la región no puede ser vacía")
	private Region region;
	
	//UN CLIENTE MUCHAS FACTURAS, MAPPED BY ATRIBUTO CLIENTE EN FACTURA
	@JsonIgnoreProperties(value = {"cliente", "hibernateLazyInitializer","handler"}, allowSetters = true) //Para ignorar la inversa y no haya loop
	@OneToMany(fetch=FetchType.LAZY, mappedBy="cliente", cascade=CascadeType.ALL)
	private List<Factura> facturas;
	
//	@PrePersist
//	public void prePersist() {
//		createAt = new Date();
//	}
	
	public Cliente() {
		this.facturas = new ArrayList<>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Region getRegion() {
		return region;
	}

	public void setRegion(Region region) {
		this.region = region;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
	
	


}
