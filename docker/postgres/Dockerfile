FROM postgres:12.3

COPY initdb.sql /docker-entrypoint-initdb.d

COPY create_backup.sh /usr/local/bin/
COPY remove_backups.sh /usr/local/bin/

RUN chmod a+x /usr/local/bin/create_backup.sh
RUN chmod a+x /usr/local/bin/remove_backups.sh