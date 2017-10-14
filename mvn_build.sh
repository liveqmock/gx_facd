#!/bin/sh
export JAVA_HOME=/sir/modules/jdk1.7.0_80
PROJECT_PATH=/sir/modules/gx_bhf/svn/platform/spring-shiro-training
TOMCAT_PATH=/sir/modules/apache-tomcat-7.0.78
TOMCAT_WEBAPPS_PATH=$TOMCAT_PATH/webapps
TOMCAT_BIN=$TOMCAT_PATH/bin
WAR_PATH=./target/facd-platform.war
echo "goto project path.. "$PROJECT_PATH
cd $PROJECT_PATH
echo "svn update"
svn up
echo "mvn clean package"
mvn clean package
echo "cp war to tomcat"
cp $WAR_PATH $TOMCAT_WEBAPPS_PATH
echo "restart tomcat"
# Kill tomcat
for i in `ps afx | grep tomcat | grep java | awk '{print $1}'`; do kill -9 $i ; done
#$TOMCAT_BIN/shutdown.sh
rm -rf $TOMCAT_WEBAPPS_PATH/facd-platform
$TOMCAT_BIN/startup.sh