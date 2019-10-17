package co.femago.assignment.config;

import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:configprops.properties")
@ConfigurationProperties("allowed-clients")
public class MapPropertyConfig {

	@Getter
	@Setter
	private Map<String, Content> eurekaClients;

	@Getter
	@Setter
	public static class Content {

		private String key;
		private String path;

		@Override
		public String toString() {
			return "{" +
				"key='" + key + '\'' +
				", path='" + path + '\'' +
				'}';
		}
	}
}
