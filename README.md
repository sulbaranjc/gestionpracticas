# Gestión de Prácticas - Sistema de Usuarios

📚 Proyecto de gestión de usuarios para un sistema de prácticas académicas, desarrollado en **Spring Boot** y **Thymeleaf**, con integración de seguridad mediante **Spring Security**.

## 📋 Descripción

Esta aplicación permite registrar usuarios, iniciar sesión, cerrar sesión, recuperar contraseña mediante preguntas de seguridad y gestionar roles de usuario. Existen dos roles principales:
- **USER**: Usuario estándar con permisos limitados.
- **ADMIN**: Usuario administrador con capacidad de gestionar roles de otros usuarios.

---

## ⚙️ Tecnologías Utilizadas

- **Java 23**
- **Spring Boot 3.4.3**
- **Spring Security 6.4.3**
- **Thymeleaf**
- **MySQL**
- **Jakarta Validation**
- **Lombok**
- **Hibernate JPA**

---

## 📂 Estructura del Proyecto

src/main/java/com/personal/gestionpracticas/ ├── controller # Controladores web (Registro, Login, Recuperación, Administración) ├── model # Entidades JPA (Usuario) ├── repository # Repositorios JPA ├── service # Lógica de negocio y servicios ├── config # Configuraciones de seguridad y beans └── dto # Objetos de transferencia de datos

yaml
Copiar
Editar

---

## 🖥️ Funcionalidades

- Registro de usuarios con **nombre**, **apellido**, **email**, **contraseña** y **preguntas de seguridad**.
- Inicio de sesión mediante formulario Thymeleaf.
- Cierre de sesión con botón dedicado.
- Recuperación de contraseña validando preguntas de seguridad.
- Gestión de roles desde el panel de administración (solo accesible para `ADMIN`).
- Redirección automática según rol.
- **Protección de rutas sensibles** usando Spring Security.

---

## 📑 Requisitos Previos

- Tener configurada una base de datos MySQL.
- Crear la base de datos manualmente (las tablas se crean automáticamente con JPA):
```sql
CREATE DATABASE gestionpracticas;
```
Ajustar el archivo application.properties con tus credenciales:
properties
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/gestionpracticas
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```
🚀 Ejecución
Clonar el repositorio:

```bash
git clone https://github.com/sulbaranjc/gestionpracticas.git
```
Importar el proyecto en IntelliJ IDEA u otro IDE compatible.

Configurar application.properties con tus credenciales de MySQL.

Ejecutar la aplicación:

http://localhost:8080/auth/login

📜 Licencia
Este proyecto está bajo la licencia MIT - consulta el archivo LICENSE para más detalles.

✉️ Contacto
Cualquier sugerencia o consulta puedes abrir un issue o contactarme en [tu correo o LinkedIn si deseas agregarlo].

yaml
Copiar
Editar

---

📥 **Instrucción**: Crea un archivo llamado `README.md` en la raíz de tu proyecto y copia todo el contenido anterior.

¿Quieres que te ayude a redactar un `LICENSE` tipo MIT también? &#8203;:contentReference[oaicite:0]{index=0}&#8203;





Tú dijiste:
donde coloco el archivo dentro de la carpeta del proyecto
ChatGPT Plus
¡Excelente pregunta!

El archivo README.md debes colocarlo directamente en la raíz de tu proyecto, es decir, al mismo nivel que la carpeta src, el pom.xml y otros archivos principales.

Estructura típica
Tu proyecto debería verse algo así:

css
Copiar
Editar
gestionpracticas/
├── src/
│   ├── main/
│   │   ├── java/
│   │   └── resources/
├── .mvn/
├── target/ (esto se genera al compilar)
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md  <--- Aquí es donde va
Ruta recomendada
📂 gestionpracticas/
📄 README.md (aquí mismo, al lado de pom.xml)

