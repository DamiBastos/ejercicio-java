# ejercicio-java

Pasos a seguir para probar la aplicación:

Ejecutar el archivo main "EjercicioJrJavaApplicacion.java"

Rutas para consultar en Postman:

GET - http://localhost:8080/producto

Traerá una lista de productos ordenados por precio

GET - http://localhost:8080/producto/{id}

Traerá un producto especifico

Post - http://localhost:8080/producto

body ejemplo:

{
  "nombre" : "Zapatillas", (string)
  "descripcion" : "Zapatos negros talle 41", (string)
  "precio": 10.00, (double)
  "cantidad": 2 (int)
}

Creará un producto 

Put - http://localhost:8080/producto/{id}

body ejemplo:

{
  "nombre" : "Zapatillas", (string)
  "descripcion" : "Zapatos negros talle 41", (string)
  "precio": 10.00, (double)
  "cantidad": 2 (int)
}

Editará un producto 

DELETE - http://localhost:8080/producto/{id}

Eliminará un producto especifico
