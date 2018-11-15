package net.aooms.core.property;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * application.yml 框架配置文件映射
 * Created by 风象南(yuboon) on 2018-11-07
 */
@Component
@ConfigurationProperties(prefix = "aooms")
public class PropertyAooms {

    private Global global = new Global();
    private Rest rest = new Rest();
    private Upload upload = new Upload();

    public static class Global {

        private boolean devMode = true;
        private boolean logEnable = false;

        public boolean isDevMode() {
            return devMode;
        }

        public void setDevMode(boolean devMode) {
            this.devMode = devMode;
        }

        public boolean isLogEnable() {
            return logEnable;
        }

        public void setLogEnable(boolean logEnable) {
            this.logEnable = logEnable;
        }
    }

    public static class Rest {
        private String mode = "local";
        private int localPort = 8080;

        public String getMode() {
            return mode;
        }

        public boolean isLocal() {
            return "local".equals(mode);
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public int getLocalPort() {
            return localPort;
        }

        public void setLocalPort(int localPort) {
            this.localPort = localPort;
        }
    }

    public static class Upload {
        private String mode = "local";
        private Local local = new Local();
        private Minio minio = new Minio();

        public static class Local {
            private String tempPath;
            private String rootPath;
            private String nameStrategy;

            public String getTempPath() {
                return tempPath;
            }

            public void setTempPath(String tempPath) {
                this.tempPath = tempPath;
            }

            public String getRootPath() {
                return rootPath;
            }

            public void setRootPath(String rootPath) {
                this.rootPath = rootPath;
            }

            public String getNameStrategy() {
                return nameStrategy;
            }

            public void setNameStrategy(String nameStrategy) {
                this.nameStrategy = nameStrategy;
            }
        }

        public static class Minio {
            private String rootPath;
            private String host = "127.0.0.1";
            private int port = 9000;

            public String getRootPath() {
                return rootPath;
            }

            public void setRootPath(String rootPath) {
                this.rootPath = rootPath;
            }

            public String getHost() {
                return host;
            }

            public void setHost(String host) {
                this.host = host;
            }

            public int getPort() {
                return port;
            }

            public void setPort(int port) {
                this.port = port;
            }
        }

        public String getMode() {
            return mode;
        }

        public void setMode(String mode) {
            this.mode = mode;
        }

        public Local getLocal() {
            return local;
        }

        public Minio getMinio() {
            return minio;
        }

    }

    public Global getGlobal() {
        return global;
    }

    public Rest getRest() {
        return rest;
    }

    public Upload getUpload() {
        return upload;
    }


}