#!/bin/bash
DB_NAME=AMTDatabaseREST
DB_TECHNICAL_USER=AMTTechnicalUser
DB_TECHNICAL_USER_PASSWORD=drez

cd '/cygdrive/c/wamp/bin/mysql/mysql5.6.12/bin'

./mysql.exe -u root << EOF

DROP DATABASE IF EXISTS $DB_NAME;	
CREATE DATABASE $DB_NAME;

GRANT USAGE ON *.* TO '$DB_TECHNICAL_USER'@'localhost';
GRANT USAGE ON *.* TO '$DB_TECHNICAL_USER'@'%';

DROP USER '$DB_TECHNICAL_USER'@'localhost';	
DROP USER '$DB_TECHNICAL_USER'@'%';

CREATE USER '$DB_TECHNICAL_USER'@'localhost' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';	
CREATE USER '$DB_TECHNICAL_USER'@'%' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';

GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'localhost';	  
GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'%';

EOF

cd '/cygdrive/c/Program Files/glassfish-4.1/glassfish/bin'

DOMAIN_NAME=domainAMTREST
JDBC_JNDI_NAME=jdbc/${DB_NAME}
JDBC_CONNECTION_POOL_NAME=${DB_NAME}_pool

./asadmin stop-domain $DOMAIN_NAME	  
./asadmin delete-domain $DOMAIN_NAME
./asadmin create-domain --nopassword=true $DOMAIN_NAME

cp 'mysql-connector-java-5.1.33.jar' ../domains/$DOMAIN_NAME/lib

./asadmin start-domain $DOMAIN_NAME 

./asadmin delete-jdbc-resource $JDBC_JNDI_NAME
./asadmin delete-jdbc-connection-pool $JDBC_CONNECTION_POOL_NAME

# Let's create a connection pool...
./asadmin create-jdbc-connection-pool \
  --restype=javax.sql.XADataSource \
  --datasourceclassname=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource \
  --property User=$DB_TECHNICAL_USER:Password=$DB_TECHNICAL_USER_PASSWORD:serverName=localhost:portNumber=3306:databaseName=$DB_NAME $JDBC_CONNECTION_POOL_NAME

# ... check that it is properly setup...
./asadmin ping-connection-pool $JDBC_CONNECTION_POOL_NAME

# ... and finally create a jdbc resource mapped to our pool
./asadmin create-jdbc-resource --connectionpoolid $JDBC_CONNECTION_POOL_NAME $JDBC_JNDI_NAME

