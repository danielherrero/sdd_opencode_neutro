# Nombre: README.md

## Objetivo:
Poner todos los prompts utilizados

### Refinamiento de la constitucion
Dime se introducirias mas elementos en el archivo consitution.md deacuerdo a los estandares de SDD. Recuerda que se trata de un proyecto backend en java que expone un api rest para gestion de varias tablas, como usuarios, departamentos, etc. Tambien se desarrollara posteriormente specs para tener un frontend web. No hagas ningun cambio en el archivo, solo planteame modificaciones
### features
#### CRUD de usuarios
Crea la primera spec, que va a ser la gestion de usuarios. Se trata de un backend REST que permite realizar el CRUD de usuarios. Cada usuarios tiene como campos un identificador numerico unico, nombre, apellidos, fecha de nacimiento y el hash de una contraseña. Todos son obligatorias. En caso de que la base de datos no este creada se creara una con la tabla de usarios. En el plan tecnico se elegira la base de datos, la cual funcionara tanto con docker como con kubernetes y se crearan los scripts necesarios para window y linux para poder arrancar la base de datos, pararla o reiniciarla. Dime que ficheros crearias y su contenido. De momento solo crea la spec.md, no el plan tecnico

#### CRUD de departamentos
Vamos a crear la segunda spec, que va a ser la gestion de departamentos. Un departamento es parecido a un usuario. Tiene un identificador asignado por la base de datos, tienen un nombre, descripcion, fecha de creacion. Solo el nombre es obligatorio. La fecha de creacion si no se manda se cogera la fecha del sistema. El nombre y descripcion tambien se hara un trim aunque si se permite numeros y caracteres speciales. En la base de datos se creara su propia tabla de departamentos en caso de no existir. El api rest sera igual que el de los usuarios (alta, modificacion, etc). Crea los mismos requisitos funcionales de la gestion de usuarios a gestion de departamentos y crea el plan.md y las tasks.md y los tests necesarios

#### CRUD de la relacion usuario-empresa
Vamos a crear la spec 003-USER-DEPARMENT-MANAGEMENT en su correspondiente rama. Igualmente se tratara de un CRUD. Se podran dar de alta asociaciones entre usuarios y departamentos y tendra su propia tabla sql. Cada registro tendra ID usuario, ID departamento y fecha de creacion, que se insertara automaticamente. No se podran dar de alta registros o modificar actuales siempre que exista el ID de usuario y el de departamento. Crea los requisitos funcionales que apliquen, asi como el plan.md y tasks.md. Pero primero hace commit y push de las otras ramas

#### WEB angular SPA
Se quiere crear una web SPA con la ultima version de angular. Dispondra de un menu lateral izquierdo para gestionar usuarios, departamentos y relaciones entre usuarios y departamentos. De este modo en la seccion de usuarios permitira hacer el alta de un usuario, modificacion, detalle de usuario, listar todos, etc. De la misma forma en la seccion de departamentos permitira hacer todas las opciones. De igual forma en la seccion de relaciones permitira realizar su gestion. En este caso, en vez de insertar manualmente los ID de usuario e ID de departamento se mostrara un combo con los usuarios y departamentos disponibles. Crea la spec, plan y tasks

