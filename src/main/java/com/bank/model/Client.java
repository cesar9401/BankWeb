package com.bank.model;

import com.bank.control.ReadXml;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.jdom2.Element;

/**
 *
 * @author cesar31
 */
public class Client extends Person {

    private int clientId;
    private java.sql.Date birth;
    private String strPdfDpi;
    private InputStream pdfDpi;
    private List<Account> accounts;
    private String date;

    //Cuando un cliente se registre, fecha de creacion de cuenta
    private java.sql.Date createdOn;

    public Client(Element e) {
        super(e);
        this.clientId = Integer.parseInt(e.getChildText("CODIGO"));
        this.birth = ReadXml.getDate(e.getChildText("BIRTH"));
        this.strPdfDpi = e.getChildText("DPI-PDF");
        
        Element el = e.getChild("CUENTAS");
        List<Element> elChild = el.getChildren("CUENTA");
        accounts = new ArrayList<>();
        for (Element i : elChild) {
            accounts.add(new Account(i, this.clientId));
        }
    }

    public Client(ResultSet rs) throws SQLException {
        super(rs);
        this.clientId = rs.getInt("client_id");
        this.birth = rs.getDate("birth");
        this.date = birth.toString();
    }

    public Client(HttpServletRequest request, boolean isNew) throws UnsupportedEncodingException {
        super(request);
        this.clientId = Integer.parseInt(request.getParameter("code"));
        this.birth = ReadXml.getDate(request.getParameter("birth"));

        if (isNew) {
            this.createdOn = ReadXml.getDate(request.getParameter("created-on"));
            Part filePart;
            try {
                filePart = request.getPart("pdf");
                this.pdfDpi = filePart.getInputStream();
                System.out.println("filePart: " + filePart.getSubmittedFileName());
            } catch (IOException | ServletException ex) {
                ex.printStackTrace(System.out);
            }
        }
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

    public String getStrPdfDpi() {
        return strPdfDpi;
    }

    public void setStrPdfDpi(String strPdfDpi) {
        this.strPdfDpi = strPdfDpi;
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

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    @Override
    public String toString() {
        return super.toString() + " Client{" + "clientId=" + clientId + ", birth=" + birth + '}';
    }
}
