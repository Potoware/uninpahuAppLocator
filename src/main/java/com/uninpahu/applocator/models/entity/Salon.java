package com.uninpahu.applocator.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="salones")
public class Salon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_salon")
	private Long idSalon;
	
	@NotEmpty(message = "no puede estar vacio")
	@Size(min = 1, max = 30)
	private String numero;
	
	private String descripcion;
	
	//1 activo, 2 Inactivo
	private boolean estado;
	
   @ManyToOne()
   @JoinColumn(name = "idSede")
   @NotNull(message = "Debe seleccionar una sede")
   private Sede sede;
   
   private String coordenadas;
   
   private String urlStreetView;
   
   @Transient
   private String criterio;
   
   @Transient
   private boolean esRuta;
	
	public Long getIdSalon() {
		return idSalon;
	}
	
	public void setIdSalon(Long idSalon) {
		this.idSalon = idSalon;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public Sede getSede() {
		return sede;
	}
	
	public void setSede(Sede sede) {
		this.sede = sede;
	}
	
	public String getUrlStreetView() {
		return urlStreetView;
	}
	
	public void setUrlStreetView(String urlStreetView) {
		this.urlStreetView = urlStreetView;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getCoordenadas() {
		return coordenadas;
	}

	public void setCoordenadas(String coordenadas) {
		this.coordenadas = coordenadas;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getCriterio() {
		return criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public boolean isEsRuta() {
		return esRuta;
	}

	public void setEsRuta(boolean esRuta) {
		this.esRuta = esRuta;
	}
	
}
