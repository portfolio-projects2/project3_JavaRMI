FROM mysql:8.0

COPY db.sql /docker-entrypoint-initdb.d/
COPY dbcertificate/* /etc/mysql/ssl/

RUN chown -R mysql:mysql /etc/mysql/ssl/

ENV MYSQL_ROOT_USER=root 
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_ALLOW_EMPTY_PASSWORD=no
ENV MYSQL_REQUIRE_SECURE_TRANSPORT=1

RUN echo "[mysqld]" >> /etc/mysql/conf.d/ssl.cnf
RUN echo "skip-name-resolve=0" >> /etc/mysql/conf.d/ssl.cnf
RUN echo "ssl-ca=/etc/mysql/ssl/group10.crt" >> /etc/mysql/conf.d/ssl.cnf
RUN echo "ssl-cert=/etc/mysql/ssl/group10.crt" >> /etc/mysql/conf.d/ssl.cnf
RUN echo "ssl-key=/etc/mysql/ssl/localhost.key" >> /etc/mysql/conf.d/ssl.cnf
RUN echo "require_secure_transport=ON" >> /etc/mysql/conf.d/ssl.cnf

CMD ["mysqld"]


