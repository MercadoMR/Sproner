/* Execute using: 
    psql -U dba -W -d dvdrental -a -f structure.sql
    or
    \i \path\structure.sql 
    inside psql  
    Check https://gist.github.com/MercadoMR/d0c394e57c06eda16cb4e1e634fab9bf for more information

CREATE TABLESPACE sproner_music 
LOCATION 'C:\pdbs\sproner';
*/


CREATE DATABASE musiclibrary
WITH 
ENCODING='UTF8' 
OWNER='dba'
TABLESPACE='sproner_music';

GRANT ALL PRIVILEGES
ON DATABASE musiclibrary
TO dba;

CREATE SCHEMA music;
CREATE SCHEMA users;
