# AGENTS.md - proyecto de ejemplo de CRUD realizado con con IA openclaude + gemma con SDD

## PROYECTO
Se trata de un proyecto en JAVA que expone una interface REST para hacer un CRUD de usuarios. Se utilizara SDD donde para se tiene un archivo printipal de consitituion y por cada feature se creare una spec donde se indicara QUE se quiere hacer y  la parte tecnica se especificara en un archivo de plan. Finalmente se creara un archivo de tasks con las tareas
Eres un desarrollador de software experto en codificacion, seguridad, diseño web, QA y tambien puedes resolver problemas generales accediente y consultando internet

## TECNOLOGIA
Al realizarse con SDD se definira en cada spec, dentro de su archivo plan.md

## ESTRUCTURA
```
openclaude_gemma/
├── AGENTS.md
├── opencode.json               # Configuracion especifica de opencode para este proyecto, como MCP
├── docs/
│   └── constitution.md         # Reglas innegociables para todo el proyecto 
├── specs/
│   └── 001-FEATURE-001/        # Featura numero 1
│       ├── spec.md             # Especificacion de lo que se va hacer con requisitos funcionales
│       ├── plan.md             # Especificaion tecnica, con tecnologicas, versiones
│       └── tasks.md            # Plan detallado de lo que se va a realizar
│   └── 002-FEATURE-001/        # Featura numero 2
│       ├── spec.md             # Especificacion de lo que se va hacer con requisitos funcionales
│       ├── plan.md             # Especificaion tecnica, con tecnologicas, versiones
│       └── tasks.md            # Plan detallado de lo que se va a realizar
├── .agents/
│   └── skills/
│   └── skill-001/              # skill 1
│       ├── SKILL.md            # Especificacion de la skill1

```
El agente empezara leyendo el archivo docs/consitution.md y luego todas las specs
