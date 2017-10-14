#!/bin/sh
if [ -z "`mysql -psirbox.cn -qfsBe "SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME='gx_facd'" 2>&1 | grep gx_facd`" ]; \
then
echo "use mysql; \
grant all privileges on gx_facd.* to gx_facd identified by 'gx_facd'; \
flush privileges; \
create database gx_facd; \
use gx_facd; \
source ./gx_facd.sql; \
" | mysql -psirbox.cn --default-character-set=utf8
fi
