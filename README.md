# Nombre: README.md

## Objetivo:
Poner todos los prompts utilizados

### Refinamiento de la constitucion
Dime se introducirias mas elementos en el archivo consitution.md deacuerdo a los estandares de SDD. Recuerda que se trata de un proyecto backend en java que expone un api rest para gestion de varias tablas, como usuarios, departamentos, etc. Tambien se desarrollara posteriormente specs para tener un frontend web. No hagas ningun cambio en el archivo, solo planteame modificaciones
### features
#### CRUD de usuarios
Crea la primera spec, que va a ser la gestion de usuarios. Se trata de un backend REST que permite realizar el CRUD de usuarios. Cada usuarios tiene como campos un identificador numerico unico, nombre, apellidos, fecha de nacimiento y el hash de una contraseña. Todos son obligatorias. En caso de que la base de datos no este creada se creara una con la tabla de usarios. En el plan tecnico se elegira la base de datos, la cual funcionara tanto con docker como con kubernetes y se crearan los scripts necesarios para window y linux para poder arrancar la base de datos, pararla o reiniciarla. Dime que ficheros crearias y su contenido. De momento solo crea la spec.md, no el plan tecnico
