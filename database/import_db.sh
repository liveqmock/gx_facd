#!/bin/sh
path=$1
/usr/bin/mysql -ugx_facd -pgx_facd gx_facd < $path
