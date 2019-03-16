release: psql ${DATABASE_URL} -f databases/init.sql
web: target/universal/stage/bin/server -Dhttp.port=${PORT} -Dplay.crypto.secret=${APPLICATION_SECRET}
