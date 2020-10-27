
package com.bank.model;

import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Person {
    private String name;
    private String dpi;
    private String address;
    private boolean gender;
    private String password;
    
    public Person(Element e) {
        this.name = e.getChildText("NOMBRE");
        this.dpi = e.getChildText("DPI");
        this.address = e.getChildText("DIRECCION");
        this.gender = e.getChildText("SEXO").equals("Masculino");
        this.password = e.getChildText("PASSWORD");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", dpi=" + dpi + ", address=" + address + ", gender=" + gender + ", password=" + password + '}';
    }
}
