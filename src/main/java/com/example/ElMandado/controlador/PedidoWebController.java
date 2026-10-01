package com.example.ElMandado.controlador;

import com.example.ElMandado.modelo.Pedido;
import com.example.ElMandado.servicio.PedidoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller 
@RequestMapping ("/pedidos")
public class PedidoWebController {

    private final PedidoService servicio;

    public PedidoWebController(PedidoService servicio){
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(Model model){
        model.addAttribute("pedidos", servicio.listarTodos());
        return "pedidos";
    }

    @GetMapping ("/nuevo")
    public String formularioNuevo(Model model){
        model.addAttribute("pedido", new Pedido());
        return "nuevo/formulario";
    }

    @PostMapping
    public String crear(@ModelAttribute Pedido pedido){
        servicio.crear(pedido.getCliente(), 
        pedido.getPlato(), 
        pedido.getPrecio());
        return "redirect:/pedidos";
    }

    @GetMapping ("/{id}/entregar")
    public String marcarEntregado(@PathVariable Long id){
        servicio.marcarEntregado(id);
        return "redirect:/pedidos";
    }
    
}
