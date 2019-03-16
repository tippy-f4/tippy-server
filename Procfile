release: psql ${DATABASE_URL} -f databases/init.sql
/universal/stage/bin/server -Dhttp.port=${PORT} -Dplay.crypto.secret=${APPLICATION_SECRET}
