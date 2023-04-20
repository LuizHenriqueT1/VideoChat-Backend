package videochat.com.br.videochat.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "videochat.com.br.videochat")
@Data
public class JwtCofigurationProperties {

    private JWT jwt = new JWT();

    @Data
    public class JWT {
        private String secretKey;
        private AccessToken accessToken = new AccessToken();
        private RefreshToken refreshToken = new RefreshToken();

        @Data
        public class AccessToken {
            /**
             * validity of access-token in minutes
             */
            private Integer validity;
        }

        @Data
        public class RefreshToken {
            /**
             * validity of refresh-token in days
             */
            private Integer validity;
        }
    }
}
