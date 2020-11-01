
package com.bank.model;

import com.bank.control.ReadXml;
import java.io.InputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Client extends Person {
    private int clientId;
    private java.sql.Date birth;
    private InputStream pdfDpi;
    private List<Account> accounts;

    public Client(Element e) {
        super(e);
        this.clientId = Integer.parseInt(e.getChildText("CODIGO"));
        this.birth = ReadXml.getDate(e.getChildText("BIRTH"));
        
        Element el = e.getChild("CUENTAS");
        List<Element> elChild = el.getChildren("CUENTA");
        accounts = new ArrayList<>();
        for(Element i : elChild) {
            accounts.add(new Account(i, this.clientId));
        }
    }
    
    public Client(ResultSet rs) throws SQLException {
        super(rs);
        this.clientId = rs.getInt("client_id");
        this.birth = rs.getDate("birth");
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public InputStream getPdfDpi() {
        return pdfDpi;
    }

    public void setPdfDpi(InputStream pdfDpi) {
        this.pdfDpi = pdfDpi;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return super.toString() + " Client{" + "clientId=" + clientId + ", birth=" + birth + '}';
    }
}
