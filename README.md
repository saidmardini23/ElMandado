## Arquitectura del Sistema

La aplicacion aplica una estricta separacion de responsabilidades con flujo unidireccional de dependencias:

1. Capa de Controladores (com.example.ElMandado.controlador):
  * PedidoController (@RestController): Expone endpoints RESTful para consumo en formato JSON (/api/pedidos).
  * PedidoWebController (@Controller): Maneja la navegacion web y renderizado de vistas dinamicas aplicando el patron POST-Redirect-GET.
2. Capa de Servicios (com.example.ElMandado.servicio):
  * PedidoService e PedidoServiceImpl: Encapsulan la logica de negocio y reglas de validacion (validacion de nombres de clientes no vacios y precios positivos).
3. Capa de Repositorios (com.example.ElMandado.repositorio):
  * PedidoRepository e PedidoRepositoryMemoria: Abstraen el acceso y almacenamiento persistente en memoria.
4. Capa de Modelo (com.example.ElMandado.modelo):
  * Pedido: Entidad de dominio encapsulada (id, cliente, plato, precio, entregado).

  * ## Caracteristicas Principales

* Listado Dinamico: Visualizacion tabular de pedidos con badges de estado (Entregado / Pendiente) y formato de moneda.
* Registro de Pedidos: Formulario interactivo con data binding bidireccional (th:object y th:field).
* Vista de Detalle: Ficha individual por identificador unico (/pedidos/{id}).
* Modularidad Web: Reutilizacion de fragmentos HTML (head, menu, pie) mediante th:fragment y th:replace.
* Estilos Independientes: Hoja de estilos externa (static/css/estilos.css) vinculada mediante th:href="@{/css/estilos.css}".
* Patron POST-Redirect-GET: Previene envios duplicados de formularios al recargar la pagina.

* ## Instrucciones de Ejecucion

1. Clonar el repositorio:

```
git clone https://github.com/TU_USUARIO/FoodExpress.git
cd FoodExpress

```

1. Ejecutar la aplicacion con Maven:

```
./mvnw spring-boot:run

```

1. Acceso a la aplicacion:
  * Interfaz Web: http://localhost:8080/pedidos
  * API REST: http://localhost:8080/api/pedidos
### 1\. Método `listar(Model model)`

```
@GetMapping
public String listar(Model model) {
    model.addAttribute("pedidos", servicio.listarTodos());
    return "pedidos/lista";
}

```

* **Mapeo (** **@GetMapping** **):** Atiende las peticiones HTTP de tipo GET a la ruta base de la clase (`/pedidos`)[1][2].
* **Uso del** **Model** **:** Funciona como un contenedor donde se depositan los datos requeridos por la plantilla[3][4]. Consulta la lista completa mediante la capa de servicio (`servicio.listarTodos()`) y la asigna bajo la clave `"pedidos"`[4][5].
* **Retorno de Vista:** Al estar anotado con `@Controller`, devolver `"pedidos/lista"` indica a Spring Boot que busque y procese la plantilla ubicada en `src/main/resources/templates/pedidos/lista.html`[3][6].

---

### 2\. Método `formularioNuevo(Model model)`

```
@GetMapping("/nuevo")
public String formularioNuevo(Model model) {
    model.addAttribute("pedido", new Pedido());
    return "pedidos/formulario";
}

```

* **Mapeo (** **@GetMapping("/nuevo")** **):** Responde a la ruta `/pedidos/nuevo` cuando el usuario solicita registrar un nuevo pedido[1][7].
* **Objeto Inicial Vacío:** Coloca una instancia limpia (`new Pedido()`) en el `Model` asociada a la clave `"pedido"`[8][9].
* **Importancia para Thymeleaf:** Es indispensable proporcionar este objeto inicial para que la directiva `th:object="${pedido}"` en `formulario.html` tenga una estructura a la cual atarse (*data binding*); de lo contrario, la vista falla[8].

---

### 3\. Método `crear(@ModelAttribute Pedido pedido)`

```
@PostMapping
public String crear(@ModelAttribute Pedido pedido) {
    servicio.crear(
        pedido.getCliente(), 
        pedido.getPlato(), 
        pedido.getPrecio()
    );
    return "redirect:/pedidos";
}

```

* **Mapeo (** **@PostMapping** **):** Procesa la información enviada mediante la acción del formulario HTML por método POST hacia `/pedidos`[2][8].
* **Anotación** **@ModelAttribute** **:** Le indica a Spring que recoja automáticamente los datos que vienen de los campos HTML y construya el objeto `Pedido` haciendo coincidir sus nombres[8][9].
* **Delegación al Servicio:** Envía la información recibida a la capa de negocio (`servicio.crear(...)`) para ejecutar las validaciones y el guardado[5][11].
* **Patrón POST-Redirect-GET:** Retorna `"redirect:/pedidos"`, indicando al navegador que realice una nueva petición GET a la lista general[5][12]. Esto previene que el pedido se vuelva a registrar por accidente si el usuario recarga la página[12].

---

### 4\. Método `obtenerDetalle(@PathVariable Long id, Model model)`

```
@GetMapping("/{id}")
public String obtenerDetalle(@PathVariable Long id, Model model) {
    model.addAttribute("pedido", servicio.obtenerPorId(id));
    return "pedidos/detalle";
}

```

* **Mapeo con Variable de Ruta (** **@GetMapping("/{id}")** **):** Escucha peticiones a URLs dinámicas como `/pedidos/1` o `/pedidos/4`[7][13].
* **Uso de** **@PathVariable** **:** Extrae el parámetro numérico `{id}` desde la misma dirección web y lo inyecta en la variable de Java `id`[13].
* **Carga por Identificador:** Consulta en el servicio el pedido correspondiente a ese ID específico (`servicio.obtenerPorId(id)`) y lo pasa al `Model`[5][13].
* **Retorno de Vista:** Renderiza la ficha informativa individual en `templates/pedidos/detalle.html`[13].

---

### 5\. Método `marcarEntregado(@PathVariable Long id)`

```
@GetMapping("/{id}/entregar")
public String marcarEntregado(@PathVariable Long id) {
    servicio.marcarEntregado(id);
    return "redirect:/pedidos";
}

```

* **Mapeo (** **@GetMapping("/{id}/entregar")** **):** Atiende la acción de cambio de estado activada desde un botón o enlace (por ejemplo `/pedidos/2/entregar`)[1][13].
* **Extracción de Parámetro (** **@PathVariable** **):** Captura el ID de la orden que se quiere marcar como completada[13].
* **Acción de Negocio:** Llama a `servicio.marcarEntregado(id)`, permitiendo que el servicio actualice el atributo de entrega en la lista o repositorio[5][14].
* **Redirección:** Devuelve `"redirect:/pedidos"` para recargar de inmediato la tabla general mostrando el cambio de estado actualizado
