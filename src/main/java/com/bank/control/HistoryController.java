package com.bank.control;

import com.bank.dao.HistoryDao;
import com.bank.model.Cashier;
import com.bank.model.ChangeHistory;
import com.bank.model.Client;
import com.bank.model.Manager;
import java.sql.Connection;

/**
 *
 * @author cesar31
 */
public class HistoryController {

    private final Connection conexion;
    private final HistoryDao historyDao;

    public HistoryController(Connection conexion) {
        this.conexion = conexion;
        historyDao = new HistoryDao(this.conexion);
    }

    public void historyManager(int managerId, Manager newM, Manager oldM) {
        String description = "Cambios:";
        description += getChanges("Nombre", newM.getName(), oldM.getName())
                + getChanges("Jornada", newM.getWorkDayName(), oldM.getWorkDayName())
                + getChanges("DPI", newM.getDpi(), oldM.getDpi())
                + getChanges("Direccion", newM.getAddress(), oldM.getAddress())
                + getChanges("Genero", newM.getSex(), oldM.getSex());

        if (!newM.getPassword().equals(oldM.getPassword())) {
            description += " - Contraseña";
        }
        description += " - ";
        ChangeHistory historial = new ChangeHistory(0, managerId, null, null, newM.getManagerId(), description);
        historyDao.insertChangeHistory(historial);
    }

    public void historyCashier(int managerId, Cashier newC, Cashier oldC) {
        String description = "Cambios:";
        description += getChanges("Nombre", newC.getName(), oldC.getName())
                + getChanges("Jornada", newC.getWorkDayName(), oldC.getWorkDayName())
                + getChanges("DPI", newC.getDpi(), oldC.getDpi())
                + getChanges("Direccion", newC.getAddress(), oldC.getAddress())
                + getChanges("Genero", newC.getSex(), oldC.getSex());

        if (!newC.getPassword().equals(oldC.getPassword())) {
            description += " - Contraseña";
        }
        description += " - ";
        ChangeHistory historial = new ChangeHistory(0, managerId, null, newC.getCashierId(), null, description);
        historyDao.insertChangeHistory(historial);
    }

    public void historyClient(int managerId, Client newC, Client oldC) {
        String description = "Cambios:";
        description += getChanges("Nombre", newC.getName(), oldC.getName())
                + getChanges("DPI", newC.getDpi(), oldC.getDpi())
                + getChanges("Fecha de Nacimiento", newC.getBirth().toString(), oldC.getBirth().toString())
                + getChanges("Direccion", newC.getAddress(), oldC.getAddress())
                + getChanges("Genero", newC.getSex(), oldC.getSex());

        if (!newC.getPassword().equals(oldC.getPassword())) {
            description += " - Contraseña";
        }
        description += " - ";
        ChangeHistory historial = new ChangeHistory(0, managerId, newC.getClientId(), null, null, description);
        historyDao.insertChangeHistory(historial);
    }

    public String getChanges(String name, String newString, String oldString) {
        return (!newString.equals(oldString)) ? " - " + name + ": " + oldString + " por " + newString : "";
    }
}
