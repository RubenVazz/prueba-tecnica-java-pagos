Este proyecto implementa un sistema automatizado para registrar y acreditar pagos de tarjetas de forma transaccional y segura.

##  Entregables
1. **Código Fuente:** Java 21 (Sin frameworks).
2. **Base de Datos:** Script SQL y Procedimiento Almacenado PL/SQL.
3. **Documentación:** (./Analisis_Solucion.pdf).

##  Requisitos
* Java JDK 21.
* Oracle Database (XE o Free).
* Driver 'ojdbc11.jar' (Incluido en la carpeta '/lib').

##  Guía de Instalacion

### 1. Base de Datos
Ejecute el script 'database.sql' ubicado en la raiz del proyecto en su cliente Oracle (SQL Plus o SQL Developer). Esto creara:
* Tablas: 'CUENTA' y 'OPERACION'.
* Procedimiento: 'actualizar_saldo'.
* Datos de prueba iniciales.

### 2. Configuracion
Verificar credenciales en el archivpo:
'src/com/procesadora/resources/config.properties'
(Configurado por defecto pasra Oracle XE local)


### 3. Compilacion (Manual)
Ejecutar desde la carpeta raiz del poryecto:
powershell
javac -cp "lib/ojdbc11.jar" -d bin (Get-ChildItem -Recurse *.java).FullName


### 4. Ejecucion
Una vez compilado ejecutar 
java -cp "bin;lib/ojdbc11.jar" com.procesadora.Main