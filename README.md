# Programmierprojekt: Ein webbasierter Routenplaner

Zusammenarbeit von:

- Ahmed Ebrahim Aldekal E-Mail: st159613@stud.uni-stuttgart.de
- Xin Pang E-Mail: st145113@stud.uni-stuttgart.de
- Jonathan Duncan Email: st157437@stud.uni-stuttgart.de

## Instalstion
Führe der Folgende Befehl in dem Verzeichnes wo die pom.xml Datei ist
```
mvn clean package
```
Danach
```
java -cp target/programmierprojekt-0.0.1-SNAPSHOT.jar backend.phase.one.programmierprojekt.Main
```

## Graph-Datei
Die Graph-Datei germany.fmi muss im Verzeichnis $HOME gespeichert sein, also /home/<benutzername>/germany.fmi.
Dabei wird davon ausgegangen, dass der Code nicht von Root, sondern von einem regulären Benutzer ausgeführt wird.
