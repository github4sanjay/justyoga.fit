# create databases
CREATE DATABASE IF NOT EXISTS `place`;
CREATE DATABASE IF NOT EXISTS `user`;
CREATE DATABASE IF NOT EXISTS `profile`;
CREATE DATABASE IF NOT EXISTS `search`;
CREATE DATABASE IF NOT EXISTS `review`;
CREATE DATABASE IF NOT EXISTS `collection`;
CREATE DATABASE IF NOT EXISTS `blog`;
CREATE DATABASE IF NOT EXISTS `bookmark`;
CREATE DATABASE IF NOT EXISTS `blog`;

# create root user and grant rights
#CREATE USER 'root'@'localhost' IDENTIFIED with mysql_native_password BY 'local';
#GRANT ALL PRIVILEGES ON *.* TO 'root'@'%';
#FLUSH PRIVILEGES;