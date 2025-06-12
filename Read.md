# AkihabaraDB
## CONFIGURAR LA BASE DE DATOS MySQL 
Para configurar la base de datos primero hay que tener MySQL instalado y con un usuario nuevo para que la aplicación lo utilice para entrar a la base de datos, una vez tengas eso descargamos la base de datos y en MySQL con la sesión del usuario iniciada iremos a File -> Open SQL Script -> seleccionamos el sql de la base de datos y aceptar para que la base de datos se cargue dentro de tu MySQL.
para ejecutar la base de datos tenemos que darle al icono del rayo para ejecutar el código de sql y crear la base de datos.
## CONFIGURAR LA API KEY
Para poder usar la IA es necesario obtener una API key del proveedor correspondiente, para configurar la API KEY hay que regístrate y crea una cuenta en el proveedor del servicio IA, luego Generar una API KEY desde el panel de control o consola del proveedor y por ultimo hay que crea un archivo de configuracion local en la raiz del proyecto, dentro añadiremos la siguiente linea: api.key="aqui ira la API KEY que has sacado"
## COMPILAR Y EJECUTAR LA APLICACION 
Para copiar la palicacion tenemos que descargarlo y meterlo en la carpeta que sea tu entornos de trabajo, luego dentro de eclipse le daremos a File -> Open Project from File System -> directory donde seleccionaremos el proyecto java y le daremos a Finih.
Para ejecutar la app tenemos que ir a MainApp.java y dalre al icono de la flecha verde debajo de windows, asi se iniciara la app.
## FUNCIONALIDADES Y ESTRUCTURAS DE LAS CLASES
ProductoOtaku
Representa un producto otaku con atributos: id, nombre, categoría, precio y stock. Incluye constructores, getters/setters y método toString().
ProductoDAO (Interfaz)
Define los métodos necesarios para gestionar productos: agregar, obtener por id, obtener todos, actualizar, eliminar y buscar por nombre.
ProductoDAO_Impl
Implementa la interfaz ProductoDAO usando JDBC para conectar con la base de datos MySQL. Incluye el código SQL para las operaciones CRUD.
DatabaseConnection
Clase utilitaria para obtener conexiones a la base de datos con el driver JDBC.
InterfazConsola
Clase para interactuar con el usuario a través de la consola, mostrando menús, pidiendo datos, y mostrando resultados.
LlmService
Servicio que permite interactuar con una API de IA para funciones avanzadas como generar descripciones de productos o sugerir categorías.
MainApp
Clase principal que inicia el programa, muestra el menú de opciones y llama a los métodos correspondientes según la elección del usuario.