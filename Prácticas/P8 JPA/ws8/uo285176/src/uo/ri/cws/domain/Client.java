package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tclients")
public class Client extends BaseEntity {
    /*
     * Atributos naturales
     */
    @Column(unique = true)
    private String dni; // identidad natural
    @Basic(optional = false)
    private String name;
    @Basic(optional = false)
    private String surname;
    @Basic(optional = false)
    private String email;
    @Basic(optional = false)
    private String phone;

    Address address;// tabla embeddable

    /*
     * Atributos accidentales
     */
    @OneToMany(mappedBy = "client")
    private Set<Vehicle> vehicles = new HashSet<>();
    @OneToMany(mappedBy = "client")
    private Set<PaymentMean> paymentMeans = new HashSet<>();

    Client() {
    }

    public Client(String dni, String nombre, String apellidos) {
	ArgumentChecks.isNotNull(dni, "The dni can't be null");
	ArgumentChecks.isNotNull(nombre, "The name can't be null");
	ArgumentChecks.isNotNull(apellidos, "The surname can't be null");
	ArgumentChecks.isNotEmpty(dni, "The dni can't be empty");
	ArgumentChecks.isNotEmpty(nombre, "The name can't be empty");
	ArgumentChecks.isNotNull(apellidos, "The surname can't be empty");
	this.dni = dni;
	this.name = nombre;
	this.surname = apellidos;
    }

    public Client(String dni) {
	ArgumentChecks.isNotNull(dni, "The dni can't be null");
	ArgumentChecks.isNotEmpty(dni, "The dni can't be empty");
	this.dni = dni;
    }

    public String getDni() {
	return dni;
    }

    public String getName() {
	return name;
    }

    public String getSurname() {
	return surname;
    }

    public String getEmail() {
	return email;
    }

    public String getPhone() {
	return phone;
    }

    public Address getAddress() {
	return address;
    }

    @Override
    public String toString() {
	return "Client [dni=" + dni + ", name=" + name + ", surname=" + surname
		+ ", email=" + email + ", phone=" + phone + ", address="
		+ address + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(dni);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Client other = (Client) obj;
	return Objects.equals(dni, other.dni);
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<>(vehicles);// hacemos una copia defensiva
    }

    Set<Vehicle> _getVehicles() {
	return vehicles;
    }

    public Set<PaymentMean> getPaymentMeans() {
	return new HashSet<>(paymentMeans);
    }

    Set<PaymentMean> _getPaymentMeans() {
	return paymentMeans;
    }

    public void setAddress(Address address) {
	ArgumentChecks.isNotNull(address);
	this.address = address;
    }
}
