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
