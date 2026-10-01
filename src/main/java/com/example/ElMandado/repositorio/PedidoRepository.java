package com.example.ElMandado.repositorio;

import com.example.ElMandado.modelo.Pedido;
import java.util.List;
import java.util.Optional;


public interface PedidoRepository {

    List<Pedido> buscarTodos();

    Optional<Pedido> buscarPorId(Long id);

    Pedido guardar (Pedido pedido);
}