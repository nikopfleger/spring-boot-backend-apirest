package com.bolsadeideas.springboot.backend.apirest.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Factura implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String descripcion;
	
	private String observacion;
	
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	private Date createAt;
	
	//MANY FACTURAS ONE CLIENTE
	//JOINCOLUMN PARA PONER EL NOMBRE DE LA LLAVE FORANEA @JoinColumn(name="cliente_id"), se recomienda poner solo si voy a usar otro nombre
	@ManyToOne(fetch=FetchType.LAZY)	
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "factura"}, allowSetters = true) //Omitimos atributos de generar JSON, tambien omitimos factura para que no haya loop
	private Cliente cliente;
	
	@OneToMany(fetch=FetchType.LAZY, cascade= CascadeType.ALL)
	@JoinColumn(name="factura_id") //CUANDO NO ES EL DUEÑO DE LA RELACION TENGO QUE PONER EL JOINCOLUMN, EN ESTE CASO LA FOREIGN LA CUAL HAGO REFERENCIA
	@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"}, allowSetters = true) //Omitimos atributos de generar JSON
	private List<ItemFactura> items;
	
	
	
	public Factura() {
		items = new ArrayList<>();
	}

	@PrePersist
	public void prePersist() {
		this.createAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<ItemFactura> getItems() {
		return items;
	}

	public void setItems(List<ItemFactura> items) {
		this.items = items;
	}
	
	public Double getTotal() {
		return items.stream().mapToDouble(a -> a.getImporte()).sum();
	}
	
	
	
}
