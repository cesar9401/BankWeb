
package com.bank.model;

import com.bank.control.ReadXml;
import java.io.InputStream;
import java.sql.Date;
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

    @Override
    public String toString() {
        return super.toString() + " Client{" + "clientId=" + clientId + ", birth=" + birth + '}';
    }
}
