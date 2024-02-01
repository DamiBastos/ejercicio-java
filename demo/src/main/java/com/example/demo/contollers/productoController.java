package com.example.demo.contollers;

import com.example.demo.dto.ProductoDTO;
import com.example.demo.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("producto")
public class productoController {
    @Autowired ProductoService productoService;
    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductoDTO save(@RequestBody ProductoDTO productoDTO){
        return productoService.crearProducto(productoDTO);
    }

    @GetMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductoDTO> buscarPorId(@PathVariable Long id) {
        try {
            ResponseEntity<ProductoDTO> responseEntity = productoService.buscarPorId(id);

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                // Si el producto se encuentra, devolver el DTO con código de estado 200 (OK)
                return ResponseEntity.ok(responseEntity.getBody());
            } else {
                // Manejar otros códigos de estado si es necesario
                return ResponseEntity.status(responseEntity.getStatusCode()).body(null);
            }
        } catch (RuntimeException e) {
            // Si hay una excepción, devolver un código de estado 404 (NOT FOUND)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDTO> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO productoDTO) {
        ResponseEntity<ProductoDTO> response = productoService.actualizarProducto(id, productoDTO);

        if (response.getStatusCode() == HttpStatus.OK) {
            // Si el producto fue actualizado exitosamente, devolver el DTO del producto actualizado
            return ResponseEntity.ok(response.getBody());
        } else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
            // Si el producto no se encontró, devolver un código de estado 404 (NOT FOUND)
            return ResponseEntity.notFound().build();
        } else {
            // Manejar otros códigos de estado si es necesario
            return ResponseEntity.status(response.getStatusCode()).build();
        }
    }
    @GetMapping(value = "/nombre/{nombre}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductoDTO>> buscarPorNombre(@PathVariable String nombre) {
        try {
            List<ProductoDTO> productos = productoService.buscarPorNombre(nombre);

            if (!productos.isEmpty()) {
                // Si se encuentran productos, devolver la lista de DTO con código de estado 200 (OK)
                return ResponseEntity.ok(productos);
            } else {
                // Si no se encuentran productos con el nombre especificado, devolver un código de estado 404 (NOT FOUND)
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            // Manejar otras excepciones si es necesario
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        ResponseEntity<String> response = productoService.eliminar(id);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }
    @GetMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ProductoDTO> listaDeProductos(){
        return this.productoService.listarProductos();
    }
}
