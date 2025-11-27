# 📚 Biblioteca EBAC 2.0
Se potencializa la version de la aplicacion integrando patrones de diseño,
persistencia de la informacion y validacion con pruebas automatizadas utilizando JUnit5

## Insumos necesarios para la ejecucion
Se integra en la seccion de resources el script base de la DB para realizar pruebas y jugar con la aplicacion.
# 📚 Biblioteca EBAC

Aplicación de consola en **Java 21** para gestionar una biblioteca digital, desarrollada como proyecto académico para **EBAC**.  
Permite administrar **usuarios**, **autores** y **libros**, además de realizar **préstamos** y **devoluciones**, con persistencia en memoria.

---

## 🧩 Características

- 📖 Crear, listar y buscar **libros**
- 👩‍💼 Crear y registrar **usuarios**
- ✍️ Crear **autores** y asignarles libros
- 📚 Registrar libros y usuarios en la biblioteca
- 🔄 Prestar y devolver libros
- 🔍 Buscar libros por título, autor o rango de años
- 🧾 Ver información completa de los objetos creados
- 🧰 Menú interactivo por consola

---

## ⚙️ Requisitos

- **Java JDK 21** o superior
- **Maven 3.9+**
- **Docker** (opcional, para empaquetar la app)

---

## 🚀 Ejecución local (sin Docker)

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/Yava1938/Ebac-library.git
   cd EbacLibrary
   
## Comandos de compilacion y ejecución
    mvn clean package -DskipTests
    java -jar target/EbacLibrary-1.0-SNAPSHOT.jar

## Ejecución de test
    mvn test

## Ejecución con Docker

- ### Construcción de la imagen
        docker run -it --rm biblioteca-ebac:1.0.0
  - ### Ejecucion de contenedor
          docker run -it --rm biblioteca-ebac:1.0.0

        mvn clean package -DskipTests
        docker build -t biblioteca-ebac:1.0.0 .
        docker run -it --rm biblioteca-ebac:1.0.0

## Autor
👨‍💻 Rodrigo Yael Morales Vázquez
📧 yavac@ebac-estudiantes.org.mx
📘 Proyecto académico para EBAC - Escuela Británica de Artes Creativas y Tecnología￼