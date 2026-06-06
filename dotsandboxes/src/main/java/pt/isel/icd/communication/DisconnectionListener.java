package pt.isel.icd.communication;

import java.util.UUID;

/**
 * Implementado por controladores que querem reagir a desligacao de um socket
 * (ex.: limpar fila de emparelhamento e terminar jogos a decorrer).
 */
public interface DisconnectionListener {
    void onDisconnected(UUID socketId);
}
