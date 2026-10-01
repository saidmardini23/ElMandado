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

    // Apunta a src/main/resources/templates/pedidos/lista.html
    @GetMapping 
    public String listar(Model model){
        model.addAttribute("pedidos", servicio.listarTodos());
        return "pedidos/lista";
    }

    // Apunta a src/main/resources/templates/pedidos/formulario.html
    @GetMapping("/nuevo") 
    public String formularioNuevo(Model model) 
    { model.addAttribute("pedido", new Pedido()); 
    return "pedidos/formulario";
    }

    // Procesa el formulario enviado y redirige a la lista
    @PostMapping
    public String crear(@ModelAttribute Pedido pedido){
        servicio.crear(
        pedido.getCliente(), 
        pedido.getPlato(), 
        pedido.getPrecio()
    );
        return "redirect:/pedidos";
    }
// Marca el pedido como entregado y redirige a la lista
    @GetMapping ("/{id}/entregar")
    public String marcarEntregado(@PathVariable Long id){
        servicio.marcarEntregado(id);
        return "redirect:/pedidos";
    }

    // Renderiza src/main/resources/templates/pedidos/detalle.html }
    @GetMapping("/{id}") 
    public String obtenerDetalle(
        @PathVariable Long id,
         Model model
        ) { model.addAttribute(
            "pedido", servicio.obtenerPorId(id)
        ); 
        return "pedidos/detalle"; 
    
}
}
