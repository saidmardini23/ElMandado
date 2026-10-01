package com.example.ElMandado.servicio;

import com.example.ElMandado.modelo.Pedido;
import java.util.List;

public interface PedidoService {

    List<Pedido> listarTodos();
    Pedido obtenerPorId(Long id);
    Pedido crear(String cliente, String plato, double precio);
    void marcarEntregado(Long id);   

}

